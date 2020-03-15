import java.util.*;

public class Path{
    private ArrayList<Edge> list=new ArrayList<Edge>();
    private double length;

    public Path()
    {
        length=0.0;
    }

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

    public void invalidate()
    {
        length=Double.MAX_VALUE;
    }

    @Override
    public boolean equals(Object _path)
    {
        Path path = (Path) _path;

        Iterator<Edge> i1 = path.getList().iterator();
        Iterator<Edge> i2 = getList().iterator();
        
        while(i1.hasNext() && i2.hasNext())
        {
            Edge e1 = i1.next();
            Edge e2 = i2.next();

            if(!e1.equals(e2))
                return false;
        }

        if(i1.hasNext() || i2.hasNext())
            return false;

        return true;
    }

    public void debug()
    {
        System.out.println("[Path]:\n");
        for(Edge e:list)
            e.debug();
        System.out.println("[/Path]");
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