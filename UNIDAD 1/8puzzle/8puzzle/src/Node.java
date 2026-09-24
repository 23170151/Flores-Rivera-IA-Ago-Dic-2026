public class Node {
    private String state;
    private Node parent;
    private int depth;
    private int cost;

    public Node(String state, Node parent){
        this.state = state;
        this.parent = parent;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setCost(int i) {
        this.cost = i;
    }

    public int getCost() {
        return cost;
    }
}