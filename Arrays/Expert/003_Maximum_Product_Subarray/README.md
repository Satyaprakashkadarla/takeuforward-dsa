# Maximum Product Subarray

## Problem Information

* **Topic:** Arrays / Dynamic Programming
* **Difficulty:** Hard
* **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums`, find the **non-empty contiguous subarray** that has the largest product.

Return the largest product.

A **subarray** is a contiguous sequence of elements within an array.

---

## Examples

### Example 1

**Input**

```text
nums = [4, 5, 3, 7, 1, 2]
```

**Output**

```text
840
```

**Explanation**

The entire array gives the maximum product:

```text
4 × 5 × 3 × 7 × 1 × 2 = 840
```

---

### Example 2

**Input**

```text
nums = [-5, 0, -2]
```

**Output**

```text
0
```

**Explanation**

The maximum product is `0`.

---

### Example 3

**Input**

```text
nums = [1, -2, 3, 4, -4, -3]
```

**Output**

```text
144
```

---

## Constraints

* `1 <= nums.length <= 10^4`
* `-10 <= nums[i] <= 10`
* Product of any prefix or suffix is within the given constraints.

---

# Brute Force Approach

## Idea

Generate every possible contiguous subarray.

For every starting index:

1. Start the product with `1`.
2. Extend the subarray one element at a time.
3. Multiply the current element with the product.
4. Update the maximum product.

Since we maintain the product while extending the subarray, we don't need to calculate the product from scratch.

---

## Complexity Analysis

| Complexity       | Value |
| ---------------- | ----- |
| Time Complexity  | O(n²) |
| Space Complexity | O(1)  |

---

# Optimal Approach

## Idea

The main challenge is handling **negative numbers**.

A negative number can change the smallest negative product into the largest positive product.

For example:

```text
(-10) × (-5) = 50
```

Therefore, at every index, maintain:

* `currentMax` → Maximum product ending at the current index.
* `currentMin` → Minimum product ending at the current index.

For every number `num`, there are three possibilities:

1. Start a new subarray with `num`.
2. Extend the previous maximum product.
3. Extend the previous minimum product.

So:

```text
currentMax = max(
    num,
    previousMax × num,
    previousMin × num
)
```

And:

```text
currentMin = min(
    num,
    previousMax × num,
    previousMin × num
)
```

We keep updating the global maximum answer using `currentMax`.

---

## Why Do We Need `currentMin`?

Consider:

```text
nums = [-2, 3, -4]
```

After processing `[-2, 3]`:

```text
currentMax = 3
currentMin = -6
```

When `-4` arrives:

```text
3 × -4  = -12
-6 × -4 = 24
```

The previous minimum becomes the new maximum.

Therefore, tracking only the maximum would fail.

---

## Handling Zero

Zero automatically allows a new subarray to begin.

For example:

```text
nums = [-5, 0, -2]
```

When we process `0`:

```text
currentMax = 0
currentMin = 0
```

The next element can then start a new subarray.

---

## Complexity Analysis

| Complexity       | Value |
| ---------------- | ----- |
| Time Complexity  | O(n)  |
| Space Complexity | O(1)  |

---

## Files

* `BruteForce.java`
* `Optimal.java`
* `Notes.md`
