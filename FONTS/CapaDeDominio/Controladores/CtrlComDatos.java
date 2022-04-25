package FONTS.CapaDeDominio.Controladores;

import FONTS.CapaDeDatos.Gestores.ActiveUser;
import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorGestorIO;
import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorGestorItem;
import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorGestorUser;
import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorUsuarioActivo;
import FONTS.CapaDeDatos.Gestores.GestorIO;
import FONTS.CapaDeDatos.Gestores.GestorItem;
import FONTS.CapaDeDatos.Gestores.GestorUsuario;
import FONTS.CapaDeDominio.DomainModel.Item;
import FONTS.CapaDeDominio.DomainModel.User;
import FONTS.CapaDeDominio.DomainModel.Valoracion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Vector;

public class CtrlComDatos {
    private ArrayList<AdaptadorGestorUser> GU;
    private ArrayList<AdaptadorGestorItem> GI;
    private AdaptadorGestorIO GIO;
    private AdaptadorUsuarioActivo AU;
    private int currentGestor;

    public CtrlComDatos() {
        currentGestor = -1;
        GI = new ArrayList<>();
        GU = new ArrayList<>();
        //GI.add(new GestorItem());
        //GU.add((new GestorUsuario(GI.get(currentGestor))));
        GIO = new GestorIO();
        AU = new ActiveUser();
    }

    public boolean swapCurrentGestor(String gestorNumber) {
        int newGestor = Integer.parseInt(gestorNumber);
        if (newGestor < GI.size() && newGestor < GU.size()) {
            currentGestor = newGestor;
            return true;
        }
        return false;
    }

    public String getCurrentGestor() {
        return String.valueOf(currentGestor);
    }

    public String getTotalGestor() {
        return String.valueOf(GI.size());
    }

    public boolean gestorInUse() {
        return currentGestor == -1 || GI.get(currentGestor).getSizeIt() > 0;
    }

    public void createNewGestor() {
        currentGestor = GI.size();
        GI.add(new GestorItem());
        GU.add((new GestorUsuario(GI.get(currentGestor))));
    }

    public void deleteGestor() {
        int size = GI.size();
        GU.remove(currentGestor);
        GI.remove(currentGestor);
        if(size == 1) {
            currentGestor = -1;
        }
        else {
            currentGestor--;
        }
    }

    //Funciones GestorUsers

    public User getUser(int userID) {
        return GU.get(currentGestor).getUser(userID);
    }

    public boolean insertUser(User user) {
        if (!existsUser(user.getUserName())) {
            GU.get(currentGestor).insertUser(user);
            return true;
        }
        return false;
    }

    public boolean existsUser(String username) {
        return GU.get(currentGestor).existsUser(username);
    }

    public String getPassword(String username) {
        if (existsUser(username)) {
            return GU.get(currentGestor).getPassword(username);
        }
        else return null;
    }

    public HashSet<User> getUsers() {
        return GU.get(currentGestor).getUsers();
    }

    public int getNextAvailableUserID() {
        return GU.get(currentGestor).getNextAvailableUserID();
    }

    //Funciones GestorItems

    public TreeMap<Integer, Item> getItemsTree() { return GI.get(currentGestor).getItemsTree(); }

    public Item getItem(int itemID) {
        return GI.get(currentGestor).getItem(itemID);
    }

    public boolean existsItem(int itemID) {
        return GI.get(currentGestor).existsItem(itemID);
    }

    //retorna la lista items valorados por una lista de usuarios
    public TreeMap<Integer,Item> getItemsUsers(Vector<String[]> list) {
        TreeMap<Integer,Item> res = new TreeMap<>();
        int posItemID = 0;
        boolean first = true;
        for (String[] s : list) {
            if (!first) {
                Item item = GI.get(currentGestor).getItem(Integer.parseInt(s[posItemID]));
                res.put(item.getItemID(), item);
            }
            else {
                //busco el itemId puesto que puede cambiar de posicion en los archivos
                for (int i = 0; i < s.length; i++) {
                    if (s[i].equals("itemId")) posItemID = i;
                }
                first = false;
            }
        }
        return res;
    }

    //Funciones Gestores Datos

    public Vector<String[]> readFile(String path) throws IOException {
        return GIO.readFile(path);
    }

    public void writeFile(String[] header, String path, Vector<String[]> data) throws IOException {
        GIO.writeFile(header, path, data);
    }

    public void fillUsers(Vector<String[]> ratings) throws IOException {
        GU.get(currentGestor).fillUsersRatings(ratings, 1);
    }

    public boolean initializeBD(String pathRatings, String pathItems) {
        boolean b1, b2, b3;
        b1 = b2 = b3 = false;
        try {
            Vector<String[]> ratings = GIO.readFile(pathRatings);
            Vector<String[]> items = GIO.readFile(pathItems);

            b1 = GI.get(currentGestor).fillItemsRatings(ratings, 1);
            b2 = GI.get(currentGestor).fillItemsAtributos(items);
            b3 = GU.get(currentGestor).fillUsersRatings(ratings, 1);

            return (b1 && b2 && b3);
        }

        catch (IOException e) {
            return false;
        }
    }

    public void importUserData(String path) throws IOException {
        if (GIO.existsFile(path)) {
            Vector<String[]> userData = GIO.readFile(path);
            GU.get(currentGestor).fillUsersData(userData);
        }
    }

