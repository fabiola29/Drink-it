/******************************* INIT CLTINF HEADER ******************************/

#ifndef CLIENT_H_
#define CLIENT_H_


#endif /* CLIENT_H_ */
#include <netinet/in.h>
#include <time.h>
#include <stdbool.h>
#include <pthread.h>


#define CLTINF_USERNAME_STRLEN   10
#define CLTINF_PASSWORD_STRLEN   10

#define CLTINF_GUEST              1
#define CLTINF_LOGGED             3


  /**
    * @attribute tidHandler: TID del thread principale che gestisce un determinato client.
    * @attribute clientSocket: Socket descriptor della socket associata ad un determinato client.
    * @attribute clientAddressIPv4: IPv4 del client.
    * @attribute username: Username del client.
    * @attribute timestamp: Ora della connessione
    * @attribute status: LOGGED, GUEST.
    * @attribute prevClientInfo: Puntatore al nodo clientInfo precedente.
    * @attribute nextClientInfo: Puntatore al nodo clientInfo successivo.
    * @attribute mutexSocket: Mutex associato alla socket.
    */

struct ClientInfo
{

    pthread_t tidHandler;
    int clientSocket;
    char clientAddressIPv4[INET_ADDRSTRLEN];
    char username[CLTINF_USERNAME_STRLEN];
    time_t timestamp;
    int status;
    pthread_mutex_t mutexSocket;

    //lista doppiamente puntata
    struct ClientInfo* prevClientInfo;
    struct ClientInfo* nextClientInfo;
  };

  typedef struct ClientInfo ClientInfo;
  typedef ClientInfo* LpClientInfo;



  /**
    * La funzione newClientInfo alloca un nuovo nodo clientInfo con gli attributi passati
    * in ingresso.
    */

  LpClientInfo newClientInfo(int, char[], char[]);



  /**
    *La funzione insertClientInfo inserisce un nuovo nodo clientInfo in testa alla lista
    * listClientInfo.
    */

  void insertClientInfo(LpClientInfo*, LpClientInfo);



  /**
    * La funzione deleteClientInfo elimina il nodo specificato da targetedClientInfo dalla
    * lista in cui risiede lo stesso.
    */

  void deleteClientInfo(LpClientInfo*, LpClientInfo*);

/******************************* END CLTINF HEADER ******************************/

