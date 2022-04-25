package FONTS.CapaDeDominio.DomainModel;


import java.util.ArrayList;
import java.util.Comparator;

public class Algoritmos {

    protected class PairItemDouble {
        public Item item;
        public Double rating;

        public PairItemDouble(Item item, Double rating) {
            this.item = item;
            this.rating = rating;
        }

        public Double getRating() {
            return rating;
        }
    }

    //dejamos la calidad de las recomendaciones aqui pues calcularemos la calidad de las recomendaciones para cada uno de los algoritmos
    public double calidadRecomendaciones (ArrayList<Valoracion> predicciones) {
        return calculaCalidadValoraciones(predicciones);
    }

    //busca el DCG ideal para una lista de valoraciones
    public double calidadRecomendacionesOptimas (ArrayList<Valoracion> predicciones) {

       ArrayList<Valoracion> unknownSorted = new ArrayList<>(predicciones);
       unknownSorted.sort(Comparator.comparing(Valoracion::getPuntuacion).reversed());
       return calculaCalidadValoraciones(unknownSorted);
    }

    //busca el DCG teniendo en cuenta el orden real en el que le llegan las valoraciones
    private double calculaCalidadValoraciones (ArrayList<Valoracion> valoraciones) {
        double sum = 0;
        for (int i = 0; i < valoraciones.size(); ++i) {
            double numerador = Math.pow(2, valoraciones.get(i).getPuntuacion()) - 1;
            //sumamos 2 al denominador en lugar de 1 ya que el bucle itera desde 0
            double denominador = Math.log(i+2) / Math.log(2); //cambio de base para log2
            sum += numerador/denominador;
        }
        return sum;
    }

    protected ArrayList<Item> getSortedItems (ArrayList<PairItemDouble> itemsAndRatios) {
        itemsAndRatios.sort(Comparator.comparing(PairItemDouble::getRating).reversed());
        ArrayList<Item> sortedItems = new ArrayList<>();
        for (PairItemDouble slopeOneResult : itemsAndRatios) {
            sortedItems.add(slopeOneResult.item);
        }
        return sortedItems;
    }
}