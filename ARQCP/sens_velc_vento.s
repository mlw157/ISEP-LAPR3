.section .text
	.global sens_velc_vento

sens_velc_vento:				# unsigned char sens_velc_vento(unsigned char ult_velc_vento, char comp_rand)

	# Prólogo
	pushq %rbp 
	movq %rsp,%rbp
						# ult_velc_vento in 'DIL', comp_rand in 'SIL'
		
	addb %sil, %dil		# %dil = ult_velc_vento + comp_rand
	movb %dil, %al		#place %dil in %al
	
	# Epilogo
	movq %rbp,%rsp
	popq %rbp
	
ret
