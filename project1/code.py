import math 
from time import perf_counter_ns
j, n = 2, 12000000; startTimer = perf_counter_ns()
while j < n: # Other Test Input Values (n): 10, 128, 1125, 16384, 107050, 1234567
    k = j
    while k < n:
        # sum += a[k] * b[k] -- This statement will always execute in O(1) time, hence commented
        k = k * k
    j += math.frexp(k)[1] - 1 # This statement is equivalent of j += log k (base 2)
endTimer = perf_counter_ns(); total_elapsed_time = endTimer - startTimer # time in nano seconds
print(total_elapsed_time) # Total elapsed time which is our experimental result
