package FONTS.CapaDePresentacion.Controladores;

import FONTS.CapaDeDominio.Controladores.CtrlDominio;
import FONTS.CapaDePresentacion.Vistas.VistaMenuInicial;

import java.io.IOException;
import java.util.Vector;

public class CtrlPresentacion {
    private CtrlDominio CD;

    public CtrlPresentacion() {
        CD = CtrlDominio.getInstance();
    }

    public void initializePresentacion() {
        VistaMenuInicial menuInicial = new VistaMenuInicial();
    }

    //gestorNumber = numero de gestor al cual cambiaremos
    public boolean swapCurrentGestor(String gestorNumber) {
        return CD.swapCurrentGestor(gestorNumber);
    }

    //Devuelve el gestor actual
    public String getCurrentGestor() {
        return CD.getCurrentGestor();
    }

    //Num gestores que hay
    public String getTotalGestor() {
        return CD.getTotalGestor();
    }

    // retorna 0 si username y pass correctos;
    // retorna 1 si el username ya existe;
    // retorna 2 si username o password son null
    public int signup(String username, String password) {
        return CD.signup(username, password);
    }

    // retorna 0 si username y pass correctos;
    // retorna 1 si el pass es incorrecto;
    // retorna 2 si el username no existe
    // retorna 3 si username o password vacios
    public int login(String username, String password) {
        return CD.login(username, password);
    }

    // 0 := se ha realizado el import correctamente
    // 1 := no existen los archivos ratings.test.known.csv o ratings.test.unknown.csv
    // 2 := el formato de los archivos está mal
    public int importFiles(String pathFolder, int multiplier) throws IOException {
        int res = CD.importFiles(pathFolder, multiplier);
        if (res == 0) {
            CD.initializeClusters();
            return 0;
        }
        return res;
    }

    public Vector<String[]> getRecomendedItems() {
        return CD.getRecomendaciones(40);
    }

    //0 := se ha realizado el export correctamente
    //1 := el path esta mal
    public int exportFiles(String pathFolder) {
        try {
            CD.exportFiles(pathFolder);
            return 0;
        }
        catch (Exception e) {
            return 1;
        }
    }

    public boolean setTest(boolean test) {
        return CD.setTest(test);
    }

    public int saveChanges(String newUsername, String newPassword) {
        return CD.saveChanges(newUsername, newPassword);
    }

    public String[] getActiveUser() {
        return CD.getAUToString();
    }

    public void rateItem(int itemID, double score) {
        CD.rateItem(itemID, score);
    }

    public boolean setRecommendationMode(String rm) {
        return CD.setRecommendationMode(rm);
    }

    public String getRating(String itemID) {
        return CD.getRating(itemID);
    }

    public boolean isRated(String itemID) {
        return CD.isRated(itemID);
    }

    public Vector<String[]> getKSimilarItems(int itemID, int k) {
        return CD.getKSimilarItems(itemID, k);
    }

    public Vector<String[]> getRatedItems() {
        return CD.getRatedItems();
    }

    public boolean deleteRating(String itemID) {
        return CD.deleteRating(Integer.parseInt(itemID));
    }
}