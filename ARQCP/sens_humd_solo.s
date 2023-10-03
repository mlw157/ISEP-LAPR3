.section .text
	.global sens_humd_solo

sens_humd_solo:				# unsigned char sens_humd_solo(unsigned char ult_hmd_solo, unsigned char ult_pluvio, char comp_rand)

	# Prólogo
	pushq %rbp 
	movq %rsp,%rbp
						# ult_hmd_solo in '%DIL', ult_pluvio in 'SIL', comp_rand in 'DL'
		
	movb %dil, %cl		# place ult_hmd_solo in an auxilar register
	addb %dl, %dil		# %dil = comp_rand + ult_hmd_solo
	subb %dil, %cl		# %cl = (comp_rand + ult_hmd_solo) - ult_hmd_solo
	
	cmpb $0, %sil
	je verification		# jmp verification if ult_pluvio=0
	
	movb %dil, %al		# place %dil in %al
	
	jmp end				# jmp end
		
	
verification:

	cmpb $30, %cl		
	jge error1			# jmp error if %cl>=30
	
	cmpb $0, %cl
	jle error2			# jmp error if %cl<=0
	
	movb %dil, %al		# place %dil in %al
	
	jmp end				# jmp end
	
error1:
	
	movb $230, %al		# place 230 in %al (error value)
	
	jmp end				# jmp end
	
error2:
	
	xorb $0b10000000, %dil
	movb %dil, %al
	
	jmp end				# jmp end
		
end:
	
	# Epilogo
	movq %rbp,%rsp
	popq %rbp
	
	ret
