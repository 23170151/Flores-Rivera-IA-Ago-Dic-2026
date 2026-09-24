import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class SearchTree {
    Node root;
    String initialState;
    String goalState;

    public SearchTree(String initialState, String goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.root = new Node(initialState, null);
    }

    public void breadthFirstSearch() {
        int time = 0;
        // Crear un conjunto para almacenar los nodos visitados y evitar ciclos
        Set<String> visited = new HashSet<>();
        // 1. Buscar el nodo raiz y agregarlo a la cola
        Node currentNode;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int maxQueueSize = queue.size();
        // 2. Mientras la cola no este vacia, hacer lo siguiente
        while (!queue.isEmpty()) {
            time++;
            // 3. Sacar el primer nodo de la cola y verificar si es el nodo objetivo
            currentNode = queue.poll();
            visited.add(currentNode.getState());
            // System.out.println(NodeUtils.formatState(currentNode.getState()));
            if (currentNode.getState().equals(goalState)) {
                System.out.println("Goal state found: " + currentNode.getState());
                // imprimir el camino desde el nodo raiz hasta el nodo objetivo
                printPath(currentNode);
                break;
            }
            // 4. Si no es el nodo objetivo, expandir el nodo y agregar sus hijos a la cola
            List<Node> children = NodeUtils.generateChildren(currentNode);
            for (Node child : children) {
                if (!visited.contains(child.getState()))
                    queue.add(child);
            }
            maxQueueSize = Math.max(maxQueueSize, queue.size());
        }

        System.out.println("Tiempo: " + time);
        System.out.println("Estados visitados: " + visited.size());
        System.out.println("Tamaño maximo queue: " + maxQueueSize);

    }

    public void depthFirstSearch() {
        int time=0;
        //Crear estructura de datos para almacenar los nodos visitados
        Set<String> visited = new HashSet<String>();
        Node currentNode = root;
        //Buscart el nodo raiz y agregarlo a la pila
        Stack<Node> stack = new Stack<>();
        stack.push(currentNode);
        int maxStackSize = stack.size();

        //Mientras la pila no esta vacia.
        while (!stack.isEmpty()) {
            time++;
            currentNode = stack.pop();

            visited.add(currentNode.getState());
            //System.out.println(NodeUtils.formatState(currentNode.getState()));
            if (currentNode.getState().equals(goalState)) {
                
                System.out.println("Goal state found: " + currentNode.getState());
                //Imprimir el camino desde la raiz hasta el nodo objetivo
                printPath(currentNode);
                System.out.println("Goal state not found");
                System.out.println("Tiempo: " + time);
                System.out.println("Estados visitados: " + visited.size());
                System.out.println("Tamaño maximo stack: " + maxStackSize);
                return;
            }
            //Si no es el estado objetivo, generar los hijos y agregarlos a la cola
            List<Node> children = NodeUtils.generateChildren(currentNode);
            for (Node child : children) {
                if (!visited.contains(child.getState())) {
                    stack.add(child);
                }
            }
            maxStackSize = Math.max(maxStackSize, stack.size());
        }
        System.out.println("Goal state not found");
    }

    public void uniformCostSearch() {
        int time = 0;
        Set<String> visited = new HashSet<>();
        Map<String, Integer> bestCosts = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodePriorityComparator());

        root.setCost(0);
        queue.add(root);
        bestCosts.put(root.getState(), root.getCost());
        int maxQueueSize = queue.size();

        while (!queue.isEmpty()) {
            time++;
            Node currentNode = queue.poll();

            if (visited.contains(currentNode.getState())) {
                continue;
            }

            visited.add(currentNode.getState());
            if (currentNode.getState().equals(goalState)) {
                System.out.println("Goal state found: " + currentNode.getState());
                printPath(currentNode);
                break;
            }

            List<Node> children = NodeUtils.generateChildren(currentNode);
            for (Node child : children) {
                int childCost = currentNode.getCost() + 1;
                child.setCost(childCost);

                Integer bestCost = bestCosts.get(child.getState());
                if (!visited.contains(child.getState())
                        && (bestCost == null || childCost < bestCost)) {
                    bestCosts.put(child.getState(), childCost);
                    queue.add(child);
                }
            }
            maxQueueSize = Math.max(maxQueueSize, queue.size());
        }

        System.out.println("Tiempo: " + time);
        System.out.println("Estados visitados: " + visited.size());
        System.out.println("Tamaño maximo queue: " + maxQueueSize);
    }

    public void depthLimitedSearch(int depthLimit) {
        int time = 0;
        Set<String> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        int maxStackSize = 0;

        root.setDepth(0);
        stack.push(root);
        maxStackSize = stack.size();

        while (!stack.isEmpty()) {
            time++;
            Node currentNode = stack.pop();

            if (visited.contains(currentNode.getState())) {
                continue;
            }

            visited.add(currentNode.getState());
            if (currentNode.getState().equals(goalState)) {
                System.out.println("Goal state found: " + currentNode.getState());
                printPath(currentNode);
                break;
            }

            if (currentNode.getDepth() >= depthLimit) {
                continue;
            }

            List<Node> children = NodeUtils.generateChildren(currentNode);
            for (Node child : children) {
                if (!visited.contains(child.getState())) {
                    child.setDepth(currentNode.getDepth() + 1);
                    stack.push(child);
                }
            }
            maxStackSize = Math.max(maxStackSize, stack.size());
        }

        if (visited.isEmpty() || !visited.contains(goalState)) {
            System.out.println("Goal state not found");
        }
        System.out.println("Tiempo: " + time);
        System.out.println("Estados visitados: " + visited.size());
        System.out.println("Tamaño maximo stack: " + maxStackSize);
    }

    
    public void iterativeDeepeningSearch() {
        int totalTime = 0;
        int depthLimit = 1;
        int maxStackSize = 0;

        while (true) {
            int time = 0;
            Set<String> visited = new HashSet<>();
            Stack<Node> stack = new Stack<>();

            root.setDepth(0);
            stack.push(root);
            maxStackSize = Math.max(maxStackSize, stack.size());

            while (!stack.isEmpty()) {
                time++;
                Node currentNode = stack.pop();

                if (visited.contains(currentNode.getState())) {
                    continue;
                }

                visited.add(currentNode.getState());
                if (currentNode.getState().equals(goalState)) {
                    System.out.println("Goal state found: " + currentNode.getState());
                    printPath(currentNode);
                    System.out.println("Depth limit: " + depthLimit);
                    System.out.println("Tiempo: " + (totalTime + time));
                    System.out.println("Estados visitados: " + visited.size());
                    System.out.println("Tamaño maximo stack: " + maxStackSize);
                    return;
                }

                if (currentNode.getDepth() >= depthLimit) {
                    continue;
                }

                List<Node> children = NodeUtils.generateChildren(currentNode);
                for (Node child : children) {
                    if (!visited.contains(child.getState())) {
                        child.setDepth(currentNode.getDepth() + 1);
                        stack.push(child);
                    }
                }
                maxStackSize = Math.max(maxStackSize, stack.size());
            }

            totalTime += time;
            depthLimit++;
        }
    }


    




    private void printPath(Node node) {
        if (node == null) {
            return;
        }
        printPath(node.getParent());
        System.out.println(NodeUtils.formatState(node.getState()));
    }

}