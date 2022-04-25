JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

default: 
	$ javac -cp .:lib/junit-4.12.jar:lib/commons-lang3-3.12.0/*.jar:lib/opencsv-5.5.2.jar CapaDeDatos/Gestores/AdaptadoresGestores/*.java CapaDeDatos/Gestores/*java CapaDeDominio/DomainModel/*.java CapaDeDominio/Controladores/*.java CapaDePresentacion/Controladores/*.java CapaDePresentacion/Vistas/*.java CapaDePresentacion/*.java

	$ jar -cvmf ../EXE/Main_jar/temp_linux.mf ../EXE/Main_jar/Main_Linux.jar ../FONTS/CapaDeDatos/Gestores/*.class  ../FONTS/CapaDeDatos/Gestores/AdaptadoresGestores/*.class ../FONTS/CapaDeDominio/DomainModel/*.class ../FONTS/CapaDeDominio/Controladores/*.class ../FONTS/lib ../FONTS/CapaDePresentacion/*.class ../FONTS/CapaDePresentacion/Controladores/*.class ../FONTS/CapaDePresentacion/Vistas/*.class

	$ java -jar ../EXE/Main_jar/Main_Linux.jar

jar:
	$ jar -cvmf ../EXE/Main_jar/temp_linux.mf ../EXE/Main_jar/Main_Linux.jar ../FONTS/CapaDeDatos/Gestores/*.class  ../FONTS/CapaDeDatos/Gestores/AdaptadoresGestores/*.class ../FONTS/CapaDeDominio/DomainModel/*.class ../FONTS/CapaDeDominio/Controladores/*.class ../FONTS/lib ../FONTS/CapaDePresentacion/*.class ../FONTS/CapaDePresentacion/Controladores/*.class ../FONTS/CapaDePresentacion/Vistas/*.class

compile:
	$ javac -cp .:lib/junit-4.12.jar:lib/commons-lang3-3.12.0/*.jar:lib/opencsv-5.5.2.jar CapaDeDatos/Gestores/AdaptadoresGestores/*.java CapaDeDatos/Gestores/*java CapaDeDominio/DomainModel/*.java CapaDeDominio/Controladores/*.java CapaDePresentacion/Controladores/*.java CapaDePresentacion/Vistas/*.java CapaDePresentacion/*.java

clean:
	$ rm CapaDePresentacion/*.class

	$ rm CapaDeDatos/Gestores/AdaptadoresGestores/*.class

	$ rm CapaDeDatos/Gestores/*.class

	$ rm CapaDeDominio/Controladores/*.class

	$ rm CapaDeDominio/DomainModel/*.class

	$ rm CapaDePresentacion/Vistas/*.class

	$ rm CapaDePresentacion/Controladores/*.class

run: 
	$ java -jar ../EXE/Main_jar/Main_Linux.jar

help: 
	@echo Per defecte compila genera i executa laplicacio sencera 
	@echo make compile: compila todos los.java de la aplicación.
	@echo make jar: genera el jar executable 
	@echo make run: per compilar i executar 
	@echo make clean: esborrar els fitxers .class
	
	
	