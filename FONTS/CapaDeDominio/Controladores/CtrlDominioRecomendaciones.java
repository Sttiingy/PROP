package FONTS.CapaDeDominio.Controladores;

import FONTS.CapaDeDominio.DomainModel.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;


public class CtrlDominioRecomendaciones {

    private final Algoritmos algoritmos;
    private final CollaborativeFiltering collaborativeFiltering;
    private final Knn knn;
    private final MostValuableItems mostValuableItems;
    //flag para saber que metodo de recomendaciones usar.
    private RecommendationMode recommendationMode;
    //flag para saber si el modo test esta activado
    private boolean test;
    private final int numIterationsKmeans = 5;




    public CtrlDominioRecomendaciones () {
        algoritmos = new Algoritmos();
        collaborativeFiltering = new CollaborativeFiltering();
        knn = new Knn();
        mostValuableItems = new MostValuableItems();
        //inicialmente para el usuario activo siempre escoges el modo que selecciona el algoritmo mas apropiado para su caso
        recommendationMode = RecommendationMode.Suited;
        test = false;
    }

    public String getRecomendationModeString () {
        switch (recommendationMode) {
            case Suited:
                return "Suited";
            case Collaborative:
                return "Collaborative";
            case Knn:
                return "Knn";
            case Trending:
                return "Trending";
            default:
                return null;
        }
    }

