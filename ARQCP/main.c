//Define the struct
#include "header.h"

//Libary stdio.h to be able to print
#include <stdio.h>

//Libary unistd.h to use the class sleep
#include <unistd.h>

//Libary stdlib.h to allocate memory
#include <stdlib.h>

//Libary stdlib.h to use boolean data type
#include <stdbool.h>

//Define all functions, function to make random number and sensors
#include "generate_random.h"
#include "sens_temp.h"
#include "pcg32_random_r.h"
#include "sens_velc_vento.h"
#include "sens_dir_vento.h"
#include "sens_humd_atm.h"
#include "sens_humd_solo.h"
#include "sens_pluvio.h"
#include "getRandom.h"
#include "temperatura.h"
#include "velocidade_vento.h"
#include "direcao_vento.h"
#include "humidade_atmosferica.h"
#include "humidade_solo.h"
#include "pluviosidade.h"

//Numero de Sensores criados
int numTemp;
int numVelVento;
int numDirVento;
int numPluv;
int numHmdAtm;
int numHmdSolo;
	
//Temperatura
Sensor sensor_temp1;
	
//Velocidade do Vento
Sensor sensor_vel_vento1;
		
//Direcao do Vento
Sensor sensor_dir_vento1;
		
//Pluviosidade
Sensor sensor_pluv1;
	
//Humidade Atmosferica
Sensor sensor_hmd_atm1;
	
//Humidade do Solo
Sensor sensor_hmd_solo1;

//Arrays com os Sensores criados
Sensor* array_temp;
Sensor* array_vel;
Sensor* array_dir; 
Sensor* array_pluv;
Sensor* array_solo;
Sensor* array_atm;	

//Variaveis uteis para libertar memoria alocada
bool temp_free=false;
bool vel_free=false;
bool dir_free=false;
bool atm_free=false;
bool solo_free=false;
bool pluv_free=false;

