.section .text
	.global sens_dir_vento

sens_dir_vento:				# unsigned short sens_dir_vento(unsigned short ult_dir_vento, short comp_rand)

	# Prólogo
	pushq %rbp 
	movq %rsp,%rbp
						# ult_dir_vento in 'DI', comp_rand in 'SI'
						
	movw $0, %ax		# clear %ax
	addw %si, %di       # %di = ult_dir_vento + comp_rand
	
	cmpw $0, %di
	jge verification	# jmp to verification if ult_dir_vento + comp_rand>=0
	
	jmp end				# jmp end 
	
	
verification:
	
	movw %di, %ax		# place %di in %al
	
	jmp end				# jmp end
	
		
end:	
	# Epilogo
	movq %rbp,%rsp
	popq %rbp
	
	ret
