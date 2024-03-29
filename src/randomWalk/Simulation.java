package randomWalk;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myLib.utils.FileIO;
import myLib.utils.Utils;
import networkModels.BA;
import network.*;

/**
 *
 * @author tadaki
 */
public class Simulation {

    /**
     * 各頂点に居るwalker数
     *
     * @param network
     * @param walkers
     * @return
     */
    public static Map<Node, Integer> walkersAtNode(
            AbstractNetwork network, List<Walker> walkers) {
        Map<Node, Integer> map = Collections.synchronizedMap(new HashMap<>());
        for (Walker w : walkers) {
            Node node = w.getCurrentNode();
            if (map.keySet().contains(node)) {
                int n = map.get(node);
                map.put(node, n + 1);
            } else {
                map.put(node, 1);
            }
        }
        return map;
    }

    /**
     * 各頂点上のwalker数(node2numWalker)を頂点の次数に対するwalker数へ変換
     *
     * @param network
     * @param node2numWalker
     * @return
     */
    public static List<Point> degreeVSNumWalkers(AbstractNetwork network,
            Map<Node, Integer> node2numWalker) {
        List<Point> list = Utils.createList();//(次数,walker数)のリスト





        return list;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int numSite = 1000;
        int m = 2;
        int tmax = 10000;
        int numWalkers = 100000;
        BA ba = new BA(numSite, m);
        ba.createNetwork();

        List<Walker> walkers = new ArrayList<>();
        for (int i = 0; i < numWalkers; i++) {
            Walker walker = new Walker(ba);
            walkers.add(walker);
        }

        for (int t = 0; t < tmax; t++) {
            walkers.forEach(w -> w.move());
        }

        Map<Node, Integer> node2numWalker = walkersAtNode(ba, walkers);
        List<Point> plist = degreeVSNumWalkers(ba, node2numWalker);

        String filename = "RandomWalk-txt";
        try (BufferedWriter out = FileIO.openWriter(filename)) {
            for (Point p : plist) {
                FileIO.writeSSV(out, p.x, p.y);
            }
        }
    }

}
