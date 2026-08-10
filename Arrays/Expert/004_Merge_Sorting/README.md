# Merge Sorting

**Difficulty:** Medium

## Problem Statement

Given an array of integers `nums`, sort the array in non-decreasing order using the **Merge Sort** algorithm.

A non-decreasing array is an array where every element is greater than or equal to the elements before it.

## Examples

### Example 1

```text
Input:
nums = [7, 4, 1, 5, 3]

Output:
[1, 3, 4, 5, 7]
```

### Example 2

```text
Input:
nums = [5, 4, 4, 1, 1]

Output:
[1, 1, 4, 4, 5]
```

### Example 3

```text
Input:
nums = [3, 2, 3, 4, 5]

Output:
[2, 3, 3, 4, 5]
```

## Approach

Merge Sort follows the **Divide and Conquer** strategy.

The algorithm:

1. Divide the array into two halves.
2. Recursively sort the left half.
3. Recursively sort the right half.
4. Merge the two sorted halves.
5. Continue until the complete array is sorted.

## Example

For:

```text
[7, 4, 1, 5, 3]
```

The array is divided recursively:

```text
              [7, 4, 1, 5, 3]
                    /     \
              [7, 4, 1]  [5, 3]
               /    \      / \
            [7,4]  [1]   [5] [3]
             / \
           [7] [4]
```

After sorting and merging:

```text
[4, 7]       [1]       [3, 5]
   \           /           /
    [1, 4, 7]           [3, 5]
             \           /
              [1, 3, 4, 5, 7]
```

## Complexity

**Time Complexity:** `O(N log N)`

**Space Complexity:** `O(N)`

The `O(N)` auxiliary space is used by the temporary array during merging.

## Key Pattern

```text
Divide → Recursively Sort → Merge
```

Merge Sort is a classic **Divide and Conquer** sorting algorithm.
