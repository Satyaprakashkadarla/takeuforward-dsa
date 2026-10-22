/**
 * Problem: Distinct Palindromic Substrings
 * Approach: Brute Force (Check Every Substring)
 *
 * Idea:
 *  - Enumerate every substring s[i..j] (there are n(n+1)/2 of them).
 *  - For each one, test whether it reads the same forwards and
 *    backwards using two pointers moving toward the middle.
 *  - Add every palindrome to a HashSet, which removes duplicates
 *    automatically.
 *  - This is the most direct translation of the problem statement. It
 *    spends O(length) on each substring check, even when the very
 *    first and last characters already differ, and it re-checks the
 *    inside of palindromes from scratch every time.
 *
 * Time Complexity:  O(n^3) -> O(n^2) substrings, each checked in O(n)
 *                    (plus O(n) to build and hash each palindrome we keep)
 * Space Complexity: O(total length of the distinct palindromes) -> the set
 */
import java.util.ArrayList;
import java.util.HashSet;

public class Bruteforce {

    public ArrayList<String> palindromicSubstr(String s) {
        HashSet<String> distinct = new HashSet<>();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(s, i, j)) {
                    distinct.add(s.substring(i, j + 1));
                }
            }
        }

        return new ArrayList<>(distinct);
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        ArrayList<String> r1 = solution.palindromicSubstr("abaaa");
        java.util.Collections.sort(r1);
        System.out.println(r1);  // Expected: [a, aa, aaa, aba, b]

        ArrayList<String> r2 = solution.palindromicSubstr("geek");
        java.util.Collections.sort(r2);
        System.out.println(r2);  // Expected: [e, ee, g, k]
    }
}
