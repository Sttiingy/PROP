package FONTS.CapaDeDominio.DomainModel;

public class Valoracion {
    private int idUser;
    private int idItem;
    private double Puntuacion;

    public Valoracion() {
        Puntuacion = -1;
        idItem = -1;
        idUser = -1;
    }

    public Valoracion(int idUser, int idItem, double p) {
        Puntuacion = p;
        this.idUser = idUser;
        this.idItem = idItem;
    }

    public Valoracion(String[] data) {
        this.idUser = Integer.parseInt(data[0]);
        this.idItem = Integer.parseInt(data[1]);
        this.Puntuacion = Double.parseDouble(data[2]);
    }

    public Valoracion(String userID, String itemID, String rating) {
        this.idUser = Integer.parseInt(userID);
        this.idItem = Integer.parseInt(itemID);
        this.Puntuacion = Double.parseDouble(rating);
    }

    public Valoracion(String userID, String itemID, double rating) {
        this.idUser = Integer.parseInt(userID);
        this.idItem = Integer.parseInt(itemID);
        this.Puntuacion = rating;
    }

    public boolean equals(Valoracion v) {
        return (this.idUser == v.idUser && this.idItem == v.idItem);
    }

    public double getPuntuacion() {
        return Puntuacion;
    }

    public int getIdUser() {return idUser;}

    public int getIdItem() {return idItem;}

    public void setPuntuacion(double puntuacion) {
        Puntuacion = puntuacion;
    }

    public int getUserID() {
        return idUser;
    }

    public void setUserID(int idUser) {
        this.idUser = idUser;
    }

    public int getItemID() {
        return idItem;
    }

    public void setItemID(int idItem) {
        this.idItem = idItem;
    }

    public String[] ToString() {
        String[] s = new String[3];
        s[0] = Integer.toString(this.getUserID());
        s[1] = Integer.toString(this.getItemID());
        s[2] = Double.toString(this.getPuntuacion());
        return s;
    }
}
