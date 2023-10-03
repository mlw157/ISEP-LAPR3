//Define the struct
#include "header.h"

//Libary stdio.h to be able to print
#include <stdio.h>

//Libary unistd.h to use the class sleep
#include <unistd.h>

//Define functions to make random number and sensor
#include "generate_random.h"
#include "sens_pluvio.h"
#include "pcg32_random_r.h"
#include "getRandom.h"

//Declarar variaveis uteis
int cont_pluv;
char ult_pluv=0;
int erro_pluv=0;
unsigned short frequencias_pluv[100];
unsigned short *ptr_pluv=frequencias_pluv;
Sensor s_pluv;

//Metodo para preencher o array de leituras e verificar a quantidade de erros do sensor
void random_pluv(){
    if(ult_pluv < s_pluv.min_limit || ult_pluv > s_pluv.max_limit){ 		//caso seja verificado um erro
        cont_pluv++;
        erro_pluv++;
    }else{																	//caso nao haja erro
        cont_pluv=0;
    }

	//guardar a leitura na estrutura
	 s_pluv.readings_size++;
	*(ptr_pluv)=ult_pluv;
	ptr_pluv++;
}

//Sensor que representa a pluviosidade
Sensor pluviosidade(char ult_temp){
	int num;
	//defenir os atributos da estrutura que representa o sensor
    s_pluv.id=6001;
    s_pluv.sensor_type=6;
    s_pluv.max_limit=300;
    s_pluv.min_limit=0;
    s_pluv.frequency=0;
    s_pluv.readings_size=0;
    s_pluv.readings=ptr_pluv;

	printf("Pluviosidade: Qual a frequencia de leituras?\n");
	scanf("%lud",&s_pluv.frequency);
	
	printf("Pluviosidade: Qual o numero de leituras?\n");
	scanf("%d",&num);

	//recolher as leituras do sensor	
    for(int i = 0; i < num; i++){
        ult_pluv = sens_pluvio(ult_pluv, ult_temp, getRandom());
        random_pluv();
        sleep(s_pluv.frequency);

        while((cont_pluv % 5 )==0 && cont_pluv!=0){
			s_pluv.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_pluv.readings+a)=0;
				}
            for(int x=0;x<5;x++){
                ult_pluv = sens_pluvio(ult_pluv, ult_temp, getRandom());
                random_pluv();
                sleep(s_pluv.frequency);
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
			ult_pluv = sens_pluvio(ult_pluv, ult_temp, getRandom());
			random_pluv();
			sleep(s_pluv.frequency);

			while((cont_pluv % 5 )==0 && cont_pluv!=0){
				s_pluv.readings_size-=5;
				for(int a=5;a>0;a--){
					*(s_pluv.readings+a)=0;
				}				
				for(int x=0;x<5;x++){
					ult_pluv = sens_pluvio(ult_pluv, ult_temp, getRandom());
					random_pluv();
					sleep(s_pluv.frequency);
				}
			}
		}
	}			
	printf("\nForam encontrados %d erros no sensor \n",erro_pluv);	
    return s_pluv;
}
