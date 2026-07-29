# Linear Search

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Easy
- **Status:** ✅ Solved

---

## Problem Statement

Given an array of integers `nums` and an integer `target`, find the smallest index (0-based indexing) where the target appears in the array. If the target is not found, return `-1`.

---

## Examples

### Example 1

**Input**

```text
nums = [2, 3, 4, 5, 3]
target = 3
```

**Output**

```text
1
```

**Explanation**

The first occurrence of `3` is at index `1`.

---

### Example 2

**Input**

```text
nums = [2, -4, 4, 0, 10]
target = 6
```

**Output**

```text
-1
```

**Explanation**

The target `6` is not present in the array.

---

### Example 3

**Input**

```text
nums = [1, 3, 5, -4, 1]
target = 1
```

**Output**

```text
0
```

---

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- `-10^4 <= target <= 10^4`

---

# Brute Force Approach

## Idea

Traverse the array from left to right and compare every element with the target.

Return the index immediately when the target is found.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(1) |

---

# Optimal Approach

Linear Search itself is the optimal approach because every element may need to be checked in the worst case.

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
