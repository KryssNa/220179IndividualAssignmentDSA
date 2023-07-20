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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelMergeSort {

    public static void mergeSort(int[] arr) {
        // Create a fixed thread pool with the number of available processors
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Perform multithreaded merge sort on the array
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
            int mid = (left + right) / 2;

            // Create tasks for the left and right halves and submit them to the executor
            executor.submit(() -> mergeSort(arr, left, mid, executor)); // Left half
            executor.submit(() -> mergeSort(arr, mid + 1, right, executor)); // Right half

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
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

    public static void main(String[] args) {
        int[] arr = { 5, 2, 8, 1, 9, 4,3 };

        System.out.println("Original Array:");
        printArray(arr);

        mergeSort(arr);

        System.out.println("Sorted Array:");
        printArray(arr);
    }

    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

