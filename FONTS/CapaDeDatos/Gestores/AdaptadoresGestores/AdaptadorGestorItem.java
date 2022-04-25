package FONTS.CapaDeDatos.Gestores.AdaptadoresGestores;

import FONTS.CapaDeDominio.DomainModel.Item;
import FONTS.CapaDeDominio.DomainModel.Valoracion;
import java.util.TreeMap;
import java.util.Vector;

public interface AdaptadorGestorItem {

    int getSizeIt();

    Item getItem(int IdItem);

    void insertItem(Item item);

    void deleteItem(int id);

    boolean existsItem(int id);

    void insertRating(Valoracion v);

    void delValoracion(int itemID, int userID, double rating);

    Valoracion getRating(int itemID, int userID);

    int getSizeRatings(int itemID);

    double getAvgRatings(int itemID);

    boolean fillItemsRatings(Vector<String[]> items, int multiplier);

    boolean fillItemsAtributos(Vector<String[]> items);

    String[] getTypes();

    Vector<String[]> getItemsBD();

    TreeMap<Integer, Item> getItemsTree();
}
