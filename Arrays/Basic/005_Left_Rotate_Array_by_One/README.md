# Left Rotate Array by One

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Easy
- **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums`, rotate the array to the left by one position.

**Note:** Modify the given array in-place. There is no need to return anything.

---

## Examples

### Example 1

**Input**

```text
nums = [1, 2, 3, 4, 5]
```

**Output**

```text
[2, 3, 4, 5, 1]
```

**Explanation**

The first element is moved to the end of the array.

---

### Example 2

**Input**

```text
nums = [-1, 0, 3, 6]
```

**Output**

```text
[0, 3, 6, -1]
```

---

### Example 3

**Input**

```text
nums = [7, 6, 5, 4]
```

**Output**

```text
[6, 5, 4, 7]
```

---

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

---

# Brute Force Approach

## Idea

Create a temporary array.

Copy every element one position to the left.

Place the first element at the last index.

Finally, copy the temporary array back to the original array.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(n) |

---

# Optimal Approach

## Idea

Store the first element in a temporary variable.

Shift every remaining element one position to the left.

Place the stored first element at the last index.

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
