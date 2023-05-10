/******************************************** INIT SERVER ********************************************/          

//______LIBRARIES______________________________________________________________________________________

#define BUFFER_STRLEN 150 
#define LISTENER_QUEUE_STRLEN 50
#define INCOMING_MSG_STRLEN 70
#define OUTCOMING_MSG_STRLEN 82
#define GRAPHICS_CHAT_WIDTH 82

//______COSTANTS______________________________________________________________________________________

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <errno.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <stdbool.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include <time.h>
#include <fcntl.h>
#include <ctype.h>
#include <stdbool.h>
#include "client.h"

//______GLOBAL VARIABLES_______________________________________________________________________________

pthread_mutex_t mutexClientInfo = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutexDatabase = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutexLogs = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutexCursor = PTHREAD_MUTEX_INITIALIZER;

LpClientInfo listClientInfo = NULL;
int totalConnections = 0;
int connectedClients = 0;
int totalInfections = 0;
int listenerSocket;
int listenerPort;
int logs;
int database;

//______FUNCTIONS DECLARATION__________________________________________________________________________

int createSocket();
void *listenerClient(void *arg);
void *connectionRequestsManagement(void *arg);
bool signIn(LpClientInfo clientInfo);
bool login(LpClientInfo clientInfo);
int initFile(void);
void sendMsg(LpClientInfo clientInfo, char *outcomingMsg);
void sendMsgToAll(LpClientInfo, char *);
void signalHandler(int);

int main()
{

	pthread_t tidConnectionRequestsManagement;
	srand(time(NULL));

	signal(SIGPIPE, SIG_IGN);
	//signal(SIGALRM, SIG_IGN);

	if (initFile())
	{
		printf("\n<!> Errore durante l'inizializzazione del Server 1.\n\n");

		return 1;
	}

	if (createSocket())
	{
		return 1;
	}

	signal(SIGSTOP, signalHandler);    // SIGSTOP = il processo viene messo in pausa 
	signal(SIGINT, signalHandler);    //  SIGINT = il processo viene interrotto 


	/* handler sigstop e sigint */

         /**
           * Dopo che la creazione della socket è andata a buon fine, il server avvia un thread che esegue il metodo 
           * connectionRequestsManagment(), quest’ultimo accetta la richiesta con il metodo accept() 
           * e crea un processo figlio a cui affidare la gestione delle richieste ricevute dal client.
           *
         */

	pthread_create(&tidConnectionRequestsManagement, NULL, connectionRequestsManagement, NULL);
	printf("\nIn ascolto ... \n\n\n");

	while (true) {}

	return 0;

}

//______FUNCTION FOR MANAGMENT CLIENT___________________________________________________

int createSocket()
{

	int bytesReaded;
	char buffer[BUFFER_STRLEN];
	int port;
	bool error;
	struct sockaddr_in serverAddress;
	memset(&serverAddress, '0', sizeof(serverAddress));
	serverAddress.sin_family = PF_INET;
	serverAddress.sin_addr.s_addr = htonl(INADDR_ANY);

	//inserire porta
	printf("\n » Inserire la porta del server: ");
	fflush(stdout);

	do {

		error = false;
		if ((bytesReaded = read(STDIN_FILENO, buffer, BUFFER_STRLEN)) == -1)
		{
			perror("\n <!> Errore read");

			return 1;
		}

		buffer[bytesReaded] = '\0';

		/* Verifico se la porta fornita è valida */

		for (int i = 0; i < strlen(buffer) - 1; i++)
		{
			if (buffer[i]<'0' || buffer[i] > '9') //controllo che la porta non abbiamo meno di 0 o più di nove caratteri
			{
				error = true;
				break;
			}
		}

		if (error)
		{

			printf("\n<!> Porta non valida, caratteri non ammessi - riprovare: ");

			fflush(stdout);
		}
		else
		{
			if ((port = atoi(buffer)) == 0 || !(port >= 0 && port <= 65535)) //controllo che la porta non sia 0 o non compresa tra 0 e 65535
			{

				printf("\n<!> Porta non valida, out of range - riprovare: ");

				fflush(stdout);
				error = true;
			}
			else
			{
				listenerPort = port;

				printf("\n    » Porta %d inserita con successo.\n", port);

				fflush(stdout);

				serverAddress.sin_port = htons(atoi(buffer));  //si converte la porta nel formato indicato al formato di rete
			}

			if (!error)
			{
				listenerSocket = socket(PF_INET, SOCK_STREAM, 0);

				/* Assegno un'indirizzo alla socket del server */
				if (bind(listenerSocket, (struct sockaddr *) &serverAddress, sizeof(serverAddress)) == -1)
				{

					perror("\n<!> Errore bind");
					puts("");

					sleep(1);
					error = true;
				}
				printf("bind riuscito");

				/* Rimango in ascolto di richieste di connessione da parte di client */
				if (listen(listenerSocket, LISTENER_QUEUE_STRLEN) == -1)
				{

					perror("\n<!> Errore listen");
					puts("");

					sleep(1);
					return 1;
				}
			}
		}
	} while (error != false);

	return 0;

}

 /**
   * @param clientinfo: puntatore della struttura che contiene informazioni del client.
   *
   * @return: puntatore a void.
   *
   * La funzione disconnectionManagement gestisce le operazioni di disconnessione del client.
   *
   */

