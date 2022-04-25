package FONTS.CapaDeDominio.Controladores;

import FONTS.CapaDeDominio.DomainModel.Item;
import FONTS.CapaDeDominio.DomainModel.User;
import FONTS.CapaDeDominio.DomainModel.Valoracion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

//Esta clase es singleton
public class CtrlDominio {

    private static CtrlDominio instance = null;
    private CtrlDominioRecomendaciones DCmjR;
    private CtrlComDatos CCD;
    private CtrlStringConversion CSC;


    private CtrlDominio() {
        CSC = new CtrlStringConversion();
        DCmjR = new CtrlDominioRecomendaciones();
        CCD = new CtrlComDatos();
    }

    public static CtrlDominio getInstance() {
        if (instance == null) instance = new CtrlDominio();
        return instance;
    }

    //CtrlComDatos

    public boolean swapCurrentGestor(String gestorNumber) {
        return CCD.swapCurrentGestor(gestorNumber);
    }

    public String getCurrentGestor() {
        return CCD.getCurrentGestor();
    }

    public String getTotalGestor() {
        return CCD.getTotalGestor();
    }

    public void rateItem(int itemID, double score) {
        CCD.rateItem(itemID, score);
        //si al ratear un item el usuario activo pertenece a un cluster con pocos usuarios se cambia al modo de
        // recomendacion suited para evitar errores al recomendar.
        if(DCmjR.getUserClusterSize(CCD.getActiveUser()) < 10 && DCmjR.getRecomendationModeString().equals("Collaborative")) setRecommendationMode("Suited");
    }

    public boolean deleteRating (int itemID) {
        boolean ratingExisted = CCD.deleteRating(itemID);
        //si al usuario activo le dejan de gustarle todos los items al eliminar una valoracion, o se cambia a un cluster vacio,
        //se cambian las recomendaciones a modo suited para que no de error al recomendar.
        User activeUser = CCD.getActiveUser();
        String recM = DCmjR.getRecomendationModeString();
        if ((activeUser.getListMetrics().get(1) == 0. && recM.equals("Knn")) || (DCmjR.getUserClusterSize(CCD.getActiveUser()) < 10 && recM.equals("Collaborative"))) setRecommendationMode("Suited");
        return ratingExisted;
    }

    // retorna 0 si username y pass correctos;
    // retorna 1 si username o pass vacíos
    // retorna 2 si el username ya existe
    public int saveChanges(String newUsername, String newPassword) {return CCD.saveChanges(newUsername, newPassword);}

    // retorna 0 si username y pass correctos;
    // retorna 1 si el pass es incorrecto;
    // retorna 2 si el username no existe en el conjunto de datos actual
    // retorna 3 si username o password vacios
    public int login(String username, String password) {
        int correctLogin = CCD.login(username, password);
        if(correctLogin == 0) {
            initializeClusters();
        }
        return correctLogin;
    }

    // retorna 0 si username y pass correctos
    // retorna 1 si el username ya existe en el conjunto de datos actual
    // retorna 2 si username o password son null
    public int signup(String username, String password) {
        int correctSignup = CCD.signup(username, password);
        if(correctSignup == 0) {
            initializeClusters();
        }
        return correctSignup;
    }

    public boolean initializeBD(String pathFolder) {
        return CCD.initializeBD(pathFolder + "/ratings.db.csv", pathFolder + "/items.csv");
    }


    // 0 := se ha realizado el import correctamente
    // 1 := no existen los archivos necesarios
    // 2 := el formato de los archivos está mal
    public int importFiles(String pathFolder, int multiplier) throws IOException {
        if (CCD.gestorInUse()) {
            CCD.createNewGestor();
        }

        int flag = 0;
        int correctImpRatings = CCD.importRatings(pathFolder + "/ratings.db.csv", multiplier);
        int correctImpItems = CCD.importItems(pathFolder + "/items.csv");

        if (correctImpItems != 0 || correctImpRatings != 0) {
            flag = Integer.max(correctImpItems, correctImpRatings);
        }

        if (flag == 0) {
            CCD.importUserData(pathFolder + "/dataUsers.csv");
            Vector<String[]> known;
            Vector<String[]> unknown;
            boolean existKnownUnknown = CCD.existsFile(pathFolder + "/ratings.test.known.csv") && CCD.existsFile(pathFolder + "/ratings.test.unknown.csv");
            if (!existKnownUnknown) {
                flag = 1;
            }
            if (flag == 0) {
                known = CCD.readFile(pathFolder + "/ratings.test.known.csv");
                unknown = CCD.readFile(pathFolder + "/ratings.test.unknown.csv");
                TreeMap<Integer, Item> items = CCD.getItemsUsers(known);
                ArrayList<User> knownBD = CSC.arrayToUserList(known, items);
                ArrayList<ArrayList<Valoracion>> unknownBD = CSC.arrayToRatingMatrix(unknown);
                if (knownBD == null || unknownBD == null) {
                    flag = 2;
                }
                CCD.importKnownUnknown(knownBD, unknownBD);
            }
        }
        else CCD.deleteGestor();
        return flag;
    }

