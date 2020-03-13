import java.util.Scanner;
import java.io.*;
import java.util.*;

public class ACOAlgorithm
{
    /**
     * N : Number of generations of ants
     * M : Number of ant siblings
     * 
     */
    private Graph graph;

    private static class EdgeData
    {

    }

    private static class VertexData
    {

    }

    private static class Graph
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
    }

    private static class Path
    {

    }

    public static class Vertex
    {
        private int index;
        private int id;
        private String nam;
        private VertexData data;

        public Vertex(int index, int id, String nam, VertexData data)
        {
            this.index = index;
            this.id = id;
            this.nam = nam;
            this.data = data;
        }

        public int getIndex()
        {
            return index;
        }

        public int getID()
        {
            return id;
        }

        public String getName()
        {
            return nam;
        }

        public VertexData getData()
        {
            return data;
        }
    }

    private static class Edge
    {
        private Vertex from;
        private Vertex to;
        private double weight;
        private EdgeData data;

        public Edge(Vertex from, Vertex to, double weight, EdgeData data)
        {
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.data = data;
        }

        public Vertex from()
        {
            return from;
        }

        public Vertex to()
        {
            return to;
        }

        public EdgeData getData()
        {
            return data;
        }

        public double weight()
        {
            return weight;
        }
    }

    private static class NeighborList
    {
        private Vertex vertex;

        private ArrayList<Vertex> neighborlist;
        public NeighborList(Vertex vertex)
        {
            neighborlist = new ArrayList<Vertex>();
            this.vertex = vertex;
        }

        public void addNeighbor(Vertex v)
        {
            neighborlist.add(v);
        }

        public boolean isNeighbor(Vertex v)
        {
            for(Vertex neighbor : neighborlist)
                if(neighbor.equals(v))
                    return true;
            return false;
        }

        public ArrayList<Vertex> getList()
        {
            return neighborlist;
        }

        public Vertex getVertex()
        {
            return vertex;
        }
    }

    private static class AdjacencyList
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
            adj_list[e.from.getID()].addNeighbor(e.to);
        }

        public NeighborList getNeighborList(int id)
        {
            return adj_list[id];
        }

        public Vertex[] getVertices()
        {
            return vertices;
        }

        public NeighborList[] getEdges()
        {
            return adj_list;
        }

        public Vertex getVertex(int id)
        {
            return vertices[id];
        }

        public boolean isEdge(Vertex u, Vertex v)
        {
            return adj_list[u.getID()].isNeighbor(v);
        }
    }

    public ACOAlgorithm()
    {

    }

    private Vector<String> getTokens(String line)
    {
        String delimiters = ",\n";
        Vector<String> tokens = new Vector<String>();
        String token="";

        for(int i=0; i<line.length(); i++)
        {
            char c = line.charAt(i);
            if(delimiters.contains(""+c))
            {
                tokens.add(token);
                token="";
            }
            else
                token+=c;
        }
        tokens.add(token);
        return tokens;
    }

    public void readAndSetGraph(String fnodes, String fedges) throws IOException
    {
        ArrayList<Vertex> vertex_list = scanNodes(fnodes);
        ArrayList<Edge> edge_list = scanEdges(fedges, vertex_list);

        this.graph = createGraph(vertex_list, edge_list);
    }

    private ArrayList<Vertex> scanNodes(String fnodes) throws IOException
    {
        try
        {
            File node_file = new File(fnodes);
            if(!node_file.exists())
                throw new IOException("Error! Node file not found!");
            
            FileInputStream node_stream = new FileInputStream(fnodes);
            ArrayList<Vertex> vertex_list = new ArrayList<Vertex>();
            Scanner freader = new Scanner(node_stream);
            int index=0;
            while(freader.hasNext())
            {
                Vector<String> tokens = getTokens(freader.nextLine());
                int id = Integer.parseInt(tokens.get(0));
                String nam = tokens.get(1);
                
                Vertex vertex = new Vertex(index,id,nam,null); //Vertex Data to be added in later revisions
                vertex_list.add(vertex);
                index++;
            }

            freader.close();
            return vertex_list;
        }
        catch(IOException ioe)
        {
            throw new IOException("Error! Unable to read node file");
        }
    }

    private ArrayList<Edge> scanEdges(String fedges, ArrayList<Vertex> vertex_list) throws IOException
    {
        try
        {
            ArrayList<Edge> edge_list = new ArrayList<Edge>();

            File edge_file = new File(fedges);
            if(!edge_file.exists())
                throw new IOException("Error! Edge file not found!");
            
            FileInputStream edge_stream = new FileInputStream(fedges);
            
            Scanner freader = new Scanner(edge_stream);
            while(freader.hasNext())
            {
                Vector<String> tokens = getTokens(freader.nextLine());
                int from = Integer.parseInt(tokens.get(0));
                int to = Integer.parseInt(tokens.get(1));
                double weight = Double.parseDouble(tokens.get(5));
                //Edge Data to be added in later revisions
                Vertex _from = findVertex(from,vertex_list);
                Vertex _to = findVertex(to,vertex_list);

                Edge edge = new Edge(_from, _to, weight, null); //Add Edge Data later
                edge_list.add(edge);
            }

            freader.close();

            return edge_list;
        }
        catch(IOException ioe)
        {
            throw new IOException("Error! Unable to read graph file");
        }
    }

    private Vertex findVertex(int id, ArrayList<Vertex> vertex_list)
    {
        Iterator<Vertex> iter = vertex_list.iterator();
        while(iter.hasNext())
        {
            Vertex vertex = iter.next();
            if(vertex.getID()==id)
                return vertex;
        }

        return null;
    }

    private Graph createGraph(ArrayList<Vertex> vertex_list, ArrayList<Edge> edge_list)
    {
        AdjacencyList adj_list = new AdjacencyList(vertex_list);

        for( Edge e : edge_list)
            adj_list.addEdge(e);

        return new Graph(adj_list);
    }

    public void setGraph(Graph graph)
    {
        this.graph = graph;
    }

    public void findShortestPath(int src, int dest)
    {
        for( int gen=0; gen<N; gen++)
        {
            Path explored_paths[] = generateSortedPaths();
            updateShortestPath(explored_paths[0]);
            updatePheromoneTrails(explored_paths);
        }
    }

    public Path generateSinglePath()
    {
        Path path = new Path();
        for(int i=0; i<graph.V().length; i++)
            pickNextUnvisitedVertex(path);
        
        return path;
    }

    public void pickNextUnvisitedVertex(Path path)
    {
        
    }

    /* To debug the graph */
    public void printGraph()
    {
        for(Vertex v : graph.V())
            System.out.println(v.index + ","+ v.id + "," + v.nam);

        System.out.println();

        for(NeighborList n : graph.E())
        {
            System.out.println("## Neighbors of "+n.vertex.index+","+n.vertex.id+","+n.vertex.nam+":");
            for(Vertex v : n.getList())
                System.out.println(v.index + "," + v.id+","+v.nam);
            System.out.println();
        }
    }
    public static void main(String[] args) throws IOException
    {
        String fnodes="sample_maps/map_1/nodes.csv";
        String fedges="sample_maps/map_1/edges.csv";
        ACOAlgorithm aco = new ACOAlgorithm();
        aco.readAndSetGraph(fnodes,fedges);
        aco.printGraph();
    }
}