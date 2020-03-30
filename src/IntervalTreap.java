public class IntervalTreap {
    private Node root;  // root of this treap.
    private int size;   // number of nodes in the treap.
    private int height; // height of the treap.

    public IntervalTreap(){
        root = null;
        size = 0;
        height = -1;    // empty treaps have -1 height
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
        height++;   // update height to reflect new number of nodes in treap
        if(root == null) root = z;
        else{
            Node temp = new Node(root.getInterv());
            Node lastVisit = null;
            int lastLeft = -1;
            int key = z.getInterv().getLow();
            while(temp!=null){
                lastVisit = temp;
                if(key < temp.getInterv().getLow()){
                    temp = temp.getLeft();
                    lastLeft = 1;
                }
                else{
                    temp = temp.getRight();
                    lastLeft = 0;
                }
            }
            z.setParent(lastVisit);

            //  setting lastVisit's children
            if(lastLeft==1){
                lastVisit.setLeft(z);
            }
            else if(lastLeft==0){
                lastVisit.setRight(z);
            }
            else  System.out.println("Error: lastLeft not initialized, lastLeft ="+ lastLeft);
            // lastLeft = -1 if not initialized otherwise

            while(z!=root && z.getPriority() < z.getParent().getPriority()){
                if(z.getParent().getLeft().equals(z)){
                    rightRotate(z);
                }
                else{
                    leftRotate(z);
                }
            }
        }
    }

    public void intervalDelete(Node z){
        //TODO: decrease the size by 1 upon successfully deleting node
    }

    public Node intervalSearch(Interval i){
        Node temp = new Node(root.getInterv());
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
    //helper methods
    public boolean overlap(Interval y,Interval z){
        if(y.getLow()<=z.getHigh() && z.getLow()<=y.getHigh())   return true;
        else return false;
    }

    public void rightRotate(Node z){
        z.getParent().setLeft(z.getRight());
        z.setRight(z.getParent());
        Node paa = z.getParent();

        if(paa.equals(root)){
            root = z;
        }
        else{   // making relations with grandparent
            if(paa.getParent().getLeft().equals(paa)){
                // My paa's a left child
                paa.getParent().setLeft(z);
            }
            else {
                // My paa's a right child
                paa.getParent().setRight(z);
            }
        }
        z.setParent(paa.getParent());      //TODO: paa.Parent might be null
        //  I am my original paa's parent
        paa.setParent(z);

    }

    public void leftRotate(Node z){
        z.getParent().setRight(z.getLeft());
        z.setLeft(z.getParent());
        Node paa = z.getParent();

        if(paa.equals(root)){
            root = z;
        }
        else{   // making relations with grandparent
            if(paa.getParent().getLeft().equals(paa)){
                // My paa's a left child
                paa.getParent().setLeft(z);
            }
            else {
                // My paa's a right child
                paa.getParent().setRight(z);
            }
        }
        z.setParent(paa.getParent());      //TODO: paa.Parent might be null
        //  I am my original paa's parent
        paa.setParent(z);
    }
}
