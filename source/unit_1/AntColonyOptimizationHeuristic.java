import java.io.*;
import java.util.*;

public class AntColonyOptimizationHeuristic
{

    public static final double INITIAL_PHEROMONE = 1.0;
    public static final double ALPHA=1.0;
    public static final double BETA=1.0;
    public static final double GAMMA=-1.0;
    public static final double DELTA=1.0;
    public static final double DECAY_CONST=0.25;
    public static final double TRAIL_CONST=1.0;

    public static final int MAX_ANTS = 8;
    public static final int MAX_GENERATIONS = 8;
    public static final int MAX_SELECTED = 4;
    public static final int MAX_FRAC_DIG = 5;

    private Graph graph;
    private  ArrayList<ShortestPathElement> shortestPaths;

    class ShortestPathElement
    {
        public ShortestPathElement(Path path, int weight)
        {
            this.path = path;
            this.weight = weight;
        }

        public void incrementWeight()
        {
            weight++;
        }

        public int getWeight()
        {
            return weight;
        }

        public Path getPath()
        {
            return path;
        }

        private int weight;
        private Path path; 
    }
    public void readAndSetGraph(String fnodes, String fedges) throws IOException
    {
        this.graph = GraphReader.readGraph(fnodes, fedges);
        pre_calc_attractiveness();
    }

    private void pre_calc_attractiveness()
    {
      for(NeighborList nl:graph.E())
          for(Edge e : nl.getList())
              e.setAttractiveness(1.0/e.getWeight());
    }

    private double calc_prob(double pheromone, double attractiveness, double traffic_density, double edge_reliability)
    {
        return Math.pow(pheromone,ALPHA)*Math.pow(attractiveness,BETA)*Math.pow(traffic_density,GAMMA)*Math.pow(edge_reliability,DELTA);
    }

    private long getIntegralProb(double num)
    {
        return (long) (num*Math.pow(10,MAX_FRAC_DIG));
    }

    private int probable_choose(ArrayList<Double> prob_list)
    {
        //Converting Probability to Integer
        ArrayList<Long> norm_prob_list=new ArrayList<Long>(prob_list.size());

        for(int i=0; i<prob_list.size(); i++)
            norm_prob_list.add(i,getIntegralProb(prob_list.get(i)));

        long seed = (long) (Math.random() * Math.pow(2,63));
        
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

        //Control Never Reaches Here
        return index;
    }

    private Edge pickNextUnvisitedVertex(Path path, Vertex source)
    {
    
        ArrayList<Edge> neighbor_row;

        if(path.top()==null)
            neighbor_row = graph.getNeighbors(source).getList();
        else
            neighbor_row = graph.getNeighbors(path.top().to()).getList();
        
        
        if(neighbor_row==null || neighbor_row.size()==0)
        {
            path.invalidate();
            return null;
        }

        boolean number_unvisited=false;

        for(Edge neighbor : neighbor_row)
        {
            boolean visited=false;
            for(Edge e : path.getList())
            {
                if(neighbor.equals(e))
                {
                    neighbor.setPheromone(0.0);
                    visited=true;
                    break;
                }
            }

            if(!visited)
                number_unvisited=true;
        }

        if(!number_unvisited)
        {
            path.invalidate();
            return null;
        }

        ArrayList<Double> prob_list=new ArrayList<Double>();
        double normalizer=0.0;
        double prob=0.0;
        for(Edge e:neighbor_row)
        {
            prob = calc_prob(e.getPheromone(),e.getAttractiveness(),e.getTrafficDensity(), e.getEdgeReliability());
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
        Path path = new Path();
        Edge edge;
        while(((edge = pickNextUnvisitedVertex(path, src))!=null)&&!edge.to().equals(dest))
            path.add(edge);
        if(edge!=null)
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

        boolean exists=false;
        ShortestPathElement path_elem = new ShortestPathElement(null,0);
        for(ShortestPathElement element : this.shortestPaths){
            if(element.getPath().equals(shortest)){
                exists=true;
                path_elem=element;
                break;
        }}
            
        if(exists)
            path_elem.incrementWeight();
        else
            this.shortestPaths.add(new ShortestPathElement(shortest, 1));
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
                e.setPheromone((e.getPheromone() - INITIAL_PHEROMONE)*(1-DECAY_CONST) + INITIAL_PHEROMONE);
        
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

        ShortestPathElement minimum = shortestPaths.get(0);

        for(ShortestPathElement element : shortestPaths)
            if(element.getWeight()<minimum.getWeight())
            minimum=element;

        return minimum.getPath();
    }

    public Path findShortestPath(int src_id, int dest_id)
    {
        shortestPaths = new ArrayList<ShortestPathElement>();
        return findShortestPath(graph.findVertex(src_id), graph.findVertex(dest_id));
    }
}