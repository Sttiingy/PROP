@echo off

::jar cvmf Manifest.mf NombreJAR.jar ClassNecesarios.class

javac -cp .;lib\junit-4.12.jar;lib\commons-lang3-3.12.0\*.jar;lib\opencsv-5.5.2.jar CapaDeDatos\Gestores\AdaptadoresGestores\*.java CapaDeDatos\Gestores\*java CapaDeDominio\DomainModel\*.java CapaDeDominio\Controladores\*.java CapaDePresentacion\Controladores\*.java CapaDePresentacion\Vistas\*.java CapaDePresentacion\*.java

jar -cvmf ..\EXE\Main_jar\temp_windows.mf ..\EXE\Main_jar\Main_Windows.jar ..\FONTS\CapaDeDatos\Gestores\*.class ..\FONTS\CapaDeDatos\Gestores\AdaptadoresGestores\*.class ..\FONTS\CapaDeDominio\DomainModel\*.class ..\FONTS\CapaDeDominio\Controladores\*.class ..\FONTS\lib ..\FONTS\CapaDePresentacion\*.class ..\FONTS\CapaDePresentacion\Controladores\*.class ..\FONTS\CapaDePresentacion\Vistas\*.class

echo Borrando archivos .class ...

del CapaDePresentacion\*.class

del CapaDeDatos\Gestores\AdaptadoresGestores\*.class

del CapaDeDatos\Gestores\*.class

del CapaDeDominio\Controladores\*.class

del CapaDeDominio\DomainModel\*.class

del CapaDePresentacion\Vistas\*.class

del CapaDePresentacion\Controladores\*.class

echo ###############

echo # He acabado! #

echo ###############

echo Pulsa cualquier tecla para finalizar

pause>nul

exit