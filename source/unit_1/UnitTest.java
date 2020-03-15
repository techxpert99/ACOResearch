import java.io.*;

public class UnitTest
{   
    public static void main(String[] args) throws IOException
    {
        String fnodes="/home/ghost/Desktop/ACO/source/unit_1/sample_maps/map_2/nodes.csv";
        String fedges="/home/ghost/Desktop/ACO/source/unit_1/sample_maps/map_2/edges.csv";
        AntColonyOptimizationHeuristic aco = new AntColonyOptimizationHeuristic();
        aco.readAndSetGraph(fnodes,fedges);
        for(int i=0; i<1000; i++)
        {
        Path shortest_path = aco.findShortestPath(1,4);
        System.out.println(shortest_path.getLength());
        }
    }
}