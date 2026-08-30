/**
 * Problem: Book Allocation Problem
 * Approach: Brute Force (Linear Scan Over Possible Page Limits)
 *
 * Idea:
 *  - If there are more students than books, it's impossible for
 *    every student to get at least one book -> return -1 immediately.
 *  - The smallest possible "max pages per student" limit worth
 *    trying is the largest single book's page count (a student must
 *    be able to handle the biggest book alone).
 *  - The largest limit worth trying is the sum of all pages (one
 *    student takes everything).
 *  - Try every candidate limit starting from the largest book size
 *    upward, greedily checking how many students would be needed
 *    for that limit.
 *  - The FIRST limit for which the required student count is <= m
 *    is the answer, since increasing the limit only ever reduces
 *    (or maintains) the number of students needed.
 *
 * Time Complexity:  O(sum(nums) * n) -> up to sum(nums) candidate
 *                    limits, each requiring an O(n) greedy check
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int findPages(int[] nums, int m) {
        int n = nums.length;
        if (m > n) return -1;

        int maxBook = 0;
        int totalPages = 0;
        for (int pages : nums) {
            maxBook = Math.max(maxBook, pages);
            totalPages += pages;
        }

        for (int limit = maxBook; limit <= totalPages; limit++) {
            if (canAllocate(nums, m, limit)) {
                return limit;
            }
        }

        return totalPages; // fallback (won't actually be reached given constraints)
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
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {12, 34, 67, 90};
        System.out.println(solution.findPages(nums1, 2));  // Expected: 113

        int[] nums2 = {25, 46, 28, 49, 24};
        System.out.println(solution.findPages(nums2, 4));  // Expected: 71

        int[] nums3 = {15, 17, 20};
        System.out.println(solution.findPages(nums3, 2));  // Expected: 32
    }
}
