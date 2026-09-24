import java.util.Comparator;


public class NodePriorityComparator implements Comparator<Node> {

    public int compare(Node n1, Node n2) {
        return Integer.compare(n1.getCost(), n2.getCost());
    }

}
