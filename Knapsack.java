import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
class Knapsack {

    public static ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<>();
    public static ArrayList<Integer> selections = new ArrayList<>();
    public static ArrayList<Integer> results = new ArrayList<>();
    public static int size;
    public static float capacity;

    // Function to calculate upper bound
    // (includes fractional part of the items)
    static float upperBound(float tv, float tw,
                            int idx, Item arr[]) {
        float value = tv;
        float weight = tw;
        for (int i = idx; i < size; i++) {
            if (weight + arr[i].weight
                    <= capacity) {
                weight += arr[i].weight;
                value -= arr[i].value;
            } else {
                value -= (float) (capacity
                        - weight)
                        / arr[i].weight
                        * arr[i].value;
                break;
            }
        }
        return value;
    }

    // Calculate lower bound (doesn't
    // include fractional part of items)
    static float lowerBound(float tv, float tw,
                            int idx, Item arr[]) {
        float value = tv;
        float weight = tw;
        for (int i = idx; i < size; i++) {
            if (weight + arr[i].weight
                    <= capacity) {
                weight += arr[i].weight;
                value -= arr[i].value;
            } else {
                break;
            }
        }
        return value;
    }

    static void assign(Node a, float ub, float lb,
                       int level, boolean flag,
                       float tv, float tw) {
        a.ub = ub;
        a.lb = lb;
        a.level = level;
        a.flag = flag;
        a.tv = tv;
        a.tw = tw;
    }

    public static void solve(Item arr[]) {
        // Sort the items based on the
        // profit/weight ratio
        Arrays.sort(arr, new sortByRatio());

        Node current, left, right;
        current = new Node();
        left = new Node();
        right = new Node();

        // min_lb -> Minimum lower bound
        // of all the nodes explored

        // final_lb -> Minimum lower bound
        // of all the paths that reached
        // the final level
        float minLB = 0, finalLB
                = Integer.MAX_VALUE;
        current.tv = current.tw = current.ub
                = current.lb = 0;
        current.level = 0;
        current.flag = false;

        // Priority queue to store elements
        // based on lower bounds
        PriorityQueue<Node> pq
                = new PriorityQueue<Node>(
                new sortByC());

        // Insert a dummy node
        pq.add(current);

        // curr_path -> Boolean array to store
        // at every index if the element is
        // included or not

        // final_path -> Boolean array to store
        // the result of selection array when
        // it reached the last level
        boolean currPath[] = new boolean[size];
        boolean finalPath[] = new boolean[size];

        while (!pq.isEmpty()) {
            current = pq.poll();
            if (current.ub > minLB
                    || current.ub >= finalLB) {
                // if the current node's best case
                // value is not optimal than minLB,
                // then there is no reason to
                // explore that node. Including
                // finalLB eliminates all those
                // paths whose best values is equal
                // to the finalLB
                continue;
            }

            if (current.level != 0)
                currPath[current.level - 1]
                        = current.flag;

            if (current.level == size) {
                if (current.lb < finalLB) {
                    // Reached last level
                    for (int i = 0; i < size; i++)
                        finalPath[arr[i].idx]
                                = currPath[i];
                    finalLB = current.lb;
                }
                continue;
            }

            int level = current.level;

            // right node -> Exludes current item
            // Hence, cp, cw will obtain the value
            // of that of parent
            assign(right, upperBound(current.tv,
                    current.tw,
                    level + 1, arr),
                    lowerBound(current.tv, current.tw,
                            level + 1, arr),
                    level + 1, false,
                    current.tv, current.tw);

            if (current.tw + arr[current.level].weight
                    <= capacity) {

                // left node -> includes current item
                // c and lb should be calculated
                // including the current item.
                left.ub = upperBound(
                        current.tv
                                - arr[level].value,
                        current.tw
                                + arr[level].weight,
                        level + 1, arr);
                left.lb = lowerBound(
                        current.tv
                                - arr[level].value,
                        current.tw
                                + arr[level].weight,
                        level + 1,
                        arr);
                assign(left, left.ub, left.lb,
                        level + 1, true,
                        current.tv - arr[level].value,
                        current.tw
                                + arr[level].weight);
            }

            // If the left node cannot
            // be inserted
            else {

                // Stop the left node from
                // getting added to the
                // priority queue
                left.ub = left.lb = 1;
            }

            // Update minLB
            minLB = Math.min(minLB, left.lb);
            minLB = Math.min(minLB, right.lb);

            if (minLB >= left.ub)
                pq.add(new Node(left));
            if (minLB >= right.ub)
                pq.add(new Node(right));
        }
        for (int i = 0; i < size; i++) {
            if (finalPath[i]) {
                System.out.print("1 ");
                selections.add(1);
            }

            else {
                System.out.print("0 ");
                selections.add(0);
            }
        }
        System.out.println("\nMaximum profit" + " is " + (-finalLB));
        results.add((int)(-finalLB));
        ArrayList<Integer> temp = (ArrayList<Integer>) selections.clone();
        listOfLists.add(temp);
        selections.clear();
//        System.out.println("");
    }

    // Driver code
    public static void main(String args[]) {
        size = 4;
        capacity = 15;

        Item arr[] = new Item[size];
        arr[0] = new Item(10, 2, 0);
        arr[1] = new Item(10, 4, 1);
        arr[2] = new Item(12, 6, 2);
        arr[3] = new Item(18, 9, 3);

        solve(arr);
    }
}

class Item {

    // Stores the weight
    // of items
    float weight;

    // Stores the values
    // of items
    int value;

    // Stores the index
    // of items
    int idx;
    public Item() {}
    public Item(int value, float weight,
                int idx)
    {
        this.value = value;
        this.weight = weight;
        this.idx = idx;
    }
}

class Node {
    // Upper Bound: Best case
    // (Fractional Knapsck)
    float ub;

    // Lower Bound: Worst case
    // (0/1)
    float lb;

    // Level of the node in
    // the decision tree
    int level;

    // Stores if the current
    // item is selected or not
    boolean flag;

    // Total Value: Stores the
    // sum of the values of the
    // items included
    float tv;

    // Total Weight: Stores the sum of
    // the weights of included items
    float tw;
    public Node() {}
    public Node(Node cpy)
    {
        this.tv = cpy.tv;
        this.tw = cpy.tw;
        this.ub = cpy.ub;
        this.lb = cpy.lb;
        this.level = cpy.level;
        this.flag = cpy.flag;
    }
}

// Comparator to sort based on lower bound
class sortByC implements Comparator<Node> {
    public int compare(Node a, Node b)
    {
        boolean temp = a.lb > b.lb;
        return temp ? 1 : -1;
    }
}

class sortByRatio implements Comparator<Item> {
    public int compare(Item a, Item b)
    {
        boolean temp = (float)a.value
                / a.weight
                > (float)b.value
                / b.weight;
        return temp ? -1 : 1;
    }
}


