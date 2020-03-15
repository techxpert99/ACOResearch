    public  class Vertex
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
        
        @Override
        public boolean equals(Object o){
            Vertex V =(Vertex) o;
            return V.id==id;
        }

        /* Debug */
        public void debug()
        {
            System.out.println("[Vertex]\nIndex:"+index+"\nName:"+nam+"Id:"+id+"\n[/Vertex]");
        }
    }

class VertexData
{
}