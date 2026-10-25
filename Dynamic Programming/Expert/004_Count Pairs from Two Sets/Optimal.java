/**
 * Problem: Count Pairs from Two Sets (Pairs of Students in Different Branches)
 * Approach: Optimal - Union-Find (Disjoint Set Union) + Size-Based Counting
 *
 * Idea:
 *  - "Same branch" is transitive, so every group of students linked
 *    by the given pairs is one BRANCH (connected component). Union-Find
 *    maintains these groups efficiently.
 *
 *  - parent[x] points toward the representative (root) of x's group;
 *    size[root] is the number of students in that group.
 *
 *  - UNION BY SIZE: when two groups are merged, the smaller group's
 *    root is attached under the larger group's root. This keeps every
 *    tree shallow (depth at most about log2(n)).
 *
 *  - PATH COMPRESSION: find(x) re-points every node on its path
 *    directly at the root, so later lookups are almost O(1). Together
 *    the two tricks give amortized O(alpha(n)) per operation, where
 *    alpha is the inverse Ackermann function (at most 4 or 5 for any
 *    realistic n).
 *
 *  - Because union by size keeps trees at depth O(log n), the
 *    RECURSIVE find is safe: it cannot overflow the call stack (a long
 *    chain, which could cause a StackOverflowError, cannot form).
 *
 *  - COUNTING: if the groups have sizes s1, s2, ..., sk, the number of
 *    pairs of students from different groups is the sum over i < j of
 *    si * sj. The code computes it in one sweep over the roots:
 *        remaining = number of students NOT yet assigned to a counted group
 *        for each root with size s:
 *            remaining -= s          (students in later groups only)
 *            result += s * remaining (pair this group with all later ones)
 *    so every cross-group pair is counted exactly once.
 *
 *    Equivalent formula: C(n, 2) - sum over groups of C(si, 2).
 *
 *  - `long` is used for the sums for safety. The largest possible
 *    answer, C(5 * 10^4, 2) = 1,249,975,000, fits in an int, but the
 *    required return type is long and the cast on the product avoids
 *    any int overflow in size[i] * remaining.
 *
 * Time Complexity:  O((n + p) * alpha(n)) -> effectively O(n + p)
 * Space Complexity: O(n) -> the parent and size arrays
 *                    (the recursion depth of find is at most about log2(n))
 */
class Solution {
    public long countPairs(int n, int[][] pairs) {
        int[] parent = new int[n];
        int[] size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // Merge students belonging to the same branch
        for (int[] pair : pairs) {
            int u = find(parent, pair[0]);
            int v = find(parent, pair[1]);

            if (u != v) {
                if (size[u] < size[v]) {
                    int temp = u;
                    u = v;
                    v = temp;
                }

                parent[v] = u;
                size[u] += size[v];
            }
        }

        // Count pairs from different components
        long result = 0;
        long remaining = n;

        for (int i = 0; i < n; i++) {
            if (find(parent, i) == i) {
                remaining -= size[i];
                result += (long) size[i] * remaining;
            }
        }

        return result;
    }

    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.countPairs(5, new int[][]{{0, 1}, {2, 3}, {0, 4}}));          // Expected: 6
        System.out.println(solution.countPairs(6, new int[][]{{0, 1}, {1, 2}, {2, 3}, {4, 5}}));  // Expected: 8
    }
}