void disconnectionManagement(LpClientInfo clientInfo)
{
	time_t timestamp = time(NULL);
	char logsBuffer[BUFFER_STRLEN];
	pthread_t tidHandler;


	sprintf(logsBuffer, " > Il client %s si è disconnesso dal Server - %s", clientInfo->clientAddressIPv4, ctime(&timestamp));
	printf("\n > Il client %s si è disconnesso dal Server - %s", clientInfo->clientAddressIPv4, ctime(&timestamp));

	pthread_mutex_lock(&mutexLogs);

	if (write(logs, logsBuffer, strlen(logsBuffer)) == -1)
	{
		printf("Errore durante la scrittura nel file di logs.");
	}

	pthread_mutex_unlock(&mutexLogs);

	pthread_mutex_lock(&mutexCursor);
	connectedClients -= 1;
	pthread_mutex_unlock(&mutexCursor);

	pthread_mutex_lock(&mutexClientInfo);

	/* Chiudo la socket di comunicazione */
	tidHandler = clientInfo->tidHandler;
	close(clientInfo->clientSocket);

	/* Elimino dalla lista dei client, il client disconnesso */
	deleteClientInfo(&listClientInfo, &clientInfo);
	pthread_mutex_unlock(&mutexClientInfo);

	/* elimino il thread */
	pthread_exit(tidHandler);

}

 /**
   * @param clientinfo : arg ->  puntatore della struttura che contiene informazioni
   * del client connesso alla socket del server.
   *
   * @return: puntatore a void.
   *
   * La funzione listenerClient si occupa di gestire le azioni di ascolto
   * dei messaggi da parte del client, e l'invio dei comandi utilizzabili
   * al client.
   *
   */

void *listenerClient(void *arg)
{

	LpClientInfo clientInfo = (LpClientInfo) arg;
	char incomingMsg[INCOMING_MSG_STRLEN];                /* Buffer messaggio in entrata         */
	int bytesReaded;                                      /* Numero di bytes letti dalla read    */

	bool exited = false;

	/* Invio il messaggio di benvenuto */
	sendMsg(clientInfo, "$Server: Benvenuto/a! Inserisci i comandi.\n");
	memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);

	while (exited != true && (bytesReaded = read(clientInfo->clientSocket, incomingMsg, INCOMING_MSG_STRLEN)) > 0)
	{
		incomingMsg[bytesReaded] = '\0';

		switch (clientInfo->status)
		{
			case CLTINF_GUEST:

				if (strncmp(incomingMsg, "signin", 6) == 0)
				{
					sendMsg(clientInfo, "$Server: sei entrato in registrazione. \n");
					exited = signIn(clientInfo);
					if (exited)
					{
						exited = false;
					}
				}
				else if (strncmp(incomingMsg, "login", 5) == 0)
				{
					sendMsg(clientInfo, "$Server: sei entrato in login. \n");
					exited = login(clientInfo);
					if (exited)
					{
						exited = false;
					}
				}
				else if (strncmp(incomingMsg, "exit", 4) == 0)
				{
					exited = true;
				}
				else
				{
					sendMsg(clientInfo, "$Server: Comando non disponibile, riprova!");
				}
				break;

			case CLTINF_LOGGED:

				if (strncmp(incomingMsg, "drinks", 6) == 0)
				{
					sendMsg(clientInfo, "$Server: sei entrato in drink. \n");
				
				}
				else if (strncmp(incomingMsg, "cart", 4) == 0)
				{
					sendMsg(clientInfo, "$Server: sei entrato in cart. \n");
					
				}
				else if (strncmp(incomingMsg, "exit", 4) == 0)
				{
					exited = true;
				}
				else
				{
					sendMsg(clientInfo, "$Server: Comando non disponibile, riprova!");
				}

				break;
		}
		memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);
	}
	if (exited)
	{
		sendMsg(clientInfo, "$Server: Torna presto! Disconnessione in corso... \n");
	}
	disconnectionManagement(clientInfo);
}

 /**
   * @param arg : NULL.
   *
   * @return: puntatore a void.
   *
   * La funzione connectionRequestsManagement gestisce le operazione di
   * connessione alla socket del server da parte dei client.
   *
   */

