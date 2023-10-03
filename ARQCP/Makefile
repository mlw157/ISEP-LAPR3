# A Makefile for the project program
INCLUDES = generate_random.h sens_temp.h pcg32_random_r.h sens_velc_vento.h sens_dir_vento.h sens_humd_atm.h sens_humd_solo.h sens_pluvio.h getRandom.h temperatura.h velocidade_vento.h direcao_vento.h humidade_atmosferica.h humidade_solo.h pluviosidade.h
SOURCES = generate_random.c sens_temp.s pcg32_random_r.s sens_velc_vento.s main.c sens_dir_vento.s sens_humd_atm.s sens_humd_solo.s sens_pluvio.s getRandom.c temperatura.c velocidade_vento.c direcao_vento.c humidade_atmosferica.c humidade_solo.c pluviosidade.c
OBJFILES = generate_random.o sens_temp.o pcg32_random_r.o sens_velc_vento.o main.o sens_dir_vento.o sens_humd_atm.o sens_humd_solo.o sens_pluvio.o getRandom.o temperatura.o velocidade_vento.o direcao_vento.o humidade_atmosferica.o humidade_solo.o pluviosidade.o
EXEC = prog

.SUFFIXES : .c .o .s

.c.o:
	gcc -Wall -g -c $<

.s.o:
	gcc -Wall -g -c $<

${EXEC}: ${OBJFILES}
	gcc -Wall -g -lm -o ${EXEC} ${OBJFILES}

${OBJFILES}: ${SOURCES} ${INCLUDES}

run: ${EXEC}
	./${EXEC}

clean:
	rm -f ${OBJFILES} ${EXEC}
