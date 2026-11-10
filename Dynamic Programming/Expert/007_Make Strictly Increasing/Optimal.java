/**
 * Problem: Make Strictly Increasing
 * Approach: Optimal - Shift by Index + Longest Non-Decreasing Subsequence
 *           (tails array with binary search)
 *
 * Idea:
 *  - Changing an element costs ONE operation regardless of the amount,
 *    so we want to keep as many elements as possible unchanged. The
 *    answer is n - (the largest number of elements that can stay).
 *
 *  - Two kept elements i < j are compatible exactly when the elements
 *    between them can be filled with strictly increasing integers:
 *        arr[j] - arr[i] >= j - i   <=>   arr[j] - j >= arr[i] - i
 *    With b[i] = arr[i] - i, the kept elements must form a
 *    NON-DECREASING subsequence of b. (The code computes
 *    val = arr[i] - i on the fly.) Everything else can always be set
 *    to a suitable integer, because negative values are allowed and
 *    there is no upper limit.
 *
 *  - Longest non-decreasing subsequence in O(n log n): keep an array
 *    `tails`, where tails[k] is the smallest possible last value of a
 *    non-decreasing subsequence of length k + 1 seen so far. The array
 *    stays sorted, so for each new value a binary search finds where
 *    it belongs:
 *        left = index of the FIRST element in tails that is > val
 *    Setting tails[left] = val either extends the longest subsequence
 *    (if left == len, then len++) or improves a shorter one.
 *
 *  - The search condition `tails[mid] <= val` (move right on ties) is
 *    what makes the subsequence NON-DECREASING: an equal value is
 *    allowed to extend. Using `<` instead would compute a STRICTLY
 *    increasing subsequence of b, which is wrong for this problem
 *    (for [1,2,3,6,5,4] it would give 4 instead of 2).
 *
 *  - `tails` is not the subsequence itself, only a table of best
 *    endings; its final length `len` is the answer we need.
 *
 *  - Range: arr[i] <= 10^9 and i < 10^5, so val stays between about
 *    -10^5 and 10^9, which fits in an int.
 *
 * Time Complexity:  O(n log n) -> n elements, each with a binary search
 * Space Complexity: O(n)       -> the tails array
 */
class Solution {
    public int minOperations(int[] arr) {
        int n = arr.length;
        int[] tails = new int[n];
        int len = 0;

        for (int i = 0; i < n; i++) {
            int val = arr[i] - i;

            // Find first element > val
            int left = 0, right = len;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (tails[mid] <= val) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = val;

            if (left == len) {
                len++;
            }
        }

        return n - len;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.minOperations(new int[]{1, 2, 3, 6, 5, 4}));  // Expected: 2
        System.out.println(solution.minOperations(new int[]{1, 1, 1, 1}));        // Expected: 3
    }
}