void *connectionRequestsManagement(void *arg)
{

	int connectSocket;
	pthread_t tidListenerClient;
	struct sockaddr_in clientAddress;
	char logsBuffer[BUFFER_STRLEN];
	LpClientInfo clientInfo;
	char clientAddressIPv4[INET_ADDRSTRLEN];
	unsigned int clientAddressSize = sizeof(clientAddress);

	while (true)
	{

		/* Accetto le richieste di connessione da parte dei client */
		if ((connectSocket = accept(listenerSocket, (struct sockaddr *) &clientAddress, &clientAddressSize)) == -1)
		{
			printf("<!> Impossibile accettare richiesta di connessione");
		}
		else
		{
			inet_ntop(AF_INET, &clientAddress, clientAddressIPv4, INET_ADDRSTRLEN); //convertire l'indirizzo di rete AF_INET in una stringa di caratteri

			clientInfo = NULL;
		}

		/* Creo una struttura clientInfo che conterrà tutte le informazioni riguardo al client */
		if ((clientInfo = newClientInfo(connectSocket, clientAddressIPv4, "")) == NULL)
		{
			printf("<!> Impossibile allocare memoria, %s disconnesso.", clientAddressIPv4);
		}
		else
		{

			/* Aggiorno le strutture che contengono informazioni sullo status attuale */
			pthread_mutex_lock(&mutexClientInfo);
			insertClientInfo(&listClientInfo, clientInfo);
			pthread_mutex_unlock(&mutexClientInfo);

			/* Creo un thread che gestirà l'accesso del client al Server */
			pthread_create(&tidListenerClient, NULL, listenerClient, clientInfo);
			clientInfo->tidHandler = tidListenerClient;

			printf("Nuova connessione accettata:[Client: %s] - %s", clientAddressIPv4, ctime(&(clientInfo->timestamp)));
			pthread_mutex_lock(&mutexCursor);
			totalConnections += 1;
			printf(" \u25cf Total CN: %7.2d", totalConnections);
			connectedClients += 1;
			printf(" \u25cf Connected: %6.2d\n", connectedClients);
			pthread_mutex_unlock(&mutexCursor);

			//scrittura nel file di log

			pthread_mutex_lock(&mutexLogs);
			sprintf(logsBuffer, " > Nuova connessione accettata:[Client: %s] - %s", clientAddressIPv4, ctime(&(clientInfo->timestamp)));
			if (write(logs, logsBuffer, strlen(logsBuffer)) == -1)
			{
				printf("Errore durante la scrittura nel file di logs.");
			}
			pthread_mutex_unlock(&mutexLogs);
		}
	}
}

 /**
   * @param clientinfo: Nodo ClientInfo con informazioni sul client.
   *
   * @return: 1 in caso di errore, 0 altrimenti.
   *
   * La funzione signin gestisce il signin da parte
   * del client al Server. Verifica inoltre che l'username del client
   * sia univoco.
   *
   */

