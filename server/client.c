/******************************************** INIT CLTINF ********************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include "client.h"


   /**
    * Nella funzione newClientInfo viene creato un nodo ClientInfo con le informazioni fornite dal client
    */
   
   LpClientInfo newClientInfo(int clientSocket, char clientAddressIPv4[], char username[]){
     LpClientInfo newClientInfo = (LpClientInfo)malloc(sizeof(ClientInfo));
     if(newClientInfo != NULL){
       newClientInfo->clientSocket = clientSocket;
       strcpy(newClientInfo->clientAddressIPv4, clientAddressIPv4);
       strcpy(newClientInfo->username, username);
       newClientInfo->timestamp = time(NULL);
       newClientInfo->status = CLTINF_GUEST;
       newClientInfo->prevClientInfo = NULL;
       newClientInfo->nextClientInfo = NULL;
       pthread_mutex_init(&(newClientInfo->mutexSocket), NULL);
     }
     return newClientInfo;
   }



   /**
    * La funzione insertClientInfo permette di inserire un nuovo nodo ClientInfo dalla testa della lista
    */
   
   void insertClientInfo(LpClientInfo* listClientInfo, LpClientInfo newClientInfo){
     if(newClientInfo != NULL){
       newClientInfo->nextClientInfo = *listClientInfo;
       if(*listClientInfo != NULL){
         newClientInfo->prevClientInfo = (*listClientInfo)->prevClientInfo;
         (*listClientInfo)->prevClientInfo = newClientInfo;
         if(newClientInfo->prevClientInfo != NULL){
           newClientInfo->prevClientInfo->nextClientInfo = newClientInfo;
         }
       }
       *listClientInfo = newClientInfo;
     }
   }


    /**
    * La funzione deleteClientInfo permette di elimina un nodo ClientInfo specificato in ingresso dalla lista
    */

   void deleteClientInfo(LpClientInfo* listClientInfo, LpClientInfo* targetedClientInfo){
     if(targetedClientInfo != NULL){
       LpClientInfo tmp = *targetedClientInfo;
       *targetedClientInfo = (*targetedClientInfo)->nextClientInfo;
       if(tmp->prevClientInfo != NULL){
         tmp->prevClientInfo->nextClientInfo = *targetedClientInfo;
       }
       if(*targetedClientInfo != NULL){
         (*targetedClientInfo)->prevClientInfo = tmp->prevClientInfo;
       }
       if(*listClientInfo == tmp){
         *listClientInfo = *targetedClientInfo;
       }
       free(tmp);
     }
   }

/******************************************** END CLTINF ********************************************/
