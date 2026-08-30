# Book Allocation Problem

**Difficulty:** Hard
**Tags:** Array, Binary Search, Greedy
**Companies:** (add as applicable)

## Problem Statement

Given an array `nums` of `n` integers, where `nums[i]` represents the number of pages in the `i`th book, and an integer `m` representing the number of students, allocate all books to the students such that:

- Each student gets **at least one** book.
- Each book is allocated to **only one** student.
- The allocation is **contiguous** (a student gets a contiguous run of books).

Allocate the books so that the **maximum number of pages assigned to any student is minimized**. If allocation isn't possible, return `-1`.

## Examples

### Example 1
```
Input:  nums = [12,34,67,90], m = 2
Output: 113
Explanation: Allocation [12,34,67] | [90] → max = 113, the minimum possible maximum.
```

### Example 2
```
Input:  nums = [25,46,28,49,24], m = 4
Output: 71
Explanation: Allocation [25,46] | [28] | [49] | [24] → max = 71.
```

### Your Turn
```
Input:  nums = [15,17,20], m = 2
Output: 32
Explanation: Allocation [15,17] | [20] → max = 32, the minimum possible maximum.
```

## Constraints

- `1 <= n, m <= 10^4`
- `1 <= nums[i] <= 10^5`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(sum(nums) * n) linear scan solution |
| `Optimal.java` | O(n log(sum(nums))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over page limits) | O(sum(nums) * n) | O(1) |
| Optimal (Binary Search on the Answer) | O(n log(sum(nums))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is structurally **identical** to "Capacity to Ship Packages Within D Days" — just renamed (books/pages/students instead of packages/weights/days). As the candidate maximum page limit increases, fewer students are needed (monotonic), which is exactly the property that makes binary search on the answer applicable.
