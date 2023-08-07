package Question3;

/*
a) Suppose you are provided an array of n targets that are placed in a row from 0 to n-1. Each target is assigned
with certain integer such that a [0] represent the value associated with target zero. You are asked to shoot all
the targets. If you shoot I th target then you will get a[i-1]*a[i]*a[i+1] points.
Note that if i-1 and i+1 position hits index out of bound, then you can assume that two target with value 1 are
padded before start target and after end target.
Return maximum point one can gain by hitting each target.
Input: a = [3,1,5,8]
Output: 167
Explanation:
a = [3,1,5,8]
[3,1,5,8] points 3*1*5 (“hitting target with value 1”)
[3,5,8] points 3*5*8 (“hitting target with value 5”)
[3,8] points 1*3*8 (“hitting target with value 3”) note that there is padded target with value 1 at beginning and end
of the provided target list
,[8] points 1*8*1 same as above
points = 3*1*5+ 3*5*8 + 1*3*8 + 1*8*1 = 167
 */
public class Question3A {

    //implement your code here
    public static int maxPoints(int[] a) {
        int n = a.length;
        int[] b = new int[n + 2];
        b[0] = 1;
        b[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            b[i] = a[i - 1];
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int i = n; i >= 1; i--) {
            for (int j = i; j <= n; j++) {
                for (int k = i; k <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k - 1] + b[i - 1] * b[k] * b[j + 1] + dp[k + 1][j]);
                }
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 5, 8};
        System.out.println(maxPoints(a));
    }
}
