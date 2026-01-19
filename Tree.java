import java.util.*;

/* Practice Class for Tree questions
 *
 */
public class Tree {

    public static void main(String[] args) {
        GoogleSearch search = new GoogleSearch();
        search.insert("howare");
        search.insert("home");
        System.out.println(search.startsWith("ho"));
    }

    public static TreeNode bstFromPreorder(int[] preorder) {

        TreeNode root = new TreeNode(preorder[0]);
        return constructTree(preorder, 1, root);
    }
    public static TreeNode constructTree(int[] preorder, int c, TreeNode root) {
        int upperBound = preorder[c - 1];
        if(c >= preorder.length) {
            return root;
        }
        TreeNode node = new TreeNode(preorder[c++]);
        if(node.val > root.val) {
            root.right = node;
            if(upperBound > node.val) constructTree(preorder, c, node);
        }else if(node.val < root.val) {
            root.left = node;
            if(upperBound > node.val) constructTree(preorder, c, node);
        }
        return root;
    }

    /**
     * Constructs a binary tree from a level-order array.
     *
     * @param arr The array representing the tree in level-order. Use null for empty nodes.
     * @return The root of the constructed binary tree.
     */
    public static TreeNode buildTree(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) {
            return null;
        }

        // The first element is the root of the tree
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1; // Start from the second element
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode currentNode = queue.poll();

            // Set the left child
            if (i < arr.length && arr[i] != null) {
                currentNode.left = new TreeNode(arr[i]);
                queue.add(currentNode.left);
            }
            i++;

            // Set the right child
            if (i < arr.length && i < arr.length && arr[i] != null) {
                currentNode.right = new TreeNode(arr[i]);
                queue.add(currentNode.right);
            }
            i++;
        }
        return root;
    }

    // Helper method to print the tree in level order for verification
    public void printLevelOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    public static void constructTree() {
        Integer[] arr = {3,5,1,6,2,0,8,-1,-1,7,4};
        TreeNode root = buildTree(arr);
        Tree tree = new Tree();
        kDistance(root);
        tree.printLevelOrder(root);
    }

    private static void kDistance(TreeNode node) {
        Queue<TreeNode> queue = new LinkedList<>();
        HashMap<TreeNode, TreeNode> parent = new HashMap<>();

        List<Integer> list = new ArrayList<>();

        HashSet<TreeNode> hashSet = new HashSet<>();
        hashSet.add(node);

        queue.offer(node);
        while(!queue.isEmpty()) {
            TreeNode current = queue.poll();

        }

    }

    static void sumChildrenProperty(TreeNode node) {
        if (node == null) return;

        int childSum = 0;
        if (node.left != null) childSum += node.left.val;
        if (node.right != null) childSum += node.right.val;

        // Step 1: Push value down if needed
        if (childSum < node.val) {
            if (node.left != null) node.left.val = node.val;
            if (node.right != null) node.right.val = node.val;
        }

        // Step 2: Fix subtrees
        sumChildrenProperty(node.left);
        sumChildrenProperty(node.right);

        // Step 3: Pull value up
        int total = 0;
        if (node.left != null) total += node.left.val;
        if (node.right != null) total += node.right.val;

        if (node.left != null || node.right != null)
            node.val = total;
    }

    static int diameter = 0;

    public static int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = diameterOfBinaryTree(root.left);
        int right = diameterOfBinaryTree(root.right);
        diameter = Math.max(diameter, left + right);
        return 1 + Math.max(left, right);
    }

    public static boolean isBalanced(TreeNode root) {
        findHeight(root);
        return Math.max(maxLeft, maxRight) - Math.min(maxRight, maxLeft) <= 1;
    }

    static boolean isBalanced = false;

    static int maxLeft = 0;
    static int maxRight = 0;

    public static int findHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = findHeight(root.left);
        int right = findHeight(root.right);
        maxLeft = Math.max(maxLeft, left);
        maxRight = Math.max(maxRight, right);
        System.out.println(maxLeft + " max Left");
        System.out.println(maxRight + " max Right");

        return 1 + Math.max(left, right);
    }

    public static void dfsIterative(TreeNode treeNode) {
        TreeNode current = treeNode;
        while (current != null) {
            System.out.print(current.val + " ");
            if (current.left != null) current = current.left;
            System.out.print(current.val + " ");
            if (current.right != null) current = current.right;
        }
    }

    public static void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    public static void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }

    /* Breath First Search
     *   @author Priyanshu Nagde
     */
    public static void bfs(TreeNode treeNode) {

        Queue<TreeNode> queue = new
                ArrayDeque<>();

        queue.offer(treeNode);

        while (!queue.isEmpty()) {

            TreeNode current = queue.poll();
            System.out.println(current.val);
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

    }

    static class TreeNode {
        TreeNode left;
        TreeNode right;

        int val;

        public TreeNode(TreeNode left, TreeNode right, int val) {
            this.left = left;
            this.right = right;
            this.val = val;
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode() {
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "left=" + left +
                    ", right=" + right +
                    ", val=" + val +
                    '}';
        }
    }
}



