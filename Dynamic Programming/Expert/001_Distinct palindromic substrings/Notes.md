# Notes: Distinct Palindromic Substrings

## 1. Problem Recap

List every **distinct** continuous substring of `s` that reads the same forwards and backwards.

```
s = "abaaa"  ->  a, aa, aaa, aba, b      (5 distinct)
s = "geek"   ->  e, ee, g, k             (4 distinct)
```

"Distinct" means by text: `"a"` appears four times in `"abaaa"` but counts once.

### Two Facts That Shape Every Solution

1. **A palindrome is determined by its center.** There are `2n - 1` centers: `n` single characters (odd-length palindromes) and `n - 1` gaps between neighbors (even-length palindromes).
2. **A string of length `n` has at most `n` distinct palindromic substrings.** (Appending one character to a string can create at most one new distinct palindrome.) I also checked this on 500 random strings. So the answer is always small in *count*, even though the number of palindromic *occurrences* can be huge (up to `n(n+1)/2`).

---

## 2. Approach 1: Brute Force (Check Every Substring)

### Idea
Try all `n(n+1)/2` substrings, test each with two pointers, and put the palindromes in a `HashSet`.

### Code Logic
```java
for (int i = 0; i < n; i++)
    for (int j = i; j < n; j++)
        if (isPalindrome(s, i, j))
            distinct.add(s.substring(i, j + 1));
```

### Dry Run
`s = "geek"`

| Substring | Palindrome? | Added |
|---|---|---|
| g | yes | g |
| ge, gee, geek | no | |
| e (index 1) | yes | e |
| ee | yes | ee |
| eek | no | |
| e (index 2) | yes | (duplicate of e) |
| ek | no | |
| k | yes | k |

Set = `{g, e, ee, k}`

Result: **4 distinct** ✅

### Complexity
- **Time:** O(n^3): O(n^2) substrings, each checked in O(n).
- **Space:** O(total length of the distinct palindromes).

For `n = 10^4` that is on the order of 10^12 operations, far too slow.

---

## 3. Approach 2: Expand Around Every Center (as Provided)

### Idea
Instead of testing every substring, grow palindromes outward from each center. If `s[left] == s[right]`, the window is a palindrome and can be widened by one on each side; if they differ, no larger window around this center can be a palindrome, so stop. Every window passed through is a palindrome, so nothing is wasted on non-palindromes.

### Code Logic
```java
for (int i = 0; i < n; i++) {
    expandAndAdd(s, i, i,     set);   // odd length: center is character i
    expandAndAdd(s, i, i + 1, set);   // even length: center is the gap after i
}

private void expandAndAdd(String s, int left, int right, HashSet<String> set) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        set.add(s.substring(left, right + 1));
        left--;
        right++;
    }
}
```

### Dry Run 1: `"abaaa"`

| i | Center type | Windows that match (substring added) | Stops because |
|---|---|---|---|
| 0 | odd | (0,0) `a` | (-1,1): out of range |
| 0 | even | none | `a` != `b` |
| 1 | odd | (1,1) `b`, (0,2) `aba` | (-1,3): out of range |
| 1 | even | none | `b` != `a` |
| 2 | odd | (2,2) `a` | (1,3): `b` != `a` |
| 2 | even | (2,3) `aa` | (1,4): `b` != `a` |
| 3 | odd | (3,3) `a`, (2,4) `aaa` | (1,5): out of range |
| 3 | even | (3,4) `aa` | (2,5): out of range |
| 4 | odd | (4,4) `a` | (3,5): out of range |
| 4 | even | none | (4,5): out of range |

Attempts in order: `a, b, aba, a, aa, a, aaa, aa, a` (9 additions, but only 5 distinct).

Set = `{a, b, aba, aa, aaa}`

Result: **["a", "aa", "aaa", "aba", "b"]** ✅ (matches expected output, after sorting)

### Dry Run 2: `"geek"`

| i | Center type | Windows that match | Stops because |
|---|---|---|---|
| 0 | odd | `g` | out of range |
| 0 | even | none | `g` != `e` |
| 1 | odd | `e` | (0,2): `g` != `e` |
| 1 | even | `ee` | (0,3): `g` != `k` |
| 2 | odd | `e` | (1,3): `e` != `k` |
| 2 | even | none | `e` != `k` |
| 3 | odd | `k` | out of range |
| 3 | even | none | out of range |

Attempts in order: `g, e, ee, e, k`. Set = `{g, e, ee, k}`.

Result: **["e", "ee", "g", "k"]** ✅ (matches expected output)

