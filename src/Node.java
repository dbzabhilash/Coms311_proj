import java.util.Random;

public class Node {
    private Node left = null;
    private Node right = null;
    private Node parent = null;
    private Interval interv;
    private int nodeHeight;
    private int imax;
    private int priority;
    public Node(Interval i){
        interv = i;
        //TODO: generate a priority for the node
        Random random = new Random();
        priority = random.nextInt(Integer.MAX_VALUE);

        // imax set to i.getHigh() by default
        imax = i.getHigh();
        nodeHeight = 0;
    }
    public Node(Node node_OG){    //  constructor overloading for cloning
        interv = node_OG.getInterv();
        parent = node_OG.getParent();
        left = node_OG.getLeft();
        right = node_OG.getRight();
        imax = node_OG.getIMax();
        nodeHeight = node_OG.getNodeHeight();
        priority = node_OG.getPriority();

    }
    public Node cloner(Node node_OG){
        Node retNode = new Node(node_OG.getInterv());
        retNode.setLeft(node_OG.getLeft());
        retNode.setRight(node_OG.getRight());
        retNode.setParent(node_OG.getParent());
        retNode.setIMax(node_OG.getIMax());
        retNode.setPriority(node_OG.getPriority());
        return retNode;
    }
    public Node getParent(){
        return parent;
    }

    public Node getLeft(){
        return left;
    }

    public Node getRight(){
        return right;
    }

    public Interval getInterv() {
        return interv;
    }

    public int getIMax(){
        return imax;
    }

    public int getPriority(){
        return priority;
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    // helper methods
    public void setParent(Node parent){
        this.parent = parent;
    }
    public void setLeft(Node left){
        this.left = left;
    }
    public void setRight(Node right){
        this.right = right;
    }
    public int setIMax(int newImax){
        this.imax = newImax;
        return this.imax;
    }
    public int setPriority(int newPriority){
        this.priority = newPriority;
        return this.priority;
    }
    public int setNodeHeight(int nodeHeight){
        this.nodeHeight = nodeHeight;
        return this.nodeHeight;
    }

}
