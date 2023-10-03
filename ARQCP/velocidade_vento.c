//Define the struct
#include "header.h"

//Libary stdio.h to be able to print
#include <stdio.h>

//Libary unistd.h to use the class sleep
#include <unistd.h>

//Define functions to make random number and sensor
#include "generate_random.h"
#include "sens_velc_vento.h"
#include "pcg32_random_r.h"
#include "getRandom.h"

//Declarar variaveis uteis
int cont_vel;
char ult_vel=0;
int erro_vel=0;	
unsigned short frequencias_vel[100];
unsigned short *ptr_vel=frequencias_vel;
Sensor s_vel;

//Metodo para preencher o array de leituras e verificar a quantidade de erros do sensor
void random_vel(){
    if(ult_vel < s_vel.min_limit || ult_vel > s_vel.max_limit){ 		//caso seja verificado um erro
        cont_vel++;
        erro_vel++;
    }else{																	//caso nao haja erro
        cont_vel=0;
    }
    s_vel.readings_size++;
	*(ptr_vel)=ult_vel;
	ptr_vel++;
}

//Sensor que representa a velocidade do vento
Sensor velocidade_vento(){
	int num;
	//defenir os atributos da estrutura que representa o sensor
    s_vel.id=2001;
    s_vel.sensor_type=2;
    s_vel.max_limit=300;
    s_vel.min_limit=0;
    s_vel.frequency=0;
    s_vel.readings_size=0;
    s_vel.readings = ptr_vel;
    
    printf("Velocidade do Vento: Qual a frequencia de leituras?\n");
	scanf("%lud",&s_vel.frequency);
	
	printf("Velocidade do Vento: Qual o numero de leituras?\n");
	scanf("%d",&num);

    //recolher as leituras do sensor
    for(int i = 0; i < num; i++){
        ult_vel = sens_velc_vento(ult_vel, getRandom());
        random_vel();
        sleep(s_vel.frequency);

        while((cont_vel % 5 )==0 && cont_vel!=0){
			s_vel.readings_size-=5;
			for(int a=5;a>0;a--){
				*(s_vel.readings+a)=0;
			}
            for(int x=0;x<5;x++){
                ult_vel = sens_velc_vento(ult_vel, getRandom());
                random_vel();
                sleep(s_vel.frequency);
            }
        }
    }
    //perguntar ao utilizador se deseja acrescentar leituras
    int res;
    do{
		printf("Deseja alterar o numero de leituras do sensor? \n");
		printf("Sim: 1\n");
		printf("Não: 2\n");
		scanf("%d",&res);
	}while(res!= 1 && res!= 2);
	
	int num1;

	if(res==1){
		printf("Deseja acrescentar quantas leituras? \n");
		scanf("%d",&num1);
		
		for(int i = num; i < num+num1; i++){
        ult_vel = sens_velc_vento(ult_vel, getRandom());
        random_vel();
        sleep(s_vel.frequency);

        while((cont_vel % 5 )==0 && cont_vel!=0){
			s_vel.readings_size-=5;
			for(int a=5;a>0;a--){
				*(s_vel.readings+a)=0;
			}
            for(int x=0;x<5;x++){
                ult_vel = sens_velc_vento(ult_vel, getRandom());
                random_vel();
                sleep(s_vel.frequency);
            }
        }
    }
}
	printf("\nForam encontrados %d erros no sensor \n",erro_vel);		
    return s_vel;
}
