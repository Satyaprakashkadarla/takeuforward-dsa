# Longest Subarray With Sum K

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums` of size `n` and an integer `k`, return the length of the longest subarray whose sum is exactly `k`.

If no such subarray exists, return `0`.

---

## Examples

### Example 1

**Input**

```text
nums = [10,5,2,7,1,9]
k = 15
```

**Output**

```text
4
```

**Explanation**

The longest subarray is:

```text
[5,2,7,1]
```

whose sum equals `15`.

---

### Example 2

**Input**

```text
nums = [-3,2,1]
k = 6
```

**Output**

```text
0
```

---

### Example 3

**Input**

```text
nums = [-1,1,1]
k = 1
```

**Output**

```text
3
```

---

## Constraints

- `1 <= n <= 10^5`
- `-10^5 <= nums[i] <= 10^5`
- `-10^9 <= k <= 10^9`

---

# Brute Force Approach

## Idea

Generate every possible subarray.

For each subarray:

- Calculate its sum.
- If the sum equals `k`, update the maximum length.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Use Prefix Sum and HashMap.

Maintain:

- Running prefix sum.
- HashMap storing the first occurrence of every prefix sum.

If:

```text
prefixSum - k
```

already exists,

then the subarray between those indices has sum `k`.

Store only the first occurrence to maximize subarray length.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(n) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
