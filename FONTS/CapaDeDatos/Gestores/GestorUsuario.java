package FONTS.CapaDeDatos.Gestores;

import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorGestorItem;
import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorGestorUser;
import FONTS.CapaDeDominio.DomainModel.Item;
import FONTS.CapaDeDominio.DomainModel.User;
import FONTS.CapaDeDominio.DomainModel.Valoracion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class GestorUsuario implements AdaptadorGestorUser {
    private TreeMap<Integer, User> usersBD;
    private AdaptadorGestorItem GI;
    private ArrayList<User> known;
    private ArrayList<ArrayList<Valoracion>> unknown;

    public GestorUsuario(AdaptadorGestorItem GI) {
        usersBD = new TreeMap<>();
        this.GI = GI;
        known = new ArrayList<>();
        unknown = new ArrayList<>();
    }

    public ArrayList<User> getKnown() {
        return known;
    }

    public void setKnown(ArrayList<User> known) {
        for (User user : known) {
            usersBD.put(user.getUserID(), user);
        }
        this.known = known;
    }

    public ArrayList<ArrayList<Valoracion>> getUnknown() {
        return unknown;
    }

    public void setUnknown(ArrayList<ArrayList<Valoracion>> unknown) {
        this.unknown = unknown;
    }

    public Vector<String[]> getKnownString() {
        Vector<String[]> known = new Vector<>();
        for (User user : this.known) {
            Vector<String[]> valUser = user.ToStringVal();
            for (String[] val : valUser) {
                known.add(val);
            }
        }
        return known;
    }

    public Vector<String[]> getUnknownString() {
        Vector<String[]> unknown = new Vector<>();
        for (ArrayList<Valoracion> listVal : this.unknown) {
            for (Valoracion val : listVal) {
                unknown.add(val.ToString());
            }
        }
        return unknown;
    }

    public Vector<String[]> getUsersBD() {
        Vector<String[]> cjtUsers = new Vector<>();
        for (Map.Entry<Integer, User> entry : usersBD.entrySet()) {
            cjtUsers.add(entry.getValue().dataToString());
        }
        return cjtUsers;
    }

    public Vector<String[]> getRatingsUsersBD() {
        Vector<String[]> cjtRatings = new Vector<>();
        for (Map.Entry<Integer, User> entry : usersBD.entrySet()) {
            Vector<String[]> valUser = entry.getValue().ToStringVal();
            for (String[] val : valUser) {
                cjtRatings.add(val);
            }
        }
        return cjtRatings;
    }

    public User getUser(String username) {
        for (Map.Entry<Integer, User> entry : usersBD.entrySet()) {
            if (entry.getValue().getUserName().equals(username)) return entry.getValue();
        }
        return null;
    }

    public void insertUser(User user) {
        usersBD.put(user.getUserID(), user);
    }

    public boolean existsUser(int userID) {
        return usersBD.containsKey(userID);
    }

    public boolean existsUser(String username) {
        for (Map.Entry<Integer, User> entry : usersBD.entrySet()) {
            if (entry.getValue().getUserName().equals(username)) return true;
        }
        return false;
    }

    public User getUser(int userID) {
        return usersBD.get(userID);
    }

    public String getUsername(int userID) {
        return usersBD.get(userID).getUserName();
    }

    public String getPassword(int userID) {
        return usersBD.get(userID).getUserPassword();
    }

    public int setPassword(String username, String NewPassword) {
        for (Map.Entry<Integer, User> entry : usersBD.entrySet()) {
            if (entry.getValue().getUserName().equals(username)) {
                entry.getValue().setUserPassword(NewPassword);
                return 0;
            }
        }
        return 1;
    }

    public String getPassword(String username) {
        for (Map.Entry<Integer, User> entry : usersBD.entrySet()) {
            if (entry.getValue().getUserName().equals(username)) return entry.getValue().getUserPassword();
        }
        return null;
    }

    public int getSizeUsers() {
        return usersBD.size();
    }

    public HashSet<User> getUsers() {
        return new HashSet<>(usersBD.values());
    }

    public int getNextAvailableUserID() {
        int last = -1;
        if (!usersBD.isEmpty()) last = usersBD.firstKey() - 1;
        return last;
    }

    //Funciones Valoraciones

    public boolean existsRating(int itemID, int userID) {
        return usersBD.get(userID).existsVal(itemID);
    }

    public Valoracion getRating(int itemID, int userID) {
        return usersBD.get(userID).getItemRating(itemID);
    }

    public int getSizeRatings(int userID) {
        return usersBD.get(userID).getValSize();
    }

    public boolean fillUsersRatings(Vector<String[]> data, int multiplier) {
        try {
            //uso estas variables porque en los conjuntos de datos, la posicion de los IDs puede cambiar
            int posUserID = 0;
            int posItemID = 0;
            int posRating = 0;
            boolean first = true;
            for (String[] s : data) {
                if (!first) {
                    if (s != null) {
                        int id = Integer.parseInt(s[posUserID]);
                        if (!existsUser(id)) {
                            User u = new User(s[posUserID]);
                            insertUser(u);
                        }
                        //siempre se va a insertar una nueva valoracion asi que no hace falta else
                        if (GI.getSizeIt() != 0)
                            usersBD.get(id).rateItem(GI.getItem(Integer.parseInt(s[posItemID])), Double.parseDouble(s[posRating]));
                        else
                            usersBD.get(id).rateItem(new Item(Integer.parseInt(s[posItemID])), Double.parseDouble(s[posRating]) * multiplier);
                    }
                } else {
                    for (int i = 0; i < s.length; i++) {
                        String find = s[i];
                        switch (find) {
                            case "userId":
                                posUserID = i;
                                break;

                            case "itemId":
                                posItemID = i;
                                break;

                            case "rating":
                                posRating = i;
                                break;
                        }
                    }
                    first = false;
                }
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean fillUsersData(Vector<String[]> userData) {
        try {
            int posUserID = 0;
            int posUsername = 1;
            int posPassword = 2;
            boolean first = true;
            for (String[] user : userData) {
                if (!first) {
                    if (user != null) {
                        int id = Integer.parseInt(user[posUserID]);
                        if (!existsUser(id)) {
                            User newUser = new User(id, user[posUsername], user[posPassword]);
                            insertUser(newUser);
                        } else {
                            User existentUser = usersBD.get(id);
                            existentUser.setUserName(user[posUsername]);
                            existentUser.setUserPassword(user[posPassword]);
                        }
                    }
                } else first = false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void printUsersBD() {
        for (Map.Entry<Integer, User> entry : usersBD.entrySet()) {
            String[] user = entry.getValue().ToString();
            for (String parameters : user) System.out.println(parameters);
            System.out.println();
        }
    }
}