package FONTS.CapaDeDominio.DomainModel;


public class Doble extends Atributo{
    //Atributo/s de la subclase Doble
    private double data;

    public Doble() {}

    public Doble (String[] datos) {
        Name = datos[0];
        atrT = AtrType.Doble;
        this.data = Double.parseDouble(datos[1]);
    }

    public Double getValue() {
        return this.data;
    }

    public void setValue(double data) {
        this.data = data;
    }

    public boolean equals(Doble doble) {
        return doble.getName().equals(this.getName()) && doble.getValue().equals(this.getValue());
    }

    public String ToString() {
        String s;
        s = Name + ",Doble," + data;
        return s;
    }

    public String ToString2Display() {
        String s;
        s = Name + ";;;Doble;;;" + data;
        return s;
    }

    public String dataToString() {
        String s;
        s = String.valueOf(data);
        return s;
    }
}
