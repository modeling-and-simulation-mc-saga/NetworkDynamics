package sis;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import myLib.utils.FileIO;
import myLib.utils.Utils;
import network.Node;
import networkModels.BA;

/**
 *
 * @author tadaki
 */
public class Degree2Infected {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int numSite = 10000;
        int m = 2;
        double susceptibility = 0.1;
        double recovery =0.2;
        double initialInfected = 0.01;
        int trelax=100;
        int tmax = 10000;
        BA ba = new BA(numSite, m);
        ba.createNetwork();

            Simulation sys = new Simulation(ba,
                    susceptibility, recovery, initialInfected);
            for (int t = 0; t < trelax; t++) {
                int c = sys.update();
            }
            Map<Node,Integer> diMap = Utils.createMap();
            for (int t = 0; t < tmax; t++) {
                int c = sys.update();
                sys.infectedTimeSteps(diMap);
            }
            String filename = "SIS-Degree2Infected.txt";
            try (BufferedWriter out = FileIO.openWriter(filename)) {
                for (Node node : diMap.keySet()) {
                    List<Node> neightours = ba.neighbours(node);
                    FileIO.writeSSV(out, neightours.size(), diMap.get(node));
                }
            }
        }

    
}
