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
    public static int indexOfOptimalKnapsack;
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
        ArrayList<Integer> optimalValues = Knapsack.results;
        ArrayList<ArrayList<Integer>> oneZeros = Knapsack.listOfLists;

        System.out.println(biggest(optimalValues));
        int indexOfOptimalKnapsack = optimalValues.indexOf(biggest(optimalValues));
        ///////////

        int[] totalWeights = new int[numberOfKnapsacks];
        for (int l = 0; l < numberOfKnapsacks; l++) {
            int sum = 0;
            for (int i = 0; i < size; i++) {
                if (oneZeros.get(indexOfOptimalKnapsack).get(i) == 1) {
                    sum = sum + knapsacks[l][i];
                }
            }
            System.out.println("total weight is: " + sum);
            totalWeights[l] = sum;
        }
        //setting of my items
        ArrayList<Integer> myItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            myItems.add(oneZeros.get(indexOfOptimalKnapsack).get(i));
        }

        int[] valuesOfItems = valuesOfItems(values, knapsacks[indexOfOptimalKnapsack]);
        int[] overweightKnapsacks = overweightKnapsacks(numberOfKnapsacks, capacities, knapsacks, myItems);
        System.out.println("");
        while(doesContain(overweightKnapsacks, 1)) {
            int indexOfKnapsackToReduce = indexOfKnapsackToReduce(overweightKnapsacks);
            if (indexOfKnapsackToReduce <= numberOfKnapsacks - 1) {
                //reduce an element 1 by 1 till overweight knapsack is no more overweight
                for (int i = 0; i < size; i++) {
                    if (myItems.get(i) == 1) {
                        ArrayList<Integer> myItemsList = new ArrayList<>();
                        myItems.set(i, 0);
                        if (!isOverweight(knapsacks[indexOfKnapsackToReduce], myItems, capacities[indexOfKnapsackToReduce]))
                            overweightKnapsacks[indexOfKnapsackToReduce] = 0;
                        break;
                    }
                }
            }
        }
        System.out.println("new optimal value= " + newOptimalValue(myItems, knapsacks));


    }
    public static int indexOfLargest(int arr[]) {
        int max = arr[0];
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int[] valuesOfItems(int[] values, int[] weights) {
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] * weights[i];
        }
        return result;
    }

    public static int newOptimalValue(ArrayList<Integer> items, int knapsacks[][]) {
        int sum = 0;
        int result = 0;
        for (int i = 0; i < knapsacks.length; i++) {
            for (int j = 0; j < items.size(); j++) {
                sum += items.get(j) * knapsacks[i][j];
            }
            if (sum > result)
            result = sum;
            sum = 0;
        }
        return result;
    }

    public static boolean doesContain(int[]arr, int element) {
        boolean found = false;
        for(int x : arr){
            if(x == element){
                found = true;
                break;
            }
        }
        return found;
    }

    public static boolean isOverweight(int[] knapsack, ArrayList<Integer> items, int capacity) {
        return (weightOfKnapsack(knapsack, items) > capacity);
    }

    //////TERMİNATE THİS
    public static int indexOfKnapsackToReduce(int[] overweightKnapsacks) {
        int i = 0;
        for (; i < overweightKnapsacks.length; i++)
            if (overweightKnapsacks[i] == 1)
                return i;

        return i + 1;
    }
    public static int[] overweightKnapsacks(int numberOfKnapsacks, int[] capacities, int[][] knapsacks, ArrayList<Integer> myItems) {
        int overweightKnapsacks[] = new int[numberOfKnapsacks];
        for (int i = 0; i < numberOfKnapsacks; i++) {
            if (capacities[i] < weightOfKnapsack(knapsacks[i], myItems))
                overweightKnapsacks[i] = 1;
        }
        return overweightKnapsacks;
    }

    public static int weightOfKnapsack(int[] knapsack, ArrayList<Integer> items) {
        int weight = 0;
        for (int i = 0; i < knapsack.length; i++) {
            if (items.get(i) == 1)
            weight += knapsack[i];
        }
        return weight;
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
