import java.io.*;

public class UnitTest
{   
    public static void main(String[] args) throws IOException
    {
        String fnodes="sample_maps/map_1/nodes.csv";
        String fedges="sample_maps/map_1/edges.csv";
        AntColonyOptimizationHeuristic aco = new AntColonyOptimizationHeuristic();
        aco.readAndSetGraph(fnodes,fedges);
        aco.findShortestPath(1,166);
    }
}