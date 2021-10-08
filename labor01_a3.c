#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>
#include <signal.h>
#include <stdbool.h>

int ergebnis = 0;
// Diese Funktion soll das Ergbenis über 10 Kindprozesse insgesamt auf 1000000
// hochzählen. Das Ergebnis am Ende ist 0. Da kein Fehler ausgeworfen wird scheint
// jedes Kind eine eigene ergebnis Variable zu besitzen und hochzuzählen.
// Das Ergebnis vom Parent bleibt dabei unverändert.
int forkedMemory(){
  pid_t pid;
  printf("Programmstart\n");
  for(int i = 0; i < 10; i++){
    switch(pid = fork()){
     case -1: printf("Fehler bei fork()\n"); break;
     case 0: 
         printf("[son] pid %d from [parent] pid %d\n",getpid(),getppid());
         for(int i = 0; i < 9999; i++) ergebnis++;
         exit(0);
     default: wait(); break;
    }
  }
  printf("Ergebnis: %d\n",ergebnis);
  return 0;
}

int shm_id;
int *shm_ptr;

int sharedMemory(){
  pid_t pid;
  printf("Programmstart\n");
  for(int i = 0; i < 10; i++){
    switch(pid = fork()){
     case -1: printf("Fehler bei fork()\n"); break;
     case 0: 
         printf("[son] pid %d from [parent] pid %d\n",getpid(),getppid());
	 shm_id = shmget(IPC_PRIVATE, 4*sizeof(int), 0666);
	 if (shm_id < 0) {
           printf("*** shmget error (child) ***\n");
           exit(1);
         }

         shm_ptr = (int *) shmat(shm_id, NULL, 0);
         if ((int) shm_ptr == -1) { /* attach */
           printf("*** shmat error (child) ***\n");
           exit(1);
         }
         for(int i = 0; i < 9999; i++) *shm_ptr+=1;
         exit(0);
     default:
         shm_id = shmget(IPC_PRIVATE, 4*sizeof(int), IPC_CREAT | 0666);
         if (shm_id < 0) {
           printf("*** shmget error (father) ***\n");
           exit(1);
         }

         shm_ptr = (int *) shmat(shm_id, NULL, 0);  /* attach */
         if ((int) shm_ptr == -1) {
           printf("*** shmat error (father) ***\n");
           exit(1);
         } 
         wait(); 
         break;
    }
   printf("Ergebnis: %d", *shm_ptr);
  }
  return 0;
}

int main(){
  sharedMemory();
  return 0;
}

