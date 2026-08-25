/**
 * Problem: Koko Eating Bananas
 * Approach: Brute Force (Linear Scan Over Possible Speeds)
 *
 * Idea:
 *  - The minimum possible speed is 1 banana/hr, and the maximum
 *    speed we'd ever NEED to try is the size of the largest pile
 *    (eating faster than the biggest pile doesn't help - one hour
 *    per pile is already the fastest possible for that pile).
 *  - Try every candidate speed k, starting from 1 upward.
 *  - For each k, compute the total hours needed to eat all piles at
 *    that speed: for a pile of size p, it takes ceil(p / k) hours.
 *  - The FIRST speed k for which totalHours <= h is the minimum
 *    valid speed - return it immediately (since increasing speed
 *    only ever decreases or maintains total hours, the first valid
 *    k we find while scanning upward is guaranteed to be the smallest).
 *
 * Time Complexity:  O(max(nums) * n) -> up to max(nums) candidate
 *                    speeds, each requiring an O(n) pass over the piles
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int minimumRateToEatBananas(int[] nums, int h) {
        int maxPile = 0;
        for (int num : nums) {
            maxPile = Math.max(maxPile, num);
        }

        for (int k = 1; k <= maxPile; k++) {
            long hours = 0;
            for (int num : nums) {
                hours += (num + k - 1) / k; // ceil(num / k)
            }
            if (hours <= h) {
                return k;
            }
        }

        return maxPile; // fallback (won't actually be reached given constraints)
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {7, 15, 6, 3};
        System.out.println(solution.minimumRateToEatBananas(nums1, 8));  // Expected: 5

        int[] nums2 = {25, 12, 8, 14, 19};
        System.out.println(solution.minimumRateToEatBananas(nums2, 5));  // Expected: 25

        int[] nums3 = {3, 7, 6, 11};
        System.out.println(solution.minimumRateToEatBananas(nums3, 8));  // Expected: 4
    }
}
