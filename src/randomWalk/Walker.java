package randomWalk;

import java.util.List;
import network.*;
/**
 *
 * @author tadaki
 */
public class Walker {
    private final AbstractNetwork network;
    private Node currentNode;

    public Walker(AbstractNetwork network) {
        this.network = network;
        currentNode=network.getNodes().get(0);
    }
    
    public void move(){
        List<Node> neighbours = network.neighbours(currentNode);
        int k = (int)(neighbours.size()*Math.random());
        currentNode = neighbours.get(k);
    }

    public Node getCurrentNode() {
        return currentNode;
    }
    
}
