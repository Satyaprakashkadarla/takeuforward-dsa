/**
 * Problem: Minimum Days to Make M Bouquets
 * Approach: Brute Force (Linear Scan Over Possible Days)
 *
 * Idea:
 *  - The earliest day worth checking is 1, and the latest useful day
 *    is the maximum bloom day in nums (waiting any longer than that
 *    doesn't unlock any additional flowers).
 *  - Try every candidate day count from 1 upward.
 *  - For each candidate day, check whether we can make at least m
 *    bouquets of k adjacent bloomed roses by that day.
 *  - The FIRST day for which this is possible is the answer, since
 *    "can we make m bouquets" only gets easier (or stays the same)
 *    as days increase.
 *  - If even waiting until the very last flower blooms isn't enough
 *    (not enough total roses, or bouquet shape doesn't work out),
 *    return -1.
 *
 * Time Complexity:  O(max(nums) * n) -> up to max(nums) candidate
 *                    days, each requiring an O(n) feasibility check
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int roseGarden(int n, int[] nums, int k, int m) {
        if ((long) m * k > n) return -1;

        int maxDay = 0;
        for (int day : nums) {
            maxDay = Math.max(maxDay, day);
        }

        for (int day = 1; day <= maxDay; day++) {
            if (canMakeBouquets(nums, k, m, day)) {
                return day;
            }
        }

        return -1;
    }

    private boolean canMakeBouquets(int[] nums, int k, int m, int days) {
        int bouquets = 0;
        int consecutive = 0;

        for (int bloomDay : nums) {
            if (bloomDay <= days) {
                consecutive++;
                if (consecutive == k) {
                    bouquets++;
                    consecutive = 0;
                }
            } else {
                consecutive = 0;
            }
        }

        return bouquets >= m;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {7, 7, 7, 7, 13, 11, 12, 7};
        System.out.println(solution.roseGarden(8, nums1, 3, 2));  // Expected: 12

        int[] nums2 = {1, 10, 3, 10, 2};
        System.out.println(solution.roseGarden(5, nums2, 2, 3));  // Expected: -1

        int[] nums3 = {1, 10, 3, 10, 2};
        System.out.println(solution.roseGarden(5, nums3, 1, 3));  // Expected: 3
    }
}
