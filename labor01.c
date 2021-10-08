#include<stdio.h>
#include<unistd.h>
#include<signal.h>

void sig_handler(int_signum){
  printf(int_signum);
  switch(int_signum){
    case SIGUSR1: printf("Signal SIGUSR1 erhalten")
    case SIGUSR2: signal(int_signum, SIG_IGN);
    case SIGINT: printf("ich lasse mich nicht unterbrechen")
    case SIGALARM: printf("ALARM")
    case SIGTERM: printf("ich lasse mich nicht terminieren")
    case SIGKILL: printf("ich lasse mich nicht terminieren")
    default: printf("Signal nicht erkannt")
  }
}
int main(){
  signal(SIGINT,sig_handler); // Register signal handler for ignoring the signal

  for(int i=1;i<10;i++){    //Infinite loop
    printf("%d : Inside main function\n",i);
    sleep(1);  // Delay for 1 second
  }
  return 0;
}
