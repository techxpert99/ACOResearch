    public class Edge
    {
        private Vertex from;
        private Vertex to;
        private double weight;
        private EdgeData data;
        private double pheromone;
        private double traffic_density;
        private double attractiveness;
         
        

        public Edge(Vertex from, Vertex to, double weight, EdgeData data)
        {
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.data = data;
            this.pheromone = AntColonyOptimizationHeuristic.INITIAL_PHEROMONE;
            this.attractiveness=0.0;
            this.traffic_density=0.0;

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

        @Override
        public boolean equals(Object _edge)
        {
            Edge edge = (Edge) _edge;
            return (edge.to().getIndex() == to().getIndex()) && (edge.from().getIndex() == from().getIndex());
        }
    }

class EdgeData
{
    
}