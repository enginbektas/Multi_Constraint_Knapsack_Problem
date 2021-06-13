import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        Object array[] = Reader.read();

        int size = (Integer)array[1];
        int capacities[] = array[3];
        int capacity = (Integer)array[3];
        System.out.println(capacities);



//
//        Item arr[] = new Item[size];
//        arr[0] = new Item(10, 2, 0);
//        arr[1] = new Item(10, 4, 1);
//        arr[2] = new Item(12, 6, 2);
//        arr[3] = new Item(18, 9, 3);
//
//        solve(arr);

    }

}
