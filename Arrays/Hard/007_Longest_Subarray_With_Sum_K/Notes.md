# Notes

## Problem Summary

Find the length of the longest contiguous subarray whose sum equals `k`.

---

## Brute Force Approach

### Idea

Generate every possible subarray.

For each subarray:

- Compute its sum.
- If the sum equals `k`, update the maximum length.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

Repeatedly calculates subarray sums, making it inefficient for large arrays.

---

## Optimal Approach

### Idea

Use Prefix Sum and a HashMap.

Maintain:

- Running prefix sum.
- HashMap storing the first occurrence of each prefix sum.

At every index:

```text
Current Prefix Sum = prefixSum
```

If:

```text
prefixSum - k
```

already exists,

then the elements between those indices form a subarray whose sum equals `k`.

Store only the first occurrence of each prefix sum to maximize the subarray length.

---

## Why Prefix Sum Works

Suppose:

```text
PrefixSum(j) - PrefixSum(i) = k
```

Then:

```text
Subarray(i+1 ... j) = k
```

This allows us to identify valid subarrays in constant time using a HashMap.

---

## Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

---

## Edge Cases

- No valid subarray
- Entire array sums to `k`
- Negative numbers
- Positive numbers
- Mixed positive and negative numbers
- Single element equal to `k`

---

## Key Concepts

- Prefix Sum
- HashMap
- Longest Subarray
- Running Sum

---

## Interview Tip

Store only the **first occurrence** of each prefix sum.

This ensures that when the same prefix sum appears again, the distance between indices is maximized, giving the longest possible subarray.

Using `long` for the prefix sum prevents integer overflow when handling large values.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Brute Force | O(n²) | O(1) |
| Prefix Sum + HashMap | O(n) | O(n) |

---

## Takeaway

The Prefix Sum + HashMap approach efficiently finds the longest subarray with sum `k` in linear time. By storing the first occurrence of each prefix sum, it guarantees the maximum possible subarray length while handling positive, negative, and mixed integer arrays.
