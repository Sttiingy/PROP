package FONTS.CapaDePresentacion.Controladores;

import java.io.IOException;

public class CtrlMenuInicial {
    private CtrlPresentacion CP;

    public CtrlMenuInicial() {
        CP = new CtrlPresentacion();
    }

    //Retornará 0 si el usuario y la contraseña son correctos, 1 si no lo son.
    public int login(String username, String password) {
        return CP.login(username, password);
    }


    // 0 := se ha realizado el import correctamente
    // 1 := no existen los archivos ratings.test.known.csv o ratings.test.unknown.csv
    // 2 := el formato de los archivos está mal
    public int importFiles(String pathFolder, int multiplier) throws IOException {
        return CP.importFiles(pathFolder, multiplier);
    }

    public String getTotalGestor() {
        return CP.getTotalGestor();
    }

    public boolean swapCurrentGestor(String gestorNumber) {
        return CP.swapCurrentGestor(gestorNumber);
    }

    public String getCurrentGestor() {
        return CP.getCurrentGestor();
    }
}


