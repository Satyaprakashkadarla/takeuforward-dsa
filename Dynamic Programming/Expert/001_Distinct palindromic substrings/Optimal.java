/**
 * Problem: Distinct Palindromic Substrings
 * Approach: Expand Around Every Center + HashSet (as provided)
 *
 * Idea:
 *  - Every palindrome is determined by its CENTER. There are 2n - 1
 *    centers: n single-character centers (odd-length palindromes) and
 *    n - 1 gaps between two characters (even-length palindromes). The
 *    code calls expandAndAdd for both kinds at every index i: (i, i)
 *    for odd, and (i, i + 1) for even.
 *
 *  - expandAndAdd(left, right) grows the window outward while the two
 *    end characters match. Because a palindrome stays a palindrome
 *    when equal characters are added to both ends, EVERY window it
 *    passes through is a palindrome, so each one is added to the set.
 *    As soon as the ends differ, no larger window around this center
 *    can be a palindrome, so it stops.
 *
 *  - The HashSet removes duplicates: the same palindrome text can
 *    appear around many different centers (for example "a" appears
 *    several times in "abaaa").
 *
 *  - Every palindromic substring is found exactly once per occurrence,
 *    because each occurrence has exactly one center.
 *
 * Useful facts:
 *  - A string of length n has AT MOST n distinct palindromic
 *    substrings, so the set itself never holds more than n entries.
 *  - The number of palindromic OCCURRENCES can be as large as
 *    n(n + 1) / 2 (for example a string made of one repeated letter).
 *
 * Time Complexity:  One expansion step per palindromic occurrence, and
 *                    each step builds and hashes a substring costing
 *                    O(its length). On typical input this is close to
 *                    O(n). On adversarial input (one repeated letter,
 *                    n = 10^4) it is about 5 * 10^7 substring creations
 *                    and about 1.7 * 10^11 characters copied, i.e.
 *                    O(n^3) character work in the worst case.
 * Space Complexity: O(total length of the distinct palindromes), which is
 *                    at most O(n^2) characters (at most n entries, each at
 *                    most n long)
 *
 * See NOTES.md for the worst case and for a linear-time alternative
 * (Palindromic Tree / Eertree).
 */
import java.util.ArrayList;
import java.util.HashSet;

class Solution {
    public ArrayList<String> palindromicSubstr(String s) {
        HashSet<String> distinctPalindromes = new HashSet<>();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            // 1. Odd length palindromes (single character center)
            expandAndAdd(s, i, i, distinctPalindromes);

            // 2. Even length palindromes (between two characters center)
            expandAndAdd(s, i, i + 1, distinctPalindromes);
        }

        return new ArrayList<>(distinctPalindromes);
    }

    private void expandAndAdd(String s, int left, int right, HashSet<String> set) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            set.add(s.substring(left, right + 1));
            left--;
            right++;
        }
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        ArrayList<String> r1 = solution.palindromicSubstr("abaaa");
        java.util.Collections.sort(r1);
        System.out.println(r1);  // Expected: [a, aa, aaa, aba, b]

        ArrayList<String> r2 = solution.palindromicSubstr("geek");
        java.util.Collections.sort(r2);
        System.out.println(r2);  // Expected: [e, ee, g, k]
    }
}
