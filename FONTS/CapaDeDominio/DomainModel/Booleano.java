package FONTS.CapaDeDominio.DomainModel;


import java.util.Locale;

public class Booleano extends Atributo {
    private boolean data;

    public Booleano(){}

    public Booleano(String[] datos) {
        Name = datos[0];
        atrT = AtrType.Booleano;
        this.data = Boolean.parseBoolean(datos[1].toLowerCase(Locale.ROOT));
    }

    public boolean getValue() {
        return this.data;
    }

    public void setValue(boolean data) {
        this.data = data;
    }

    public boolean equals(Booleano bool) {
        return bool.getName().equals(super.getName()) && (bool.getValue() == this.getValue());
    }

    public String ToString() {
        String s;
        s = Name + ",Booleano," + data;
        return s;
    }
    public String ToString2Display() {
        String s;
        s = Name + ";;;Booleano;;;" + data;
        return s;
    }

    public String dataToString() {
        String s;
        s = String.valueOf(data);
        return s;
    }
}
