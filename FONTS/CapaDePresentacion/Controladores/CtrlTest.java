package FONTS.CapaDePresentacion.Controladores;

import java.util.Vector;

public class CtrlTest {
    private CtrlPresentacion CP;

    public CtrlTest() {
        CP = new CtrlPresentacion();
    }

    public boolean setTest(boolean test) {
        return CP.setTest(test);
    }

    public Vector<String[]> getRecomendedItems() {
        return CP.getRecomendedItems();
    }

    public int exportFiles(String pathFolder) {
        return CP.exportFiles(pathFolder);
    }


    public boolean setRecommendationMode(String rm) {
        return CP.setRecommendationMode(rm);
    }
}
