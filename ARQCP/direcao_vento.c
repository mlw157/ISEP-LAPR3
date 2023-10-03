//Define the struct
#include "header.h"

//Libary stdio.h to be able to print
#include <stdio.h>

//Libary unistd.h to use the class sleep
#include <unistd.h>

//Define functions to make random number and sensor
#include "generate_random.h"
#include "sens_dir_vento.h"
#include "pcg32_random_r.h"
#include "getRandom.h"

//Declarar variaveis uteis
int cont_dir;
int erro_direcao=0;
char ult_dir_vento=0;
unsigned short frequencias_dir[100];
unsigned short *ptr_dir=frequencias_dir;
Sensor s_dir;
	
//Metodo para preencher o array de leituras e verificar a quantidade de erros do sensor
void random_dir(){
	if(ult_dir_vento < s_dir.min_limit || ult_dir_vento > s_dir.max_limit){ 		//caso seja verificado um erro
		cont_dir++;
		erro_direcao++;		
	}else{																	//caso nao haja erro
		cont_dir=0;	
	}
	s_dir.readings_size++;
	*(ptr_dir)=ult_dir_vento;
	ptr_dir++;
}

//Sensor que representa a direcao do vento
Sensor direcao_vento(){
	int num;
	//defenir os atributos da estrutura que representa o sensor
	s_dir.id=3001;
	s_dir.sensor_type=3;
	s_dir.max_limit=360;
	s_dir.min_limit=0;
	s_dir.frequency=0;
	s_dir.readings_size=0;
	s_dir.readings=ptr_dir;
	
	printf("Direção do Vento: Qual a frequencia de leituras?\n");
	scanf("%lud",&s_dir.frequency);
	
	printf("Direção do Vento: Qual o numero de leituras?\n");
	scanf("%d",&num);
	
	//recolher as leituras do sensor
	for(int i = 0; i < num; i++){		
			ult_dir_vento = sens_dir_vento(ult_dir_vento, getRandom());
			random_dir();
			sleep(s_dir.frequency);
							
			while((cont_dir % 5 )==0 && cont_dir!=0){
				s_dir.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_dir.readings+a)=0;
				}
				for(int x=0;x<5;x++){
					ult_dir_vento = sens_dir_vento(ult_dir_vento, getRandom());
					random_dir();
					sleep(s_dir.frequency);
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
			ult_dir_vento = sens_dir_vento(ult_dir_vento, getRandom());
			random_dir();
			sleep(s_dir.frequency);
							
			while((cont_dir % 5 )==0 && cont_dir!=0){
				s_dir.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_dir.readings+a)=0;
				}
				for(int x=0;x<5;x++){
					ult_dir_vento = sens_dir_vento(ult_dir_vento, getRandom());
					random_dir();
					sleep(s_dir.frequency);
				}	
			}
		}
	}		
	printf("\nForam encontrados %d erros no sensor \n",erro_direcao);
	return s_dir;
}
