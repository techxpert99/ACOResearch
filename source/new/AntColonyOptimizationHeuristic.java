import java.io.*;
import java.util.*;

public class AntColonyOptimizationHeuristic
{
    /**
     * N : Number of generations of ants
     * M : Number of ant siblings
     * 
     */

    public static final double INITIAL_PHEROMONE = 1.0;
    public static final double ALPHA=1.0;
    public static final double BETA=1.0;
    public static final double GAMMA=1.0;
    public static final double DECAY_CONST=0.25;
    public static final double TRAIL_CONST=1.0;
    
    public static final int MAX_ANTS = 8;
    public static final int MAX_GENERATIONS = 8;
    public static final int MAX_SELECTED = 4;
    public static final int MAX_FRAC_DIG = 5;

    private Graph graph;
    private Path shortestPath;

    public void readAndSetGraph(String fnodes, String fedges) throws IOException
    {
        this.graph = GraphReader.readGraph(fnodes, fedges);
    }

    private void pre_calc_attractiveness()
    {
      for(NeighborList nl:graph.E())
          for(Edge e : nl.getList())
              e.setAttractiveness(1.0/e.getWeight());
    }

    private double calc_prob(double pheromone,double attractiveness,double traffic_density)
    {
        return Math.pow(pheromone,ALPHA)*Math.pow(attractiveness,BETA)*Math.pow(traffic_density,GAMMA);
    }

    private long getIntegralProb(double num)
    {
        return (long) (num*Math.pow(10,MAX_FRAC_DIG));
    }

    private int probable_choose(ArrayList<Double> prob_list)
    {
        //Converting Probability to Integer

        ArrayList<Long> norm_prob_list=new ArrayList<Long>();

        for(int i=0; i<prob_list.size(); i++)
            norm_prob_list.set(i,getIntegralProb(prob_list.get(i)));

        long seed = (long) Math.random(); 
        
        //Normalizing seed

        long normalizer = 0;
        for(long prob : norm_prob_list)
            normalizer += prob;
        
        seed = seed%normalizer;

        //Selecting a number in [0,prob_list.length()-1] using probability distribution from prob_list
         
        long prefix_sum=0;
        int index=0;
        
        for(long prob : norm_prob_list)
        {
            if(prefix_sum <= seed && seed < prefix_sum+prob)
                return index;
            index++;
            prefix_sum+=prob;
        }

        return index;
    }

    private Edge pickNextUnvisitedVertex(Path path, Vertex source)
    {
        ArrayList<Edge> neighbor_row;

        if(path.top()==null)
            neighbor_row = graph.getNeighbors(source).getList();
        else
            neighbor_row = graph.getNeighbors(path.top().to()).getList();
        
        
        for(Edge neighbor : neighbor_row)
        {
            for(Edge e : path.getList())
            {
                if(neighbor.equals(e))
                {
                    neighbor.setPheromone(0.0);
                    break;
                }
            }
        }

        ArrayList<Double> prob_list=new ArrayList<Double>();
        double normalizer=0.0;
        double prob=0.0;
        for(Edge e:neighbor_row)
        {
            prob = calc_prob(e.getPheromone(),e.getAttractiveness(),e.getTrafficDensity());
            prob_list.add(prob);
            normalizer += prob;
        }

        for(int i=0;i<prob_list.size();i++)
           prob_list.set(i,prob_list.get(i)/normalizer);

        int local_index=probable_choose(prob_list);
        return neighbor_row.get(local_index);

    }

    private Path generateSinglePath(Vertex src, Vertex dest)
    {
        pre_calc_attractiveness();
        Path path = new Path();
        Edge edge;
        while(!((edge = pickNextUnvisitedVertex(path, src)).to().equals(dest)))
            path.add(edge);
        path.add(edge);
        return path;
    }

    private Path[] generateSortedPaths(Vertex src, Vertex dest){
        Path ptemp;
        PriorityQueue<Path> pQueue = new PriorityQueue<Path>(new PathComparator());
        for(int i=0;i<MAX_ANTS;i++){
              ptemp=generateSinglePath(src, dest);
              pQueue.add(ptemp);
        }
        
        Path explored_paths[] = new Path[MAX_ANTS];
        return pQueue.toArray(explored_paths);
    }

    private void updateShortestPath(Path shortest)
    {
        if(shortest==null)
            return;
        if(this.shortestPath==null || (this.shortestPath.getLength() > shortest.getLength()))
            this.shortestPath=shortest;
    }
    
    private  double calcPhermononeTrail(Path path)
    {
        return TRAIL_CONST/path.getLength();
    }

    public void updatePheromoneTrails(Path[] explored_paths)
    {
        //Decaying previous pheromones
        for(NeighborList n : graph.E())
            for(Edge e : n.getList())
                e.setPheromone((e.getPheromone() - INITIAL_PHEROMONE)*(1-DECAY_CONST));
        
        //Depositing new Pheromones
        for(int i=0; i<MAX_SELECTED; i++){
            double trail = calcPhermononeTrail(explored_paths[i]);
             for(Edge e : explored_paths[i].getList())
                e.addPheromone(trail);
            }
    }

    public Path findShortestPath(Vertex src, Vertex dest)
    {
        for( int gen = 0; gen < MAX_GENERATIONS; gen++)
        {
            Path explored_paths[] = generateSortedPaths(src, dest);
            updateShortestPath(explored_paths[0]);
            updatePheromoneTrails(explored_paths);
        }

        return shortestPath;
    }

    public Path findShortestPath(int src_id, int dest_id)
    {
        return findShortestPath(graph.findVertex(src_id), graph.findVertex(dest_id));
    }
}