# Notes — 4 Sum

## Problem Summary

Given an integer array `nums` and an integer `target`, find all unique quadruplets:

```text
[nums[a], nums[b], nums[c], nums[d]]
```

such that:

```text
a, b, c, d are distinct
```

and:

```text
nums[a] + nums[b] + nums[c] + nums[d] == target
```

The solution must not contain duplicate quadruplets.

---

# Brute Force Approach

## Idea

Check every possible combination of four different elements.

Use four nested loops:

```text
i
 └── j
      └── k
           └── l
```

For every combination:

1. Select four different indices.
2. Calculate their sum.
3. If the sum equals `target`, store the quadruplet.
4. Sort the quadruplet before storing it.
5. Use a suitable structure to avoid duplicate quadruplets.

## Example

For:

```text
nums = [1, -2, 3, 5, 7, 9]
target = 7
```

One valid combination is:

```text
[-2, 1, 3, 5]
```

because:

```text
-2 + 1 + 3 + 5 = 7
```

## Complexity

* **Time Complexity:** `O(n⁴)`
* **Space Complexity:** `O(1)`, excluding the space required for storing the answer.

## Drawback

Four nested loops make the solution extremely slow for large arrays.

With:

```text
n = 200
```

the number of combinations becomes very large.

Therefore, a more efficient approach is required.

---

# Optimal Approach

## Idea

Use:

* Sorting
* Two fixed pointers
* Two-pointer technique
* Duplicate skipping

The array is first sorted.

Then:

1. Fix the first element using `i`.
2. Fix the second element using `j`.
3. Use `left` and `right` pointers to find the remaining two elements.
4. Compare the current sum with `target`.
5. Move pointers accordingly.
6. Skip duplicate values to avoid duplicate quadruplets.

---

## Step 1: Sort the Array

Example:

```text
nums = [1, -2, 3, 5, 7, 9]
```

After sorting:

```text
[-2, 1, 3, 5, 7, 9]
```

Sorting allows us to use the two-pointer technique.

---

## Step 2: Fix the First Element

Use `i` to select the first number.

For example:

```text
i = 0
nums[i] = -2
```

---

## Step 3: Fix the Second Element

Use `j` to select the second number.

For example:

```text
j = 1
nums[j] = 1
```

Now we need two more numbers whose sum is:

```text
target - nums[i] - nums[j]
```

For:

```text
target = 7
```

we need:

```text
7 - (-2) - 1 = 8
```

---

## Step 4: Use Two Pointers

Set:

```text
left = j + 1
right = n - 1
```

Then calculate:

```text
sum = nums[i] + nums[j] + nums[left] + nums[right]
```

### If `sum` equals `target`

Store the quadruplet.

Then move both pointers:

```text
left++
right--
```

### If `sum` is smaller than `target`

Move:

```text
left++
```

Because the array is sorted and we need a larger sum.

### If `sum` is greater than `target`

Move:

```text
right--
```

Because we need a smaller sum.

---

# Duplicate Handling

Duplicate quadruplets must not be returned.

## Skip Duplicate `i`

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

## Skip Duplicate `j`

```java
if (j > i + 1 && nums[j] == nums[j - 1]) {
    continue;
}
```

## Skip Duplicate `left` and `right`

After finding a valid quadruplet:

```java
while (left < right && nums[left] == nums[left - 1]) {
    left++;
}

while (left < right && nums[right] == nums[right + 1]) {
    right--;
}
```

This ensures that the same quadruplet is not added multiple times.

---

# Why Use `long` for the Sum?

The array values can be as large as:

```text
-10^4 to 10^4
```

Although these constraints are small enough for `int`, using `long` for the sum is safer and prevents integer overflow if the constraints are increased later.

The calculation can therefore be written as:

```java
long sum = (long) nums[i]
         + nums[j]
         + nums[left]
         + nums[right];
```

---

# Complexity

After sorting:

* **Sorting:** `O(n log n)`
* **Two fixed loops + two-pointer traversal:** `O(n³)`

Therefore:

## Time Complexity

```text
O(n³)
```

## Space Complexity

```text
O(1)
```

excluding the space required to store the result.

---

# Example Walkthrough

## Input

```text
nums = [1, -2, 3, 5, 7, 9]
target = 7
```

## Sorted Array

```text
[-2, 1, 3, 5, 7, 9]
```

Choose:

```text
i = 0 → -2
j = 1 → 1
```

Now:

```text
left = 2 → 3
right = 5 → 9
```

Sum:

```text
-2 + 1 + 3 + 9 = 11
```

Since:

```text
11 > 7
```

move:

```text
right--
```

Now:

```text
right = 4 → 7
```

Sum:

```text
-2 + 1 + 3 + 7 = 9
```

Still greater than `7`.

Move `right` again.

Eventually:

```text
-2 + 1 + 3 + 5 = 7
```

Therefore:

```text
[-2, 1, 3, 5]
```

is added to the result.

---

# Edge Cases

Consider:

* Array contains fewer than 4 elements.
* No quadruplet exists.
* Duplicate values.
* Multiple valid quadruplets.
* All values are the same.
* Negative numbers.
* Positive and negative numbers together.
* `target` is negative.
* `target` is zero.
* Duplicate quadruplets generated from different indices.

---

# Key Concepts

* Sorting
* Nested loops
* Two-pointer technique
* Duplicate elimination
* Array traversal
* `long` for safe sum calculation
* In-place array sorting

---

# Interview Explanation

A good way to explain the solution:

> First, I sort the array so that I can use the two-pointer technique. I fix the first two elements using two loops. For the remaining two elements, I use `left` and `right` pointers. If the current sum is smaller than the target, I move `left` forward. If it is larger, I move `right` backward. When the sum equals the target, I store the quadruplet and move both pointers. I skip duplicate values at every relevant position so that the result contains only unique quadruplets.

---

# Comparison

| Approach               | Time Complexity | Extra Space |
| ---------------------- | --------------: | ----------: |
| Brute Force            |         `O(n⁴)` |     `O(1)`* |
| Sorting + Two Pointers |         `O(n³)` |     `O(1)`* |

> `*` Excluding the space required to store the output.

---

# Takeaway

The main optimization is reducing the last two nested loops to a **two-pointer search** after sorting.

Instead of checking every combination of four elements:

```text
O(n⁴)
```

we fix two elements and efficiently search for the remaining two:

```text
O(n³)
```

The most important parts to remember are:

```text
Sort
  ↓
Fix i
  ↓
Fix j
  ↓
Two pointers: left + right
  ↓
Compare sum with target
  ↓
Skip duplicates
```

This gives an efficient **`O(n³)`** solution while maintaining unique quadruplets.
