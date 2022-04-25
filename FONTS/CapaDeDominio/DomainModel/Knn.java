package FONTS.CapaDeDominio.DomainModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;


public class Knn extends Algoritmos {


    public ArrayList<Item> getUserKnnRecommendations(User user, TreeMap<Integer, Item> allItems, int k) {
        ArrayList<PairItemDouble> allKnnResults = new ArrayList<>();
        //creamos una segunda lista con solo los items, ya que debemos encontrar las posiciones de los items que se repiten
        ArrayList<Item> knnResultItems = new ArrayList<>();

        for(Valoracion val : user.getValColl()) {
            //solo añadimos los items que sean gustados por el usuario.
            if(val.getPuntuacion() >= 5.) {
                Item ratedItem = allItems.get(val.getItemID());
                ArrayList<PairItemDouble> knnResult = new ArrayList<>(knn(ratedItem, allItems, k));
                //el coste de este bucle es solo k (no allItems), pues knn devuelve solo los k mas cercanos, por lo que tiene coste nk
                for (PairItemDouble itemAndSimilarity : knnResult) {
                    //solo se añaden items no valorados por el usuario
                    if(!user.getKeySetVal().contains(itemAndSimilarity.item.getItemID())) {
                        //el verdadero valor del ratio es similitud*puntuacion al item rateado
                        itemAndSimilarity.rating *= val.getPuntuacion();
                        //ahora comprobamos si este item ya estaba repetido en la lista de los items, y si es asi no lo añadimos, sino que sumamos sus 2 ratios.
                        int originalItemPosition = knnResultItems.indexOf(itemAndSimilarity.item);
                        if (originalItemPosition == -1) { //el item aun no existe en los resultados
                            //mantenemos el orden en ambos arraylists añadiendolos siempre a la vez
                            allKnnResults.add(itemAndSimilarity);
                            knnResultItems.add(itemAndSimilarity.item);
                        }
                        else {
                            //actualizamos el ratio de la primera aparicion del item sumandolo con el nuevo ratio
                            PairItemDouble originalItemAndRatio = allKnnResults.get(originalItemPosition);
                            allKnnResults.set(originalItemPosition, new PairItemDouble(originalItemAndRatio.item, originalItemAndRatio.rating + itemAndSimilarity.rating));
                        }
                    }
                }
            }
        }
        return getSortedItems(allKnnResults);
    }

    public ArrayList<Item> getItemKnnRecommendations(Item item, TreeMap<Integer, Item> allItems, int k) {
        //como no necesitamos el valor del ratio que viene con el pair resultante del knn en el caso de un solo item, lo obviamos.
        ArrayList<PairItemDouble> itemsAndSimilarities = new ArrayList<>(knn(item, allItems, k+1));
        ArrayList<Item> kMostSimilarItems = new ArrayList<>();
        for(PairItemDouble itemAndSimilarity : itemsAndSimilarities) {
            if(itemAndSimilarity.item != item) {
                kMostSimilarItems.add(itemAndSimilarity.item);
            }
        }
        return kMostSimilarItems;
    }

    //retorna un array de pairs (item, distancia) con los k items con menor distancia con el mainItem en allItems
    private ArrayList<PairItemDouble> knn (Item mainItem, TreeMap<Integer, Item> allItems, int k) {
        if(allItems.size() > 1) {
            ArrayList<PairItemDouble> knnResult = new ArrayList<>();
            //encuentra para todos los items de allItems su distancia con el item del pair y retorna su similitud multiplicada por su puntuacion
            for (Item randomItem : allItems.values()) {
                double similitud = SimilarityItems(mainItem, randomItem);
                knnResult.add(new PairItemDouble(randomItem, similitud));
            }
            return getBestRatioKItems(knnResult, k);
        }
        return new ArrayList<>();
    }

    //en este caso no puedo utilizar getSortedItems de la superclase ya que he de devolver los items junto con el ratio para poder hacer la seleccion posterior con todos los items.
    private ArrayList<PairItemDouble> getBestRatioKItems (final ArrayList<PairItemDouble> allKnnResults, int k) {
        allKnnResults.sort(Comparator.comparing(PairItemDouble::getRating).reversed());
        ArrayList<PairItemDouble> kSortedItems = new ArrayList<>();
        for(int i = 0; i < k && i < allKnnResults.size(); ++i) {
            kSortedItems.add(new PairItemDouble(allKnnResults.get(i).item, allKnnResults.get(i).rating));
        }
        return kSortedItems;
    }