//Funções dos sensores que irão trabalhar com as estruturas dos mesmos, bem como chamar a função que irá preencher a estrutura e printar devidamente os seus atributos 
void temp(int num){
		for(int i = num; i< numTemp;i++){
			sensor_temp1=temperatura();
			sensor_temp1.id+=i;
			printf("\nId: %hd", sensor_temp1.id);
			printf("\nType: %d" , sensor_temp1.sensor_type);
			printf("\nMax Limit: %hd", sensor_temp1.max_limit);
			printf("\nMin Limit: %hd", sensor_temp1.min_limit);
			printf("\nFrequecy: %ld",sensor_temp1.frequency);
			printf("\nReading Size: %ld", sensor_temp1.readings_size);
			for(int j = 0; j < sensor_temp1.readings_size; j++){
				printf("\nTemperatura: %hdºC", *(sensor_temp1.readings+j));
			}	
			printf("\n");
			*(array_temp+i)=sensor_temp1;
		}
	}
	
	void vel_vento(int num){
		for(int i = num; i< numVelVento;i++){
			sensor_vel_vento1=velocidade_vento();
			sensor_vel_vento1.id+=i;
			printf("\nId: %hd", sensor_vel_vento1.id);
			printf("\nType: %d" , sensor_vel_vento1.sensor_type);
			printf("\nMax Limit: %hd", sensor_vel_vento1.max_limit);
			printf("\nMin Limit: %hd", sensor_vel_vento1.min_limit);
			printf("\nFrequecy: %ld",sensor_vel_vento1.frequency);
			printf("\nReading Size: %ld", sensor_vel_vento1.readings_size);
			for(int j = 0; j < sensor_vel_vento1.readings_size; j++){
				printf("\nVelocidade do Vento: %hdkm/h", *(sensor_vel_vento1.readings+j));
			}	
			printf("\n");
			*(array_vel+i)=sensor_vel_vento1;
		}
	}
		
	void dir_vento(int num){
		for(int i = num; i< numDirVento;i++){
			sensor_dir_vento1=direcao_vento();
			sensor_dir_vento1.id+=i;
			printf("\nId: %hd", sensor_dir_vento1.id);
			printf("\nType: %d" , sensor_dir_vento1.sensor_type);
			printf("\nMax Limit: %hd", sensor_dir_vento1.max_limit);
			printf("\nMin Limit: %hd", sensor_dir_vento1.min_limit);
			printf("\nFrequecy: %ld",sensor_dir_vento1.frequency);
			printf("\nReading Size: %ld", sensor_dir_vento1.readings_size);
			for(int j = 0; j < sensor_dir_vento1.readings_size; j++){
				printf("\nDireção do Vento: %hdº", *(sensor_dir_vento1.readings+j));
			}	
			printf("\n");
			*(array_dir+i)=sensor_dir_vento1;
		}
	}
	
	void hmd_solo(int num){
		for(int i = num; i< numHmdSolo;i++){
			if(sensor_pluv1.readings==0){
				printf("\nÉ necessário existirem leituras de pluviosidade antes de inicializar este sensor!");
				exit(0);
			}else{	
				sensor_hmd_solo1=humidade_solo(*sensor_pluv1.readings);
				sensor_hmd_solo1.id+=i;
				printf("\nId: %hd", sensor_hmd_solo1.id);
				printf("\nType: %d" , sensor_hmd_solo1.sensor_type);
				printf("\nMax Limit: %hd", sensor_hmd_solo1.max_limit);
				printf("\nMin Limit: %hd", sensor_hmd_solo1.min_limit);
				printf("\nFrequecy: %ld",sensor_hmd_solo1.frequency);
				printf("\nReading Size: %ld", sensor_hmd_solo1.readings_size);
				for(int j = 0; j < sensor_hmd_solo1.readings_size; j++){
					printf("\nHumidade do Solo: %hd%%", *(sensor_hmd_solo1.readings+j));
				}	
				printf("\n");
				*(array_solo+i)=sensor_hmd_solo1;
			}
		}
	}	
	
	void hmd_atm(int num){
		for(int i = num; i< numHmdAtm;i++){
			if(sensor_pluv1.readings_size==0){
				printf("\nÉ necessário existirem leituras de pluviosidade antes de inicializar este sensor!");
				exit(0);
			}else{	
				sensor_hmd_atm1=humidade_atmosferica(*sensor_pluv1.readings);
				sensor_hmd_atm1.id+=i;		
				printf("\nId: %hd", sensor_hmd_atm1.id);
				printf("\nType: %d" , sensor_hmd_atm1.sensor_type);
				printf("\nMax Limit: %hd", sensor_hmd_atm1.max_limit);
				printf("\nMin Limit: %hd", sensor_hmd_atm1.min_limit);
				printf("\nFrequecy: %ld",sensor_hmd_atm1.frequency);
				printf("\nReading Size: %ld", sensor_hmd_atm1.readings_size);
				for(int j = 0; j < sensor_hmd_atm1.readings_size; j++){
					printf("\nHumidade Atmosferica: %hd%%", *(sensor_hmd_atm1.readings+j));
				}	
				printf("\n");
				*(array_atm+i)=sensor_hmd_atm1;
			}
		}
	}
	
	void pluvio(int num){
		for(int i = num; i< numPluv;i++){
			if(sensor_temp1.readings_size==0){
				printf("\nÉ necessário existirem leituras de temperatura antes de inicializar este sensor!");
				exit(0);
			}else{	
				sensor_pluv1=pluviosidade(*sensor_temp1.readings);
				sensor_pluv1.id+=i;			
				printf("\nId: %hd", sensor_pluv1.id);
				printf("\nType: %d" , sensor_pluv1.sensor_type);
				printf("\nMax Limit: %hd", sensor_pluv1.max_limit);
				printf("\nMin Limit: %hd", sensor_pluv1.min_limit);
				printf("\nFrequecy: %ld",sensor_pluv1.frequency);
				printf("\nReading Size: %ld", sensor_pluv1.readings_size);
				for(int j = 0; j < sensor_pluv1.readings_size; j++){
					printf("\nPluviosidade: %hdmm", *(sensor_pluv1.readings+j));
				}	
				printf("\n");
				*(array_pluv+i)=sensor_pluv1;
			}	
		}
	}
	
//Define a 2-dimensional array to hold min, max and average values for each sensor
unsigned short matriz[6][3];
unsigned short i;
unsigned short l;
unsigned short c;
	
unsigned short max_temp=0;
unsigned short med_temp=0;
unsigned short min_temp=1000;

unsigned short max_vel_vento=0;
unsigned short med_vel_vento=0;
unsigned short min_vel_vento=1000;

unsigned short max_dir_vento=0;
unsigned short med_dir_vento=0;
unsigned short min_dir_vento=1000;
	
unsigned short max_pluvio=0;
unsigned short med_pluvio=0;
unsigned short min_pluvio=1000;
	
unsigned short max_hmd_atm=0;
unsigned short med_hmd_atm=0;
unsigned short min_hmd_atm=1000;
	
unsigned short max_hmd_solo=0;
unsigned short med_hmd_solo=0;
unsigned short min_hmd_solo=1000;

