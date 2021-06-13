import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {

    public static void main (String args[]) {
        /*
        array[0] = numberOfKnapsacks;
        array[1] = numberOfItems;
        array[2] = values;
        array[3] = capacities of knapsacks;
        array[4] = knapsacks (sets of weights); (double array)
         */
        Reader.read();

        int capacities[] = Reader.capacities;
        int size = Reader.numberOfItems;
        int values[] = Reader.values;
        Knapsack.capacity = capacities[0];
        Knapsack.size = size;
        int knapsacks[][] = Reader.knapsacks;
        int numberOfKnapsacks = Reader.numberOfKnapsacks;


        Item arr[] = new Item[size];

        for (int i = 0; i < numberOfKnapsacks; i++) {
            for (int j = 0; j < size; j++) {
                arr[j] = new Item(values[j], knapsacks[i][j], j);
            }
            Knapsack.solve(arr);
        }
        ArrayList<Integer> myResults = Knapsack.results;
        ArrayList<ArrayList<Integer>> myLists = Knapsack.listOfLists;
        System.out.print("Hi! " + biggest(myResults));


    }

    public static int biggest(ArrayList<Integer> list) {
        Object obj = Collections.max(list);
        return (Integer)obj;
    }

}
