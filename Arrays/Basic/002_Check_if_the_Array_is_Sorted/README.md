# Check if the Array is Sorted

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Easy
- **Status:** ✅ Solved

---

## Problem Statement

Given an array `nums` of `n` integers, return `true` if the array is sorted in **non-decreasing order**, otherwise return `false`.

---

## Examples

### Example 1

**Input**

```text
nums = [1, 2, 3, 4, 5]
```

**Output**

```text
true
```

**Explanation**

Every element is less than or equal to the next element.

---

### Example 2

**Input**

```text
nums = [1, 2, 1, 4, 5]
```

**Output**

```text
false
```

**Explanation**

Since `2 > 1`, the array is not sorted.

---

### Example 3

**Input**

```text
nums = [1, 9, 6, 8, 5, 4, 0]
```

**Output**

```text
false
```

---

## Constraints

- `1 <= n <= 100`
- `1 <= nums[i] <= 100`

---

# Brute Force Approach

## Idea

Traverse the array and compare every element with its next element.

If any element is greater than the next one, return `false`.

Otherwise return `true`.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(1) |

---

# Optimal Approach

The brute-force approach is already the most efficient solution because every element needs to be checked at most once.

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