//Escrever o ficheiro .csv
void writeFile(){
	// Open a file for writing
	FILE *file = fopen("matriz.csv", "w");
	if (file == NULL) {
		printf("Error opening file!\n");
		return;
	}

	// Write the data to the file
	fprintf(file, "Tipo de sensor,MÁX,MÍN,MED\n");
	for (l=0;l<6;l++){
		switch (l){
			case 0:
				fprintf(file,"Temperatura(ºC),");
				break;
			case 1:
				fprintf(file,"Velocidade do vento(KM/H),");
				break;
			case 2:
				fprintf(file,"Direção do vento(º),");
				break;
			case 3:
				fprintf(file,"Pluviosidade(mm),");
				break;
			case 4:
				fprintf(file,"Humidade da Atmosfera(%%),");
				break;
			case 5:
				fprintf(file,"Humidade do solo(%%),");
				break;
		}
		
		for(c=0;c<3;c++){
			fprintf(file,"%d,",*(*(matriz+l)+c));
		}
		fprintf(file,"\n");
	}

	// Close the file
	fclose(file);
}	

//Constroi a matriz
void matrix(){
	unsigned long size;
	unsigned short value;
	for(int i = 0; i<numTemp;i++){
		size = (array_temp+i)->readings_size;
		for(int j = 0; j<size;j++){
			value=(*(array_temp+i)->readings+j);
			if(value<min_temp){
				min_temp=value;
				*(*(matriz)+1)=value;
			}
			if(value>max_temp){
				max_temp=value;
				*(*(matriz))=value;
			}
			med_temp+=value;
			*(*(matriz)+2)=med_temp/size;
		}	
	}
	
	for(int i = 0; i<numVelVento;i++){
		size = (array_vel+i)->readings_size;
		for(int j = 0; j<size;j++){
			value=(*(array_vel+i)->readings+j);
			if(value<min_vel_vento){
				min_vel_vento=value;
				*(*(matriz+1)+1)=value;
			}
			if(value>max_vel_vento){
				max_vel_vento=value;
				*(*(matriz+1))=value;
			}
			med_vel_vento+=value;
			*(*(matriz+1)+2)=med_vel_vento/size;
		}	
	}
	
	for(int i = 0; i<numDirVento;i++){
		size = (array_dir+i)->readings_size;
		for(int j = 0; j<size;j++){
			value=(*(array_dir+i)->readings+j);
			if(value<min_dir_vento){
				min_dir_vento=value;
				*(*(matriz+2)+1)=value;
			}
			if(value>max_dir_vento){
				max_dir_vento=value;
				*(*(matriz+2))=value;
			}
			med_dir_vento+=value;
			*(*(matriz+2)+2)=med_dir_vento/size;
		}	
	}
	
	for(int i = 0; i<numPluv;i++){
		size = (array_pluv+i)->readings_size;
		for(int j = 0; j<size;j++){
			value=(*(array_pluv+i)->readings+j);
			if(value<min_pluvio){
				min_pluvio=value;
				*(*(matriz+3)+1)=value;
			}
			if(value>max_pluvio){
				max_pluvio=value;
				*(*(matriz+3))=value;
			}
			med_pluvio+=value;
			*(*(matriz+3)+2)=med_pluvio/size;
		}	
	}
	
	for(int i = 0; i<numHmdAtm;i++){
		size = (array_atm+i)->readings_size;
		for(int j = 0; j<size;j++){
			value=(*(array_atm+i)->readings+j);
			if(value<min_hmd_atm){
				min_hmd_atm=value;
				*(*(matriz+4)+1)=value;
			}
			if(value>max_hmd_atm){
				max_hmd_atm=value;
				*(*(matriz+4))=value;
			}
			med_hmd_atm+=value;
			*(*(matriz+4)+2)=med_hmd_atm/size;
		}	
	}
	
	for(int i = 0; i<numHmdSolo;i++){
		size = (array_solo+i)->readings_size;
		for(int j = 0; j<size;j++){
			value=(*(array_solo+i)->readings+j);
			if(value<min_hmd_solo){
				min_hmd_solo=value;
				*(*(matriz+5)+1)=value;
			}
			if(value>max_hmd_solo){
				max_hmd_solo=value;
				*(*(matriz+5))=value;
			}
			med_hmd_solo+=value;
			*(*(matriz+5)+2)=med_hmd_solo/size;
		}	
	}
	
	
}	
	

