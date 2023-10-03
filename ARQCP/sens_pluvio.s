.section .text
	.global sens_pluvio

sens_pluvio:				# unsigned char sens_pluvio(unsigned char ult_pluvio, char ult_temp, char comp_rand)

	# Prólogo
	pushq %rbp 
	movq %rsp,%rbp
						# ult_pluvio in 'DIL', ult_temp in 'SIL', comp_rand in 'DL'
		
	cmpb $0, %dil		
	je verification		# jmp verification if ult_pluvio=0
		
	addb %dl, %dil		# %dil = ult_pluvio+comp_rand
	
	jmp end				#jmp end
	
	
verification:
	cmpb $0, %dl		
	jl pluv_to_zero		# jmp pluv_to_zero if comp_rand<0
	
	addb %dl, %dil		# %dil = ult_pluvio+comp_rand
	
	jmp end				# jmp end
	
pluv_to_zero:
	movb $0, %dil 		# place 0 in ult_pluvio
	jmp end

end:	
	
	movb %dil, %al		# place %dil in %al
	
	# Epilogo
	movq %rbp,%rsp
	popq %rbp
	
	ret
