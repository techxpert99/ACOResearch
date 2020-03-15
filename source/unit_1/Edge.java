    public class Edge
    {
        private int eid;
        private Vertex from;
        private Vertex to;
        private double weight;
        private EdgeData data;
        private double pheromone;
        private double traffic_density;
        private double attractiveness;
        private double edge_reliability;
         
        

        public Edge(int eid, Vertex from, Vertex to, double weight, EdgeData data)
        {
            this.eid = eid;
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.data = data;
            this.pheromone = AntColonyOptimizationHeuristic.INITIAL_PHEROMONE;
            this.attractiveness=0.0;
            this.traffic_density = data.traffic_density();
            this.edge_reliability = data.edge_reliability();

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

        public double getWeight()
        {
            return weight;
        }

        public double getPheromone(){
            return pheromone;
        }
        public double getAttractiveness(){
            return attractiveness;
        }
        public double getTrafficDensity(){
            return traffic_density;
        }
        public void setPheromone(double pheromone){
            this.pheromone=pheromone;
            
        }

        public void addPheromone(double pheromone)
        {
            this.pheromone+=pheromone;
        }
        
        public void setAttractiveness(double attractiveness){
            this.attractiveness= attractiveness;
            
        }
        public void setTrafficDensity(double traffic_density){
            this.traffic_density=traffic_density;
          
        }

        public void setEdgeReliability(double edge_reliability) {
            this.edge_reliability = edge_reliability;
        }

        public double getEdgeReliability() {
            return edge_reliability;
        }

        public int getEdgeId()
        {
            return eid;
        }
        
        @Override
        public boolean equals(Object _edge)
        {
            Edge edge = (Edge) _edge;
            return (edge.to().getIndex() == to().getIndex()) && (edge.from().getIndex() == from().getIndex());
        }
        
        /* Debug */
        public void debug()
        {
            System.out.println("[Edge]\nFrom:");
            from.debug();
            System.out.println("To:");
            to.debug();
            System.out.println("Weight:"+weight+"\nTraffic Density:"+traffic_density+"\nPheromones:"+pheromone+"\n[/Edge]");
        }
    }

class EdgeData
{
    private double traffic_density;
    private double edge_reliability;
    private String additional_info;

    public EdgeData(double traffic_density, double edge_reliability, String additional_info)
    {
         this.traffic_density = traffic_density;
         this.edge_reliability = edge_reliability;
         this.additional_info = additional_info;
    }
    
    public double traffic_density()
    {
        return traffic_density;
    }

    public double edge_reliability()
    {
        return edge_reliability;
    }

    public String additional_info()
    {
        return this.additional_info;
    }

    public void setAdditionalInfo(String additional_info)
    {
        this.additional_info = additional_info;
    }
    
}