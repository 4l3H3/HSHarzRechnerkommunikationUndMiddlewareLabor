#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>
#include <stdbool.h>

int main(){
  pid_t pid;
  printf("Programmstart\n");
  for(int i = 0; i < 10; i++) {
    if(fork() == 0){
      printf("[son] pid %d from [parent] pid %d\n", getpid(), getppid());
      exit(0);
    }
  }
  return 0;
}
