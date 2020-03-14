import java.util.*;

public class Path{
    private ArrayList<Edge> list=new ArrayList<Edge>();
    private double length=0;
    public void add(Edge e){
        length += e.getWeight();
        list.add(e);
    }
    public double getLength(){
        return length;
    }
    public Edge top(){
        if(list.size()==0)
            return null;
        return list.get(list.size()-1);
    }
    public ArrayList<Edge> getList(){
        return list;
    }
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