bool signIn(LpClientInfo clientInfo)
{

	char username[CLTINF_USERNAME_STRLEN];	     /* Buffer per username del client             */
	char password[CLTINF_PASSWORD_STRLEN];	     /* Buffer per password del client             */
	char incomingMsg[INCOMING_MSG_STRLEN];	     /* Buffer per messaggi in entrata             */
	char record[GRAPHICS_CHAT_WIDTH];            /* Buffer per la lettura di un record del DB  */
	char msg[GRAPHICS_CHAT_WIDTH];
	char character[1];                           /* Array per la lettura dei dati dal DB       */
	char *usernameDB;                            /* Username estratto dal DB                   */
	bool passed = false;                         /* Flag che indica il superamento di una fase */
	int recordIndex;                             /* Indice di posizione del buffer record      */
	int bytesReaded;                             /* Numero di bytes letti dalla read           */
	time_t timestamp;
	char logsBuffer[BUFFER_STRLEN];

	memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);
	//sendMsg(clientInfo, "$Server: Inserisci l'username - massimo 10 caratteri.");

	do {
		passed = true;

		/* Leggo l'username inserito dal client */
		/* Verifico se l'utente ha inserito il comando di uscita */
		/* Controllo se lo username supera la lunghezza di 10 caratteri stabiliti */
		/* Controllo se nell'username siano presenti caratteri di spazio */
		if ((bytesReaded = read(clientInfo->clientSocket, incomingMsg, INCOMING_MSG_STRLEN)) <= 0)
		{
			return false;
		}
		incomingMsg[bytesReaded] = '\0';

		if (!strcmp(incomingMsg, "exit"))
		{
			return true;
		}
		else
		{

			if (strlen(incomingMsg) > CLTINF_USERNAME_STRLEN)
			{
				sendMsg(clientInfo, "se1\n");	//username troppo lungo -[massimo 10 caratteri]
				passed = false;
			}
			else
			{

				for (int i = 0; i < strlen(incomingMsg); i++)
				{
					if (incomingMsg[i] == ' ')
					{
						sendMsg(clientInfo, "se2\n");	//gli spazi non sono consentiti
						passed = false;
						break;
					}
				}
				/* Nel caso in cui l'username fosse ancora valido proseguo con le verifiche */
				if (passed != false)
				{

					pthread_mutex_lock(&mutexDatabase);
					lseek(database, 0, SEEK_SET);
					memset(record, '\0', GRAPHICS_CHAT_WIDTH);
					recordIndex = 0;
					//sendMsg(clientInfo, "1");

					/* Ciclo sul file database per verificare che lo username non esista già */
					/* La lettura avviene un carattere alla volta fintanto non viene trovato un newline oppure \0 */
					do {

						if ((bytesReaded = read(database, character, 1)) > 0)
						{

							/* Il carattere è diverso da un newline oppure un fine stringa? */
							if (character[0] != '\n')
							{

								record[recordIndex++] = character[0];
							}
							else
							{

								record[recordIndex] = '\0';
								usernameDB = strtok(record, " ");

								/* Controllo se l'username esista gia nel database, se sì, stampo un errore e riclico il do */
								if (!strcmp(usernameDB, incomingMsg))
								{
									sendMsg(clientInfo, "se3\n");	//username non disponibile
									passed = false;
									break;
								}
								else
								{ 
                                                                        /* Ripulisco le strutture di appoggio */
									memset(record, '\0', GRAPHICS_CHAT_WIDTH);
									recordIndex = 0;
								}
							}
						}
					} while (bytesReaded != 0);
					//sendMsg(clientInfo, "sonofuori");

					/* Verifico l'eventuale presenza di errori */
					if (bytesReaded == -1)
					{
						sprintf(msg, "<!> Impossibile leggere dati dal Database.");

						sleep(2);
						/* La terminazione è necessariamente bruta a causa dell'impossibilità di catturare    */
						/* l'exit status del thread chiamante signin (numero di utenti non deterministico).   */
						/* Chiaramente una soluzione sarebbe potuta essere una lista di tid, tuttavia         */
						/* sarebbe stata un'implementazione "inutilmente" costosa, considerando che in certi  */
						/* casi la terminazione è obbligatoria.                                               */
						pthread_kill(pthread_self(), SIGTERM);
					}
					else if (passed != false)
					{
						strcpy(username, incomingMsg);
					}
					pthread_mutex_unlock(&mutexDatabase);
				}
			}
			memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);
		}
	} while (passed != true);

	sendMsg(clientInfo, "$Server: In attesa della password \n");
	memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);

	do {
		passed = true;
		/* Leggo la password inserita dal client */
		if ((bytesReaded = read(clientInfo->clientSocket, incomingMsg, INCOMING_MSG_STRLEN)) <= 0)
		{
			return false;
		}
		incomingMsg[bytesReaded] = '\0';

		/* Verifico se l'utente ha inserito il comando di uscita */
		if (!strcmp(incomingMsg, "exit"))
		{
			return true;
		}
		else
		{ 
                        /* Controllo se la password supera la lunghezza di 10 caratteri stabiliti  */
			if (strlen(incomingMsg) > CLTINF_PASSWORD_STRLEN)
			{
				sendMsg(clientInfo, "se4\n");	//pass troppo lunga
				passed = false;
			}
			else
			{
                                /* Controllo che nella password non siano presenti caratteri di spazio */
				for (int i = 0; i < strlen(incomingMsg); i++)
				{
					if (incomingMsg[i] == ' ')
					{
						sendMsg(clientInfo, "se5\n");	//pass con spazi
						passed = false;
						break;
					}
				}
				if (passed != false)
				{
					strcpy(password, incomingMsg);
				}
			}
			memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);
		}
	} while (passed != true);

	memset(record, '\0', GRAPHICS_CHAT_WIDTH);

	/* Concateno username e password in un solo buffer */
	sprintf(record, "%s %s\n", username, password);

	pthread_mutex_lock(&mutexDatabase);

	/* Scrivo i dati di registazione del client nel database */
	if (write(database, record, strlen(record)) == -1)
	{
		printf("<!> Impossibile scrivere dati nel Database.");

		sleep(2);
		/* La terminazione è necessariamente bruta a causa dell'impossibilità di catturare    */
		/* l'exit status del thread chiamante signin (numero di utenti non deterministico)    */
		/* Chiaramente una soluzione sarebbe potuta essere una lista di tid, tuttavia         */
		/* sarebbe stata un'implementazione "inutilmente" costosa, considerando che in certi  */
		/* casi la terminazione è obbligatoria.                                               */
		pthread_kill(pthread_self(), SIGTERM);
	}
	pthread_mutex_unlock(&mutexDatabase);

	/* Registrazione effettuata con successo */
	timestamp = time(NULL);
	pthread_mutex_lock(&mutexLogs);
	sprintf(logsBuffer, " > Registrazione del client %s avvenuta con successo - %s", clientInfo->clientAddressIPv4, ctime(&(clientInfo->timestamp)));
	if (write(logs, logsBuffer, strlen(logsBuffer)) == -1)
	{
		printf("Errore durante la scrittura nel file di logs.");
	}
	pthread_mutex_unlock(&mutexLogs);
	printf("Registrazione del client %s avvenuta con successo.", clientInfo->clientAddressIPv4);

	sendMsg(clientInfo, "seok\n");	//notifico la registrazione avvenuta con successo


	return false;
}


 /**
   * @param clientinfo: Nodo ClientInfo con informazioni sul client.
   *
   * @return: true in caso di errore, false altrimenti.
   *
   * La funzione login gestisce il login da parte
   * del client al Server.
   *
   */

