import java.lang.annotation.*;
import java.util.*;
import java.util.stream.Stream;

public class ArrayPractice {

    public static void main(String[] args) {
//        recursionPattern(4);
        Integer[] root = {10, 5, -3, 3, 2, null, 11, 3, -2, null, 1};
        Tree.TreeNode treeNode = Tree.buildTree(root);
//        bfs(treeNode);
//        verticalTraversal(treeNode);
        System.out.println(pathSumIII(treeNode, 8));
    }

    public static int pathSumIII(Tree.TreeNode root, int targetSum) {
        int count = 0;
        List<Integer> ans = new ArrayList<>();
        List<List<Integer>> allPaths = new ArrayList<>();
        System.out.println(pathIII(root, targetSum, ans, allPaths));
        System.out.println(allPaths);
        return count;
    }

    public static boolean hasPathSum(Tree.TreeNode root, int targetSum) {
        if (root == null) return false;

        int sum = targetSum - root.val;

        if (root.left == null && root.right == null) {
            return sum == 0;
        }
        boolean left = hasPathSum(root.left, sum);
        boolean right = hasPathSum(root.right, sum);

        return left || right;
    }

    public static List<Integer> pathIII(Tree.TreeNode root, int targetSum, List<Integer> currentPath, List<List<Integer>> allPaths) {
        if (root == null) return null;

        currentPath.add(root.val);
        int remainingSum = targetSum - root.val; // -2

        if (remainingSum < 0 || remainingSum > targetSum) {
            remainingSum = targetSum;
            currentPath.remove(currentPath.size() - 1);
        }

        if (remainingSum == 0) {
            return new ArrayList<>(currentPath);
        }

        if (root.left == null && root.right == null) {
            return null;
        }

        List<Integer> left = pathIII(root.left, remainingSum, currentPath, allPaths);
        if (left != null) {
            allPaths.add(left);
            currentPath.remove(currentPath.size() - 1);

        }
        List<Integer> right = pathIII(root.right, remainingSum, currentPath, allPaths);
        if (right != null) {
            allPaths.add(right);
            currentPath.remove(currentPath.size() - 1);

        }
        return null;
    }

//    public static List<Integer> pathSum(Tree.TreeNode root, int targetSum) {
//        List<List<Integer>> ans = new ArrayList<>();
//        List<Integer> inner = new ArrayList<>();
//        return path(root, targetSum, inner);
//    }


    public static List<List<Integer>> verticalTraversal(Tree.TreeNode root) {
        Map<Integer, List<Integer>> map = new TreeMap<>();
        Queue<Pair> queue = new LinkedList<>();
        if (root == null) return List.of();
        Pair first = new Pair(root, 0);
        queue.offer(first);

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            Tree.TreeNode current = pair.getTreeNode();
            int hd = pair.getHorizontalDistance();
            map.putIfAbsent(hd, new ArrayList<>());
            map.get(hd).add(current.getVal());

            if (current.left != null) {
                queue.offer(new Pair(current.left, hd - 1));
            }
            if (current.right != null) {
                queue.offer(new Pair(current.right, hd + 1));
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int key : map.keySet()) {
            ans.add(map.get(key));
        }
        System.out.println(ans);
        return ans;
    }

    static class Pair {
        Tree.TreeNode treeNode;
        int horizontalDistance;

        public Pair() {
        }

        public Pair(Tree.TreeNode treeNode, int horizontalDistance) {
            this.treeNode = treeNode;
            this.horizontalDistance = horizontalDistance;
        }

        public Tree.TreeNode getTreeNode() {
            return treeNode;
        }

        public void setTreeNode(Tree.TreeNode treeNode) {
            this.treeNode = treeNode;
        }

        public int getHorizontalDistance() {
            return horizontalDistance;
        }

        public void setHorizontalDistance(int horizontalDistance) {
            this.horizontalDistance = horizontalDistance;
        }
    }

    public static void reverseArray(int[] arr, int[] copy, int i, int n) {
        if (n < 0) {
            return;
        }
        reverseArray(arr, copy, i + 1, n - 1);
        copy[n] = arr[i];
    }

    public static void recursionPattern(int n) {
        if (n <= 0) return;
        recursionPattern(n - 1);
        Row(n);
        System.out.println();
    }

    public static void Row(int n) {
        if (n == 0) return;

        System.out.print("*");
        Row(n - 1);
    }

    public static int diameter = 0;

    public static int splitArray(int[] nums, int k) {
        int l = 0;
        int h = 0;
        for (int n : nums) {
            if (n > h) {
                l = n;
            }
            h += n;
        }

        while (l < h) {
            int m = l + (h - l) / 2;
            if (splitsPossible(k, m, nums)) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    public static boolean splitsPossible(int k, int m, int[] nums) {
        int currentSum = 0;
        int split = 0;
        for (int n : nums) {
            if (currentSum + n > m) {
                currentSum = 0;
                split++;
            }
            currentSum += n;
        }
        return split + 1 == k;
    }


    public static int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = diameterOfBinaryTree(root.left);
        int right = diameterOfBinaryTree(root.right);
        diameter = Math.max(diameter, left + right);

        return 1 + Math.max(left, right);
    }

    public static int getDiameter() {
        return diameter;
    }

    public static void setDiameter(int diameter) {
        ArrayPractice.diameter = diameter;
    }

    // 1
    // 4 ->  1
    // 5 -> 1

    public static int maxDepth(TreeNode root) {
        return depth(root);
    }

    private static int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = depth(root.left);
        int right = depth(root.right);
        return 1 + Math.max(left, right);
    }

    private static void dfs(Tree.TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        dfs(root.left);
        dfs(root.right);
    }

    private static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        // Swap the left and right subtree
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);

        return root;
    }

    private static TreeNode bfsInvert(TreeNode root) {
        if (root == null) return null;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            // Swap children
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            // Add children to queue if not null
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

        return root;
    }

    private static Tree.TreeNode bfs(Tree.TreeNode root) {
        if (root == null) return null;

        Queue<Tree.TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Tree.TreeNode current = queue.poll();
            System.out.println(current.val);
            // Add children to queue if not null
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

        return root;
    }

    private static TreeNode dfsInvert(TreeNode root) {
        if (root == null) return null;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();

            // Swap children
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            // Push children to stack
            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }

        return root;
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
