/**
 * Problem: Maximum Sum Bitonic Subsequence
 * Approach: Brute Force / Standard DP - O(n^2) Two-Array DP
 *
 * Idea:
 *  - inc[i] = maximum sum of a STRICTLY INCREASING subsequence that
 *    ENDS at index i (always includes arr[i] itself).
 *      inc[i] = arr[i] + max( inc[j] for all j < i where arr[j] < arr[i] )
 *               (or just arr[i] alone if no such j exists)
 *
 *  - dec[i] = maximum sum of a STRICTLY DECREASING subsequence that
 *    STARTS at index i (always includes arr[i] itself).
 *      dec[i] = arr[i] + max( dec[j] for all j > i where arr[j] < arr[i] )
 *               (or just arr[i] alone if no such j exists)
 *
 *  - Treating index i as the "peak" of a bitonic subsequence, the
 *    best bitonic sum achievable WITH THAT PEAK is:
 *        inc[i] + dec[i] - arr[i]
 *    (subtracting arr[i] once, since both inc[i] and dec[i] already
 *    count it).
 *
 *  - The overall answer is the max of this quantity across all i.
 *
 *  - Computing inc[] and dec[] each takes O(n) work per index (a
 *    linear scan over all earlier/later elements), giving O(n^2)
 *    total for each array, O(n^2) overall.
 *
 * Time Complexity:  O(n^2) -> for each index, scan all other
 *                    relevant indices to find the best predecessor/successor
 * Space Complexity: O(n)   -> the inc[] and dec[] arrays
 */
import java.util.ArrayList;

public class Bruteforce {

    public int maxSumBS(ArrayList<Integer> arrList) {
        int n = arrList.size();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = arrList.get(i);
        }

        int[] inc = new int[n];
        int[] dec = new int[n];

        // inc[i] = max sum strictly increasing subsequence ending at i
        for (int i = 0; i < n; i++) {
            inc[i] = arr[i];
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    inc[i] = Math.max(inc[i], inc[j] + arr[i]);
                }
            }
        }

        // dec[i] = max sum strictly decreasing subsequence starting at i
        for (int i = n - 1; i >= 0; i--) {
            dec[i] = arr[i];
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[i]) {
                    dec[i] = Math.max(dec[i], dec[j] + arr[i]);
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, inc[i] + dec[i] - arr[i]);
        }

        return ans;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        ArrayList<Integer> arr1 = new ArrayList<>(java.util.Arrays.asList(80, 60, 30, 40, 20, 10));
        System.out.println(solution.maxSumBS(arr1));  // Expected: 210

        ArrayList<Integer> arr2 = new ArrayList<>(java.util.Arrays.asList(1, 15, 51, 45, 33, 100, 12, 18, 9));
        System.out.println(solution.maxSumBS(arr2));  // Expected: 194

        ArrayList<Integer> arr3 = new ArrayList<>(java.util.Arrays.asList(10, 10, 10));
        System.out.println(solution.maxSumBS(arr3));  // Expected: 10
    }
}
