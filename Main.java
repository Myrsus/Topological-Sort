import java.util.Random;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++){
            Random random = new Random();
            int vertices = random.nextInt(10000);
            Graph g = new Graph(vertices);
            g.generateAcyclicGraph();
            g.topologicalSort();
            //System.out.println(i);
        }
    }
}