int main (){
	printf("Quantos sensores de temperatura necessita? ");
	scanf("%d",&numTemp);
	printf("Quantos sensores de velocidade do vento necessita? ");
	scanf("%d",&numVelVento);
	printf("Quantos sensores de direção do vento necessita? ");
	scanf("%d",&numDirVento);
	printf("Quantos sensores de pluviosidade necessita? ");
	scanf("%d",&numPluv);
	printf("Quantos sensores de humidade atmosferica necessita? ");
	scanf("%d",&numHmdAtm);
	printf("Quantos sensores de humidade solo necessita? ");
	scanf("%d",&numHmdSolo);
	
	//Aloca memoria suficiente para guardar os sensores
	array_temp = (Sensor*)malloc(numTemp*sizeof(Sensor));
	array_vel = (Sensor*)malloc(numVelVento*sizeof(Sensor));
	array_dir = (Sensor*)malloc(numDirVento*sizeof(Sensor)); 
	array_pluv = (Sensor*)malloc(numPluv*sizeof(Sensor));
	array_solo = (Sensor*)malloc(numHmdSolo*sizeof(Sensor));
	array_atm = (Sensor*)malloc(numHmdAtm*sizeof(Sensor));	
	
	//Chama as funções para criar os sensores
	temp(0);
	vel_vento(0);
	dir_vento(0);
	pluvio(0);
	hmd_solo(0);
	hmd_atm(0);
	
	//Pergunta ao utilizador se pretende remover ou acrescentar sensores.
	int res;
	int sen;
	do{
		printf("Deseja acrescentar/remover sensores de um dado tipo? \n");
		printf("Sim: 1\n");
		printf("Não: 2\n");
		scanf("%d",&res);
	}while(res!= 1 && res!= 2);
	
	if(res==1){
		do{
			printf("Acrescentar ou remover? \n");
			printf("Acrescentar: 1\n");
			printf("Remover: 2\n");
			scanf("%d",&res);
		}while(res!= 1 && res!= 2);
		
		do{
			printf("Qual o tipo de sensor? \n");
			printf("Temperatura: 1\n");
			printf("Velocidade do Vento: 2\n");
			printf("Direção do Vento: 3\n");
			printf("Humidade Atmosferica: 4\n");
			printf("Humidade do solo: 5\n");
			printf("Pluviosidade: 6\n");
			scanf("%d",&sen);
		}while(sen!= 1 && sen!= 2 && sen!= 3 && sen!= 4 && sen!= 5 && sen!= 6);
			
		//Realoca memoria caso o utilizador pretenda acrescentar algum tipo de sensor	
		if(res==1){
			switch(sen){
				case (1) : 
					numTemp++;
					array_temp = (Sensor*)realloc(array_temp, numTemp*sizeof(Sensor));
					temp(numTemp-1);
					break;
				case (2) : 
					numVelVento++;
					array_vel = (Sensor*)realloc(array_vel, numVelVento*sizeof(Sensor));
					vel_vento(numVelVento-1);
					break;
				case (3) : 
					numDirVento++;
					array_dir = (Sensor*)realloc(array_dir, numDirVento*sizeof(Sensor));
					dir_vento(numDirVento-1);
					break;
				case (4) : 
					numHmdAtm++;
					array_atm = (Sensor*)realloc(array_atm, numHmdAtm*sizeof(Sensor));					
					hmd_atm(numHmdAtm-1);
					break;
				case (5) : 
					numHmdSolo++;
					array_solo = (Sensor*)realloc(array_solo, numHmdSolo*sizeof(Sensor));					
					hmd_solo(numHmdSolo-1);
					break;
				case (6) : 
					numPluv++;
					array_pluv = (Sensor*)realloc(array_pluv, numPluv*sizeof(Sensor));
					pluvio(numPluv-1);	
					break;
				}						
		}else{
			switch(sen){
				case (1) : 
					numTemp=0;
					free(array_temp);
					temp_free=true;
					break;
				case (2) : 
					numVelVento=0;
					free(array_vel);
					vel_free=true;
					break;
				case (3) : 
					numDirVento=0;
					free(array_dir);
					dir_free=true;
					break;
				case (4) : 
					numHmdAtm=0;
					free(array_atm);
					atm_free=true;
					break;
				case (5) : 
					numHmdSolo=0;
					free(array_solo);
					solo_free=true;
					break;
				case (6) : 
					numPluv=0;
					free(array_pluv);	
					pluv_free=true;
					break;
			}
			printf("\nSensor removido!\n");				
		}
	}
	//Chama a função que permite criar a matriz final
	matrix();
	//Chama a função que permite criar o ficheiro .csv com a matriz anteriomente criada
	writeFile();
	//Limpa a memoria alocada
	if(!temp_free){
		free(array_temp);
	}
	if(!dir_free){
		free(array_dir);
	}
	if(!vel_free){
		free(array_vel);
	}
	if(!pluv_free){
		free(array_pluv);
	}
	if(!solo_free){
		free(array_solo);
	}
	if(!atm_free){
		free(array_atm);
	}
	return 0;
	
}	
	
