.section .text
	.global sens_temp

sens_temp:				# char sens_temp(char ult_temp, char comp_rand)

	# Prólogo
	pushq %rbp 
	movq %rsp,%rbp
						# ult_temp in 'DIL', comp_rand in 'SIL'
		
	addb %sil, %dil		# %dil = ult_temp + comp_rand
	movb %dil, %al		# place %dil in %al
	
	# Epilogo
	movq %rbp,%rsp
	popq %rbp
	
ret
