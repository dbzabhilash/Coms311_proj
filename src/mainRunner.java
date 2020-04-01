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

        //  Test Nullable and cloning
        newND.setParent(null);
        nd.setParent(newND.getParent());
        System.out.println("Node newND parent = "+newND.getParent());
        System.out.println("Node nd parent = "+nd.getParent());
        Node nod0 = new Node(i);
        Node nod1 = new Node(nod0);
        nod1.setIMax(100);
        System.out.println("Node nod0 parent = "+nod0.getIMax());
        System.out.println("Node nod1 parent = "+nod1.getIMax());

        //  Test treap insertion
        IntervalTreap T = new IntervalTreap();
        int lowest = Integer.MAX_VALUE;
        for(int n=0; n<5; n++){
            randLow = rand.nextInt(100);
            randHigh = rand.nextInt(100) + randLow;
            i = new Interval(randLow,randHigh);
            nd = new Node(i);
            ndPrio = nd.getPriority();
            ndImax = nd.getIMax();
            System.out.println("Node nd"+(n+1)+" stores interval " +
                    "("+randLow+","+randHigh+") has priority: "+ ndPrio+" and Imax: "+ ndImax);
            if(ndPrio<lowest)   lowest = ndPrio;
            T.intervalInsert(nd);
        }
        nd = new Node(new Interval(52,92));
        T.intervalInsert(nd);


        Node rootNode = T.getRoot();
        System.out.println(rootNode);
        System.out.println("Root node has interval ("+rootNode.getInterv().getLow()+","+rootNode.getInterv().getHigh()+") and priority "+rootNode.getPriority() );
        System.out.println("The lowest priority is "+lowest);
        Node someND = T.intervalSearch(new Interval(0,1));
        if(someND == null){
            System.out.println("Interval (15,70) falls in some Node with interval.Low ->  "+ T.intervalSearch(new Interval(0,100000)).getInterv().getLow());

        }
        else{
            System.out.println("Interval (15,70) falls in some Node with interval.Low ->  "+ T.intervalSearch(new Interval(0,100000)).getInterv().getLow());
        }

    }


}
