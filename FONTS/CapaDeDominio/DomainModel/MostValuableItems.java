package FONTS.CapaDeDominio.DomainModel;

import java.util.ArrayList;
import java.util.Collection;

public class MostValuableItems extends Algoritmos {


    public ArrayList<Item> getTrendingItems(User user, Collection<Item> allItems) {
        ArrayList<PairItemDouble> allTrendingItemsAndRatio = new ArrayList<>();
        for (Item item : allItems) {
            //si el usuario ya ha valorado el item no lo evaluamos, de tal manera que los que ya haya rateado no se muestren por pantalla
            boolean alreadyRated = false;
            for(Valoracion val : user.getValColl()) {
                if (val.getItemID() == item.getItemID()) {
                    alreadyRated = true;
                    break;
                }
            }
            if(!alreadyRated) {
                double ratio = 0;
                //sumamos (sizeVal del item / 100) a la nota media para que si hay empate de puntuación, salga el que mas veces se haya puntuado.
                // Además, al hacer esto, hacemos que aunque tenga peor nota, el item pueda aparecer antes que otros con mejor nota si ha sido valorado
                //muchas mas veces
                if (item.getSizeVal() > 10) ratio = item.getAvgRating() + item.getSizeVal() / 200.;
                else if (item.getSizeVal() != 0) {
                    //hacemos que cuanto mas se acerque a 10, mas valgan la valoracion promedio progresivamente mediante el ln(10)
                    double itemAvgDouble = item.getAvgRating();
                    ratio = itemAvgDouble / (1 + Math.log(10) - Math.log(item.getSizeVal()));
                }
                PairItemDouble itemAndRatio = new PairItemDouble(item, ratio);
                allTrendingItemsAndRatio.add(itemAndRatio);
            }
        }
        return getSortedItems(allTrendingItemsAndRatio);
    }
}
