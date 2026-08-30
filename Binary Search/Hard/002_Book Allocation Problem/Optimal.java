/**
 * Problem: Book Allocation Problem
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - If there are more students than books (m > n), it's impossible
 *    for every student to get at least one book -> return -1 immediately.
 *
 *  - The answer (minimum possible "maximum pages assigned to a
 *    student") lies somewhere between:
 *      low  = the largest single book's page count (a student must
 *             be able to handle the biggest book alone, so the limit
 *             can never be smaller than this)
 *      high = the sum of all pages (a single student could take
 *             everything, trivially satisfying any m >= 1)
 *
 *  - As the candidate page limit increases, the number of students
 *    NEEDED to allocate all books DECREASES (or stays the same) -
 *    this monotonic relationship is what makes binary search
 *    applicable. This is structurally identical to "Capacity to Ship
 *    Packages Within D Days" (same "minimize the maximum" pattern).
 *
 *  - For a candidate limit mid, canAllocate() greedily assigns books
 *    IN ORDER: keep adding books to the current student's pile as
 *    long as it doesn't exceed mid; the moment adding the next book
 *    WOULD exceed mid, move to a new student, starting with that book.
 *    If the number of students required ever exceeds m, this limit
 *    is infeasible.
 *
 *  - If canAllocate(mid) is true, mid is a VALID limit, so we try an
 *    even smaller limit: high = mid (keep mid in the search space,
 *    since it's our best-known candidate).
 *  - If canAllocate(mid) is false, mid is too small - we need a
 *    bigger limit: low = mid + 1.
 *
 * Time Complexity:  O(n log(sum(nums))) -> binary search does
 *                    O(log(sum(nums))) iterations, each doing an O(n)
 *                    greedy check
 * Space Complexity: O(1) -> iterative, no extra space
 */
class Solution {
    public int findPages(int[] nums, int m) {
        int n = nums.length;

        if (m > n) return -1;

        int low = 0, high = 0;

        for (int pages : nums) {
            low = Math.max(low, pages);
            high += pages;
        }

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (canAllocate(nums, m, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private boolean canAllocate(int[] nums, int m, int maxPages) {
        int students = 1;
        int pages = 0;

        for (int book : nums) {
            if (pages + book <= maxPages) {
                pages += book;
            } else {
                students++;
                pages = book;

                if (students > m) {
                    return false;
                }
            }
        }

        return true;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {12, 34, 67, 90};
        System.out.println(solution.findPages(nums1, 2));  // Expected: 113

        int[] nums2 = {25, 46, 28, 49, 24};
        System.out.println(solution.findPages(nums2, 4));  // Expected: 71

        int[] nums3 = {15, 17, 20};
        System.out.println(solution.findPages(nums3, 2));  // Expected: 32
    }
}
