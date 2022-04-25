package FONTS.CapaDeDominio.DomainModel;

public class Entero extends Atributo {
    private int data;

    public Entero(){}

    public Entero (String[] datos) {
        Name = datos[0];
        atrT = AtrType.Entero;
       this.data = Integer.parseInt(datos[1]);
    }

    public int getValue() { return this.data;}

    public void setValue(int x) {
        this.data = x;
    }

    public boolean equals(Entero inte) {
        return inte.getName().equals(super.getName()) && inte.getValue() == this.getValue();
    }

    public String ToString() {
        String s;
        s = Name + ",Entero," + data;
        return s;
    }

    public String dataToString() {
        String s;
        s = String.valueOf(data);
        return s;
    }

    public String ToString2Display() {
        String s;
        s = Name + ";;;Entero;;;" + data;
        return s;
    }
}
