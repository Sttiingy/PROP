package FONTS.CapaDeDominio.DomainModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {
    private int IdItem;
    private double sumVal;
    private List<Atributo> ListAtr;
    private Map<Integer,Valoracion> MapVal;

    public Item() {
        IdItem = -1;
        ListAtr = new ArrayList<>();
        MapVal = new HashMap<>();
        sumVal = 0;
    }

    public Item(int id) {
        IdItem = id;
        ListAtr = new ArrayList<>();
        MapVal = new HashMap<>();
        sumVal = 0;
    }

    public Item(int id, List<Atributo> lAtr, Map<Integer,Valoracion> mVal) {
        IdItem = id;
        ListAtr = lAtr;
        MapVal = mVal;
        sumVal = 0;
        Valoracion v;
        for (Map.Entry<Integer, Valoracion> it : MapVal.entrySet()) {
            sumVal += it.getValue().getPuntuacion();
        }
    }

    public Item(String data) {
        IdItem = Integer.parseInt(data);
        ListAtr = new ArrayList<>();
        MapVal = new HashMap<>();
        sumVal = 0;
    }

    public int getItemID() {
        return IdItem;
    }

    public void setItemID(int itemID) {
        IdItem = itemID;
        MapVal.forEach((k, v) -> v.setItemID(itemID));
    }

    public List<Atributo> getAtributo(String name) {
        List<Atributo> l = new ArrayList<>();
        for (Atributo atributo : ListAtr) {
            if (name.equals(atributo.getName())) l.add(atributo);
        }
        return l;
    }

    public void addAtributo(Atributo a) {
        ListAtr.add(a);
    }

    public void addValoracion(Valoracion v) {
        if (existsValoracion(v.getUserID())) {
            sumVal -= MapVal.get(v.getUserID()).getPuntuacion();
            delValoracion(v.getUserID(), MapVal.get(v.getUserID()).getPuntuacion());
        }
        MapVal.put(v.getUserID(), v);
        sumVal += v.getPuntuacion();
    }

    public void delValoracion(int userID, double rating) {
        sumVal -= rating;
        MapVal.remove(userID);
    }

    public boolean existsValoracion(int userID) {
        return MapVal.containsKey(userID);
    }

    public Valoracion getValoracion(int userID) {
        return MapVal.get(userID);
    }

    public double getAvgRating() {
        if (sumVal != 0) return sumVal / MapVal.size();
        return sumVal;
    }

    public int getSizeVal() {
        return MapVal.size();
    }

    public int getSizeAtr() {
        return ListAtr.size();
    }

    public List<Atributo> getAtributos () {
        return ListAtr;
    }


    public String[] ToString() {
        String[] item = new String[2 + ListAtr.size() + MapVal.size()];
        item[0] = Integer.toString(IdItem);
        item[1] = Double.toString(sumVal);

        int i;
        for (i = 2; i < ListAtr.size() + 2; i++) {
            item[i] = ListAtr.get(i - 2).ToString();
        }

        int j = i;
        for (Map.Entry<Integer, Valoracion> it : MapVal.entrySet()) {
            item[j] = String.valueOf(it.getValue().ToString());
            j++;
        }
        return item;
    }


    public String[] ToStringExport(int posID, int typesSize) {
        String[] item = new String[typesSize];

        int i;
        int dif = 0;
        String prevName = "";
        String data = "";
        int j = 0;
        for (i = 0; i < ListAtr.size() + 1; i++) {
            if (i == posID) {
                item[j] = String.valueOf(IdItem);
                dif++;
                j++;
            }
            else {
                //si tienen el mismo nombre
                if (prevName.equals(ListAtr.get(i - dif).getName())) {
                    //es el ultimo
                    if (i < ListAtr.size() + 1 && !(ListAtr.get(i - dif).getName().equals(prevName))) {
                        data += ListAtr.get(i - dif).dataToString();
                        item[j] = data;
                        data = "";
                        j++;
                    }
                    //no es el ultimo
                    else {
                        data += ListAtr.get(i - dif).dataToString() + ";";
                    }
                }
                //si no tienen el mismo nombre
                else {
                    item[j] = ListAtr.get(i - dif).dataToString();
                    prevName = ListAtr.get(i - dif).getName();
                    data = "";
                    j++;
                }
            }
        }
        return item;
    }

    public String[] ToString2Display() {
        String[] item = new String[1 + ListAtr.size()];
        item[0] = Integer.toString(IdItem);

        int i;
        for (i = 1; i < ListAtr.size() + 1; i++) {
            item[i] = ListAtr.get(i - 1).ToString2Display();
        }
        return item;
    }
}
