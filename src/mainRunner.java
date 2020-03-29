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
        for(int n=0; n<25; n++){
            randLow = rand.nextInt(100);
            randHigh = rand.nextInt(100) + randLow;
            i = new Interval(randLow,randHigh);
            nd = new Node(i);
            ndPrio = nd.getPriority();
            ndImax = nd.getIMax();
            System.out.println("Node nd"+(n+1)+" stores interval " +
                    "("+randLow+","+randHigh+") has priority: "+ ndPrio+" and Imax: "+ ndImax);

        }
        IntervalTreap T;

    }


}
