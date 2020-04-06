/**
 * Authors: Abhilash Tripathy, Abir Mojumder
 */
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
        size--;
        if (z.getLeft() != null && z.getRight() != null) {
            Node succ = Minimum(z.getRight());

            if(succ.getParent() == z) {
                succ.setLeft(z.getLeft());
                z.getLeft().setParent(succ);
                succ.setParent(z.getParent());
            }
            else {



                // detatching successor from its parent
                if(succ.getRight()!=null) {
                    succ.getParent().setLeft(succ.getRight());
                    succ.getRight().setParent(succ.getParent());
                }
                else{
                    succ.getParent().setLeft(null);
                }

                //  adjust height
                Node paa = succ.getParent();
                int leftH = 0, rightH = 0;
                while(paa.getParent()!=null){
                    // to handle NullPointer Exception
                    if (paa.getLeft() == null) leftH = 0;
                    else leftH = paa.getLeft().getNodeHeight();
                    if (paa.getRight() == null) rightH = 0;
                    else rightH = paa.getRight().getNodeHeight();

                    // if both heights left and right are 0
                    if(isLeaf(paa)) paa.setNodeHeight(0);
                    else paa.setNodeHeight(Integer.max(leftH,rightH)+1);

                    if (paa.getRight() == null) {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getLeft().getIMax()));
                    } else if (paa.getLeft() == null) {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getRight().getIMax()));
                    } else {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), Math.max(paa.getLeft().getIMax(), paa.getRight().getIMax())));
                    }
                    paa = paa.getParent();
                }


                // swapping succ with z
                succ.setLeft(z.getLeft());
                z.getLeft().setParent(succ);
                succ.setRight(z.getRight());
                z.getRight().setParent(succ);
                succ.setParent(z.getParent());
                succ.setNodeHeight(z.getNodeHeight());
                succ.setIMax(Math.max(succ.getInterv().getHigh(), z.getIMax()));
            }
            if(z.getParent()!=null && z.getParent().getLeft()==z){
                z.getParent().setLeft(succ);
            }
            else if(z.getParent()!=null && z.getParent().getRight()==z){
                z.getParent().setRight(succ);
            }
            else{
                root = succ;
            }

            //Maintaining priority

            while (!isLeaf(succ)){
                if(leftExist(succ) && !rightExist(succ)){  // if succ only has left child
                    if(succ.getPriority() > succ.getLeft().getPriority())   rightRotate(succ.getLeft());
                    else break;
                }
                else if(!leftExist(succ) && rightExist(succ)){  // if succ only has right child
                    if(succ.getPriority() > succ.getRight().getPriority())   leftRotate(succ.getRight());
                    else break;
                }
                //  if succ has both children
                else{
                    if(succ.getPriority() < succ.getLeft().getPriority() && succ.getPriority() < succ.getRight().getPriority()) break;

                    else if(succ.getLeft().getPriority() < succ.getRight().getPriority()){
                            rightRotate(succ.getLeft());
                    }
                    else leftRotate(succ.getRight());
                }
            }

            //  adjust height
            Node paa = succ.getParent();
            int leftH = 0, rightH = 0;
            while(paa.getParent()!=null){
                // to handle NullPointer Exception
                if (paa.getLeft() == null) leftH = 0;
                else leftH = paa.getLeft().getNodeHeight();
                if (paa.getRight() == null) rightH = 0;
                else rightH = paa.getRight().getNodeHeight();

                // if both heights left and right are 0
                if(isLeaf(paa)) paa.setNodeHeight(0);
                else paa.setNodeHeight(Integer.max(leftH,rightH)+1);

                if (paa.getRight() == null) {
                    paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getLeft().getIMax()));
                } else if (paa.getLeft() == null) {
                    paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getRight().getIMax()));
                } else {
                    paa.setIMax(Math.max(paa.getInterv().getHigh(), Math.max(paa.getLeft().getIMax(), paa.getRight().getIMax())));
                }
                paa = paa.getParent();
            }


        }
        else if (z.getLeft() == null && z.getRight() != null) {

            if (z == root) {
                root = z.getRight();
                z.getRight().setParent(null); //new root must not have parent
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

                Node paa = z.getParent();
                int leftH = 0, rightH = 0;
                while(paa.getParent()!=null){
                    // to handle NullPointer Exception
                    if (paa.getLeft() == null) leftH = 0;
                    else leftH = paa.getLeft().getNodeHeight();
                    if (paa.getRight() == null) rightH = 0;
                    else rightH = paa.getRight().getNodeHeight();

                    // if both heights left and right are 0
                    if(isLeaf(paa)) paa.setNodeHeight(0);
                    else paa.setNodeHeight(Integer.max(leftH,rightH)+1);

                    if (paa.getRight() == null) {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getLeft().getIMax()));
                    } else if (paa.getLeft() == null) {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getRight().getIMax()));
                    } else {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), Math.max(paa.getLeft().getIMax(), paa.getRight().getIMax())));
                    }
                    paa = paa.getParent();
                }

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

                //height
                Node paa = z.getParent();
                int leftH = 0, rightH = 0;
                while(paa.getParent()!=null){
                    // to handle NullPointer Exception
                    if (paa.getLeft() == null) leftH = 0;
                    else leftH = paa.getLeft().getNodeHeight();
                    if (paa.getRight() == null) rightH = 0;
                    else rightH = paa.getRight().getNodeHeight();

                    // if both heights left and right are 0
                    if(isLeaf(paa)) paa.setNodeHeight(0);
                    else paa.setNodeHeight(Integer.max(leftH,rightH)+1);

                    if (paa.getRight() == null) {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getLeft().getIMax()));
                    } else if (paa.getLeft() == null) {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getRight().getIMax()));
                    } else {
                        paa.setIMax(Math.max(paa.getInterv().getHigh(), Math.max(paa.getLeft().getIMax(), paa.getRight().getIMax())));
                    }
                    paa = paa.getParent();
                }

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
            //height
            Node paa = z.getParent();
            int leftH = 0, rightH = 0;
            while(paa.getParent()!=null){
                // to handle NullPointer Exception
                if (paa.getLeft() == null) leftH = 0;
                else leftH = paa.getLeft().getNodeHeight();
                if (paa.getRight() == null) rightH = 0;
                else rightH = paa.getRight().getNodeHeight();

                // if both heights left and right are 0
                if(isLeaf(paa)) paa.setNodeHeight(0);
                else paa.setNodeHeight(Integer.max(leftH,rightH)+1);
                if(isLeaf(paa)) paa.setIMax(paa.getInterv().getHigh());
                else if (paa.getRight() == null) {
                    paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getLeft().getIMax()));
                } else if (paa.getLeft() == null) {
                    paa.setIMax(Math.max(paa.getInterv().getHigh(), paa.getRight().getIMax()));
                } else {
                    paa.setIMax(Math.max(paa.getInterv().getHigh(), Math.max(paa.getLeft().getIMax(), paa.getRight().getIMax())));
                }
                paa = paa.getParent();
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


        if (!rightExist(z.getRight()) && leftExist(z.getRight())) {
            z.getRight().setIMax(Math.max(z.getRight().getInterv().getHigh(), z.getRight().getLeft().getIMax()));
        } else if (rightExist(z.getRight()) && !leftExist(z.getRight())) {
            z.getRight().setIMax(Math.max(z.getRight().getInterv().getHigh(), z.getRight().getRight().getIMax()));
        }
        else if(!leftExist(z.getRight()) && !rightExist(z.getRight())){
            z.getRight().setIMax(z.getRight().getInterv().getHigh());
        }
        else z.getRight().setIMax(Math.max(z.getRight().getInterv().getHigh(), Math.max(z.getRight().getLeft().getIMax(), z.getRight().getRight().getIMax())));


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

        if (!rightExist(z.getLeft()) && leftExist(z.getLeft())) {
            z.getLeft().setIMax(Math.max(z.getLeft().getInterv().getHigh(), z.getLeft().getLeft().getIMax()));
        } else if (rightExist(z.getLeft()) && !leftExist(z.getLeft())) {
            z.getLeft().setIMax(Math.max(z.getLeft().getInterv().getHigh(), z.getLeft().getRight().getIMax()));
        }
        else if(!leftExist(z.getLeft()) && !rightExist(z.getLeft())){
            z.getLeft().setIMax(z.getLeft().getInterv().getHigh());
        }
        else z.getLeft().setIMax(Math.max(z.getLeft().getInterv().getHigh(), Math.max(z.getLeft().getLeft().getIMax(), z.getLeft().getRight().getIMax())));


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

    public boolean isLeaf(Node node){
        if(node.getLeft() == null && node.getRight()==null) return true;
        else return false;
    }
    public boolean leftExist(Node node){
        if(node.getLeft()!=null) return true;
        else return false;
    }
    public boolean rightExist(Node node){
        if(node.getRight()!=null) return true;
        else return false;
    }
}
