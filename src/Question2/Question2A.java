package Question2;

/*
a)
Given an integer array nums and another integer k, the goal is to find the longest subsequence of nums that
satisfies the following two conditions:
The subsequence is strictly decreasing.
The difference between adjacent elements in the subsequence is at most k.
The output should be the length of the longest subsequence that meets these requirements.
For example, consider the following input:
nums = [8,5,4, 2, 1, 4, 3, 4, 3, 1, 15] k = 3
output=[8,5,4,2,1] or [8,5,4,3,1]
Output: 5
Explanation:
The longest subsequence that meets the requirements is [8,5,4,2,1] or [8,5,4,3,1].
The subsequence has a length of 5, so we return 5.
Note that the subsequence [1,3,4,5,8,15] does not meet the requirements because 15 - 8 = 7 is larger than 3.

 */
public class Question2A {

    // Function to find the length of the longest subsequence with a difference of at most 'k'
    public static int longestSubsequence(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n]; // Create an array to store the length of longest subsequences ending at each index

        for (int i = 0; i < n; i++) {
            int maxLen = 0; // Initialize the maximum length of subsequence ending at index 'i'
            for (int j = 0; j < i; j++) {
                // Check if the difference between current number 'nums[i]' and a previous number 'nums[j]'
                // is at most 'k', and the current number is smaller than the previous number
                if (nums[i] < nums[j] && nums[j] - nums[i] <= k) {
                    maxLen = Math.max(maxLen, dp[j]); // Update 'maxLen' with the maximum length found so far
                }
            }
            dp[i] = maxLen + 1; // Store the length of the longest subsequence ending at index 'i'
        }

        int maxLength = 0; // Initialize the maximum length of subsequences overall
        for (int len : dp) {
            maxLength = Math.max(maxLength, len); // Find the maximum length among all subsequences
        }

        return maxLength; // Return the maximum length of subsequences with the required difference 'k'
    }

    public static void main(String[] args) {
        int[] nums = {8, 5, 4, 2, 1, 4, 3, 4, 3, 1, 15}; // Input array
        int k = 3; // Maximum allowed difference 'k'
        System.out.println(longestSubsequence(nums, k)); // Print the length of the longest subsequence
    }
}
