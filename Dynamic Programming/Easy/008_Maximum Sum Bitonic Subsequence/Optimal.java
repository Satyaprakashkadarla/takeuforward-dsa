/**
 * Problem: Maximum Sum Bitonic Subsequence
 * Approach: Optimal - DP with Fenwick Tree (Max-BIT) + Coordinate Compression
 *
 * Idea:
 *  - This computes the SAME inc[i] / dec[i] quantities as the O(n^2)
 *    standard DP, but replaces the O(n) inner scan (to find the best
 *    predecessor/successor with a smaller value) with an O(log n)
 *    FENWICK TREE query, bringing the total down to O(n log n).
 *
 *  - COORDINATE COMPRESSION: since array values can be as large as
 *    10^6 but there are at most n (<=10^5) of them, the distinct
 *    values are first sorted and de-duplicated into `sorted[]`.
 *    Each value's position in `sorted[]` (its "rank") is then used
 *    as a compact index into the Fenwick Tree, instead of using the
 *    raw (potentially huge) value directly as an index.
 *
 *  - FENWICK TREE (Binary Indexed Tree), here storing MAXIMUMS
 *    instead of the more common sums: `update(rank, value)` records
 *    that the best sum ending with a value of this rank is at least
 *    `value`; `query(rank)` returns the best sum among all ranks
 *    from 1 up to `rank` seen so far.
 *
 *  - FORWARD PASS (computing inc[]): process left to right. For
 *    each arr[i], find its rank, then query the Fenwick Tree for
 *    the best inc-sum among all STRICTLY SMALLER values already
 *    processed (ranks 1 to rank-1) - this is exactly "the best
 *    increasing subsequence sum I could extend." Add arr[i] to get
 *    inc[i], then update the tree at this rank with the new inc[i]
 *    value (so later, larger elements can build on it).
 *
 *  - BACKWARD PASS (computing dec[]): symmetric, but processing
 *    right to left with a FRESH Fenwick Tree, computing the best
 *    decreasing-subsequence-starting-here sums.
 *
 *  - FINAL ANSWER: as in the O(n^2) version, try each index i as
 *    the "peak" of a bitonic subsequence: inc[i] + dec[i] - arr[i].
 *
 * Time Complexity:  O(n log n) -> O(n log n) for sorting during
 *                    coordinate compression, plus O(n log n) for
 *                    the two Fenwick-Tree-based passes (each
 *                    update/query is O(log n), done n times per pass)
 * Space Complexity: O(n) -> the sorted/compressed array, the inc[]
 *                    and dec[] arrays, and the Fenwick Tree itself
 */
import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    public int maxSumBS(ArrayList<Integer> arr) {
        int n = arr.size();

        int[] a = new int[n];
        int[] sorted = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = arr.get(i);
            sorted[i] = a[i];
        }

        // Coordinate compression
        Arrays.sort(sorted);

        int m = 0;
        for (int x : sorted) {
            if (m == 0 || sorted[m - 1] != x) {
                sorted[m++] = x;
            }
        }

        int[] inc = new int[n];
        int[] dec = new int[n];

        // Maximum sum strictly increasing subsequence ending at i
        Fenwick bit = new Fenwick(m);

        for (int i = 0; i < n; i++) {
            int rank = lowerBound(sorted, m, a[i]) + 1;

            inc[i] = a[i] + bit.query(rank - 1);
            bit.update(rank, inc[i]);
        }

        // Maximum sum strictly decreasing subsequence starting at i
        bit = new Fenwick(m);

        for (int i = n - 1; i >= 0; i--) {
            int rank = lowerBound(sorted, m, a[i]) + 1;

            // Need values strictly smaller than a[i]
            dec[i] = a[i] + bit.query(rank - 1);
            bit.update(rank, dec[i]);
        }

        int ans = 0;

        // Use i as the peak
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, inc[i] + dec[i] - a[i]);
        }

        return ans;
    }

    private int lowerBound(int[] a, int n, int target) {
        int low = 0, high = n;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (a[mid] < target)
                low = mid + 1;
            else
                high = mid;
        }

        return low;
    }

    static class Fenwick {
        int[] tree;

        Fenwick(int n) {
            tree = new int[n + 1];
        }

        void update(int index, int value) {
            while (index < tree.length) {
                tree[index] = Math.max(tree[index], value);
                index += index & -index;
            }
        }

        int query(int index) {
            int result = 0;

            while (index > 0) {
                result = Math.max(result, tree[index]);
                index -= index & -index;
            }

            return result;
        }
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        ArrayList<Integer> arr1 = new ArrayList<>(Arrays.asList(80, 60, 30, 40, 20, 10));
        System.out.println(solution.maxSumBS(arr1));  // Expected: 210

        ArrayList<Integer> arr2 = new ArrayList<>(Arrays.asList(1, 15, 51, 45, 33, 100, 12, 18, 9));
        System.out.println(solution.maxSumBS(arr2));  // Expected: 194

        ArrayList<Integer> arr3 = new ArrayList<>(Arrays.asList(10, 10, 10));
        System.out.println(solution.maxSumBS(arr3));  // Expected: 10
    }
}
