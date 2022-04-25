package FONTS.CapaDeDominio.DomainModel;

public class Atributo {
    protected String Name;
    protected AtrType atrT;

    public Atributo() {}

    public Atributo (String datos[]) {
        this.Name = datos[0];
        Boolean esEntero = false;
        Boolean esDoble = false;
        Boolean esBool = false;
        //Miramos si es Booleano
        if(datos[1].equals("True") || datos[1].equals("False")) {
            this.atrT = AtrType.Booleano;
            esBool = true;
        }
        //Miramos si es Entero
        try {
            Integer.parseInt(datos[1]);
            esEntero = true;
        } catch (NumberFormatException excepcion) {
            esEntero = false;
        }
        if(esEntero) {
            this.atrT = AtrType.Entero;
        }
        //Miramos si es Doble
        try {
            Double.parseDouble(datos[1]);
            esDoble = true;
        } catch (NumberFormatException excepcion) {
            esDoble = false;
        }
        if(esDoble && !esEntero) {
            this.atrT = AtrType.Doble;
        }
        //Si no es ninguno de los anteriores es Strings
        if(!esDoble && !esBool && !esEntero) this.atrT = AtrType.Strings;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public AtrType getTipo() {
        return atrT;
    }

    public boolean equals(Atributo comparador) {
        if (comparador.Name.equals(this.Name) && comparador.atrT.equals(this.atrT)) {
            AtrType type = this.getTipo();
            switch (type) {
                case Doble:
                    return ((Doble)comparador).equals(((Doble)this));

                case Entero:
                    return ((Entero)comparador).equals(((Entero)this));

                case Booleano:
                    return ((Booleano)comparador).equals(((Booleano)this));

                case Strings:
                    return ((Strings)comparador).equals(((Strings)this));
            }
        }
        return false;
    }

    public String ToString() {
        String s;
        String type;
        switch (atrT) {
            case Doble:
                type = "Double";
                break;

            case Entero:
                type = "Integer";
                break;

            case Booleano:
                type = "Boolean";
                break;

            case Strings:
                type = "String";
                break;
            default:
                type = null;
        }
        s = Name + "," + type;
        return s;
    }

    public String dataToString() {
        return null;
    }

    public String ToString2Display() {
        return null;
    }
}