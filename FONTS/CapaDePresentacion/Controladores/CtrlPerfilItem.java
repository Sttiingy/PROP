package FONTS.CapaDePresentacion.Controladores;

import java.util.Vector;

public class CtrlPerfilItem {
    private CtrlPresentacion CP;
    public CtrlPerfilItem() {
        CP = new CtrlPresentacion();
    }


    private boolean validRating(double score) {
        return score <= 10 && score >= 0 && score%0.5 == 0;
    }

    public int exportFiles(String pathFolder) {
        return CP.exportFiles(pathFolder);
    }

    public Vector<String[]> getRecomendedItems() {
        return CP.getRecomendedItems();
    }

    //0 := se ha valorado correctamente
    //1 := la valoracion introducida no tiene el formato correcto
    public int rateItem(int itemID, double score) {
        if (validRating(score)) {
            CP.rateItem(itemID, score);
            return 0;
        }
        return 1;
    }

    public String getRating(String itemID) {
        return CP.getRating(itemID);
    }

    public boolean isRated(String itemID) {
        return CP.isRated(itemID);
    }

    public Vector<String[]> getKNNItems(String id, int k) {
        return CP.getKSimilarItems(Integer.parseInt(id), k);
    }

    public boolean deleteRating(String itemID) {
        return CP.deleteRating(itemID);
    }


}
