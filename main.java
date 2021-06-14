import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        //System.out.println(Recursion.knapsackRec(knapsacks[0], values, size, capacities[0]));
        //DP method
        //System.out.println(Recursion.knapsackRec(knapsacks[0], values, size, capacities[0]));


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

        int[] totalWeights = new int[numberOfKnapsacks];
        for (int l = 0; l<numberOfKnapsacks; l++) {
            int sum = 0;
            for (int i = 0; i < size; i++) {
                if (myLists.get(index).get(i) == 1) {
                    sum = sum + knapsacks[l][i];
                }
            }
            System.out.println("total weight is: " + sum);
            totalWeights[l] = sum;
        }



        ArrayList<Integer> list2 = new ArrayList<Integer>();
        for(int x:totalWeights) {
            list2.add(x);
        }
        int indexOfBiggest = list2.indexOf(biggest(list2));
        System.out.println(indexOfBiggest);
        System.out.println(biggestWeight(knapsacks[indexOfBiggest]));
        System.out.println(myLists.get(indexOfBiggest).get(biggestWeight(knapsacks[indexOfBiggest])));

        if (myLists.get(indexOfBiggest).get(biggestWeight(knapsacks[indexOfBiggest])) == 1) {
            myLists.get(indexOfBiggest).set(biggestWeight(knapsacks[indexOfBiggest]),0);
        }
        System.out.println(myLists.get(indexOfBiggest).get(biggestWeight(knapsacks[indexOfBiggest])));
    }
    public static int biggestWeight(int arr[]) {
        int i = 1;
        int j = 0;
        // Initialize maximum element
        int max = arr[0];

        // Traverse array elements from second and
        // compare every element with current max
        for (; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                j = i;
            }
        }
        return j;
    }
}
