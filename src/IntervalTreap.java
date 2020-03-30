public class IntervalTreap {
    private Node root;  // root of this treap.
    private int size;   // number of nodes in the treap.
    private int height; // height of the treap.

    public IntervalTreap(){
        root = null;
        size = 0;
        height = 0;
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public int getHeight() {
        return height;
    }

    public void intervalInsert(Node z){
        size++;     // update size to reflect new number of nodes in treap

    }

    public void intervalDelete(Node z){

    }

    public Node intervalSearch(Interval i){
        Node temp = root;
        while(temp!=null && !overlap(temp.getInterv(),i)){
            if(temp.getLeft().getIMax()>=i.getLow() && temp.getLeft()!=null){
                temp = temp.getLeft();
            }
            else{
                temp = temp.getRight();
            }
        }
        return temp;
    }

    public boolean overlap(Interval y,Interval z){
        if(y.getLow()<=z.getHigh() && z.getLow()<=y.getHigh())   return true;
        else return false;
    }
}
