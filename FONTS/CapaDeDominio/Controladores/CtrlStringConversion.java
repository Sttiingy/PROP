package FONTS.CapaDeDominio.Controladores;

import FONTS.CapaDeDominio.DomainModel.Item;
import FONTS.CapaDeDominio.DomainModel.User;
import FONTS.CapaDeDominio.DomainModel.Valoracion;

import java.util.*;

public class CtrlStringConversion {

    public CtrlStringConversion() {

    }

    //genera un Vector<String[]> con las outputqueries a partir de una matriz de Valoraciones
    //el vector queries pasado como parametro esta ordenado asi que las valoraciones con el mismo userID estan seguidas
    public Vector<String[]> arrayQueriesToString(ArrayList<ArrayList<Valoracion>> queries) {
        Vector<String[]> output = new Vector<>();
        for (int i = 0; i < queries.size(); i++) {
            String[] row = new String[queries.get(i).size()];
            //inicializo la 1a posicion del String[] de cada posicion del Vector
            if (queries.get(i).size() != 0)
                row[0] = Arrays.toString(new String[]{queries.get(i).get(0).ToString()[0]});
            //una vez tengo la 1a posicion del String[] (que contiene el userID), le puedo meter los itemID
            for (int j = 0; j < queries.get(i).size(); j++) {
                String[] s = queries.get(i).get(j).ToString();
                //aqui recojo el itemID
                row[j] = Arrays.toString(new String[]{s[1]});
            }
            output.add(i, row);
        }
        return output;
    }

    public Vector<String[]> itemArrayToStringVector(ArrayList<Item> recomendedItems) {
        Vector<String[]> stringItems = new Vector<String[]>();
        for (Item item : recomendedItems) {
            stringItems.add(item.ToString2Display());
        }
        return stringItems;
    }


    //genera una lista de usuarios en base al contenido del fichero known y de items.csv para generar despues las recomendaciones
    public ArrayList<User> arrayToUserList(Vector<String[]> known, TreeMap<Integer, Item> items) {
        try {
            ArrayList<User> users = new ArrayList<>();
            //variables para reconocer las posicion de cada variable (puesto que el input viene de un archivo)
            int posUserID = 0;
            int posItemID = 0;
            int posRating = 0;
            boolean first = true;
            for (int i = 0; i < known.size(); i++) {
                if (!first) {
                    //creo el user
                    int id = Integer.parseInt(known.get(i)[posUserID]);
                    User user = new User(known.get(i)[posUserID]);
                    //la variable pos es un flag que me indica si se ha encontrado el usuario user, asi como su posicion
                    //en caso de que se haya encontrado en users
                    int pos = -1;
                    for (int j = 0; j < users.size(); ++j) {
                        if (users.get(j).getUserID() == id) pos = j;
                    }
                    //si se ha encontrado, solo hay que añadirle el item a sus valoraciones
                    if (pos != -1)
                        users.get(pos).rateItem(items.get(Integer.parseInt(known.get(i)[posItemID])), Double.parseDouble(known.get(i)[posRating]));
                        //si no se ha encontrado, le añadimos el item a sus valoraciones y añadimos el usuario a la lista
                    else {
                        user.rateItem(items.get(Integer.parseInt(known.get(i)[posItemID])), Double.parseDouble(known.get(i)[posRating]));
                        users.add(user);
                    }
                } else {
                    //tengo que mirar donde esta cada variable porque en algunos archivos cambian de posicion
                    for (int j = 0; j < known.get(i).length; j++) {
                        String find = known.get(i)[j];
                        switch (find) {
                            case "userId":
                                posUserID = j;
                                break;

                            case "itemId":
                                posItemID = j;
                                break;

                            case "rating":
                                posRating = j;
                                break;
                        }
                    }
                    first = false;
                }
            }
            return users;
        }
        catch (Exception e) {
            return null;
        }
    }


    //genera una matriz de valoraciones a partir del contenido del fichero unknown
    public ArrayList<ArrayList<Valoracion>> arrayToRatingMatrix(Vector<String[]> unknown) {
        try {
            //variables para reconocer las posicion de cada variable (puesto que el input viene de un archivo)
            int posUserID = 0;
            int posItemID = 0;
            int posRating = 0;
            ArrayList<ArrayList<Valoracion>> ratings = new ArrayList<>();
            if (unknown != null) {
                String valActual = unknown.get(0)[0];
                int j = 0;
                ratings.add(new ArrayList<>());
                boolean first = true;
                for (String[] strings : unknown) {
                    if (!first) {
                        if (!strings[posUserID].equals(valActual)) { //si no son iguales (hemos cambiado de user), avanzamos a la siguiente posicion de ratings
                            valActual = strings[posUserID];
                            j++;
                            ratings.add(new ArrayList<>());
                        }
                        ratings.get(j).add(new Valoracion(strings[posUserID], strings[posItemID], strings[posRating])); //mientras sea igual, le va insertando a ratings[j]
                    } else {
                        //tengo que mirar donde esta cada variable porque en algunos archivos cambian de posicion
                        for (int i = 0; i < strings.length; i++) {
                            String find = strings[i];
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
            }
            return ratings;
        }
        catch (Exception e) {
            return null;
        }
    }
}
