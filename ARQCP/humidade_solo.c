//Define the struct
#include "header.h"

//Libary stdio.h to be able to print
#include <stdio.h>

//Libary unistd.h to use the class sleep
#include <unistd.h>

//Define functions to make random number and sensor
#include "generate_random.h"
#include "sens_humd_solo.h"
#include "pcg32_random_r.h"
#include "getRandom.h"

//Declarar variaveis uteis
int cont_solo;
int erro_solo=0;
char ult_solo=0;
unsigned short frequencias_solo[100];
unsigned short *ptr_solo=frequencias_solo;
Sensor s_solo;
	
//Metodo para preencher o array de leituras e verificar a quantidade de erros do sensor	
void random_solo(){	
	if(ult_solo < s_solo.min_limit || ult_solo > s_solo.max_limit){ 		//caso seja verificado um erro
		erro_solo++;
		cont_solo++;
		ult_solo=50;
	}else{																	//caso nao haja erro
		cont_solo=0;
	}
	s_solo.readings_size++;
	*(ptr_solo)=ult_solo;
	ptr_solo++;
}

//Sensor que representa a humidade do solo
Sensor humidade_solo(unsigned char ult_pluvio){
	int num;
	//defenir os atributos da estrutura que representa o sensor
	s_solo.id=5001;
	s_solo.sensor_type=5;
	s_solo.max_limit=100;
	s_solo.min_limit=0;
	s_solo.frequency=0;
	s_solo.readings_size=0;
	s_solo.readings=ptr_solo;
	
	printf("Humidade Solo: Qual a frequencia de leituras?\n");
	scanf("%lud",&s_solo.frequency);
	
	printf("Humidade Solo: Qual o numero de leituras?\n");
	scanf("%d",&num);
	
	//recolher as leituras do sensor
	for(int i = 0; i < num; i++){
		ult_solo = sens_humd_solo(ult_solo,ult_pluvio, getRandom());
		random_solo();
		sleep(s_solo.frequency);
						
		while((cont_solo % 5 )==0 && cont_solo!=0){
			s_solo.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_solo.readings+a)=0;
				}
			for(int x=0;x<5;x++){
				ult_solo = sens_humd_solo(ult_solo, ult_pluvio, getRandom());
				random_solo();
				sleep(s_solo.frequency);
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
			ult_solo = sens_humd_solo(ult_solo,ult_pluvio, getRandom());
			random_solo();
			sleep(s_solo.frequency);
							
			while((cont_solo % 5 )==0 && cont_solo!=0){
				s_solo.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_solo.readings+a)=0;
				}
				for(int x=0;x<5;x++){
					ult_solo = sens_humd_solo(ult_solo, ult_pluvio, getRandom());
					random_solo();
					sleep(s_solo.frequency);
				}	
			}
		}
	}
	printf("\nForam encontrados %d erros no sensor \n",erro_solo);
	return s_solo;
}
