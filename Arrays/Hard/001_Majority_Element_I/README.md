# Majority Element-I

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums` of size `n`, return the **majority element**.

The majority element is the element that appears **more than ⌊n/2⌋ times** in the array.

It is guaranteed that the majority element always exists.

---

## Examples

### Example 1

**Input**

```text
nums = [7,0,0,1,7,7,2,7,7]
```

**Output**

```text
7
```

---

### Example 2

**Input**

```text
nums = [1,1,1,2,1,2]
```

**Output**

```text
1
```

---

### Example 3

**Input**

```text
nums = [-1,-1,-1,-1]
```

**Output**

```text
-1
```

---

## Constraints

- `1 <= n <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- A majority element always exists.

---

# Brute Force Approach

## Idea

For every element, count its frequency by traversing the entire array.

If the frequency becomes greater than `n/2`, return that element.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Use **Boyer-Moore Voting Algorithm**.

Maintain:

- Candidate
- Count

If count becomes zero, choose the current element as the new candidate.

Increase count for the same element and decrease it for different elements.

Since a majority element always exists, the remaining candidate is the answer.

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
