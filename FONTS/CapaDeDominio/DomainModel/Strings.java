package FONTS.CapaDeDominio.DomainModel;

public class Strings extends Atributo {
    private String data;

    public Strings(){}

    public Strings (String datos[]) {
        Name = datos[0];
        atrT = AtrType.Strings;
        this.data = datos[1];
    }

    //Get del valor del atributo tipo Stringç
    public String getValue() {
        return this.data;
    }

    //Set del valor del atributo tipo String
    public void setValue(String x) {
        this.data = x;
    }

    //Comparamos si dos atributos de tipo strings son iguales
    public boolean equals(Strings string) {
        return string.getName().equals(super.getName()) && (string.getValue().equals(this.getValue()));
    }

    public String ToString() {
        String s;
        s = Name + ",Strings," + data;
        return s;
    }

    public String ToString2Display() {
        String s;
        s = Name + ";;;Strings;;;" + data;
        return s;
    }

    public String dataToString() {
        return data;
    }
}
