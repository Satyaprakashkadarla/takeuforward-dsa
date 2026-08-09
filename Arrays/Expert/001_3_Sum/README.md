# 3 Sum

## Problem

Given an integer array `nums`, return all unique triplets `[nums[i], nums[j], nums[k]]` such that:

* `i != j`
* `i != k`
* `j != k`
* `nums[i] + nums[j] + nums[k] == 0`

The solution must not contain duplicate triplets.

## Examples

### Example 1

```text
Input:
[2, -2, 0, 3, -3, 5]

Output:
[[-3, -2, 5], [-3, 0, 3], [-2, 0, 2]]
```

### Example 2

```text
Input:
[2, -1, -1, 3, -1]

Output:
[[-1, -1, 2]]
```

### Example 3

```text
Input:
[8, -6, 5, 4]

Output:
[]
```

---

# Brute Force Approach

Check every possible combination of three different indexes.

For every:

```text
i < j < k
```

check whether:

```text
nums[i] + nums[j] + nums[k] == 0
```

A `HashSet` is used to remove duplicate triplets.

### Complexity

```text
Time:  O(n³)
Space: O(k)
```

where `k` is the number of unique triplets.

---

# Optimal Approach

Use:

```text
Sorting + Two Pointers
```

First sort the array.

For every index `i`, fix `nums[i]` and use two pointers:

```text
left  = i + 1
right = n - 1
```

Calculate:

```text
sum = nums[i] + nums[left] + nums[right]
```

If:

```text
sum == 0
```

we found a triplet.

If:

```text
sum < 0
```

move `left` forward.

If:

```text
sum > 0
```

move `right` backward.

Skip duplicate values to ensure unique triplets.

### Complexity

```text
Time:  O(n²)
Space: O(1)
```

excluding the returned result.

---

# Important Optimizations

Skip duplicate first elements:

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

If the first number is positive, stop:

```java
if (nums[i] > 0) {
    break;
}
```

Skip duplicate left and right values after finding a triplet:

```java
while (left < right && nums[left] == nums[left + 1]) {
    left++;
}

while (left < right && nums[right] == nums[right - 1]) {
    right--;
}
```

Use `long` for the sum:

```java
long sum = (long) nums[i] + nums[left] + nums[right];
```

This avoids integer overflow.

---

# Recommended Solution

For `n <= 3000`, use the sorting + two-pointer solution because it reduces the complexity from:

```text
O(n³)
```

to:

```text
O(n²)
```
