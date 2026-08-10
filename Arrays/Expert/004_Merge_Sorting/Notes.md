# Merge Sort — Notes

## Core Concept

Merge Sort is a **Divide and Conquer** sorting algorithm.

Instead of trying to sort the entire array at once, it repeatedly divides the array into smaller portions.

The process is:

```text
Divide
  ↓
Sort Left
  ↓
Sort Right
  ↓
Merge
```

---

## Step 1 — Divide

For an array:

```text
[7, 4, 1, 5, 3]
```

calculate the midpoint:

```java
int mid = left + (right - left) / 2;
```

This divides the range into:

```text
Left:  [left ... mid]
Right: [mid + 1 ... right]
```

The expression:

```java
left + (right - left) / 2
```

is preferred over:

```java
(left + right) / 2
```

because it avoids integer overflow when `left` and `right` are very large.

---

## Step 2 — Recursively Sort

The algorithm recursively divides both halves:

```java
mergeSortHelper(nums, temp, left, mid);
mergeSortHelper(nums, temp, mid + 1, right);
```

The recursion continues until:

```java
left >= right
```

At that point, the subarray contains zero or one element.

A zero- or one-element array is already sorted.

Therefore:

```java
if (left >= right) {
    return;
}
```

is the base case.

---

## Step 3 — Merge

After recursion returns, both halves are sorted.

For example:

```text
Left Half  = [1, 4, 7]
Right Half = [3, 5]
```

We need to combine them into:

```text
[1, 3, 4, 5, 7]
```

The merge operation uses two pointers:

```text
i → left half
j → right half
k → temporary array
```

---

## Two-Pointer Merge

```java
while (i <= mid && j <= right) {

    if (nums[i] <= nums[j]) {
        temp[k++] = nums[i++];
    } else {
        temp[k++] = nums[j++];
    }
}
```

At every step, compare the current elements of both sorted halves.

For example:

```text
Left  = [1, 4, 7]
Right = [3, 5]
```

First:

```text
1 < 3
```

Store `1`.

```text
temp = [1]
```

Then:

```text
4 > 3
```

Store `3`.

```text
temp = [1, 3]
```

Then:

```text
4 < 5
```

Store `4`.

Continue until both halves are processed.

---

## Remaining Elements

After the main comparison loop, one half may still contain elements.

Remaining left elements:

```java
while (i <= mid) {
    temp[k++] = nums[i++];
}
```

Remaining right elements:

```java
while (j <= right) {
    temp[k++] = nums[j++];
}
```

Because each half was already sorted, the remaining elements can simply be copied.

---

## Why Use a Temporary Array?

During merging, we need a safe place to construct the sorted result.

Therefore:

```java
int[] temp = new int[nums.length];
```

is created once in the main method.

The same temporary array is reused during every merge operation.

This is better than creating a new temporary array during every recursive call.

---

## Why Is `temp` Created Only Once?

The code does:

```java
int[] temp = new int[nums.length];

mergeSortHelper(nums, temp, 0, nums.length - 1);
```

The same `temp` array is passed through recursion.

This avoids repeatedly allocating arrays.

Conceptually:

```text
Original array
       ↓
Merge Sort
       ↓
Same reusable temp array
       ↓
Sorted result
```

---

## Copying Back

After merging:

```java
for (i = left; i <= right; i++) {
    nums[i] = temp[i];
}
```

copies the sorted section back into the original array.

This is important because the next level of recursion expects the corresponding subarray of `nums` to be sorted.

---

## Complete Flow

For:

```text
[7, 4, 1, 5, 3]
```

The recursion creates:

```text
             [7, 4, 1, 5, 3]
                    |
             -------------
             |           |
          [7,4,1]      [5,3]
           /   \        / \
        [7,4] [1]    [5] [3]
         / \
       [7] [4]
```

Single elements are already sorted.

Then merging begins:

```text
[7] + [4] → [4,7]

[4,7] + [1] → [1,4,7]

[5] + [3] → [3,5]

[1,4,7] + [3,5]
          ↓
[1,3,4,5,7]
```

---

## Why Is Merge Sort O(N log N)?

At every recursion level, the total number of elements processed during merging is approximately `N`.

The array is divided approximately in half at every level:

```text
N
N/2
N/4
N/8
...
1
```

The number of levels is:

```text
log₂(N)
```

Each level performs:

```text
O(N)
```

work.

Therefore:

```text
O(N) × O(log N)
= O(N log N)
```

---

## Space Complexity

The temporary array requires:

```text
O(N)
```

additional space.

The recursion depth is:

```text
O(log N)
```

Therefore, the dominant auxiliary space is:

```text
O(N)
```

---

## Stability

The merge condition is:

```java
if (nums[i] <= nums[j])
```

The `<=` means that when two values are equal, the element from the left half is selected first.

This preserves the relative order of equal elements, making this implementation **stable**.

---

## Important Code Components

### Main Method

```java
public int[] mergeSort(int[] nums)
```

Entry point of the algorithm.

It handles edge cases, creates the reusable temporary array, and starts recursion.

### `mergeSortHelper()`

```java
private void mergeSortHelper(...)
```

Responsible for:

* Dividing the array.
* Recursively sorting both halves.
* Calling `merge()`.

### `merge()`

```java
private void merge(...)
```

Responsible for:

* Comparing both sorted halves.
* Creating the sorted sequence.
* Copying the result back into `nums`.

---

## Key Pattern to Remember

```text
             Merge Sort
                 |
          Divide and Conquer
                 |
        -------------------
        |                 |
    Left Half         Right Half
        |                 |
      Sort              Sort
        \                 /
         \               /
             Merge
               |
          Sorted Array
```

## Interview Explanation

> Merge Sort is a divide-and-conquer algorithm. I recursively divide the array into two halves until each subarray contains at most one element. Then I merge the sorted halves using two pointers. During merging, I compare the current elements from both halves and place the smaller element into a reusable temporary array. Finally, I copy the merged elements back into the original array. The time complexity is O(N log N), and the auxiliary space complexity is O(N).
