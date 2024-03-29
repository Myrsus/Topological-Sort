import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Graph {
    // список списков для представления смежности вершин
    private List<List<Integer>> adjacencyList;
    // количество вершин в графе
    private int vertices;

    // конструктор
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        generateAcyclicGraph();
    }

    // метод добавления ребра от u к v
    public void addEdge(int u, int v) {
        adjacencyList.get(u).add(v);
    }

    // метод для генерации графа без циклов
    public void generateAcyclicGraph() {
        // очищаем существующий граф
        for (List<Integer> list : adjacencyList) {
            list.clear();
        }
        // используем HashSet для избежания дубликатов ребер
        Set<Integer> connectedVertices = new HashSet<>();
        // генерируем ребра таким образом, чтобы не образовывать циклов
        for (int i = 0; i < vertices; i++) {
            connectedVertices.clear();
            int edges = (int) (Math.random() * vertices);
            for (int j = 0; j < edges; j++) {
                int v = (int) (Math.random() * vertices);
                if (v > i && connectedVertices.add(v)) { // добавляем уникальное ребро
                    addEdge(i, v);
                }
            }
        }
    }


    // гетеры сетеры
    public int getVertices() {
        return vertices;
    }

    public List<List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public void printGraph() {
        System.out.println("Graph representation: adjacency list");
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print(i + " -> ");
            List<Integer> edges = adjacencyList.get(i);
            for (int edge : edges) {
                System.out.print(edge + " ");
            }
            System.out.println();
        }

    }

    public void topologicalSort() {
        Stack<Integer> stack = new Stack<>(); // Стек для хранения упорядоченной последовательности вершин
        boolean[] visited = new boolean[vertices]; // Массив для отслеживания посещенных вершин

        long iterations = 0; // Счетчик итераций

        // Запускаем таймер
        long startTime = System.nanoTime();


        // Инициализируем все вершины как непосещенные
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }

        // Вызываем рекурсивный помощник для топологической сортировки для каждой непосещенной вершины
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                iterations += topologicalSortUtil(i, visited, stack,iterations);
            }
        }
        // Останавливаем таймер
        long endTime = System.nanoTime();
        //System.out.println("\nЗанято времени: " + (endTime - startTime) / 1000 + " мс");
        //System.out.println("Итераций: " + iterations);
        System.out.print(this.vertices + "\t");
        System.out.print((endTime - startTime) / 1000 + "\t");
        System.out.println(iterations);

        // Печатаем содержимое стека, которое теперь представляет собой упорядоченную последовательность
        //while (!stack.isEmpty()) {
            //System.out.print(stack.pop() + " ");
        //}
    }

    // Рекурсивный метод помощник, использующий DFS для топологической сортировки
    private long topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack,long iterations) {
        // Помечаем текущую вершину как посещенную
        visited[v] = true;

        // Рекурсивно вызываем метод помощник для всех смежных вершин
        for (int i : adjacencyList.get(v)) {
            if (!visited[i]) {
                iterations = topologicalSortUtil(i, visited, stack, iterations);
            }
        }

        // После посещения всех смежных вершин, добавляем текущую вершину в стек
        stack.push(v);
        return iterations + 1; // Увеличиваем счетчик итераций
    }
}