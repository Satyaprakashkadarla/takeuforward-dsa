/**
 * Brute Force Approach: Plain Recursion
 * Time Complexity: O(2^(m+n)) - Explores every path combination.
 * Space Complexity: O(m + n) - Recursion stack space proportional to max path length.
 */
class Solution {

    public int uniquePaths(int m, int n) {
        return countPaths(0, 0, m, n);
    }

    private int countPaths(int i, int j, int m, int n) {
        // Base Case: Reached destination
        if (i == m - 1 && j == n - 1) {
            return 1;
        }

        // Out of bounds check
        if (i >= m || j >= n) {
            return 0;
        }

        // Sum paths by moving Down and Right
        return countPaths(i + 1, j, m, n) + countPaths(i, j + 1, m, n);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println("Unique Paths (Brute Force): " + sol.uniquePaths(3, 3));
    }
}
