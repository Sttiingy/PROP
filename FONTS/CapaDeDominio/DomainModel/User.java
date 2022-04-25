package FONTS.CapaDeDominio.DomainModel;

import java.util.*;

public class User {

    private final int sizeMetrics = 4;
    private HashMap<Integer, Valoracion> MapVal;
    private int userID;
    private String userName;
    private String password;
    private ArrayList<Double> metrics;

    //Creadoras

    public User() {
        userID = -1;
        userName = null;
        password = null;
        MapVal = new HashMap<>();
        metrics = new ArrayList<>();
        for (int i = 0; i < sizeMetrics; ++i) {
            metrics.add(i,0.0);
        }
    }


    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        MapVal = new HashMap<>();
        metrics = new ArrayList<>();
        for (int i = 0; i < sizeMetrics; ++i) {
            metrics.add(i,0.0);
        }
    }

    public User (String data) {

        userID = Integer.parseInt(data);
        userName = data;
        password = data;
        MapVal = new HashMap<>();
        metrics = new ArrayList<>();
        for (int i = 0; i < sizeMetrics; ++i) {
            metrics.add(i,0.0);
        }
    }



    //Modificadoras

    public void setUserID(int newUserID) {
        userID = newUserID;
        MapVal.forEach((k, v) -> v.setUserID(newUserID));
    }

    public void setUserName(String newName) {
        userName = newName;
    }

    public void setUserPassword(String newPassword){
        password = newPassword;
    }

    public void rateItem(Item item, double rating) {

        Valoracion Val = new Valoracion(this.userID, item.getItemID(), rating);
        //borra anterior Val si existe
        Valoracion oldVal = deleteRating(item);
        MapVal.put(item.getItemID(), Val);
        calculMetrics(rating, oldVal);
    }

    public Valoracion deleteRating(Item item){
        if(existsVal(item.getItemID())){
            Valoracion userVal = getItemRating(item.getItemID());
            //metric[0] AvgRating
            metrics.set(0, ((metrics.get(0)*getValSize()*5)-userVal.getPuntuacion())/getValSize()-1);
            if(userVal.getPuntuacion() >= 5.) {
                //metric[1] LikedItems
                metrics.set(1, ((metrics.get(1)*getValSize()-1)/getValSize()-1));
            }
            else if (userVal.getPuntuacion() < 5.) {
                //metric[2] NotLikedItems
                metrics.set(2, ((metrics.get(2)*getValSize()-1)/getValSize()-1));
            }
            //metric[3] numVals (no podemos utilizar el metodo que ya existe pues se hace con tamaño -1
            double size = MapVal.size()-1;
            if (size <= 20) metrics.set(3, size/20);
            else metrics.set(3, 1.);
        }
        return MapVal.remove(item.getItemID());
    }


    //Consultoras

    public int getUserID() {
        return userID;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserPassword(){
        return password;
    }

    public Valoracion getItemRating (int itemID) {
        return MapVal.get(itemID);
    }

    public int getValSize (){
        return MapVal.size();
    }

    public Set<Integer> getKeySetVal () {return MapVal.keySet();}

    public Collection<Valoracion> getValColl() { return MapVal.values(); }

    public boolean existsVal (int itemID) { return MapVal.containsKey(itemID); }

    public ArrayList<Double> getListMetrics (){
        return metrics;
    }

    public double getRatingItemID (int itemID) {
        return MapVal.get(itemID).getPuntuacion();
    }

    public int getSizeMetrics() { return metrics.size(); }



    //Other

    //calcular las nuevas metrics a partir del item rateado
    private void calculMetrics(double rating, Valoracion oldVal) {

        int sizeLastVal;
        if(oldVal == null) sizeLastVal = MapVal.size()-1;
        else sizeLastVal = MapVal.size();

        calculAverageRatings(rating, oldVal, sizeLastVal);       //parametros[0] (tanto por 1 media valoraciones)

        calculLikedItems(rating, oldVal, sizeLastVal);           //parametros[1] (tanto por 1 items gustados (val >= 5.))

        calculNotLikedItems(rating, oldVal, sizeLastVal);        //parametros[2] (tanto por 1 items no gustados (val < 5.))

        calculSizeVal();                                         //parametros[3] numValoraciones/20
    }

    //Lista de calculos de metrics para situar al usuario en los ejes

    //como esta metric es probablemente la mas relevante hacemos que sea sobre 2 en lugar de sobre 1
    private void calculAverageRatings (double rating, Valoracion oldVal, int sizeLastVal) {
        double ratingsSum = metrics.get(0) * sizeLastVal * 5.;
        if (oldVal == null) ratingsSum += rating;
        else ratingsSum = ratingsSum - oldVal.getPuntuacion() + rating;
        metrics.set(0, (ratingsSum/MapVal.size())/5.);
    }

    //resto de metrics son sobre 1, y se calculan de forma trivial.

    private void calculLikedItems(double rating, Valoracion oldVal, int sizeLastVal) {
        double numLovedItems = metrics.get(1) * sizeLastVal;
        if (oldVal == null) {
            if (rating >= 5.) numLovedItems++;
        }
        else {
            if(oldVal.getPuntuacion() >= 5. && rating < 5.) --numLovedItems;
            else if(oldVal.getPuntuacion() < 5. && rating >= 5.) ++numLovedItems;
        }
        metrics.set(1, numLovedItems/MapVal.size());
    }

    private void calculNotLikedItems (double rating, Valoracion oldVal, int sizeLastVal) {
        double numNotLikedItems = metrics.get(2) * sizeLastVal;
        if (oldVal == null) {
            if (rating < 5.) numNotLikedItems++;
        }
        else {
            if(oldVal.getPuntuacion() < 5. && rating >= 5.) --numNotLikedItems;
            else if(oldVal.getPuntuacion() >= 5. && rating < 5.) ++numNotLikedItems;
        }
        metrics.set(2, numNotLikedItems/MapVal.size());
    }

    private void calculSizeVal() {
        double size = MapVal.size();
        if (size <= 20) metrics.set(3, size/20);
        else metrics.set(3, 1.);
    }

    public String[] ToString() {
        String[] user = new String[3 + MapVal.size()];

        user[0] = Integer.toString(userID);
        user[1] = userName;
        user[2] = password;

        int i = 3;
        for (Map.Entry<Integer, Valoracion> it : MapVal.entrySet()) {
            user[i] = String.valueOf(it.getValue().ToString());
            i++;
        }
        return user;
    }

    public Vector<String[]> ToStringVal() {
        Vector<String[]> val = new Vector<>(MapVal.size());

        int i = 0;
        for (Map.Entry<Integer, Valoracion> it : MapVal.entrySet()) {
            val.add(it.getValue().ToString());
            i++;
        }
        return val;
    }

    public String[] dataToString() {
        String[] user = new String[3];

        user[0] = Integer.toString(userID);
        user[1] = userName;
        user[2] = password;
        return user;
    }

    public ArrayList<Integer> getRatedItems() {
        ArrayList<Integer> listItems = new ArrayList<>(MapVal.size());
        for (Map.Entry<Integer, Valoracion> it : MapVal.entrySet()) {
            listItems.add(it.getKey());
        }
        return listItems;
    }
}
