
package FONTS.CapaDeDatos.Gestores;

import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorGestorItem;
import FONTS.CapaDeDominio.DomainModel.*;
import java.util.*;

public class GestorItem implements AdaptadorGestorItem {
    private TreeMap<Integer, Item> itemsBD;
    private String[] types;

    //Funciones Item
    public GestorItem() {
        itemsBD = new TreeMap<>();
    }

    public Vector<String[]> getItemsBD() {
        Vector<String[]> cjtItems = new Vector<>();
        int posID = getPosID();
        for (Map.Entry<Integer, Item> entry : itemsBD.entrySet()) {
            cjtItems.add(entry.getValue().ToStringExport(posID, types.length));
        }
        return cjtItems;
    }

    private int getPosID() {
        for (int i = 0; i < types.length; ++i) {
            if (types[i].equals("id")) return i;
        }
        return -1;
    }

    public String[] getTypes() {
        return types;
    }

    public int getSizeIt() {
        return itemsBD.size();
    }

    public Item getItem(int IdItem) {
        return itemsBD.get(IdItem);
    }

    public TreeMap<Integer, Item> getItemsTree() {
        return itemsBD;
    }

    public void insertItem(Item item) {
        itemsBD.put(item.getItemID(), item);
    }

    public void deleteItem(int id) {
        itemsBD.remove(id);
    }

    public boolean existsItem(int id) {
        return itemsBD.containsKey(id);
    }

    //Funciones Valoraciones

    public void insertRating(Valoracion v) {
        itemsBD.get(v.getItemID()).addValoracion(v);
    }

    public void delValoracion(int itemID, int userID, double rating) {
        itemsBD.get(itemID).delValoracion(userID, rating);
    }

    public Valoracion getRating(int itemID, int userID) {
        return itemsBD.get(itemID).getValoracion(userID);
    }

    public int getSizeRatings(int itemID) {
        return itemsBD.get(itemID).getSizeVal();
    }

    public double getAvgRatings(int itemID) {
        return itemsBD.get(itemID).getAvgRating();
    }

    //Funciones inicializacion

    public boolean fillItemsRatings(Vector<String[]> items, int multiplier) {
        try {
            //uso estas variables porque en los conjuntos de datos, la posicion de los IDs puede cambiar
            int posUserID = 0;
            int posItemID = 0;
            int posRating = 0;
            boolean first = true;
            for (String[] s : items) {
                if (!first) {
                    int id = Integer.parseInt(s[posItemID]);
                    if (s[posItemID] != null) {
                        if (!existsItem(id)) {
                            Item item = new Item(s[posItemID]);
                            insertItem(item);
                        }
                        //siempre se va a insertar una nueva valoracion asi que no hace falta else
                        itemsBD.get(id).addValoracion(new Valoracion(s[posUserID], s[posItemID], Double.parseDouble(s[posRating]) * multiplier));
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

    //relleno la base de datos con el Vector<String[]> obtenido de items.csv
    public boolean fillItemsAtributos(Vector<String[]> items) {
        try {
            types = new String[items.get(0).length];
            int posID = 0;
            boolean first = true;
            for (String[] item : items) {
                if (!first) {
                    if (item != null) {
                        //busco la posicion del ID
                        //añado atributos
                        int itemID = Integer.parseInt(item[posID]);
                        //lo inserto si no esta dentro aun
                        if (!existsItem(itemID)) insertItem(new Item(item[posID]));
                        for (int j = 0; j < item.length; j++) {
                            if (posID != j) {
                                //aqui puedo añadir atributos (hay que mirar de que tipo es)
                                String[] aux = new String[2];
                                for (String split : item[j].split(";")) {
                                    if (types != null) {
                                        aux[0] = types[j];
                                        aux[1] = split;
                                    }
                                    Atributo atr = new Atributo(aux);
                                    AtrType type = atr.getTipo();
                                    switch (type) {
                                        case Doble:
                                            atr = new Doble(aux);
                                            break;

                                        case Entero:
                                            atr = new Entero(aux);
                                            break;

                                        case Booleano:
                                            atr = new Booleano(aux);
                                            break;

                                        case Strings:
                                            atr = new Strings(aux);
                                            break;
                                    }
                                    itemsBD.get(itemID).addAtributo(atr);
                                }
                            }
                        }
                    }
                } else {
                    if (item != null) {
                        //busco el id puesto que puede cambiar de posicion en los archivos
                        types = item;
                        for (int i = 0; i < item.length; i++) {
                            if (item[i].equals("id")) posID = i;
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
}
