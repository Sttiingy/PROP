package FONTS.CapaDePresentacion.Controladores;

import java.util.Vector;

public class CtrlHome {
    private CtrlPresentacion CP;

    public CtrlHome() {
        CP = new CtrlPresentacion();
    }

    public int exportFiles(String pathFolder) {
        return CP.exportFiles(pathFolder);
    }

    public Vector<String[]> getRecomendedItems() {
        return CP.getRecomendedItems();
    }

    public boolean setRecommendationMode(String rm) {
        return CP.setRecommendationMode(rm);
    }

    public boolean setTest(boolean test) {
        return CP.setTest(test);
    }

}