### Complexity: Be Precise About the Cost
Each successful expansion step corresponds to one palindromic **occurrence**, and each step calls `substring` (copies the characters) and `HashSet.add` (hashes them), both O(length of that palindrome).

- **Number of steps:** equals the number of palindromic occurrences, between `n` and `n(n+1)/2`.
- **Typical input:** palindromes are short and rare. I counted the work for a random 26-letter string of length 10^4: **10,772** substring creations and **11,948** characters copied. That is essentially O(n).
- **Worst case, one repeated letter (`"aaaa...a"`, n = 10^4):** every substring is a palindrome, so there are **50,005,000** substring creations copying **166,716,670,000** characters in total (`n(n+1)(n+2)/6`). That is O(n^3) character work, which could take minutes in Java. (These are counts I computed, not timings; I could not run Java here.)
- **Space:** at most `n` distinct strings are stored, but they can be long. For `"aaaa...a"` the distinct palindromes are `a`, `aa`, ..., up to length `n`, about 50 million characters in total (tens of MB).

So the provided solution is **a good practical solution** that is fast on typical tests, but it is not asymptotically optimal, and an adversarial test would stress it.

---

## 4. Bonus: The True Linear-Time Method (Palindromic Tree / Eertree)

If you ever need a guaranteed bound, the **palindromic tree** (also called Eertree) stores every distinct palindrome of the string as a node, built in O(n) time with a small alphabet:

- Each node represents one distinct palindrome, with its length.
- A child edge labeled `c` leads from palindrome `P` to `cPc` (wrap `P` in one more `c` on both sides).
- Each node has a **suffix link** to its longest proper palindromic suffix.
- Process the string one character at a time: follow suffix links from the current longest palindromic suffix until you find one that can be extended by the new character. That extension is the new longest palindromic suffix, and it creates **at most one new node**.

Because each character adds at most one new node, there are at most `n` nodes (this is exactly the "at most `n` distinct palindromes" fact), and the amortized work is O(n).

Caveat: this problem asks you to **return the strings themselves**, so the output costs time proportional to the sum of the palindromes' lengths. For `"aaaa...a"` that is about 50 million characters, so printing dominates. The tree removes the wasted work of building, hashing and comparing duplicates, but cannot beat the size of the answer.

---

## 5. Comparing the Approaches

| Aspect | Brute Force | Expand Around Center (provided) | Eertree (bonus) |
|---|---|---|---|
| Time | O(n^3) | O(n^2) expansion steps; O(n^3) character work worst case | O(n) to find all distinct palindromes |
| Space | O(sum of distinct lengths) | O(sum of distinct lengths) | O(n) for the tree |
| Typical random input | Slow | Fast | Fast |
| `"aaaa...a"`, n = 10^4 | Impossible | Very heavy | Fast to build; output size dominates |

---

## 6. Edge Cases to Consider

1. **Single character:** `"x"` gives `["x"]`.
2. **All the same letter:** `"aaaa"` gives `a, aa, aaa, aaaa`, the worst case for the provided solution.
3. **No repeated letters:** `"abc"` gives only the single characters: `a, b, c`.
4. **Even-length palindromes:** `"abba"` is only found via the gap between the two `b`s, which is why both `(i, i)` and `(i, i + 1)` are expanded.
5. **Duplicates across centers:** the same palindrome appears from several centers and is stored once by the set.
6. **Output order:** the problem allows any order; the driver sorts, so the `HashSet` ordering does not matter.

---

## 7. Related Concepts / Follow-Ups

- **Longest Palindromic Substring** (LeetCode 5): the same expand-around-center technique, keeping only the longest.
- **Palindromic Substrings** (LeetCode 647): count **all occurrences**, not distinct ones. Expand around center works directly, with no set and no substring creation, so it is O(n^2) time and O(1) space.
- **Manacher's algorithm:** finds the longest palindrome around every center in O(n).
- **Palindromic Tree (Eertree):** distinct palindromes in O(n), discussed above.
- **Palindrome Partitioning I and II:** DP over palindromic substrings.

---

## 8. Key Takeaways

- Every palindrome has a center, so growing outward from `2n - 1` centers visits only palindromes and never wastes work on non-palindromes.
- A string has at most `n` distinct palindromic substrings, so the set is small even when the number of occurrences is huge.
- The real cost of the provided approach is **building and hashing a string for every occurrence**, not the expansion itself. It is fast on typical inputs and heavy on strings like `"aaaa...a"`.
- If you need a guaranteed bound, use a palindromic tree, or avoid creating strings (for example, deduplicate using positions and hashes) when you only need counts.