    public void setRecommendationMode(String recM) {
        if (recM.equals("Trending")) recommendationMode = RecommendationMode.Trending;
        else if (recM.equals("Collaborative")) recommendationMode = RecommendationMode.Collaborative;
        else if (recM.equals("Suited")) recommendationMode = RecommendationMode.Suited;
        else recommendationMode = RecommendationMode.Knn;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public boolean getTest() {
        return test;
    }

    public void kmeans (HashSet<User> users) {
        //es una k con valor muy bajo, pero para las metrics que tenemos es lo correcto.
        int k = 5;
        //hacemos el kmeans numIterationKmeans veces y cogemos el resultado con menor entropia
        double entropiaMinima = Double.MAX_VALUE;
        HashSet<Cluster> kmeansBestResult = new HashSet<>();
        if (users.size() > k) {
            for (int i = 0; i < numIterationsKmeans; ++i) {
                HashSet<Cluster> kmeansResult = new HashSet<>(collaborativeFiltering.kmeans(users, k));
                double entropia = collaborativeFiltering.calculEntropia();
                if(entropia < entropiaMinima) {
                    entropiaMinima = entropia;
                    kmeansBestResult = kmeansResult;
                }
            }
            collaborativeFiltering.setCjtClusters(kmeansBestResult);
        }
    }

    public ArrayList<Item> getRecomendacionesNormales(User user, TreeMap<Integer, Item> allItems, int k) {

        ArrayList<Item> allRecomendedItems;

        if (recommendationMode == RecommendationMode.Trending) {
            allRecomendedItems = getTrendingRecommendations(user, allItems);
        } else if (recommendationMode == RecommendationMode.Suited) {
            allRecomendedItems = getSuitedRecomendations(user, allItems, k);
        } else if (recommendationMode == RecommendationMode.Knn) {
                allRecomendedItems = getKnnRecommendations(user, allItems, k);
        } else {
            allRecomendedItems = getCollaborativeRecomendations(user, allItems);
        }
        //escoge los k primeros items de la lista total
        ArrayList<Item> kRecomendedItems = new ArrayList<>();
        for (int i = 0; i < k && i < allRecomendedItems.size(); ++i) {
            kRecomendedItems.add(allRecomendedItems.get(i));
        }
        return kRecomendedItems;
    }


    private ArrayList<Item> getCollaborativeRecomendations(User user, TreeMap<Integer, Item> allItems) {
        return collaborativeFiltering.getCollaborativeRecomendations(user, allItems);
    }

    private ArrayList<Item> getKnnRecommendations(User user, TreeMap<Integer, Item> allItems, int k){
        return knn.getUserKnnRecommendations(user, allItems, k);
    }

    private ArrayList<Item> getTrendingRecommendations(User user, TreeMap<Integer, Item> allItems){
        return mostValuableItems.getTrendingItems(user, allItems.values());
    }

    private ArrayList<Item> getSuitedRecomendations (User user, TreeMap<Integer, Item> allItems, int k) {
        //Caso que el usuario aun no tenga ningun item valorado, o no tenga ningun gustado (metrics[1] = 0) y su cluster sea pequeño (no se puede hacer ni slope one ni knn).
        Cluster clusterActiveUser = collaborativeFiltering.getClosestCluster(user);
        if (user.getValSize() == 0 || ((clusterActiveUser.getClusterSize() < 10 || user.getValSize() < 5) && user.getListMetrics().get(1) == 0.)) {
            return getTrendingRecommendations(user, allItems);
        }
        //Caso en el que usuario tenga pocas valoraciones o no hayan muchos usuarios parecidos a el (knn)
        //Aqui sabes que el usuario le gusta almenos 1 item para hacer el knn pues no ha entrado en el anterior if.
        //miramos en que cluster se encuentra el usuario activo y sus caracteristicas para ello
        else if (clusterActiveUser.getClusterSize() < 10 || user.getValSize() < 5) {
            return getKnnRecommendations(user, allItems, k);
        }
        //Caso de un usuario con mas valoraciones y no aislado (SlopeOne)
        else {
            return getCollaborativeRecomendations(user, allItems);
        }
    }

    //retorna los k items mas similares a un item dado
    public ArrayList<Item> getKSimilarItems(Item item, TreeMap<Integer, Item> allItems, int k) {
        return knn.getItemKnnRecommendations(item, allItems, k);
    }


    public int getUserClusterSize(User user) {
        return collaborativeFiltering.getClosestCluster(user).getClusterSize();
    }




    //TEST

    //le entra un user, un conjunto de valoraciones y el conjunto total de items y saca valoraciones ordenadas descendientemente por la prediccion de notas
    public ArrayList<Valoracion> getItemsRecomendadosTest(User user, ArrayList<Valoracion> valUnknown, TreeMap<Integer, Item> allItems) {

        //variable donde se guarda el resultado
        ArrayList<Valoracion> orderedVals = new ArrayList<>();
        //lista auxiliar donde se guardan los resultados de slopeOne anteriores para ordenar correctamente
        ArrayList<Double> orderedRatings = new ArrayList<>();

        Cluster clusterActiveUser = collaborativeFiltering.getClosestCluster(user);

        //si el cluster es pequeño y no tiene items gustados se hace trending. SI tiene almenos un item gustado se hace el knn. Si el cluster es aceptable se hace el kmeans.
        //las condiciones cambian un poco puesto que ahora el conjunto total de items para recomendar es de tamaño 10, por lo que el knn no es tan efectivo
        if(clusterActiveUser.getClusterSize() < 15 && user.getListMetrics().get(1) == 0.) {
            ArrayList<Item> orderedItems = mostValuableItems.getTrendingItems(user, allItems.values());
            for(Valoracion val : valUnknown) {
                for (Item item : orderedItems) {
                    if(val.getItemID() == item.getItemID()) orderedVals.add(val);
                }
            }
        }
        else if ((clusterActiveUser.getClusterSize() < 15)) {

            ArrayList<Item> orderedItems = knn.getUserKnnRecommendations(user, allItems, valUnknown.size());
            for(Valoracion val : valUnknown) {
                for (Item item : orderedItems) {
                    if(val.getItemID() == item.getItemID()) orderedVals.add(val);
                }
            }
        }
        else {
            Cluster userCluster = collaborativeFiltering.getClosestCluster(user);
            HashSet<User> users = new HashSet<>(userCluster.getUsers());
            for(Valoracion val : valUnknown) {
                double prediccion = collaborativeFiltering.SlopeOne(user, users, val.getItemID());

                int it = 0;
                for(Double predictedRating : orderedRatings) {
                    if(prediccion < predictedRating) ++it;
                }
                orderedRatings.add(it, prediccion);
                orderedVals.add(it, val);
            }
        }
        return orderedVals;
    }

    public double[] calidadRecomendaciones (ArrayList<Valoracion> predicciones) {
        //guardamos en calidades[0] el DCG , en calidades[1] el DGC ideal, y en calidades[2] el DCG normalizado
        double[] calidades = new double[3];
        calidades[0] = algoritmos.calidadRecomendaciones(predicciones);
        calidades[1] = algoritmos.calidadRecomendacionesOptimas(predicciones);
        calidades[2] = calidades[0] / calidades[1];
        return calidades;
    }
}