    public int importRatings(String path, int multiplier) throws IOException {
        if (GIO.existsFile(path)) {
            Vector<String[]> ratings = GIO.readFile(path);
            boolean b1 = GU.get(currentGestor).fillUsersRatings(ratings, multiplier);
            boolean b2 = GI.get(currentGestor).fillItemsRatings(ratings, multiplier);
            if (b1 && b2) return 0;
            return 2;
        }
        return 1;
    }

    public boolean existsFile(String path) {
        return GIO.existsFile(path);
    }

    public void importKnownUnknown(ArrayList<User> known, ArrayList<ArrayList<Valoracion>> unknown) {
        GU.get(currentGestor).setKnown(known);
        GU.get(currentGestor).setUnknown(unknown);
    }

    public int importItems(String path) throws IOException {
        if (existsFile(path)) {
            Vector<String[]> items = GIO.readFile(path);
            if (GI.get(currentGestor).fillItemsAtributos(items)) return 0;
            return 2;
        }
        return 1;
    }

    public void exportFiles(String pathFolder) throws IOException {
        Vector<String[]> userNamePass = GU.get(currentGestor).getUsersBD();
        Vector<String[]> ratingsUsers = GU.get(currentGestor).getRatingsUsersBD();
        Vector<String[]> known = GU.get(currentGestor).getKnownString();
        Vector<String[]> unknown = GU.get(currentGestor).getUnknownString();
        Vector<String[]> items = GI.get(currentGestor).getItemsBD();

        String[] h1 = {"userId", "itemId", "rating"};
        String[] h2 = GI.get(currentGestor).getTypes();
        String[] h3 = {"userId", "username", "password"};
        GIO.writeFile(h1, pathFolder + "/ratings.db.csv", ratingsUsers);
        GIO.writeFile(h2, pathFolder + "/items.csv", items);
        GIO.writeFile(h3, pathFolder + "/dataUsers.csv", userNamePass);
        GIO.writeFile(h1, pathFolder + "/ratings.test.known.csv", known);
        GIO.writeFile(h1, pathFolder + "/ratings.test.unknown.csv", unknown);
    }

    public ArrayList<User> getKnown() {
        return GU.get(currentGestor).getKnown();
    }

    public ArrayList<ArrayList<Valoracion>> getUnknown() {
        return GU.get(currentGestor).getUnknown();
    }

    // retorna 0 si username y pass correctos
    // retorna 1 si el username ya existe en el conjunto de datos actual
    // retorna 2 si username o password son null
    public int signup(String username, String password) {
        if (!((username.equals("") || password.equals("")) || (username.equals(" ") || password.equals(" ")))) {
            int id = getNextAvailableUserID();
            User user = new User(id, username, password);
            if (insertUser(user)) {
                setActiveUser(user);
                return 0;
            }
            else return 1;
        }
        return 2;
    }

    // retorna 0 si username y pass correctos;
    // retorna 1 si el pass es incorrecto;
    // retorna 2 si el username no existe en el conjunto de datos actual
    // retorna 3 si username o password vacios
    public int login(String username, String password) {
        if (!((username.equals("") || password.equals("")) || (username.equals(" ") || password.equals(" ")))) {
            String dataPassword = getPassword(username);
            if (dataPassword != null) {
                if (dataPassword.equals(password)) {
                    User user = GU.get(currentGestor).getUser(username);
                    if (user != null) {
                        setActiveUser(user);
                        return 0;
                    }
                    else return 2;
                }
                else return 1;
            }
            return 2;
        }
        else return 3;
    }

    // retorna 0 si username y pass correctos;
    // retorna 1 si username o pass vacíos
    // retorna 2 si el username ya existe
    public int saveChanges(String newUsername, String newPassword) {
        if (!((newUsername.equals("") || newPassword.equals("")) || (newUsername.equals(" ") || newPassword.equals(" ")))) {
            if (!GU.get(currentGestor).existsUser(newUsername)) {
                AU.setUsername(newUsername);
                AU.setPassword(newPassword);
                return 0;
            }
            return 2;
        }
        return 1;
    }

    //ActiveUser

    public void setActiveUser(User activeUser) {
        AU.setActiveUser(activeUser);
    }


    public User getActiveUser () {
        return AU.getActiveUser();
    }

    public String[] getAUToString() {
        return AU.getAUToString();
    }

    public void rateItem(int itemID, double score) {
        GI.get(currentGestor).insertRating(new Valoracion(AU.getActiveUserID(), itemID, score));
        AU.rateItem(GI.get(currentGestor).getItem(itemID), score);
    }

    public boolean isRated(String itemID) {
        return AU.isRated(Integer.parseInt(itemID));
    }

    public String getRating(String itemID) {
        return String.valueOf(AU.getRating(Integer.parseInt(itemID)));
    }

    public boolean deleteRating(int itemID) {
        User activeUser = AU.getActiveUser();
        if(activeUser.existsVal(itemID)) {
            double ratingUser = activeUser.getRatingItemID(itemID);
            AU.deleteRating(GI.get(currentGestor).getItem(itemID));
            GI.get(currentGestor).delValoracion(itemID,activeUser.getUserID(), ratingUser);
            return true;
        }
        return false;
    }

    public ArrayList<Item> getRatedItems() {
        ArrayList<Integer> listID = AU.getRatedItems();
        ArrayList<Item> listItem = new ArrayList<>(listID.size());
        for (int id : listID) {
            listItem.add(GI.get(currentGestor).getItem(id));
        }
        return listItem;
    }
}