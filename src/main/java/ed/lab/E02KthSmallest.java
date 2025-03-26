package ed.lab;

public class E02KthSmallest {

    public int kthSmallest(TreeNode<Integer> root, int k) {
        int[] count = {0};
        int[] result = {0};
        inOrder(root, k, count, result);
        return result[0];
    }

    private void inOrder(TreeNode<Integer> node, int k, int[] count, int[] result) {
        if (node == null) return;

        inOrder(node.left, k, count, result);

        count[0]++;
        if (count[0] == k) {
            result[0] = node.value;
            return;
        }

        inOrder(node.right, k, count, result);
    }
}