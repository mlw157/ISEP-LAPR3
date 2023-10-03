#include "generate_random.h"
#include "pcg32_random_r.h"

char getRandom(){

    char res;
    do{
        res = (char)generate_random();
    }while (res<-20 || res>50);
    return res;
}
