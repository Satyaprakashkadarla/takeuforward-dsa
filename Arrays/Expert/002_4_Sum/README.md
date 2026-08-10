# 4 Sum

## Problem Information

* **Topic:** Arrays
* **Difficulty:** Expert
* **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums` and an integer `target`, return all unique quadruplets:

```text
[nums[a], nums[b], nums[c], nums[d]]
```

such that:

* `a`, `b`, `c`, and `d` are all distinct valid indices.
* `nums[a] + nums[b] + nums[c] + nums[d] == target`
* The solution set must not contain duplicate quadruplets.

The output and the quadruplets can be returned in any order.

---

## Examples

### Example 1

#### Input

```text
nums = [1, -2, 3, 5, 7, 9]
target = 7
```

#### Output

```text
[[-2, 1, 3, 5]]
```

#### Explanation

The quadruplet:

```text
[-2, 1, 3, 5]
```

has a sum of:

```text
-2 + 1 + 3 + 5 = 7
```

Therefore, the answer is:

```text
[[-2, 1, 3, 5]]
```

---

### Example 2

#### Input

```text
nums = [7, -7, 1, 2, 14, 3]
target = 9
```

#### Output

```text
[]
```

#### Explanation

There is no combination of four distinct elements whose sum is equal to `9`.

Therefore, the result is an empty list.

---

### Example 3

#### Input

```text
nums = [1, 1, 3, 4, -3]
target = 5
```

#### Output

```text
[[-3, 1, 3, 4]]
```

#### Explanation

The quadruplet:

```text
[-3, 1, 3, 4]
```

has a sum of:

```text
-3 + 1 + 3 + 4 = 5
```

Therefore, the answer is:

```text
[[-3, 1, 3, 4]]
```

---

## Constraints

* `1 <= nums.length <= 200`
* `-10^4 <= nums[i] <= 10^4`
* `-10^4 <= target <= 10^4`

---

## Expected Approach

A common approach is:

1. Sort the array.
2. Fix the first two elements using two nested loops.
3. Use two pointers to find the remaining two elements.
4. Skip duplicate values to ensure unique quadruplets.
5. Optionally use pruning based on the current sum to improve performance.

### Time Complexity

```text
O(n^3)
```

### Space Complexity

```text
O(1)
```

excluding the space required for the output.

---

## Example Walkthrough

For:

```text
nums = [1, -2, 3, 5, 7, 9]
target = 7
```

After sorting:

```text
[-2, 1, 3, 5, 7, 9]
```

We search for four values whose sum is `7`.

The valid combination is:

```text
-2 + 1 + 3 + 5 = 7
```

So the result is:

```text
[[-2, 1, 3, 5]]
```

---

## Key Points

* The four indices must be distinct.
* Quadruplets must not be duplicated.
* Sorting makes duplicate handling easier.
* The two-pointer technique reduces the complexity from `O(n^4)` to `O(n^3)`.
* The order of the output does not matter.
