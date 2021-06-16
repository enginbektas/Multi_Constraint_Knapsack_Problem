/*
Authors:
-Engin Bektaş
-Mahmut Salman
 */

import java.util.ArrayList;
import java.util.Collections;

public class main {
    //READ HERE!
    /*
    To run the code, mark project directory as source root.
    To run different tests, change the input name as test1, test2, etc right below!
     */
    public static String input = "test4.txt";
    public static void main (String args[]) {

        Reader.read();
        int capacities[] = Reader.capacities;
        int size = Reader.numberOfItems;
        int values[] = Reader.values;
        int knapsacks[][] = Reader.knapsacks;
        int numberOfKnapsacks = Reader.numberOfKnapsacks;
        Knapsack.size = size;
        //Branch and Bound Method
        BB();

        //To use other methods, uncomment neccesary lines.
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
        //Solve for each knapsack individually and save results
        int[] totalWeights = new int[numberOfKnapsacks];
        for (int i = 0; i < numberOfKnapsacks; i++) {
            Knapsack.capacity = capacities[i];
            for (int j = 0; j < size; j++) {
                arr[j] = new Item(values[j], knapsacks[i][j], j);
            }
            Knapsack.solve(arr);
        }
        //save optimal results for each knapsack, individually
        ArrayList<Integer> optimalValues = Knapsack.results;
        //store our item selection as 0's and 1's for our highest scored knapsack
        ArrayList<ArrayList<Integer>> oneZeros = Knapsack.listOfLists;
        //find the index of the knapsack which has the highest score
        int indexOfOptimalKnapsack = optimalValues.indexOf(biggest(optimalValues));
        //totalWeights will hold weights for our knapsacks for our item selection

        for (int l = 0; l < numberOfKnapsacks; l++) {
            int sum = 0;
            for (int i = 0; i < size; i++) {
                if (oneZeros.get(indexOfOptimalKnapsack).get(i) == 1) {
                    sum = sum + knapsacks[l][i];
                }
            }
            totalWeights[l] = sum;
        }
        //setting of my items
        ArrayList<Integer> myItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            myItems.add(oneZeros.get(indexOfOptimalKnapsack).get(i));
        }
        double[] valuesOfItems = valuesOfItems(values, knapsacks[indexOfOptimalKnapsack]);
        //hold knapsacks that are overweight with our item selection
        int[] overweightKnapsacks = overweightKnapsacks(numberOfKnapsacks, capacities, knapsacks, myItems);
        //remove least valuable item for our optimal knapsack from knapsacks that are overweight, from start to end
        //till there are no more overweight knapsacks.
        int x = 0;
        for (int j = 0; j < knapsacks[indexOfOptimalKnapsack].length; j++) {
            if (myItems.get(j) == 1)
                x =+ knapsacks[indexOfOptimalKnapsack][j];
        }
        while(doesContain(overweightKnapsacks, 1)) {
            int indexOfKnapsackToReduce = indexOfKnapsackToReduce(overweightKnapsacks);
            if (indexOfKnapsackToReduce <= numberOfKnapsacks - 1) {
                //reduce an element 1 by 1 till overweight knapsack is no more overweight
                for (int i = 0; i < size; i++) {
                    int indexOfLargest = indexOfLargest(valuesOfItems);
                    if (myItems.get(indexOfLargest) == 1) {
                        myItems.set((indexOfLargest), 0);
                        if (!isOverweight(knapsacks[indexOfKnapsackToReduce], myItems, capacities[indexOfKnapsackToReduce])) {
                            overweightKnapsacks[indexOfKnapsackToReduce] = 0;
                            break;
                        }
                    }
                }
            }
            overweightKnapsacks = overweightKnapsacks(numberOfKnapsacks, capacities, knapsacks, myItems);
        }
        System.out.println(newOptimalValue(myItems, values, knapsacks));
        for (int i = 0; i<size; i++) {
            System.out.println(myItems.get(i));
        }

    }

    //returns the largest item of given array.
    public static int indexOfLargest(double arr[]) {
        double max = -2147200000;
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max && arr[i] < 0) {
                max = arr[i];
                maxIndex = i;
            }
        }
        arr[maxIndex] = 1.0;
        return maxIndex;
    }

    //returns values of the given array, excluding the non selected items
    public static double[] valuesOfItems(int[] values, int[] weights) {
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            if (weights[i] == 0)
                result[i] = -2147483648;
            else
            result[i] = - (float) values[i] / (float) weights[i];
        }
        return result;
    }

    //returns optimal value of given items and given knapsack
    public static int newOptimalValue(ArrayList<Integer> myItems, int[] items, int knapsacks[][]) {
        int sum = 0;
        int result = 0;
        for (int i = 0; i < knapsacks.length; i++) {
            for (int j = 0; j < items.length; j++) {
                if(myItems.get(j) == 1)
                sum += items[j];
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

    //returns true if a knapsack is overweight
    public static boolean isOverweight(int[] knapsack, ArrayList<Integer> items, int capacity) {
        int weight = weightOfKnapsack(knapsack, items);
        return (weight > capacity);
    }

    //returns overweight knapsacks from the knapsack list respectively
    public static int indexOfKnapsackToReduce(int[] overweightKnapsacks) {
        int i = 0;
        for (; i < overweightKnapsacks.length; i++)
            if (overweightKnapsacks[i] == 1)
                return i;

        return i + 1;
    }
    //return an int array as 0's and 1's for the overweighted knapsacks.
    public static int[] overweightKnapsacks(int numberOfKnapsacks, int[] capacities, int[][] knapsacks, ArrayList<Integer> myItems) {
        int overweightKnapsacks[] = new int[numberOfKnapsacks];
        for (int i = 0; i < numberOfKnapsacks; i++) {
            if (capacities[i] < weightOfKnapsack(knapsacks[i], myItems))
                overweightKnapsacks[i] = 1;
        }
        return overweightKnapsacks;
    }

    //returns the total weight when we put our item selection in it
    public static int weightOfKnapsack(int[] knapsack, ArrayList<Integer> items) {
        int weight = 0;
        for (int i = 0; i < knapsack.length; i++) {
            if (items.get(i) == 1)
            weight += knapsack[i];
        }
        return weight;
    }
}