    public void exportFiles(String pathFolder) throws IOException {
        CCD.exportFiles(pathFolder);
    }

    public String getRating(String itemID) {
        return CCD.getRating(itemID);
    }

    public boolean isRated(String itemID) {
        return CCD.isRated(itemID);
    }

    public Vector<String[]> getRatedItems() {
        return CSC.itemArrayToStringVector(CCD.getRatedItems());
    }

    //retorna si se puede hacer Test para el usuario dependiendo si se encuentra en known
    public boolean setTest(boolean test) {
        User activeUser =  CCD.getActiveUser();
        ArrayList<User> knownUsers = CCD.getKnown();
        boolean knownUser = false;
        //miramos si el usuario activo existe en known y si es asi, encontramos su posicion en el arraylist
        for (User user : knownUsers) {
            if (user.getUserID() == activeUser.getUserID()) {
                knownUser = true;
                break;
            }
        }
        if(knownUser) DCmjR.setTest(test);
        return knownUser;
    }

    //ControladorRecomendaciones
    public String getRecomendationMode () {
        return DCmjR.getRecomendationModeString();
    }

    //devuelve un booleano que indica si se ha cambiado con exito
    public boolean setRecommendationMode(String rm) {
        User activeUser = CCD.getActiveUser();
        if(rm.equals("Knn") && activeUser.getListMetrics().get(1) == 0.) return false;
        else if (rm.equals("Collaborative") && (DCmjR.getUserClusterSize(activeUser) < 10 || activeUser.getValSize() < 5)) return false;
        DCmjR.setRecommendationMode(rm);
        return true;
    }

    public Vector<String[]> getRecomendaciones(int k) {
        User activeUser =  CCD.getActiveUser();

        ArrayList<User> knownUsers = CCD.getKnown();
        boolean knownUser = false;
        int iterador = 0;
        //miramos si el usuario activo existe en known y si es asi, encontramos su posicion en el arraylist
        for(; iterador < knownUsers.size() && !knownUser; iterador++) {
            if(knownUsers.get(iterador).getUserID() == activeUser.getUserID()) knownUser = true;
        }
        //si el usuario existe en known y esta el modo test activado, se recomiendan los items de unknown y se devuelve la calidad de las recomendaciones
        if(knownUser && DCmjR.getTest()){
            ArrayList<ArrayList<Valoracion>> unknown = CCD.getUnknown();
            TreeMap<Integer, Item> itemsKnownUnknownUser = new TreeMap<>();

            for(Valoracion val : activeUser.getValColl()){
                Item itemValUnknown = CCD.getItemsTree().get(val.getItemID());
                if(itemValUnknown != null) itemsKnownUnknownUser.put(itemValUnknown.getItemID(), itemValUnknown);
            }
            for(Valoracion val : unknown.get(iterador)) {
                Item itemValUnknown = CCD.getItemsTree().get(val.getItemID());
                if(itemValUnknown != null) itemsKnownUnknownUser.put(itemValUnknown.getItemID(), itemValUnknown);
            }


            ArrayList<Valoracion> resultadosTest = DCmjR.getItemsRecomendadosTest(activeUser, unknown.get(iterador), itemsKnownUnknownUser);
            double[] calidades = DCmjR.calidadRecomendaciones(resultadosTest);

            //cojo las Q (random int) primeras recomendaciones, nunca menor que 3 (para enseñar algunas recomendaciones siempre)
            double random = Math.random();
            random =  random * resultadosTest.size();
            int randomInt = (int) random;
            if(randomInt < 3) randomInt = 3;
            for (int j = resultadosTest.size()-1; j > randomInt; --j) {
                resultadosTest.remove(j);
            }
            ArrayList<Item> itemResults = new ArrayList<>();
            for(Valoracion val : resultadosTest) {
                itemResults.add(itemsKnownUnknownUser.get(val.getItemID()));
            }
            String[] notQuality = {"---MODOTEST---", String.valueOf(calidades[2])};
            Vector<String[]> itemsString = CSC.itemArrayToStringVector(itemResults);
            itemsString.add(0, notQuality);
            return itemsString;
            }
        //si no, se recomienda de manera normal al usuario activo
        else {
            ArrayList<Item> recommendedItems = DCmjR.getRecomendacionesNormales(CCD.getActiveUser(), CCD.getItemsTree(), k);
            String[] notQuality = {"---NOTMODOTEST---"};
            Vector<String[]> itemsString = CSC.itemArrayToStringVector(recommendedItems);
            itemsString.add(0, notQuality);
            return itemsString;
        }
    }

