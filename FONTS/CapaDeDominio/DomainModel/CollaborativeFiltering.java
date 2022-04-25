package FONTS.CapaDeDominio.DomainModel;

import java.util.*;

public class CollaborativeFiltering extends Algoritmos {

    private HashSet<Cluster> CjtClusters = new HashSet<>();
    private final int iteracionesKmeans = 40;

    public void setCjtClusters(HashSet<Cluster> CjtClusters) {
        this.CjtClusters = CjtClusters;
    }

    //se guarda en CjtClusters los clusters generados en el kmeans
    public HashSet<Cluster> kmeans (HashSet<User> users, int k) {
        try {
            if(users.size() > k) {
                CjtClusters = new HashSet<>();
                chooseRandomCentroids(users, k);
                assignClustersAllUsers(users);
                int iteraciones = 0; //por si queda atrapado en un bucle infinito, le doy suficientes iteraciones para que consiga un buen resultado
                while (!endKmeans() && iteraciones < iteracionesKmeans) {
                    calculateCentroids(users);
                    for (Cluster cluster : CjtClusters) cluster.eraseUsers();
                    assignClustersAllUsers(users);
                    ++iteraciones;
                }
            }
            return CjtClusters;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    //escoger k centroides aleatorios no repetidos entre los usuarios
    private void chooseRandomCentroids (HashSet<User> users, int k) {

        HashSet<Cluster> clusters = new HashSet<>();
        //asigna aleatoriamente centroides a los clusters
        for (int i = 0; i < k; i++) {
            Cluster cluster = new Cluster(getRandomUserMetrics(users));
            //mirar si este while acaba metiendo cluster o no por si se ha repetido el numero random
            while(!clusters.add(cluster)) {
                cluster = new Cluster(getRandomUserMetrics(users));
            }
        }
        CjtClusters = clusters;
    }


    //comprueba si el algoritmo debe acabar
    private boolean endKmeans () {
        for(Cluster cluster : CjtClusters) {
            if(!cluster.getFinished()) return false;
        }
        return true;
    }


    private void assignClustersAllUsers (HashSet<User> users) {
        for(User user : users) {
            assignCluster(user);
        }
    }

    //pre: s'ha inicialitzat kmeans anteriorment, pel que ja s'ha creat el CjtClusters
    private void assignCluster (User user) {
        //asignamos al usuario al cluster con centroide mas cercano a sus coordenadas
        double distMin = Double.MAX_VALUE;
        Cluster closestCluster = new Cluster();
        for(Cluster cluster : CjtClusters) {
            double distancia = DistanciaUsuarios(user.getListMetrics(), cluster.getCentroide());
            if(distancia < distMin) {
                closestCluster = cluster;
                distMin = distancia;
            }
        }
        closestCluster.insertUser(user);
    }


    private void calculateCentroids(HashSet<User> users) {
        //recalculas la posicion de cada centroide haciendo la media de las coordenadas de los usuarios de cada centroide
        for(Cluster cluster : CjtClusters) {
            ArrayList<Double> newCentroide = new ArrayList<>();
            if (cluster.getClusterSize() != 0) {
                for (int i = 0; i < cluster.getParamSize(); ++i) {
                    double sum = 0;
                    for (ArrayList<Double> usersMetrics : cluster.getUsersMetrics()) {
                        sum += usersMetrics.get(i);
                    }
                    newCentroide.add(sum / (double) cluster.getClusterSize());
                }
            }
            //si el cluster esta vacio reposicionamos el centroide en coordenadas de un usuario aleatorio (y comprobamos que no coincidan con otro centroide de otro cluster)
            else {
                newCentroide = getRandomUserMetrics(users);
                //comprobamos que no hay otro cluster con el mismo centroide que el nuevo
                boolean unique = false;
                while (!unique) {
                    unique = true;
                    for(Cluster otherCluster : CjtClusters) {
                        if(!otherCluster.equals(cluster) && otherCluster.getCentroide().equals(newCentroide)) {
                            unique = false;
                            newCentroide = getRandomUserMetrics(users);
                        }
                    }
                }
            }

            ArrayList<Double> lastCentroide = new ArrayList<>(cluster.getCentroide());

            if (lastCentroide.equals(newCentroide)) cluster.finished();
            else cluster.setCentroide(newCentroide);
        }
    }

    private ArrayList<Double> getRandomUserMetrics (HashSet<User> users) {
        ArrayList<User> userList = new ArrayList<>(users);
        Random Rand = new Random();
        int index = Rand.nextInt(userList.size());
        return userList.get(index).getListMetrics();
    }


    //buscas el cluster mas cercano a las metrics del usuario que no sea vacio
    public Cluster getClosestCluster (User user) {
        double distMin = Double.MAX_VALUE;
        Cluster closestCluster = new Cluster();
        for(Cluster cluster : CjtClusters) {
            double distancia = DistanciaUsuarios(user.getListMetrics(), cluster.getCentroide());
            if(distancia < distMin) {
                closestCluster = cluster;
                distMin = distancia;
            }
        }
        return closestCluster;
    }


    //funcion utilizada para calcular la k optima para nuestro modelo de kmeans
    public double calculEntropia() {

        double sumDistancias = 0;
        for (Cluster cluster : CjtClusters) {
            for(User user : cluster.getUsers()) {
                sumDistancias += Math.pow(DistanciaUsuarios(cluster.getCentroide(), user.getListMetrics()),2);
            }
        }
        return sumDistancias;
    }

    //simple distancia euclidiana entre las metrics de 2 usuarios
    public double DistanciaUsuarios(ArrayList<Double> u1, ArrayList<Double> u2) {

        double sum = 0;
        for (int i = 0; i < u1.size(); ++i) {
            sum += Math.pow(u1.get(i) - u2.get(i), 2);
        }
        return Math.sqrt(sum);
    }


    //predice la puntuacion que le dara el usuario pasado por parametro del item con itemID a partir de los usuarios que pertenecen a este cluster.
    public double SlopeOne(User user, HashSet<User> users,  int itemID) {
        try {
            double medianValuser = 0;
            double sum1 = 0;
            double sizeItems = 0;
            //como el usuario no tiene ninguna valoracion, no se puede hacer un orden en sus predicciones, por lo que todas son el mismo valor aleatorio.
            //en siguientes entregas añadiremos otros metodos de recomendacion que tendran esto en cuenta para no basarse en slope one
            if(user.getValSize() == 0) return -1;
            for (Valoracion Val : user.getValColl()) {
                //aprovechamos este bucle para calcular tanto la media de valoraciones de user como para calcular la suma de desviaciones, pues recorremos el mismo conjunto
                medianValuser += Val.getPuntuacion();
                if (Val.getItemID() != itemID) {
                    double sum2 = 0;
                    int sizeUsers = 0;
                    for (User itUser : users) {
                        if (itUser.existsVal(itemID) && itUser.existsVal(Val.getItemID())) {
                            sum2 += (itUser.getRatingItemID(itemID) - itUser.getRatingItemID(Val.getItemID()));
                            ++sizeUsers;
                        }
                    }
                    if (sizeUsers > 0) {
                        ++sizeItems;
                        sum1 += sum2 / sizeUsers;
                    }
                }
            }
            medianValuser = medianValuser / user.getValSize();
            double sumDivCard = 0;
            if (sizeItems > 0) sumDivCard = sum1 / sizeItems;
            return medianValuser + sumDivCard;
        } catch (Exception e) {
            System.out.println("error en slopeOne");
            return -1;
        }
    }

    public ArrayList<Item> getCollaborativeRecomendations(User user, TreeMap<Integer, Item> allItems) {
        ArrayList<PairItemDouble> allSlopeOneResults = new ArrayList<>();
        HashSet<User> users = new HashSet<> (getClosestCluster(user).getUsers());

        for (Map.Entry<Integer, Item> entry : allItems.entrySet()) {
            if(!user.existsVal(entry.getKey())) {
                double prediccion = SlopeOne(user, users, entry.getKey());
                allSlopeOneResults.add(new PairItemDouble(entry.getValue(), prediccion));
            }
        }
        return getSortedItems(allSlopeOneResults);
    }
}

