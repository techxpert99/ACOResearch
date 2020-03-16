import java.util.*;
import java.io.*;

/**
* The GraphReader program reads graph by taking inputs from a file containing information about
* nodes and another with information about nodes in the form of csv files.
* @version 1.0
* @date   17-03-2020
*/
public class GraphReader
{   
    
    /**
    * The getTokens function returns a vector of tokens separeted by delimiters.
    * @param line Line from the file to be tokenized.
    * @return Vector of tokens
    * @version 1.0
    * @date   17-03-2020
    */
    private static Vector<String> getTokens(String line)
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
    
    /**
    * Finds a vertex with given Id.
    * @param id Id of the vertex to be found.
    * @param vertex_list list of the vertices.
    * @return required vertex with given Id.
    * @version 1.0
    * @date   17-03-2020
    */
    private static Vertex findVertex(int id, ArrayList<Vertex> vertex_list)
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
    
    /**
    * Scan the nodes file to get id and name of the vertices.
    * @param fnodes Nodes file to be scanned.
    * @return vertex_list ArrayList of vertices with their id and names.
    * @throw IOException if file does not exist.
    * @version 1.0
    * @date   17-03-2020
    */
    private static ArrayList<Vertex> scanNodes(String fnodes) throws IOException
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
    
    /**
    * Scan the edges file to get edge id, initial node, final node, weight , 
    * traffic density, edge reliability and other information of the edges.
    * @param fedges Edge file to be scanned.
    * @param vertex_list List of the vertices.
    * @return ArrayList of edges with required attributes.
    * @throw IOException If file does not exist.
    * @version 1.0
    * @date   17-03-2020
    */
    private static ArrayList<Edge> scanEdges(String fedges, ArrayList<Vertex> vertex_list) throws IOException
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
                int eid= Integer.parseInt(tokens.get(0));
                int from = Integer.parseInt(tokens.get(1));
                int to = Integer.parseInt(tokens.get(2));
                double weight = Double.parseDouble(tokens.get(3));
                double traffic_density = Double.parseDouble(tokens.get(4));
                double edge_reliability = Double.parseDouble(tokens.get(5));
                String additional_info = tokens.get(6);


                Vertex _from = findVertex(from,vertex_list);
                Vertex _to = findVertex(to,vertex_list);

                EdgeData data = new EdgeData(traffic_density, edge_reliability, additional_info);
                Edge edge = new Edge(eid, _from, _to, weight, data);
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
    
    /**
    * Create a graph using the list of vertices and edges represented by an adjacency list.
    * @param vertex_list List of vertices.
    * @param edge_list List of edges.
    * @return Graph represented by an adjacenecy list
    * @version 1.0
    * @date   17-03-2020
    */    
    private static Graph createGraph(ArrayList<Vertex> vertex_list, ArrayList<Edge> edge_list)
    {
        AdjacencyList adj_list = new AdjacencyList(vertex_list);

        for( Edge e : edge_list)
            adj_list.addEdge(e);

        return new Graph(adj_list);
    }

    public static Graph readGraph(String fnodes, String fedges) throws IOException
    {
        ArrayList<Vertex> vertex_list = scanNodes(fnodes);
        ArrayList<Edge> edge_list = scanEdges(fedges, vertex_list);

        return createGraph(vertex_list, edge_list);
    }
}
