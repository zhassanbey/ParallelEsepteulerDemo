import sys
import time
import threading

# Demalu kajettiligin baskarushy.
SLEEP_NEEDED = True

x = 40**1900000

squares = [-1, -1]

def _sqr(z, i):
	if SLEEP_NEEDED:
		time.sleep(2) # <- adeyi 2 sekund uiktatamyz.
	squares[i] = z**2

def _plus(f, b):
	 f + b

def sequential() -> float:
	start_time = time.time()
	
	# Kvardarttardy brinien son birin tizbektei eseptep alamyz.
	_sqr(x, 0)
	_sqr(x, 1)

	# Sosyn kosyndyny esepteimiz.
	_plus(squares[0], squares[1])

	return time.time() - start_time

def parallel() -> float:
	start_time = time.time()
	
	# Eki kvardartty eki jeke threadtin ishinde esepteimiz.
	t1 = threading.Thread(target=_sqr, args=(x, 0,))
	t1.start()
	t2 = threading.Thread(target=_sqr, args=(x, 1,))
	t2.start()
	
	# Eki threadting de bitkenin kutemiz.
	t1.join()
	t2.join()
	
	# Sosyn kosyndyny esepteimiz.
	_plus(squares[0], squares[1])

	return time.time() - start_time

if __name__ == '__main__':
	SLEEP_NEEDED = 'demalma' not in sys.argv
	print(f'Python {sys.version_info.major}.{sys.version_info.minor}.{sys.version_info.micro}')
	print(f'\tTizbektei esepteuge {sequential()} sekund ketti')
	print(f'\tParallel esepteuge {parallel()} sekund ketti')
