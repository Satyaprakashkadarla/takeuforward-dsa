# Notes

## Problem Summary

Find the **maximum sum** among all possible **contiguous subarrays**.

---

## Brute Force Approach

### Idea

Generate every possible subarray.

Calculate the sum of each subarray.

Keep track of the maximum sum encountered.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

Many overlapping subarrays are recalculated, making the solution inefficient for large arrays.

---

## Optimal Approach (Kadane's Algorithm)

### Idea

Maintain two variables:

- `currentSum` → Maximum sum ending at the current index.
- `maxSum` → Maximum sum found so far.

For every element:

```text
currentSum = max(nums[i], currentSum + nums[i])
```

Update:

```text
maxSum = max(maxSum, currentSum)
```

This determines whether to:

- Start a new subarray from the current element.
- Extend the previous subarray.

---

## Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Single element array
- All negative numbers
- All positive numbers
- Mixed positive and negative numbers
- Maximum subarray at the beginning
- Maximum subarray at the end

---

## Key Concepts

- Dynamic Programming
- Kadane's Algorithm
- Greedy Technique
- Running Sum

---

## Interview Tip

Whenever you need the **maximum sum contiguous subarray**, think of **Kadane's Algorithm**.

The key observation is:

- If the running sum becomes worse than the current element alone, start a new subarray.

This achieves the optimal **O(n)** solution.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Generate All Subarrays | O(n²) | O(1) |
| Kadane's Algorithm | O(n) | O(1) |

---

## Takeaway

Kadane's Algorithm efficiently computes the maximum subarray sum in a single traversal by deciding at each element whether to continue the current subarray or start a new one. It is the standard optimal solution for this problem.
