package Question4;

/*
Given the root of a binary tree with unique values and the values of two different nodes of the tree x and y,
return true if the nodes corresponding to the values x and y in the tree are brothers, or false otherwise.
Two nodes of a binary tree are brothers if they have the same depth with different parents.
Note that in a binary tree, the root node is at the depth 0, and children of each depth k node are at the depth k + 1.
 */
public class Question4B {

        //implement your code here
        public static boolean isCousins(TreeNode root, int x, int y) {
            if (root == null) {
                return false;
            }

            int[] xInfo = findNode(root, x, 0, -1);
            int[] yInfo = findNode(root, y, 0, -1);

            return xInfo[0] == yInfo[0] && xInfo[1] != yInfo[1];
        }

        private static int[] findNode(TreeNode root, int target, int depth, int parent) {
            if (root == null) {
                return null;
            }

            if (root.val == target) {
                return new int[]{depth, parent};
            }

            int[] left = findNode(root.left, target, depth + 1, root.val);
            int[] right = findNode(root.right, target, depth + 1, root.val);

            return left == null ? right : left;
        }

        public static void main(String[] args) {
            TreeNode root = new TreeNode(1, new TreeNode(2, null, new TreeNode(4)), new TreeNode(3, null, new TreeNode(5)));
            int x = 5;
            int y = 4;
            System.out.println(isCousins(root, x, y));
        }

        public static class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            TreeNode() {
            }

            TreeNode(int val) {
                this.val = val;
            }

            TreeNode(int val, TreeNode left, TreeNode right) {
                this.val = val;
                this.left = left;
                this.right = right;
            }
        }
}
