# Majority Element-II

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums` of size `n`, return all elements that appear **more than ⌊n/3⌋ times**.

The output can be returned in any order.

**Note:** There can be at most **two** majority elements.

---

## Examples

### Example 1

**Input**

```text
nums = [1,2,1,1,3,2]
```

**Output**

```text
[1]
```

---

### Example 2

**Input**

```text
nums = [1,2,1,1,3,2,2]
```

**Output**

```text
[1,2]
```

---

### Example 3

**Input**

```text
nums = [1,2,1,1,3,2,2,3]
```

**Output**

```text
[1,2]
```

---

## Constraints

- `2 <= n <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

---

# Brute Force Approach

## Idea

For every element:

- Count its occurrences.
- If its frequency is greater than `n/3`, add it to the answer.
- Avoid inserting duplicate elements.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Use the **Extended Boyer-Moore Voting Algorithm**.

Since an element must appear more than `n/3` times, there can be **at most two such elements**.

Maintain:

- Candidate1
- Candidate2
- Count1
- Count2

Phase 1:

Find two potential candidates.

Phase 2:

Verify whether they actually occur more than `n/3` times.

Finally, return the valid candidates in ascending order.

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
