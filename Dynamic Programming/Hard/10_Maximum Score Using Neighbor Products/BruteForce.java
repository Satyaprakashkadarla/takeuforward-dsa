/**
 * Problem: Maximum Score Using Neighbor Products
 * Approach: Brute Force (Try Every Removal Order)
 *
 * Idea:
 *  - Follow the process literally. From the current list of
 *    remaining elements, try removing EACH element in turn:
 *      score for this removal = left * element * right
 *    where left/right are the element's current neighbors (or 1 at
 *    an end of the list).
 *  - After removing it, recurse on the shorter list and add the best
 *    score obtainable from it:
 *        f(list) = max over i of ( left_i * list[i] * right_i
 *                                  + f(list without i) )
 *    with f(empty list) = 0.
 *  - This explores every possible removal order, so it is always
 *    correct, but there are n! orders (n choices for the first
 *    removal, n-1 for the second, and so on).
 *
 * Time Complexity:  O(n!) -> every permutation of removals is tried,
 *                    each step also copies the list (O(n))
 * Space Complexity: O(n^2) -> recursion depth n, with a list copy of
 *                    up to n elements at each level
 *
 * Only usable for very small n (about 10 or less); it is a
 * correctness baseline.
 */
import java.util.ArrayList;
import java.util.List;

public class Bruteforce {

    public int maxProductSum(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int x : arr) {
            list.add(x);
        }
        return solve(list);
    }

    private int solve(List<Integer> list) {
        if (list.isEmpty()) {
            return 0;
        }

        int best = 0;

        for (int i = 0; i < list.size(); i++) {
            int left = (i > 0) ? list.get(i - 1) : 1;
            int right = (i < list.size() - 1) ? list.get(i + 1) : 1;
            int gain = left * list.get(i) * right;

            // Build the list without element i
            List<Integer> next = new ArrayList<>(list);
            next.remove(i);

            best = Math.max(best, gain + solve(next));
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.maxProductSum(new int[]{5, 10}));          // Expected: 60
        System.out.println(solution.maxProductSum(new int[]{1, 2, 3, 4, 5}));  // Expected: 110
    }
}
