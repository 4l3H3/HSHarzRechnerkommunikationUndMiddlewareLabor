#include <stdio.h>
#include <sys/types.h>
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

int main(){
  forkedMemory();
  return 0;
}

