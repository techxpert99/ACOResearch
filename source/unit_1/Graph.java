import java.util.*;

public  class Graph
{
    private AdjacencyList adj_list;
    
    public Graph(AdjacencyList adj_list)
    {
        this.adj_list = adj_list;
    }

    public Vertex[] V()
    {
        return adj_list.getVertices();
    }

    public NeighborList[] E()
    {
        return adj_list.getEdges();
    }

    public Edge getEdge(int uid, int vid)
    {
        return adj_list.getNeighborList(uid).getNeighbor(vid);
    }

    public NeighborList getNeighbors(Vertex v)
    {
        return adj_list.getNeighborList(v.getIndex());
    }

    public Vertex findVertex(int id)
    {
        for(Vertex v : V())
            if(v.getID() == id)
                return v;
        return null;
    }

    public void debug()
    {
        System.out.println("[Graph]");
        adj_list.debug();
        System.out.println("[/Graph]");
    }
}

class NeighborList
{
    private Vertex vertex;
    private ArrayList<Edge> neighborlist;
    
    public Edge getNeighbor(int index){
        Iterator<Edge> iter = neighborlist.iterator();
        for (Edge e : neighborlist) {
            if(e.to().getIndex()==index) return e;
        }
        return null;
    }
    
    public NeighborList(Vertex vertex)
    {
        neighborlist = new ArrayList<Edge>();
        this.vertex = vertex;
    }

    public void addNeighbor(Edge e)
    {
        neighborlist.add(e);
    }

    public boolean isNeighbor(Vertex v)
    {
        for(Edge neighbor : neighborlist)
            if(neighbor.to().equals(v))
                return true;
        return false;
    }

    public ArrayList<Edge> getList()
    {
        return neighborlist;
    }

    public Vertex getVertex()
    {
        return vertex;
    }

    public void debug()
    {
        System.out.println("[NeighborList]");
        vertex.debug();
        for(Edge e:neighborlist)
            e.debug();
        System.out.println("[/NeighborList]");
    }
}

class AdjacencyList
{
    private NeighborList[] adj_list;
    private Vertex[] vertices;

    public AdjacencyList(ArrayList<Vertex> vertex_list)
    {
        adj_list = new NeighborList[vertex_list.size()];
        Iterator<Vertex> iter = vertex_list.iterator();
        int index=0;
        while(iter.hasNext()){
            adj_list[index] = new NeighborList(iter.next());
            index++;
        }
        vertices = new Vertex[vertex_list.size()];
        vertices = vertex_list.toArray(vertices);
    }

    public void addEdge(Edge e)
    {
        adj_list[e.from().getIndex()].addNeighbor(e);
    }

    public NeighborList getNeighborList(int index)
    {
        return adj_list[index];
    }

    public Vertex[] getVertices()
    {
        return vertices;
    }

    public NeighborList[] getEdges()
    {
        return adj_list;
    }

    public Vertex getVertex(int index)
    {
        return vertices[index];
    }

    public boolean isEdge(Vertex u, Vertex v)
    {
        return adj_list[u.getIndex()].isNeighbor(v);
    }

    public void debug()
    {
        System.out.println("[AdjacencyList]");
        for(Vertex v : vertices)
            v.debug();
        for(NeighborList n : adj_list)
            n.debug();
    }
}