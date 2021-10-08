#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main(){
  pid_t pid;
  switch (pid = fork()){
    case -1: printf("Fehler bei fork()\n");
    case 0: printf("Ich bin das Kind");
    default: printf("Ich bin der Vater");
  }
  return 0;
}
