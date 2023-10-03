//Define the struct
#include "header.h"

//Libary stdio.h to be able to print
#include <stdio.h>

//Libary unistd.h to use the class sleep
#include <unistd.h>

//Define functions to make random number and sensor
#include "generate_random.h"
#include "sens_humd_atm.h"
#include "pcg32_random_r.h"
#include "getRandom.h"

//Declarar variaveis uteis
int cont_atm;
unsigned char ult_atm=0;
int erro_atm=0;
unsigned short frequencias_atm[100];
unsigned short *ptr_atm=frequencias_atm;
Sensor s_atm;
	
//Metodo para preencher o array de leituras e verificar a quantidade de erros do sensor
void random_atm(){
	if(ult_atm < s_atm.min_limit || ult_atm > s_atm.max_limit){ 		//caso seja verificado um erro
		cont_atm++;
		erro_atm++;
		ult_atm=50;
	}else{																	//caso nao haja erro
		cont_atm=0;		
	}
	s_atm.readings_size++;
	*(ptr_atm)=ult_atm;
	ptr_atm++;
}

//Sensor que representa a humidade atmosferica
Sensor humidade_atmosferica(unsigned char ult_pluvio){
	int num;
	//defenir os atributos da estrutura que representa o sensor
	s_atm.id=4001;
	s_atm.sensor_type=4;
	s_atm.max_limit=100;
	s_atm.min_limit=0;
	s_atm.frequency=0;
	s_atm.readings_size=0;
	s_atm.readings=ptr_atm;
	
	printf("Humidade Atmosferica: Qual a frequencia de leituras?\n");
	scanf("%lud",&s_atm.frequency);
	
	printf("Humidade Atmosferica: Qual o numero de leituras?\n");
	scanf("%d",&num);

	//recolher as leituras do sensor
	for(int i = 0; i < num; i++){
		ult_atm = sens_humd_atm(ult_atm, ult_pluvio, getRandom());
		random_atm();
		sleep(s_atm.frequency);
						
		while((cont_atm % 5 )==0 && cont_atm!=0){
			s_atm.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_atm.readings+a)=0;
				}
				
				for(int x=0;x<5;x++){
					ult_atm = sens_humd_atm(ult_atm,ult_pluvio, getRandom());
					random_atm();
					sleep(s_atm.frequency);
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
			ult_atm = sens_humd_atm(ult_atm, ult_pluvio, getRandom());
			random_atm();
			sleep(s_atm.frequency);
							
			while((cont_atm % 5 )==0 && cont_atm!=0){
				s_atm.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_atm.readings+a)=0;
				}
				
				for(int x=0;x<5;x++){
					ult_atm = sens_humd_atm(ult_atm,ult_pluvio, getRandom());
					random_atm();
					sleep(s_atm.frequency);
				}	
			}
		}
	}	
		printf("\nForam encontrados %d erros no sensor \n",erro_atm);
		return s_atm;
}
