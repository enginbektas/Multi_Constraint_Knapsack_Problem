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
        System.out.println(biggest(myResults));
        int index = myResults.indexOf(biggest(myResults));

        for (int i=0; i<size; i++) {
            System.out.println(myLists.get(index).get(i));
        }

        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(biggest(myResults));
            for (int i=0; i<size; i++) {
                myWriter.write("" + myLists.get(index).get(i));
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("");
    }

    public static int biggest(ArrayList<Integer> list) {
        Object obj = Collections.max(list);
        return (Integer)obj;
    }

}
