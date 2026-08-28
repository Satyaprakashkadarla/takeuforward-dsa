/**
 * Problem: Capacity to Ship Packages Within D Days
 * Approach: Brute Force (Linear Scan Over Possible Capacities)
 *
 * Idea:
 *  - The smallest possible capacity we could ever try is the largest
 *    single package weight (the ship must be able to carry the
 *    heaviest package by itself, at minimum).
 *  - The largest capacity we'd ever NEED to try is the sum of all
 *    weights (a ship this big can carry everything in a single day).
 *  - Try every candidate capacity from max(weights) upward.
 *  - For each candidate, greedily simulate loading packages day by
 *    day: keep adding packages to the current day's load as long as
 *    it doesn't exceed capacity; once it would, start a new day.
 *  - The FIRST capacity for which the greedy simulation fits
 *    everything within `days` days is the answer, since increasing
 *    capacity only ever reduces (or maintains) the number of days
 *    needed.
 *
 * Time Complexity:  O(sum(weights) * n) -> up to sum(weights) candidate
 *                    capacities, each requiring an O(n) simulation
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int shipWithinDays(int[] weights, int days) {
        int maxWeight = 0;
        int totalWeight = 0;
        for (int w : weights) {
            maxWeight = Math.max(maxWeight, w);
            totalWeight += w;
        }

        for (int capacity = maxWeight; capacity <= totalWeight; capacity++) {
            if (canShip(weights, days, capacity)) {
                return capacity;
            }
        }

        return totalWeight; // fallback (won't actually be reached given constraints)
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
        Bruteforce solution = new Bruteforce();

        int[] weights1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(solution.shipWithinDays(weights1, 5));  // Expected: 15

        int[] weights2 = {3, 2, 2, 4, 1, 4};
        System.out.println(solution.shipWithinDays(weights2, 3));  // Expected: 6

        int[] weights3 = {10, 50, 50, 10};
        System.out.println(solution.shipWithinDays(weights3, 2));  // Expected: 60
    }
}
