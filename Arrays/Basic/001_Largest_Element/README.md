# Largest Element in an Array

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Basic
- **Status:** ✅ Solved

---

## Problem Statement

Given an array of integers `nums`, return the value of the largest element in the array.

### Example 1

**Input**

```text
nums = [3, 3, 6, 1]
```

**Output**

```text
6
```

### Example 2

**Input**

```text
nums = [3, 3, 0, 99, -40]
```

**Output**

```text
99
```

---

## Approach

Traverse the array once while maintaining the maximum element.

---

## Algorithm

1. Initialize `max = nums[0]`.
2. Traverse the array from index `1`.
3. Update `max` whenever a larger element is found.
4. Return `max`.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(1) |

---

## Files

- `Optimal.java`
- `Notes.md`
