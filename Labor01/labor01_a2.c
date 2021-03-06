#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>
#include <stdbool.h>

int main(){
  pid_t pid;
  printf("Programmstart\n");
  switch (pid = fork()){
    case -1: printf("Fehler bei fork()\n"); break;
    case 0: printf("Ich bin das Kind mit der pid=%d und habe den Vater mit pid=%d\n", getpid(), getppid());
	    while(true);
    default: printf("Ich bin der Vater mit pid=%d und habe das Kind mit der pid=%d\n", getpid(), pid);
             wait();
             printf("Kindprozess wurde beendet"); break;
  }
  return 0;
}
