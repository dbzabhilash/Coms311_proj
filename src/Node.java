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

        // imax set to -1 by default, TODO: consider changing to interv.getHigh()
        imax = -1;
        nodeHeight = 0;
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

}