     private double SimilarityItems(Item i1, Item i2) {
        if(i1.getAtributos().size() == 0 || i2.getAtributos().size() == 0) return 0.;
        //para calcular la distancia entre items dividimos la suma de similitud entre atributos en comun de ambos items,
         // dividida entre el numero de atributos del item con mas atributos relevantes (importantAtr).
        double count = 0;
        int importantAtr1 = 0;
        int importantAtr2 = 0;
        //contamos primero los atributos importantes de atr2, fuera del doble bucle
        for (Atributo atr2 : i2.getAtributos()) {
             if(importantAtr(atr2)) ++importantAtr2;
        }
        //guardamos en count la suma de distancias entre todos los atributos de i1 e i2
        for(Atributo atr1 : i1.getAtributos()) {
            if(importantAtr(atr1)) {
                ++importantAtr1;
                for (Atributo atr2 : i2.getAtributos()) {
                    if(importantAtr(atr2)) {
                        count += distanciaAtributos(atr1, atr2);
                    }
                }
            }
        }
        //devolvemos count dividido por el numero de atr importantes mayor entre i1 e i2
        if (importantAtr1 == 0 || importantAtr2 == 0) return 0.;
        else if(importantAtr1 > importantAtr2) return count / (double)importantAtr1;
        else return count / (double)importantAtr2;
    }

    static boolean importantAtr (Atributo atr) {
        String name = atr.getName();
        String data = atr.dataToString();
        //eliminamos los atributos que indican ids, las sinopsis y textos largos, y los links y paths, ya que no indican ninguna caracteristica del item relevante para la similitud
        return !data.contains("/") && data.length() < 30 && !name.equals("id") && !name.equals("synopsis") && !name.equals("overview") && !name.contains("path") && !name.contains("link");
    }

    private double distanciaAtributos (Atributo atr1, Atributo atr2) {
    //comparando distancia booleanos
        if (atr1.getTipo() == AtrType.Booleano && atr2.getTipo() == AtrType.Booleano) {
            return similitudBooleanAtr(atr1, atr2);
        }
        //comparando distancia doubles y enteros mediante la fraccion del pequeño/grande
        else if ((atr1.getTipo() == AtrType.Doble && atr2.getTipo() == AtrType.Doble) || (atr1.getTipo() == AtrType.Entero && atr2.getTipo() == AtrType.Entero)) {
            return similitudNumericAtr(atr1, atr2);
        }
        //comparando distancia entre strings
        else if (atr1.getTipo() == AtrType.Strings && atr2.getTipo() == AtrType.Strings) {
            return similitudStringAtr(atr1, atr2);
        }
        return 0;
    }

    //podria retornar un booleano, pero con double se indica mejor que lo que buscas es la similitud entre los atributos.
    //ademas, permite actualizar mas facilmente esta funcion en un futuro sin tener que cambiar el metodo principal.
    private double similitudBooleanAtr(Atributo booleanAtr1, Atributo booleanAtr2) {
        if (booleanAtr1.getName().equals(booleanAtr2.getName()) && booleanAtr1.dataToString().equals(booleanAtr2.dataToString())) return 1;
        return 0;
    }

    private double similitudNumericAtr(Atributo numAtr1, Atributo numAtr2) {
        if (numAtr1.getName().equals(numAtr2.getName())) {
            double dataAtr1 = Double.parseDouble(numAtr1.dataToString());
            double dataAtr2 = Double.parseDouble(numAtr2.dataToString());
            if(dataAtr1 == 0 && dataAtr2 == 0) return 1;
            if (dataAtr1 > dataAtr2 && dataAtr1 != 0) {
                return Math.abs(dataAtr2 / dataAtr1);
            }
            else if(dataAtr2 != 0)
                return Math.abs(dataAtr1 / dataAtr2);
        }
        return 0;
    }

    //si los string data de los atributos coinciden retorna 1, sino 0.
    //podria retornar un booleano, pero con double se indica mejor que lo que buscas es la similitud entre los atributos.
    //ademas, permite actualizar mas facilmente esta funcion en un futuro sin tener que cambiar el metodo principal.
    private double similitudStringAtr (Atributo stringAtr1, Atributo stringAtr2) {
        if(stringAtr1.dataToString().equals(stringAtr2.dataToString())) return 1;
        return 0;
    }
}


