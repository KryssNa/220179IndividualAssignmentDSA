package Task6;

/* Write a Java program that uses multithreading to implement a parallel merge sort algorithm. Your program should
meet the following requirements:
1. Input: Your program should accept an array of integers as input.
2. Output: Your program should output the sorted array.
3. Algorithm: Your program should use a parallel merge sort algorithm to sort the input array. The algorithm
should divide the input array into subarrays, sort the subarrays in parallel using multiple threads, and then
merge the sorted subarrays to produce the final sorted array.
4. Performance: Your program should be optimized for performance, such that it sorts the input array as
quickly as possible. You should experiment with different thread counts and input array sizes to find the
optimal settings.
5. Error handling: Your program should handle errors and exceptions gracefully, such as by providing
informative error messages and exiting gracefully.
6. Testing: Your program should be thoroughly tested to ensure that it correctly sorts the input array and
produces the expected output.
To complete this assignment, you will need to implement the parallel merge sort algorithm in Java using
multithreading. You should also experiment with different thread counts and input array sizes to find the optimal
settings for performance. You can use Java's built-in threading and synchronization features, such as the Thread
class and synchronized keyword, to implement the parallel merge sort algorithm */


import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelMergeSort {

    private static final int THRESHOLD = 1000; // Threshold for using sequential merge sort

    public static void mergeSort(int[] arr) {
        // Create a fixed thread pool with the number of available processors
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Perform parallel merge sort on the array
        mergeSort(arr, 0, arr.length - 1, executor);

        // Shut down the executor and wait until all tasks are completed
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void mergeSort(int[] arr, int left, int right, ExecutorService executor) {
        if (left < right) {
            if (right - left <= THRESHOLD) {
                // Use sequential merge sort for small subarrays
                mergeSortSequential(arr, left, right);
            } else {
                int mid = (left + right) / 2;

                // Create tasks for the left and right halves and submit them to the executor
                executor.submit(() -> mergeSort(arr, left, mid, executor)); // Left half
                executor.submit(() -> mergeSort(arr, mid + 1, right, executor)); // Right half

                // Merge the sorted halves
                merge(arr, left, mid, right);
            }
        }
    }

    private static void mergeSortSequential(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Sort each half separately using parallel merge sort
            mergeSortSequential(arr, left, mid);
            mergeSortSequential(arr, mid + 1, right);

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    // Merge two sorted subarrays arr[left..mid] and arr[mid+1..right]
    private static void merge(int[] arr, int left, int mid, int right) {

        // Find sizes of the two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays for the left and right halves
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }


        // Merge the temporary arrays back into the original array
        int i = 0; // Index for the left array
        int j = 0; // Index for the right array
        int k = left; // Index for the merged array

        // Merge the two arrays by comparing the smallest elements of each
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of the left array, if any
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        // Copy remaining elements of the right array, if any
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }


    // Print the elements of an array
    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        // Test the parallel merge sort algorithm
        System.out.println("performing Parallel Merge Sort Algorithm......");

        // Get input from the user
        System.out.println("Enter elements of the array separated by space:");
        Scanner sc = new Scanner(System.in);

        // Convert the input into an array of integers
        int[] arr;
        try {
            String[] input = sc.nextLine().split(" "); // Split the input by space delimiter and store in an array
            arr = Arrays.stream(input).mapToInt(Integer::parseInt).toArray(); // Convert String[] to int[]
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers separated by space.");
            return; // Exit gracefully in case of an error
        }

        // Print the original array
        System.out.println("\nOriginal array:");
        printArray(arr);

        // Sort the array using parallel merge sort
        mergeSort(arr);

        // Print the sorted array
        System.out.println("\nSorted array:");
        printArray(arr);

        // Performance testing with different thread counts and input sizes
        for (int numThreads = 1; numThreads <= 8; numThreads++) { // Test with 1 to 8 threads
            long startTime = System.nanoTime(); // Start the timer
            mergeSort(arr); // Sort the array using parallel merge sort
            long endTime = System.nanoTime(); // Stop the timer
            long elapsedTime = endTime - startTime; // Calculate the elapsed time

            // Print the sorted array and the time taken
            System.out.println("\nSorted array with " + numThreads + " thread(s):");
            printArray(arr);
            System.out.println("Time taken: " + (elapsedTime / 1000000) + " ms");
        }
    }
}


