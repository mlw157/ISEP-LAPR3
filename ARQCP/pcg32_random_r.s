.section data
	.global state
	.global inc

.section .text
	.global pcg32_random_r

pcg32_random_r:				# uint32_t pcg32_random_r()

	# Prólogo
	pushq %rbp 
	movq %rsp,%rbp
	movq $0, %rax
	movq $0, %rbx
	movq $0, %rcx
	movq $0, %rdx
	movq $0, %rdi
	movq $0, %rsi
	movq $0, %r8
	movq $0, %r9
	movq $0, %r10
	
	
	movq state(%rip), %rcx					# place Oldstate in rcx
	movq %rcx, %rsi							# place Oldstate in rsi
	movq state(%rip), %rax					# place Oldstate in rax
	movq inc(%rip), %r8						# place Inc in r8
	movq $6364136223846793005, %r10			# place 6364136223846793005 in r10
	
	imulq %r10, %rcx						# rcx = 6364136223846793005 * Oldstate
	or $1, %r8								# r8 = 1 | inc
	addq %r8, %rcx							# rcx = (6364136223846793005 * Oldstate) + (1 | inc)
	movq %rcx, %rax							# place rcx in rax
	
	movq state(%rip), %rcx					# place Oldstate in rcx
	shr $18, %rcx							# shift right 18 positions on rcx
	xor %rsi, %rcx							# rcx = rcx ^ Oldstate
	shr $27, %rcx							# shift right 27 positions on rcx
	movq %rcx, %r9							# place rcx in r9
	
	shr $59, %rsi							# shift right 59 positions on rsi
	movq %rsi, %rdi							# place rsi in rdi
	
	neg %rdi								# neg rdi
	and $31, %rdi							# rdi = rdi AND 31
	
	pushq %rcx								# save rcx
	movq %rcx, %r10							# place rcx in an auciliar register
	movq %rdi, %rcx							# place rdi in rcx
	shlq %rcx, %r10							# shift left rcx positions on r10
	movq %rsi, %rcx							# place rsi in rcx
	shrq %rcx, %r9							# shift right rcx postions on r9
	popq %rcx								# restore old rcx
	
	or %r9, %r10							# r9 OR r10
	movq %r10, %rax 						# place r10 in rax
		
	# Epilogo
	movq %rbp,%rsp
	popq %rbp
	
ret
