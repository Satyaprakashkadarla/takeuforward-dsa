# Notes

## Problem Summary

Find the **maximum profit** that can be earned by buying one stock and selling it **exactly once** after the purchase.

If no profit is possible, return `0`.

---

## Brute Force Approach

### Idea

Consider every possible buying day.

For each buying day, try every possible selling day after it.

Compute the profit for each pair and store the maximum profit.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

Many buy-sell pairs are checked repeatedly, making the solution inefficient for large inputs.

---

## Optimal Approach

### Idea

Maintain two variables:

- `minPrice` → Lowest stock price encountered so far.
- `maxProfit` → Maximum profit obtained so far.

For every day's price:

- Update `minPrice` if the current price is lower.
- Otherwise, calculate the profit by selling today and update `maxProfit`.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Only one day's price
- Prices always decreasing
- Prices always increasing
- Duplicate prices
- Profit equals zero

---

## Key Concepts

- Single Traversal
- Greedy Algorithm
- Minimum Value Tracking
- Maximum Profit Calculation

---

## Interview Tip

When maximizing profit from a single transaction, don't compare every pair of days.

Instead, keep track of the **minimum price seen so far** and calculate the profit for each subsequent day.

This reduces the time complexity from **O(n²)** to **O(n)**.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Nested Loops | O(n²) | O(1) |
| Single Traversal | O(n) | O(1) |

---

## Takeaway

Tracking the minimum stock price while traversing the array once provides the most efficient solution for maximizing profit from a single buy-and-sell transaction.
