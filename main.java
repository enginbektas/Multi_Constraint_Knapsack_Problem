import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {

    public static void main (String args[]) {

        Reader.read();
        int capacities[] = Reader.capacities;
        int size = Reader.numberOfItems;
        int values[] = Reader.values;
        int knapsacks[][] = Reader.knapsacks;
        int numberOfKnapsacks = Reader.numberOfKnapsacks;
        Knapsack.capacity = capacities[0];
        Knapsack.size = size;

        //Bound and Bound method
        BB();
        //Recursion method
        System.out.println(Recursion.knapsackRec(knapsacks[0], values, size, capacities[0]));
        //DP method
        System.out.println(Recursion.knapsackRec(knapsacks[0], values, size, capacities[0]));


    }

    public static int biggest(ArrayList<Integer> list) {
        Object obj = Collections.max(list);
        return (Integer)obj;
    }

    public static void BB() {
        int capacities[] = Reader.capacities;
        int size = Reader.numberOfItems;
        int values[] = Reader.values;
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
        System.out.println(biggest(myResults));
        int index = myResults.indexOf(biggest(myResults));

        for (int i=0; i<size; i++) {
            System.out.println(myLists.get(index).get(i));
        }
    }

}
