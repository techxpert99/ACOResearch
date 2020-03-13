import java.util.*;
import java.lang.*;
class GenerateSortedPaths{
       private int m;
    public PriorityQueue<Path> getPriorityQueue(){
        Path ptemp=new Path();
        PriorityQueue<Path> pQueue = new PriorityQueue<Path>(new PathComparator());
        for(int i=0;i<m;i++){
              ptemp=GenerateSinglePath(m);
              pQueue.add(ptemp);
        }
      //  while (!pQueue.isEmpty()) { 
        //System.out.println(pQueue.poll().length); 
       // }
        /*for(int i=0;i<m;i++){
                p=GenerateSinglePath();
                pQueue.add(p);
        }*/
        return pQueue;
}
}
class Path{
    private ArrayList<Vertex> list=new ArrayList<Vertex>();
    private double length=0;
    public void add(Vertex v){
        double Al;
        Al=graph.adj_list.getNeighborList(top().index).getNeighbor(v.index).weight;
        length+=Al;
        list.add(v);
    }
    public double getLength(){
        return length;
    }
    public Vertex top(){
        Vertex tp;
        tp=list.get(list.size()-1);
                 return tp;
    }
    public ArrayList<Vertex> getList(){
        return list;
    }

}
class Vertex{
    private int id;
    private String name;
    private VertexData Vd=new VertexData();
    Vertex(int id,String name,VertexData Vd){
        this.id=id;
        this.name=name;
        this.Vd=Vd;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public VertexData getData(){
        return Vd;
    }

    
}
class VertexData{

}
class PathComparator implements Comparator<Path>{ 
    public int compare(Path s1, Path s2) { 
        if (s1.getLength() > s2.getLength()) 
            return 1; 
        else if (s1.getLength() < s2.getLength()) 
            return -1; 
            return 0;
             
        } 
} 
class UpdPhr{
    int m;
    GenerateSortedPaths g=new GenerateSortedPaths();
    Edge e;
    private PriorityQueue<Path> pQueue = new PriorityQueue<Path>();
    private int BEST_PATH;
    private ArrayList<Vertex> list = new ArrayList<Vertex>();
    private int count=0;
    public void updatePheromone(){
    pQueue=g.getPriorityQueue();
    for(int i=0;i<BEST_PATH;i++){
         list=pQueue.poll().getList();
         int ln=list.size();
         for(int j=0;j<ln-1;j++){
            e=graph.adj_list.getNeighborList(list.get(j).index).getNeighbor(list.get(j+1).index);
            e.setPheromone=(e.getPheromone-INITIAL_PHEROMONE)*(1-DECAY_CONST)+MAX_PHEROMONE*(1-Math.exp(-e.weight));
            count+=e.weight;
         }
        }
    }
}
