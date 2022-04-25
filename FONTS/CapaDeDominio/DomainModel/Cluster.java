package FONTS.CapaDeDominio.DomainModel;

import java.util.*;

public class Cluster {

    HashSet<User> users;
    ArrayList<Double> centroide;
    boolean finished;
    int sizeParam;


    public Cluster () {
        users = new HashSet<>();
        centroide = new ArrayList<>();
        finished = false;
        sizeParam = 4;
    }

    public Cluster (ArrayList<Double> centroide) {
        users = new HashSet<>();
        this.centroide = centroide;
        finished = false;
        sizeParam = centroide.size();
    }

    public boolean getFinished () { return finished;}

    public ArrayList<Double> getCentroide () { return centroide; }

    public void insertUser (User user) {
        for(User existantUser : getUsers()) {
            if (existantUser.getUserID() == user.getUserID()) {
                throw new RuntimeException("user insertado en cluster ya existente");
            }
        }
        users.add(user);
    }

    public void setCentroide (ArrayList<Double> centroide) { this.centroide = centroide; }

    public void eraseUsers () { users.clear(); }

    public int getClusterSize () { return users.size(); }

    public void finished () {
        finished = true;
    }

    public int getParamSize() {
        return centroide.size();
    }

    public Collection<User> getUsers() { return users; }

    public Collection<ArrayList<Double>> getUsersMetrics () {
        HashSet<ArrayList<Double>> metricsList = new HashSet<>();
        for(User user : users) {
            metricsList.add(user.getListMetrics());
        }
        return metricsList;
    }
}
