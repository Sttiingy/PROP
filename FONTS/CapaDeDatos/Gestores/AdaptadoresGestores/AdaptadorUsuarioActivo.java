package FONTS.CapaDeDatos.Gestores.AdaptadoresGestores;

import FONTS.CapaDeDominio.DomainModel.Item;
import FONTS.CapaDeDominio.DomainModel.User;
import java.util.ArrayList;

public interface AdaptadorUsuarioActivo {

    User getActiveUser ();

    void setActiveUser(User activeUser);

    String[] getAUToString();

    void setPassword(String newPassword);

    void setUsername(String newUsername);

    int getActiveUserID();

    void rateItem(Item item, double score);

    boolean isRated(int itemID);

    double getRating(int parseInt);

    void deleteRating(Item item);

    ArrayList<Integer> getRatedItems();
}
