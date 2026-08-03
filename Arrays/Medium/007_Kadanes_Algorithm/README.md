# Kadane's Algorithm

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums`, find the **contiguous non-empty subarray** having the largest sum and return that sum.

A subarray is a contiguous sequence of elements within an array.

---

## Examples

### Example 1

**Input**

```text
nums = [2, 3, 5, -2, 7, -4]
```

**Output**

```text
15
```

**Explanation**

The maximum sum subarray is:

```text
[2, 3, 5, -2, 7]
```

Sum = 15

---

### Example 2

**Input**

```text
nums = [-2, -3, -7, -2, -10, -4]
```

**Output**

```text
-2
```

---

### Example 3

**Input**

```text
nums = [-1, 2, 3, -1, 2, -6, 5]
```

**Output**

```text
6
```

---

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

---

# Brute Force Approach

## Idea

Generate every possible subarray.

Compute the sum of each subarray.

Return the maximum sum obtained.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Use **Kadane's Algorithm**.

Maintain:

- `currentSum` → Maximum subarray sum ending at the current index.
- `maxSum` → Overall maximum subarray sum.

At each element:

- Either start a new subarray.
- Or extend the existing subarray.

Choose whichever gives the larger sum.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
