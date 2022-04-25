package FONTS.CapaDeDatos.Gestores;

import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorUsuarioActivo;
import FONTS.CapaDeDominio.DomainModel.Item;
import FONTS.CapaDeDominio.DomainModel.User;
import java.util.ArrayList;

public class ActiveUser implements AdaptadorUsuarioActivo {
    private User activeUser;

    public ActiveUser() {
        activeUser = new User();
    }

    public ActiveUser(User user) {
        this.activeUser = user;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public int getActiveUserID() {
        return activeUser.getUserID();
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public String[] getAUToString() {
        return activeUser.dataToString();
    }

    public void setPassword(String newPassword) {
        activeUser.setUserPassword(newPassword);
    }

    public void setUsername(String newUsername) {
        activeUser.setUserName(newUsername);
    }

    public void rateItem(Item item, double score) {
        activeUser.rateItem(item, score);
    }

    public void deleteRating (Item item) {
        activeUser.deleteRating(item);
    }

    public ArrayList<Integer> getRatedItems() {
        return activeUser.getRatedItems();
    }

    public boolean isRated(int itemID) {
        return activeUser.existsVal(itemID);
    }

    public double getRating(int itemID) {
        return activeUser.getRatingItemID(itemID);
    }
}
