.section .text
	.global sens_humd_atm

sens_humd_atm:				# unsigned char sens_humd_atm(unsigned char ult_hmd_atm, unsigned char ult_pluvio, char comp_rand)

	# Prólogo
	pushq %rbp 
	movq %rsp,%rbp
						# ult_hmd_atm in 'DIL', ult_pluvio in 'SIL', comp_rand in 'DL'

	movb %dil, %cl		# place ult_hmd_atm in an auxilar register
	addb %dl, %dil		# %dil = comp_rand + ult_hmd_atm
	subb %dil, %cl		# %cl = (comp_rand + ult_hmd_atm) - ult_hmd_atm
	
	cmpb $0, %sil
	je verification		# jmp verification if ult_pluvio=0
	
	movb %dil, %al		# place %dil in %al
	
	jmp end				# jmp end
	
	
verification:

	cmpb $35, %cl		
	jge error1			# jmp error if %cl>=35
	
	cmpb $-35, %cl
	jle error2			# jmp error if %cl<=-35
	
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