    public Vector<String[]> getKSimilarItems(int itemID, int k) {
        ArrayList<Item> kRecommendedItems = DCmjR.getKSimilarItems(CCD.getItem(itemID), CCD.getItemsTree(), k);
        return CSC.itemArrayToStringVector(kRecommendedItems);
    }

    public void initializeClusters () {
        DCmjR.kmeans(CCD.getUsers());
    }

    public void cargarActiveUser(User activeUser) {
        CCD.setActiveUser(activeUser);
    }

    public String[] getAUToString() {
        return CCD.getAUToString();
    }





    //Tests primera entrega


    public Vector<String[]> getOutputQueriesTest(String pathFolder) throws IOException {
        Vector<String[]> known = CCD.readFile(pathFolder + "/ratings.test.known.csv");
        TreeMap<Integer, Item> items = CCD.getItemsUsers(known);
        ArrayList<User> users = CSC.arrayToUserList(known, items);


        Vector<String[]> unknown = CCD.readFile(pathFolder + "/ratings.test.unknown.csv");
        ArrayList<ArrayList<Valoracion>> ratings = CSC.arrayToRatingMatrix(unknown);

        ArrayList<ArrayList<Valoracion>> arrayQueries = getAllRecomendacionesTest(users, ratings);
        Vector<String[]> output = CSC.arrayQueriesToString(arrayQueries);
        CCD.writeFile(null, pathFolder + "/outputqueries.txt", output);
        return output;
    }


    //algoritmo de la primera entrega en el que entra el inputqueries y saca el outputqueries
    public ArrayList<ArrayList<Valoracion>> getAllRecomendacionesTest(ArrayList<User> usersQueries, ArrayList<ArrayList<Valoracion>> valUnknown) {
        double sumICG = 0;
        double sumIDCG = 0;
        ArrayList<ArrayList<Valoracion>> outPutQueriesList = new ArrayList<>();

        for(int i = 0; i < usersQueries.size(); ++i) {
            cargarActiveUser(usersQueries.get(i));
            ArrayList<Valoracion> prediccionRatings = getRecomendacionesTest(valUnknown.get(i+1));
            double[] calidades = DCmjR.calidadRecomendaciones(prediccionRatings);

            sumICG += calidades[0];
            sumIDCG += calidades[1];
            //cojo las Q (random int) primeras recomendaciones
            double random = Math.random();
            random =  random * prediccionRatings.size();
            int randomInt = (int) random;
            for (int j = prediccionRatings.size()-1; j > randomInt; --j) {
                prediccionRatings.remove(j);
            }
            outPutQueriesList.add(prediccionRatings);
        }
        System.out.println("Calidad total recomendaciones: " + sumICG + "\nCalidad total optima: " + sumIDCG + "\nCalidad total normalizada: " + sumICG/sumIDCG);
        return outPutQueriesList;
    }

    public ArrayList<Valoracion> getRecomendacionesTest(ArrayList<Valoracion> valUnknown) {
        TreeMap<Integer, Item> itemsUnknownUser = new TreeMap<>();
        for(Valoracion val : valUnknown) {
            for(Item item : CCD.getItemsTree().values()) {
                if(val.getItemID() == item.getItemID()) itemsUnknownUser.put(item.getItemID(), item);
            }
        }
        return DCmjR.getItemsRecomendadosTest(CCD.getActiveUser(), valUnknown, itemsUnknownUser);
    }
}