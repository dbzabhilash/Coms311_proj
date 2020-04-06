public class IntervalTreap {
    private Node root;  // root of this treap.
    private int size;   // number of nodes in the treap.
    private int height; // height of the treap.

    public IntervalTreap() {
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
        if (root == null) return -1;
        else return root.getNodeHeight();
    }

    public void intervalInsert(Node z) {
        size++;     // update size to reflect new number of nodes in treap
        if (root == null) root = z;
        else {
            Node temp = root; //dont make this new lmao
            Node lastVisit = null;
            int lastLeft = -1;
            int counter = 0;
            int key = z.getInterv().getLow();

            while (temp != null) {
                if (key < temp.getInterv().getLow()) {

                    lastLeft = 1;
                    if (temp.getLeft() == null) {
                        temp.setIMax(Math.max(temp.getIMax(), z.getIMax()));
                        break;
                    } else {
                        temp.setIMax(Math.max(temp.getIMax(), z.getIMax()));
                        temp = temp.getLeft();
                    }

                } else {
                    lastLeft = 0;
                    if (temp.getRight() == null) {
                        temp.setIMax(Math.max(temp.getIMax(), z.getIMax()));
                        break;
                    } else {
                        temp.setIMax(Math.max(temp.getIMax(), z.getIMax()));
                        temp = temp.getRight();
                    }

                }
            }
            z.setParent(temp);

            //  setting lastVisit's children
            if (lastLeft == 1) {
                temp.setLeft(z);
            } else if (lastLeft == 0) {
                temp.setRight(z);
            } else System.out.println("Error: lastLeft not initialized, lastLeft =" + lastLeft);
            // lastLeft = -1 if not initialized otherwise
            temp = z;
            while (temp.getParent() != null) {
                counter++;
                temp = temp.getParent();
                if (temp.getNodeHeight() < counter) {
                    temp.setNodeHeight(counter);
                }
            }

            while (z != root && z.getPriority() < z.getParent().getPriority()) {
                if (z.getParent().getLeft() == z) {
                    rightRotate(z);
                } else {
                    leftRotate(z);
                }
            }

            //height
            temp = z;
            counter = temp.getNodeHeight();
            while (temp.getParent() != null) {
                counter++;
                temp = temp.getParent();
                if (temp.getNodeHeight() < counter) {
                    temp.setNodeHeight(counter);
                }
            }

        }
    }

    public void intervalDelete(Node z) { //TODO

        if (z.getLeft() != null && z.getRight() != null) {
            Node succ = Minimum(z.getRight());
            // detatching successor from its parent
            succ.getParent().setLeft(succ.getRight());
            succ.getRight().setParent(succ.getParent());
            // swapping succ w z
            succ.setLeft(z.getLeft());
            z.getLeft().setParent(succ);
            succ.setRight(z.getRight());
            z.getRight().setParent(succ);
            succ.setParent(z.getParent());
            if(z.getParent().getLeft()==z){
                z.getParent().setLeft(succ);
            }
            else{
                z.getParent().setRight(succ);
            }

            //Maintaining priority
            int leftPrio, rightPrio;
            leftPrio = succ.getLeft().getPriority();
            rightPrio = succ.getRight().getPriority();
            if(succ.getLeft()==null)    leftPrio = -1;
            if(succ.getRight()==null)    rightPrio = -1;

            while (succ.getLeft()!=null || succ.getRight()!=null){
                if(succ.getPriority() > leftPrio || succ.getPriority() > rightPrio){
                    
                }

            }


        }
        else if (z.getLeft() == null && z.getRight() != null) {

            if (z == root) {
                root = z.getRight();
            }
            else{
                Node parent = z.getParent();
                if (parent.getLeft() == z) {
                    parent.setLeft(z.getRight());
                }
                else {
                    parent.setRight(z.getRight());
                }
                z.getRight().setParent(parent);
            }
        }
        else if (z.getRight() == null && z.getLeft() != null) {
            if (z == root) {
                root = z.getLeft();
                z.getLeft().setParent(null); //new root must not have parent
            }
            else {
                Node parent = z.getParent();
                if (parent.getLeft() == z) {
                    parent.setLeft(z.getLeft());
                }
                else {
                    parent.setRight(z.getLeft());
                }
                z.getLeft().setParent(parent);
            }
        }
        else {
            //z is leaf node, just remove it
            if (z == root) {
                root = null;
            } else if (z.getParent().getLeft() == z) {
                //z is left child
                z.getParent().setLeft(null);
            } else {
                z.getParent().setRight(null);
            }
        }

    }

    public Node intervalSearch(Interval i) {
        Node temp = root;
        while (temp != null && !overlap(temp.getInterv(), i)) {
            if (temp.getLeft() != null && temp.getLeft().getIMax() >= i.getLow()) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
        }
//        if(temp == null) return null;
        return temp;
    }

    //helper methods
    public boolean overlap(Interval y, Interval z) {
        if (y.getLow() <= z.getHigh() && z.getLow() <= y.getHigh()) return true;
        else return false;
    }

    public void rightRotate(Node z) {
        z.getParent().setLeft(z.getRight());
        z.setRight(z.getParent());
        Node paa = z.getParent();

        if (paa == root) {
            root = z;
        } else {   // making relations with grandparent
            if (paa.getParent().getLeft() == paa) { //check if left child
                // My paa's a left child
                paa.getParent().setLeft(z);
            } else {
                // My paa's a right child
                paa.getParent().setRight(z);
            }
        }
        z.setParent(paa.getParent());      //TODO: paa.Parent might be null
        //  I am my original paa's parent after rotation
        paa.setParent(z);

        //now re-validate iMax values
        if (z.getRight() == null) {
            z.setIMax(Math.max(z.getInterv().getHigh(), z.getLeft().getIMax()));
        } else if (z.getLeft() == null) {
            z.setIMax(Math.max(z.getInterv().getHigh(), z.getRight().getIMax()));
        } else {
            z.setIMax(Math.max(z.getInterv().getHigh(), Math.max(z.getLeft().getIMax(), z.getRight().getIMax())));
        }

        //now adjust height
        int leftH = 0, rightH = 0;
        if (paa.getLeft() != null) leftH = paa.getLeft().getNodeHeight();
        if (paa.getRight() != null) rightH = paa.getRight().getNodeHeight();
        if (paa.getLeft() == null && paa.getRight() == null) {
            paa.setNodeHeight(0);
        } else {
            paa.setNodeHeight(Math.max(leftH, rightH) + 1);
        }

        leftH = 0;
        rightH = 0;
        if (z.getLeft() != null) leftH = z.getLeft().getNodeHeight();
        if (z.getRight() != null) rightH = z.getRight().getNodeHeight();
        if (z.getLeft() == null && z.getRight() == null) {
            z.setNodeHeight(0);
        } else {
            z.setNodeHeight(Math.max(leftH, rightH) + 1);
        }
    }

    public void leftRotate(Node z) {
        z.getParent().setRight(z.getLeft());
        z.setLeft(z.getParent());
        Node paa = z.getParent();

        if (paa == root) {
            root = z;
        } else {   // making relations with grandparent
            if (paa.getParent().getLeft() == paa) {
                // My paa's a left child
                paa.getParent().setLeft(z);
            } else {
                // My paa's a right child
                paa.getParent().setRight(z);
            }
        }
        z.setParent(paa.getParent());      //TODO: paa.Parent might be null
        //  I am my original paa's parent after rotation
        paa.setParent(z);

        //now re-validate iMax values
        if (z.getRight() == null) {
            z.setIMax(Math.max(z.getInterv().getHigh(), z.getLeft().getIMax()));
        } else if (z.getLeft() == null) {
            z.setIMax(Math.max(z.getInterv().getHigh(), z.getRight().getIMax()));
        } else {
            z.setIMax(Math.max(z.getInterv().getHigh(), Math.max(z.getLeft().getIMax(), z.getRight().getIMax())));
        }

        //now adjust height
        int leftH = 0, rightH = 0;
        if (paa.getLeft() != null) leftH = paa.getLeft().getNodeHeight();
        if (paa.getRight() != null) rightH = paa.getRight().getNodeHeight();
        if (paa.getLeft() == null && paa.getRight() == null) {
            paa.setNodeHeight(0);
        } else {
            paa.setNodeHeight(Math.max(leftH, rightH) + 1);
        }

        leftH = 0;
        rightH = 0;
        if (z.getLeft() != null) leftH = z.getLeft().getNodeHeight();
        if (z.getRight() != null) rightH = z.getRight().getNodeHeight();
        if (z.getLeft() == null && z.getRight() == null) {
            z.setNodeHeight(0);
        } else {
            z.setNodeHeight(Math.max(leftH, rightH) + 1);
        }

    }

    //helper methods
    public Node Minimum(Node z){
        Node current = z;
        while(current.getLeft()!=null) current = current.getLeft();
        return current;
    }
    public int tester(Node node) {

        if (node.getLeft() != null) {
            return node.getLeft().getIMax();
        } else if (node.getRight() != null) {
            return node.getRight().getIMax();
        } else {
            return 0;
        }
    }
}
