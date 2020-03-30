import java.util.Random;

public class mainRunner {
    public static void main(String[] args){
        Interval i = new Interval(4,5);
        Node nd = new Node(i);
        int ndImax = nd.getIMax();
        int ndPrio = nd.getPriority();
        //System.out.println("Node nd has priority: "+ ndPrio+" and Imax: "+ ndImax);

        Random rand = new Random();
        int randHigh, randLow;
//        for(int n=0; n<25; n++){
//            randLow = rand.nextInt(100);
//            randHigh = rand.nextInt(100) + randLow;
//            i = new Interval(randLow,randHigh);
//            nd = new Node(i);
//            ndPrio = nd.getPriority();
//            ndImax = nd.getIMax();
//            System.out.println("Node nd"+(n+1)+" stores interval " +
//                    "("+randLow+","+randHigh+") has priority: "+ ndPrio+" and Imax: "+ ndImax);
//
//        }

        //  Deep object copy test
        Node newND = new Node(nd.getInterv());
        newND.setIMax(34);
        System.out.println("Node nd imax = "+nd.getIMax());
        System.out.println("Node newND imax = "+newND.getIMax());

        //  Test Nullable
        newND.setParent(null);
        nd.setParent(newND.getParent());
        System.out.println("Node newND parent = "+newND.getParent());
        System.out.println("Node nd parent = "+nd.getParent());

        //  Test treap insertion
        IntervalTreap T;
        T.

    }


}
