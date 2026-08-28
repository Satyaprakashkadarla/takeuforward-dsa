/**
 * Problem: Capacity to Ship Packages Within D Days
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - The answer (minimum valid ship capacity) lies somewhere between:
 *      low  = the largest single package weight (the ship MUST be
 *             able to carry the heaviest package alone, so capacity
 *             can never be smaller than this)
 *      high = the sum of all package weights (a ship this large can
 *             carry everything in a single day, trivially satisfying
 *             any days >= 1)
 *
 *  - As candidate capacity increases, the number of days needed to
 *    ship everything DECREASES (or stays the same) - this monotonic
 *    relationship is what makes binary search applicable.
 *
 *  - For a candidate capacity mid, canShip() greedily simulates
 *    loading packages IN ORDER: keep adding to the current day's
 *    load as long as it doesn't exceed capacity; the moment adding
 *    the next package WOULD exceed capacity, start a new day with
 *    that package as the first item.
 *
 *  - If canShip(mid) is true, mid is a VALID capacity, so we record
 *    it as our current best answer (ans = mid) and try an even
 *    smaller capacity: high = mid - 1.
 *  - If canShip(mid) is false, mid is too small - we need more
 *    capacity: low = mid + 1.
 *
 * Time Complexity:  O(n log(sum(weights))) -> binary search does
 *                    O(log(sum(weights))) iterations, each doing an
 *                    O(n) greedy simulation
 * Space Complexity: O(1) -> iterative, no extra space
 */
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int low = 0;
        int high = 0;
        for (int w : weights) {
            low = Math.max(low, w);
            high += w;
        }

        int ans = high;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canShip(weights, days, mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    private boolean canShip(int[] weights, int days, int capacity) {
        int currentDays = 1;
        int currentWeight = 0;
        for (int w : weights) {
            if (currentWeight + w > capacity) {
                currentDays++;
                currentWeight = w;
            } else {
                currentWeight += w;
            }
        }
        return currentDays <= days;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] weights1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(solution.shipWithinDays(weights1, 5));  // Expected: 15

        int[] weights2 = {3, 2, 2, 4, 1, 4};
        System.out.println(solution.shipWithinDays(weights2, 3));  // Expected: 6

        int[] weights3 = {10, 50, 50, 10};
        System.out.println(solution.shipWithinDays(weights3, 2));  // Expected: 60
    }
}
