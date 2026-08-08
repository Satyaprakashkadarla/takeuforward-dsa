# Count Subarrays With Given XOR K

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given an array of integers `nums` and an integer `k`, return the total number of subarrays whose XOR is equal to `k`.

A subarray is a contiguous, non-empty sequence of elements from the array.

---

## Examples

### Example 1

**Input**

```text
nums = [4, 2, 2, 6, 4]
k = 6
```

**Output**

```text
4
```

**Explanation**

The subarrays whose XOR equals `6` are:

```text
[4, 2]
[4, 2, 2, 6, 4]
[2, 2, 6]
[6]
```

---

### Example 2

**Input**

```text
nums = [5, 6, 7, 8, 9]
k = 5
```

**Output**

```text
2
```

**Explanation**

The valid subarrays are:

```text
[5]
[5, 6, 7, 8, 9]
```

---

### Example 3

**Input**

```text
nums = [5, 2, 9]
k = 7
```

**Output**

```text
1
```

---

## Constraints

- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^9`
- `1 <= k <= 10^9`

---

# Brute Force Approach

## Idea

Generate every possible subarray.

For every starting index:

1. Initialize XOR to `0`.
2. Extend the subarray towards the right.
3. Update the XOR using the current element.
4. If the XOR equals `k`, increment the count.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Use **Prefix XOR + HashMap**.

Maintain the XOR of all elements from the beginning of the array up to the current index.

Let:

```text
currentXor = XOR from index 0 to current index
```

Suppose an earlier prefix XOR is:

```text
previousXor
```

The XOR of the subarray between them is:

```text
currentXor ^ previousXor
```

We need:

```text
currentXor ^ previousXor = k
```

Therefore:

```text
previousXor = currentXor ^ k
```

So at every index, check how many times:

```text
currentXor ^ k
```

has already appeared.

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
