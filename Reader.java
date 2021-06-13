import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public static Object[] read() {
        /*
    1.: read two integers. first knapsack #, second item #
    2.: read #item times of integers, which are values
    3.: read #knapsack times of integers, which are capacities
    4.: read #item times of integers, which are weights of knapsack #n, n=1,2,3...
    5.: repeat step 4, but this time read weights of knapsack #n+1.
     */
        Object array[] = new Object[5];
        int numberOfKnapsacks;
        int numberOfItems;
        ArrayList<Integer> elements = new ArrayList();
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("sample1_commented.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner s2 = new Scanner(sc2.nextLine());
        numberOfKnapsacks = Integer.parseInt(s2.next());
        numberOfItems = Integer.parseInt(s2.next());
        int[] values = new int[numberOfItems];
        int[] capacities = new int[numberOfKnapsacks];
        int[][] knapsacks = new int[numberOfKnapsacks][numberOfItems];

        while (sc2.hasNextLine()) {
            s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()) {
                String data = s2.next();
                elements.add(Integer.parseInt(data));
            }
        }
        int i = 0;
        for (; i < numberOfItems; i++) {
            values[i] = elements.get(i);
        }
        for (int j = 0; j < numberOfKnapsacks; i++) {
            capacities[j] = elements.get(i);
            j++;
        }
        for (int j = 0; j < numberOfKnapsacks; i++) {
            for (int k = 0; k < numberOfItems; i++) {
                knapsacks[j][k] = elements.get(i);
                k++;
            }
            j++;
            i--;
        }
        array[0] = numberOfKnapsacks;
        array[1] = numberOfItems;
        array[2] = values;
        array[3] = capacities;
        array[4] = knapsacks;
        return array;
    }
}
