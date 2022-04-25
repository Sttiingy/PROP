package FONTS.CapaDeDatos.Gestores.AdaptadoresGestores;

import FONTS.CapaDeDominio.DomainModel.User;
import FONTS.CapaDeDominio.DomainModel.Valoracion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

public interface AdaptadorGestorUser {

    ArrayList<User> getKnown();

    void setKnown(ArrayList<User> known);

    ArrayList<ArrayList<Valoracion>> getUnknown();

    void setUnknown(ArrayList<ArrayList<Valoracion>> unknown);

    Vector<String[]> getUsersBD();

    Vector<String[]> getRatingsUsersBD();

    User getUser(String username);

    int setPassword(String username, String NewPassword);

    int getNextAvailableUserID();

    void insertUser(User user);

    boolean existsUser(int userID);

    boolean existsUser(String username);

    User getUser(int userID);

    String getUsername(int userID);

    String getPassword(int userID);

    String getPassword(String user);

    int getSizeUsers();

    HashSet<User> getUsers();

    boolean existsRating(int itemID, int userID);

    Valoracion getRating(int itemID, int userID);

    int getSizeRatings(int itemID);

    boolean fillUsersRatings(Vector<String[]> data, int multiplier);

    void printUsersBD();

    boolean fillUsersData(Vector<String[]> userData);

    Vector<String[]> getKnownString();

    Vector<String[]> getUnknownString();
}
