# Best Time to Buy and Sell Stock

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given an array `arr` where `arr[i]` represents the stock price on the `iᵗʰ` day, determine the maximum profit that can be achieved by buying and selling the stock **at most once**.

The stock must be bought before it is sold.

If no profit can be made, return `0`.

---

## Examples

### Example 1

**Input**

```text
arr = [10, 7, 5, 8, 11, 9]
```

**Output**

```text
6
```

**Explanation**

Buy at price `5` and sell at price `11`.

Profit = `11 - 5 = 6`

---

### Example 2

**Input**

```text
arr = [5, 4, 3, 2, 1]
```

**Output**

```text
0
```

**Explanation**

Stock prices continuously decrease, so no profitable transaction can be made.

---

### Example 3

**Input**

```text
arr = [3, 8, 1, 4, 6, 2]
```

**Output**

```text
5
```

---

## Constraints

- `1 <= n <= 10^5`
- `0 <= arr[i] <= 10^6`

---

# Brute Force Approach

## Idea

Try every possible buying day.

For each buying day, check every possible selling day after it.

Calculate the profit and keep track of the maximum profit.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Traverse the array once while maintaining:

- Minimum stock price seen so far.
- Maximum profit obtained so far.

For each day's price:

- Update the minimum price if a lower price is found.
- Otherwise, calculate the current profit and update the maximum profit.

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
