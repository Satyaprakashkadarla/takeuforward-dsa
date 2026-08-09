# 3 Sum — Notes

## Pattern

```text
Sorting + Two Pointers
```

This pattern is useful for:

* 2 Sum
* 3 Sum
* 4 Sum
* Target Sum
* Unique combinations

---

## Algorithm

### Step 1: Sort

```java
Arrays.sort(nums);
```

Example:

```text
[2, -2, 0, 3, -3, 5]

↓

[-3, -2, 0, 2, 3, 5]
```

### Step 2: Fix One Element

```java
for (int i = 0; i < n - 2; i++)
```

Treat:

```text
nums[i]
```

as the first element.

### Step 3: Use Two Pointers

```text
left  = i + 1
right = n - 1
```

Calculate:

```text
sum = nums[i] + nums[left] + nums[right]
```

---

## Pointer Rules

### Sum is zero

```text
sum == 0
```

Add the triplet:

```text
[nums[i], nums[left], nums[right]]
```

Then move both pointers.

---

### Sum is too small

```text
sum < 0
```

Because the array is sorted, increase the sum by:

```text
left++
```

---

### Sum is too large

```text
sum > 0
```

Decrease the sum by:

```text
right--
```

---

# Duplicate Handling

There are three important cases.

## Duplicate `i`

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

Example:

```text
[-1, -1, 0, 1]
```

The second `-1` should not start another identical set of triplets.

---

## Duplicate `left`

After finding a valid triplet:

```java
while (left < right &&
       nums[left] == nums[left + 1]) {
    left++;
}
```

---

## Duplicate `right`

```java
while (left < right &&
       nums[right] == nums[right - 1]) {
    right--;
}
```

Then:

```java
left++;
right--;
```

---

# Early Break

Because the array is sorted:

```java
if (nums[i] > 0) {
    break;
}
```

If `nums[i]` is already greater than zero, all later numbers are also positive.

Therefore:

```text
positive + positive + positive > 0
```

So no more solutions are possible.

---

# Example

```text
nums = [2, -2, 0, 3, -3, 5]
```

Sorted:

```text
[-3, -2, 0, 2, 3, 5]
```

Start:

```text
i = 0
nums[i] = -3
```

Pointers:

```text
left = -2
right = 5
```

Sum:

```text
-3 + (-2) + 5 = 0
```

Triplet:

```text
[-3, -2, 5]
```

Next:

```text
-3 + 0 + 3 = 0
```

Triplet:

```text
[-3, 0, 3]
```

Then:

```text
i = 1
nums[i] = -2
```

Find:

```text
-2 + 0 + 2 = 0
```

Triplet:

```text
[-2, 0, 2]
```

Final:

```text
[
    [-3, -2, 5],
    [-3, 0, 3],
    [-2, 0, 2]
]
```

---

# Complexity

### Brute Force

```text
Time:  O(n³)
Space: O(k)
```

### Optimal

```text
Time:  O(n²)
Space: O(1)
```

The returned result itself requires additional memory.

---

# Interview Template

```java
Arrays.sort(nums);

for (int i = 0; i < n - 2; i++) {

    if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
    }

    if (nums[i] > 0) {
        break;
    }

    int left = i + 1;
    int right = n - 1;

    while (left < right) {

        long sum = (long) nums[i]
                 + nums[left]
                 + nums[right];

        if (sum == 0) {

            // Add triplet

            // Skip duplicates

            left++;
            right--;

        } else if (sum < 0) {
            left++;
        } else {
            right--;
        }
    }
}
```

## Key Takeaway

When you see:

> Find all unique triplets with a target sum.

Think:

```text
Sort
  ↓
Fix one element
  ↓
Two pointers
  ↓
Skip duplicates
  ↓
O(n²)
```
