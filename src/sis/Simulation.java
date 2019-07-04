package sis;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import myLib.utils.FileIO;
import myLib.utils.Utils;
import network.*;
import networkModels.BA;

/**
 *
 * @author tadaki
 */
public class Simulation {

    private final AbstractNetwork network;
    private final double susceptibility;
    private final double recovery;
    private final Map<Node, Individual> map;

    public Simulation(AbstractNetwork network,
            double susceptibility, double recovery, double initialInfected) {
        this.network = network;
        this.susceptibility = susceptibility;
        this.recovery = recovery;
        map = Utils.createMap();
        List<Node> nodes = network.getNodes();
        for (Node node : nodes) {
            map.put(node, new Individual(susceptibility, recovery));
        }
        int numSite = network.getNumNode();
        int numInfected = (int) (initialInfected * numSite);
        int infected[] = Utils.createRandomNumberList(numSite, numInfected);
        for (int k : infected) {
            Node node = nodes.get(k);
            Individual ind = map.get(node);
            ind.setState(Individual.State.Infected);
        }
    }

    public int update() {
        int numInfected = 0;
        List<Node> nodes = network.getNodes();
        for (Node node : nodes) {
            List<Node> neighbourNodes = network.neighbours(node);
            List<Individual> neighbours = Utils.createList();
            for (Node n : neighbourNodes) {
                neighbours.add(map.get(n));
            }
            Individual ind = map.get(node);
            if (ind.update(neighbours) == Individual.State.Infected) {
                numInfected++;
            }
        }
        return numInfected;
    }

    public void infectedTimeSteps(Map<Node, Integer> n2iMap) {
        for (Node node : map.keySet()) {
            if (map.get(node).getState() == Individual.State.Infected) {
                if (n2iMap.containsKey(node)) {
                    int v = n2iMap.get(node);
                    n2iMap.put(node, v + 1);
                } else {
                    n2iMap.put(node, 1);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int numSite = 10000;
        int m = 1;
        double susceptibility = 0.1;
        double recoveryArray[] = {0.1, 0.2, 0.4};
        double initialInfected = 0.01;
        int tmax = 1000;
        BA ba = new BA(numSite, m);
        ba.createNetwork();

        for (double recovery : recoveryArray) {
            Simulation sys = new Simulation(ba,
                    susceptibility, recovery, initialInfected);
            List<Point> plist = Utils.createList();
            for (int t = 0; t < tmax; t++) {
                int c = sys.update();
                plist.add(new Point(t, c));
            }
            String filename = "SIS-" + String.valueOf(susceptibility) + "-"
                    + String.valueOf(recovery) + ".txt";
            try (BufferedWriter out = FileIO.openWriter(filename)) {
                for (Point p : plist) {
                    FileIO.writeSSV(out, p.x, p.y);
                }
            }
        }
    }
}