bool login(LpClientInfo clientInfo)
{

	bool passed = false;                         /* Flag che indica il superamento di una fase */
	char incomingMsg[INCOMING_MSG_STRLEN];       /* Buffer per messaggi in entrata             */
	char record[GRAPHICS_CHAT_WIDTH];            /* Buffer per la lettura di un record del DB  */
	char username[CLTINF_USERNAME_STRLEN];       /* Buffer per username del client             */
	char password[CLTINF_PASSWORD_STRLEN];       /* Buffer per password del client             */
	char incomingRecord[GRAPHICS_CHAT_WIDTH];    /* Username + Password inseriti dall'utente   */
	int recordIndex;                             /* Indice di posizione del buffer record      */
	char character[1];                           /* Array per la lettura dei dati dal DB       */
	int bytesReaded;                             /* Numero di bytes letti dalla read           */

	bool alreadyLogged;

	char logsBuffer[BUFFER_STRLEN];

	do {
		//sendMsg(clientInfo, "$Server: In attesa dell'username. \n");
		memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);

		do { 	sendMsg(clientInfo, "$Server: In attesa dell'username. \n");
			passed = true;

			/* Leggo l'username inserito dal client */
			if ((bytesReaded = read(clientInfo->clientSocket, incomingMsg, INCOMING_MSG_STRLEN)) <= 0)
			{
				return false;
			}
			incomingMsg[bytesReaded] = '\0';

			if (!strcmp(incomingMsg, "exit"))
			{
				return true;
			}
			else
			{
				if (strlen(incomingMsg) > CLTINF_USERNAME_STRLEN)
				{
					sendMsg(clientInfo, "se1\n");	//errore username troppo lungo
					passed = false;
				}
				else
				{
					for (int i = 0; i < strlen(incomingMsg); i++)
					{
						if (incomingMsg[i] == ' ')
						{
							sendMsg(clientInfo, "se2\n");	//errore spazi non sono consentiti
							passed = false;
							break;
						}
					}
					if (passed != false)
					{
						strcpy(username, incomingMsg);
					}
				}
				memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);
			}
		} while (passed != true);

		//sendMsg(clientInfo, "$Server: In attesa della password. \n");
		memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);

		do { 	sendMsg(clientInfo, "$Server: In attesa della password. \n");
			//puts("devo leggere la password");
			passed = true;

			/* Leggo la password inserita dal client */
			if ((bytesReaded = read(clientInfo->clientSocket, incomingMsg, INCOMING_MSG_STRLEN)) <= 0)
			{
				return false;
			}
			incomingMsg[bytesReaded] = '\0';

			/* Verifico se l'utente ha inserito il comando di uscita */
			if (!strcmp(incomingMsg, "exit"))
			{
				return true;
			}
			else
			{
                                /* Controllo se la password supera la lunghezza di 10 caratteri stabiliti  */
				if (strlen(incomingMsg) > CLTINF_PASSWORD_STRLEN)
				{
					sendMsg(clientInfo, "se3\n");	//errore lunghezza password
					passed = false;
				}
				else
				{ 
                                        /* Controllo che nella password non siano presenti caratteri di spazio */
					for (int i = 0; i < strlen(incomingMsg); i++)
					{
						if (incomingMsg[i] == ' ')
						{
							sendMsg(clientInfo, "se4\n");	//errore password contenente spazi
							passed = false;
							break;
						}
					}
					if (passed != false)
					{
						strcpy(password, incomingMsg);
					}
				}
				memset(incomingMsg, '\0', INCOMING_MSG_STRLEN);
			}
		} while (passed != true);

		memset(incomingRecord, '\0', CLTINF_USERNAME_STRLEN *2);
		sprintf(incomingRecord, "%s %s", username, password);
		pthread_mutex_lock(&mutexDatabase);
		lseek(database, 0, SEEK_SET);
		memset(record, '\0', GRAPHICS_CHAT_WIDTH);
		recordIndex = 0;

		/* Ciclo sul file database per verificare che lo username non esista già */
		do { 
                        /* La lettura avviene un carattere alla volta fintanto non viene trovato un newline */
			alreadyLogged = false;
			if ((bytesReaded = read(database, character, 1)) > 0)
			{
                                /* Il carattere è diverso da un newline? */
				if (character[0] != '\n')
				{
					record[recordIndex++] = character[0];
				}
				else
				{
					if (!strcmp(record, incomingRecord))
					{
						pthread_mutex_lock(&mutexClientInfo);
						LpClientInfo tmp = listClientInfo;
						while (tmp != NULL && alreadyLogged == false)
						{
							if ((tmp->status == CLTINF_LOGGED) && !strcmp(tmp->username, username) && tmp != clientInfo)
							{
								sendMsg(clientInfo, "selog\n");	// errore di connessione al server
								sleep(1);
								alreadyLogged = true;
							}
							tmp = tmp->nextClientInfo;
						}
						pthread_mutex_unlock(&mutexClientInfo);

						if (alreadyLogged == false)
						{
							clientInfo->status = CLTINF_LOGGED;
							strcpy(clientInfo->username, username);

							pthread_mutex_lock(&mutexLogs);
							sprintf(logsBuffer, " > Login effettuato con successo:[Client: %s - %s] - %s", clientInfo->clientAddressIPv4, clientInfo->username, ctime(&(clientInfo->timestamp)));
							if (write(logs, logsBuffer, strlen(logsBuffer)) == -1)
							{
								printf("Errore durante la scrittura nel file di logs.");
							}
							pthread_mutex_unlock(&mutexLogs);
							sendMsg(clientInfo, "seok\n");	//login effettuato con successo
							sleep(1);
						}
						break;
					}
					else
					{ 
                                                /* Ripulisco le strutture di appoggio */
						memset(record, '\0', GRAPHICS_CHAT_WIDTH);
						recordIndex = 0;
					}
				}
			}
		} while (bytesReaded != 0);
		pthread_mutex_unlock(&mutexDatabase);

		if (clientInfo->status != CLTINF_LOGGED && alreadyLogged != true)
		{
			sendMsg(clientInfo, "seerror\n");	//login fallito
			sleep(1);
		}
	} while (clientInfo->status != CLTINF_LOGGED);

	return false;

}

 /**
   * @param void.
   *
   * @return: return 1 in caso di errore e 0 altrimenti.
   *
   * La funzione initFile apre i file di logs, database.
   *
  */

