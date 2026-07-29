# Second Largest Element

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Easy
- **Status:** ✅ Solved

---

## Problem Statement

Given an array of integers `nums`, return the second-largest element in the array. If the second-largest element does not exist, return `-1`.

---

## Examples

### Example 1

**Input**

```text
nums = [8, 8, 7, 6, 5]
```

**Output**

```text
7
```

**Explanation**

The largest value is `8`, and the second largest distinct value is `7`.

---

### Example 2

**Input**

```text
nums = [10, 10, 10, 10, 10]
```

**Output**

```text
-1
```

**Explanation**

Only one distinct element exists, so there is no second largest element.

---

### Example 3

**Input**

```text
nums = [7, 7, 2, 2, 10, 10, 10]
```

**Output**

```text
7
```

---

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- Array may contain duplicate elements.

---

# Brute Force Approach

## Idea

Sort the array in ascending order and traverse from the end to find the first element smaller than the largest.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n log n) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Maintain two variables:

- `largest`
- `secondLargest`

Update them in a single traversal without sorting.

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
