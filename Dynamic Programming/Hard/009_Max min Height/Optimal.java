/**
 * Problem: Max Min Height
 * Approach: Optimal - Binary Search on the Answer + Greedy Check
 *           (Difference Array / Sliding Window of Active Waterings)
 *
 * Idea:
 *  - We want to MAXIMIZE the MINIMUM height, a classic "binary search
 *    on the answer" shape. Let canAchieve(T) answer: "can every flower
 *    reach height >= T using at most k waterings?" This is MONOTONIC:
 *    if T is achievable, every smaller target is too.
 *
 *  - Search range:
 *      lo = min(arr)      -> always achievable (water nothing)
 *      hi = max(arr) + k + 1 -> a safe upper bound (the answer can never
 *                           exceed min(arr) + k, which is below this)
 *    The loop keeps the answer inside [lo, hi].
 *
 *  - Upper-mid binary search: because success moves `lo = mid` (not
 *    mid + 1), the midpoint must round UP, i.e.
 *        mid = lo + (hi - lo + 1) / 2
 *    Otherwise `lo = mid` could loop forever when hi = lo + 1.
 *
 *  - canAchieve(target): sweep left to right keeping `curr`, the number
 *    of waterings that currently cover flower i.
 *      needed = target - arr[i] - curr
 *    If needed > 0, flower i is still too short: water the window that
 *    STARTS at i exactly `needed` times (it reaches furthest right, so
 *    it helps the most upcoming flowers). Add `needed` to ops (fail if
 *    ops > k) and to curr.
 *
 *  - Those `needed` waterings stop covering flowers at index i + w, so
 *    record `extra[i + w] -= needed`. At each step, `curr += extra[i]`
 *    removes waterings that have just expired. This is a DIFFERENCE
 *    ARRAY that gives an O(n) check instead of O(n * w).
 *
 *  - Windows near the right end: the check lets a window start at i
 *    even when i + w > n (it just covers i..n-1). A real window must lie
 *    inside the garden, but it can be shifted left to end at n - 1, and
 *    that covers at least the same flowers on the right plus more on the
 *    left, which can only help. So feasibility is unchanged.
 *
 *  - ops, curr and extra are `long` to stay safe from overflow, since
 *    needed can be as large as about 10^9.
 *
 * Time Complexity:  O(n * log(k + max(arr))) -> about 31 binary-search
 *                    rounds, each an O(n) check
 * Space Complexity: O(n) -> the difference array
 */
class Solution {
    public int maxMinHeight(int[] arr, int k, int w) {
        int n = arr.length;
        int lo = Integer.MAX_VALUE, hi = 0;
        for (int h : arr) {
            lo = Math.min(lo, h);
            hi = Math.max(hi, h);
        }
        hi += k + 1; // upper bound
        
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (canAchieve(arr, mid, k, w)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
    
    private boolean canAchieve(int[] arr, int target, int k, int w) {
        int n = arr.length;
        long[] extra = new long[n + 1];
        long ops = 0;
        long curr = 0;
        for (int i = 0; i < n; i++) {
            curr += extra[i];
            long needed = target - arr[i] - curr;
            if (needed > 0) {
                ops += needed;
                if (ops > k) return false;
                curr += needed;
                if (i + w < n) {
                    extra[i + w] -= needed;
                }
            }
        }
        return true;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.maxMinHeight(new int[]{2, 3, 4, 5, 1}, 2, 2));  // Expected: 2
        System.out.println(solution.maxMinHeight(new int[]{5, 8}, 5, 1));           // Expected: 9
    }
}
