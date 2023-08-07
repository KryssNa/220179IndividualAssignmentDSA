package Question1;

/* A group of n Pathao riders stood in a queue, and each rider was assigned a unique integer rating based on their
performance over the year. The Pathao company planned to distribute gold coins to each rider in ascending order,
starting from count 1. The riders with higher ratings should receive more coins than their neighboring riders. The
objective was to determine the minimum number of coins required by Pathao to distribute coins to the selected
riders according to their ratings.
Input: ratings = [1,0,2]
Output: 5
Explanation: You can give the first, second, and third rider 2, 1, 2 gold coins, respectively.
*/

public class Question1B {
    public static int minCoins(int[] ratings) {
        int[] coins = new int[ratings.length];
        coins[0] = 1;

        // Traverse the array from left to right and assign coins to each rider based on their rating and the rating of
        // their left neighbor.
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                coins[i] = coins[i - 1] + 1;
            } else {
                coins[i] = 1;
            }
        }

        // Traverse the array from right to left and assign coins to each rider based on their rating and the rating of
        // their right neighbor.
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                coins[i] = Math.max(coins[i], coins[i + 1] + 1);
            }
        }

        int totalCoins = 0;
        for (int coin : coins) {
            totalCoins += coin;
        }

        return totalCoins;
    }

    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};
        int minCoins = minCoins(ratings);
        System.out.println("Minimum number of coins required: " + minCoins);
    }
}
