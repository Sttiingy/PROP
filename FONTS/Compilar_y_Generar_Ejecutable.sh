#jar cvmf Manifest.mf NombreJAR.jar ClassNecesarios.class

javac -cp .:lib/junit-4.12.jar:lib/commons-lang3-3.12.0/*.jar:lib/opencsv-5.5.2.jar CapaDeDatos/Gestores/AdaptadoresGestores/*.java CapaDeDatos/Gestores/*java CapaDeDominio/DomainModel/*.java CapaDeDominio/Controladores/*.java CapaDePresentacion/Controladores/*.java CapaDePresentacion/Vistas/*.java CapaDePresentacion/*.java

jar -cvmf ../EXE/Main_jar/temp_linux.mf ../EXE/Main_jar/Main_Linux.jar ../FONTS/CapaDeDatos/Gestores/*.class  ../FONTS/CapaDeDatos/Gestores/AdaptadoresGestores/*.class ../FONTS/CapaDeDominio/DomainModel/*.class ../FONTS/CapaDeDominio/Controladores/*.class ../FONTS/lib ../FONTS/CapaDePresentacion/*.class ../FONTS/CapaDePresentacion/Controladores/*.class ../FONTS/CapaDePresentacion/Vistas/*.class

echo Borrando archivos .class ...

rm CapaDePresentacion/*.class

rm CapaDeDatos/Gestores/AdaptadoresGestores/*.class

rm CapaDeDatos/Gestores/*.class

rm CapaDeDominio/Controladores/*.class

rm CapaDeDominio/DomainModel/*.class

rm CapaDePresentacion/Vistas/*.class

rm CapaDePresentacion/Controladores/*.class

 echo ###############

 echo # He acabado! #

 echo ###############
