/**
 * Problem: Form Pyramid (Minimum Cost to Build a Pyramid of Stones)
 * Approach: Brute Force (Try Every Center, Expand Outward for Each)
 *
 * Idea:
 *  - A pyramid of peak height h, centered at position i, requires:
 *      arr[i]   >= h
 *      arr[i-1] >= h-1, arr[i+1] >= h-1
 *      arr[i-2] >= h-2, arr[i+2] >= h-2
 *      ... and so on, until the ramp reaches height 1 on both sides
 *      (or runs off the edge of the array).
 *  - For each possible center i, expand outward step by step
 *    (k = 0, 1, 2, ...), checking whether both arr[i-k] and arr[i+k]
 *    (when in bounds) can support the required height (h-k). Stop
 *    expanding the moment either side fails to support the required
 *    height, or we run off the array's bounds - whichever height we
 *    reached just before stopping is this center's maximum feasible
 *    pyramid height.
 *  - Track the global maximum feasible height across all centers.
 *  - A pyramid of peak height h always sums to EXACTLY h^2 (the
 *    classic 1+2+...+h+...+2+1 = h^2 identity), so the minimum cost
 *    is simply (total original sum) - (maxHeight^2), since every
 *    stone height reduction is "wasted" sum, and we want to keep as
 *    much sum as possible (by maximizing the pyramid we can build).
 *
 * Time Complexity:  O(n^2) -> for each of n possible centers, the
 *                    outward expansion can take up to O(n) steps
 * Space Complexity: O(1)   -> no extra space beyond a few variables
 */
public class Bruteforce {

    public int formPyramid(int[] arr) {
        int n = arr.length;
        long total = 0;
        for (int x : arr) {
            total += x;
        }

        int maxHeight = 0;

        for (int center = 0; center < n; center++) {
            int height = 0;
            int k = 0;
            while (true) {
                int required = height + 1; // the height needed at distance k from center
                int leftIdx = center - k;
                int rightIdx = center + k;

                boolean leftOk = (leftIdx >= 0) && (arr[leftIdx] >= required);
                boolean rightOk = (rightIdx < n) && (arr[rightIdx] >= required);

                if (leftOk && rightOk) {
                    height = required;
                    k++;
                } else {
                    break;
                }
            }
            maxHeight = Math.max(maxHeight, height);
        }

        return (int) (total - (long) maxHeight * maxHeight);
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1 = {1, 2, 3, 4, 2, 1};
        System.out.println(solution.formPyramid(arr1));  // Expected: 4

        int[] arr2 = {1, 2, 1};
        System.out.println(solution.formPyramid(arr2));  // Expected: 0
    }
}
