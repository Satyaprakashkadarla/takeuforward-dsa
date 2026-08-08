# Count Subarrays With Given Sum

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given an array of integers `nums` and an integer `k`, return the total number of subarrays whose sum is equal to `k`.

A subarray is a contiguous, non-empty sequence of elements from the array.

---

## Examples

### Example 1

**Input**

```text
nums = [1, 1, 1]
k = 2
```

**Output**

```text
2
```

**Explanation**

The two subarrays whose sum is `2` are:

```text
[1, 1]
[1, 1]
```

---

### Example 2

**Input**

```text
nums = [1, 2, 3]
k = 3
```

**Output**

```text
2
```

**Explanation**

The valid subarrays are:

```text
[1, 2]
[3]
```

---

### Example 3

**Input**

```text
nums = [3, 1, 2, 4]
k = 6
```

**Output**

```text
2
```

---

## Constraints

- `1 <= nums.length <= 10^5`
- `-1000 <= nums[i] <= 1000`
- `-10^7 <= k <= 10^7`

---

# Brute Force Approach

## Idea

Generate every possible subarray.

For every starting index:

1. Initialize the sum to `0`.
2. Extend the subarray towards the right.
3. Add each element to the current sum.
4. If the sum becomes equal to `k`, increment the count.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Use **Prefix Sum + HashMap**.

Maintain a running prefix sum while traversing the array.

For every current prefix sum:

```text
prefix
```

We need to find how many previous prefix sums are equal to:

```text
prefix - k
```

Because:

```text
prefix - previousPrefix = k
```

Therefore:

```text
previousPrefix = prefix - k
```

The HashMap stores the frequency of every prefix sum encountered so far.

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
