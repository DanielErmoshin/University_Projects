OPCIONS = -std=c++11
OPCIONS1 = -std=c++11 -D_GLIBCXX_DEBUG
OPCIONS2 = -D_JUDGE_ -D_GLIBCXX_DEBUG -O2 -Wall -Wextra -Werror -Wno-sign-compare -std=c++11

program.exe: program.o Proceso.o CPU.o Cluster.o Priority.o Area_Procesos.o
	g++ -o program.exe program.o Proceso.o CPU.o Cluster.o Priority.o Area_Procesos.o

Proceso.o:  Proceso.cc Proceso.hh
	g++ -c Proceso.cc $(OPCIONS2)

CPU.o:  CPU.cc CPU.hh
	g++ -c CPU.cc $(OPCIONS2)

Cluster.o:  Cluster.cc Cluster.hh
	g++ -c Cluster.cc $(OPCIONS2)

Priority.o:  Priority.cc Priority.hh
	g++ -c Priority.cc $(OPCIONS2)

Area_Procesos.o: Area_Procesos.cc Area_Procesos.hh
	g++ -c Area_Procesos.cc $(OPCIONS2)

program.o: program.cc
	g++ -c program.cc $(OPCIONS2)

clean:
	rm *.o
	rm *.exe