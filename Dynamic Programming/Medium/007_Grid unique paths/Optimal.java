/**
 * Optimal Approach: Combinatorics (nCr)
 * Time Complexity: O(min(m, n)) - Single loop iteration up to min(m-1, n-1).
 * Space Complexity: O(1) - Uses only constant extra variables.
 */
class Solution {

    public int uniquePaths(int m, int n) {
        // Total steps required: (m - 1) Down + (n - 1) Right = m + n - 2
        int totalSteps = m + n - 2;
        int r = Math.min(m - 1, n - 1); // Select smaller count to minimize iterations
        
        long res = 1;

        // Calculate combination C(totalSteps, r)
        for (int i = 1; i <= r; i++) {
            res = res * (totalSteps - r + i) / i;
        }

        return (int) res;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println("Unique Paths (Optimal Combinatorics): " + sol.uniquePaths(3, 3));
    }
}