int initFile(void)
{

	/* Apro il file di log */
	if ((logs = open("logs.txt", O_RDWR | O_CREAT | O_TRUNC, S_IRWXU)) == -1)
	{
		printf("%d", logs);
		return 1;
	}

	/* Apro il file di database */
	if ((database = open("database.txt", O_RDWR | O_CREAT | O_APPEND, S_IRWXU)) == -1)
	{

		return 1;
	}

	return 0;

}

 /**
   * @param clientInfo: puntatore della struttura che contiene informazioni
   * del client.
   * @param outcomingMsg: Messaggio da scrivere da scrivere al client.
   *
   * @return: void.
   *
   * La funzione sendMsg inoltra il messaggio catturato
   * dal listenerTextField al client.
   *
   */

void sendMsg(LpClientInfo clientInfo, char *outcomingMsg)
{

	char buffer[OUTCOMING_MSG_STRLEN];

	memset(buffer, '\0', OUTCOMING_MSG_STRLEN);
	strcpy(buffer, outcomingMsg);
	pthread_mutex_lock(&(clientInfo->mutexSocket));

	write(clientInfo->clientSocket, buffer, OUTCOMING_MSG_STRLEN);

	pthread_mutex_unlock(&(clientInfo->mutexSocket));
}

 /**
   * @param signal: segnale catturato
   *
   * @return void.
   *
   * La funzione signalHandler cattura i segnali in questione
   * e ne detta il comportamento al loro verificarsi.
   *
   */

void signalHandler(int signal)
{

	LpClientInfo tmp;

	switch (signal)
	{
		case SIGINT:

			printf("Disconnessione del server in corso!");

			close(listenerSocket);
			close(logs);
			close(database);

			tmp = listClientInfo;
			while (tmp != NULL)
			{
				close(tmp->clientSocket);
				tmp = tmp->nextClientInfo;
			}
			sleep(2);

			raise(SIGTERM);    /* SIGTERM = terminazione programma */
			break;

		case SIGSTOP:

			printf("Disconnessione del server in corso!");

			close(listenerSocket);
			close(logs);
			close(database);

			tmp = listClientInfo;
			while (tmp != NULL)
			{
				close(tmp->clientSocket);
				tmp = tmp->nextClientInfo;
			}
			sleep(2);

			raise(SIGTERM);  /* SIGTERM = terminazione programma */
			break;
	}
}
/******************************************** END SERVER ********************************************/
