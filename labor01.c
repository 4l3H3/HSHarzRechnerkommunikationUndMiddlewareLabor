#include<stdio.h>
#include<unistd.h>
#include<signal.h>

void sig_handler(int_signum){
  printf(int_signum)
}
int main(){
  signal(SIGINT,SIG_IGN); // Register signal handler for ignoring the signal

  for(int i=1;i<10;i++){    //Infinite loop
    printf("%d : Inside main function\n",i);
    sleep(1);  // Delay for 1 second
  }
  return 0;
}
