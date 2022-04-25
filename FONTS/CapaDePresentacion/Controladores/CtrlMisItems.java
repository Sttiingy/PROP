package FONTS.CapaDePresentacion.Controladores;

import java.util.Vector;

public class CtrlMisItems {
    private CtrlPresentacion CP;

    public CtrlMisItems() {
        CP = new CtrlPresentacion();
    }

    public int exportFiles(String pathFolder) {
        return CP.exportFiles(pathFolder);
    }

    public Vector<String[]> getMyItems() {
        return CP.getRatedItems();
    }


    public boolean setRecommendationMode(String rm) {
        return CP.setRecommendationMode(rm);
    }
}
