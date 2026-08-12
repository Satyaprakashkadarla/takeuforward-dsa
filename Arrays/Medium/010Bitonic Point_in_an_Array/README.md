# Bitonic Point in an Array

## Problem Statement

Given a bitonic array `arr[]`, find the bitonic point.

A bitonic array first strictly increases and then strictly decreases.

Example:

```text
[1, 3, 8, 12, 9, 5, 2]
```

The bitonic point is:

```text
12
```

because:

```text
1 < 3 < 8 < 12 > 9 > 5 > 2
```

## Example

### Input

```text
arr = [1, 3, 8, 12, 9, 5, 2]
```

### Output

```text
12
```

## Constraints

* `1 <= n <= 10^5`
* Array is guaranteed to be bitonic.
* Elements are distinct.

## Approaches

### Brute Force

Find the maximum element by traversing the entire array.

**Time:** `O(n)`

**Space:** `O(1)`

### Optimal

Use binary search.

If:

```text
arr[mid] < arr[mid + 1]
```

we are on the increasing side, so move right.

Otherwise, the peak is at `mid` or to the left.

**Time:** `O(log n)`

**Space:** `O(1)`
