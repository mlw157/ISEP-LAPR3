//Define the struct
#include "header.h"

//Libary stdio.h to be able to print
#include <stdio.h>

//Libary unistd.h to use the class sleep
#include <unistd.h>

//Define functions to make random number and sensor
#include "generate_random.h"
#include "sens_temp.h"
#include "pcg32_random_r.h"
#include "getRandom.h"

//Declarar variaveis uteis
int contTemp;
int erro_temp=0;
char ult_temp=10;
unsigned short min;
unsigned short max;
unsigned short frequencias[100];
unsigned short *ptr=frequencias;
Sensor s1;

//Metodo para preencher o array de leituras e verificar a quantidade de erros do sensor	
void random(){
	if(ult_temp < min || ult_temp > max){ 		//caso seja verificado um erro
		contTemp++;
		erro_temp++;
		ult_temp = sens_temp(ult_temp, getRandom());
	}else{																	//caso nao haja erro
		contTemp=0;	
	}
	s1.readings_size++;
	*(ptr)=ult_temp;
	ptr++;
}
	
//Sensor que representa a temperatura	
Sensor temperatura(){
	int num;
	//defenir os atributos da estrutura que representa o sensor
	s1.id=1001;
	s1.sensor_type=1;
	s1.max_limit=100;
	s1.min_limit=0;
	s1.readings_size=0;
	s1.readings=ptr;
	
	printf("Temperatura: Qual a frequencia de leituras?\n");
	scanf("%lud",&s1.frequency);
	
	printf("Temperatura: Qual o numero de leituras?\n");
	scanf("%d",&num);
	
	min = s1.min_limit;
	max = s1.max_limit;
		
	//recolher as leituras do sensor	
	for(int i = 0; i < num; i++){	
		ult_temp = sens_temp(ult_temp, getRandom());
		random();
		sleep(s1.frequency);
						
		while((contTemp % 5 )==0 && contTemp!=0){
			s1.readings_size-=5;
			for(int a=5;a>0;a--){
				*(s1.readings+a)=0;
			}	
			for(int x=0;x<5;x++){
				ult_temp = sens_temp(ult_temp, getRandom());
				random();
				sleep(s1.frequency);
				
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
			ult_temp = sens_temp(ult_temp, getRandom());
			random();
			sleep(s1.frequency);
							
			while((contTemp % 5 )==0 && contTemp!=0){
				s1.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s1.readings+a)=0;
				}	
				for(int x=0;x<5;x++){
					ult_temp = sens_temp(ult_temp, getRandom());
					random();
					sleep(s1.frequency);
					
				}	
			}
		}	
		
	}
	printf("\nForam encontrados %d erros no sensor \n",erro_temp);	
	return s1;
}
