# Maximum Product Subarray — Notes

## Core Observation

For every position, we need to know the maximum and minimum product of a subarray ending at that position.

Why?

Because negative numbers can reverse the result.

```text
Positive × Negative = Negative
Negative × Negative = Positive
```

Therefore:

```text
currentMax
currentMin
```

must both be maintained.

---

## DP State

### `currentMax`

Maximum product of a subarray ending at the current index.

### `currentMin`

Minimum product of a subarray ending at the current index.

---

## Transition

For the current number:

```text
num = nums[i]
```

We have three choices:

```text
num
previousMax × num
previousMin × num
```

Therefore:

```text
currentMax =
    max(num, previousMax × num, previousMin × num)
```

```text
currentMin =
    min(num, previousMax × num, previousMin × num)
```

---

## Why Save Previous Values?

We need the old `currentMax` and `currentMin` while calculating both new values.

Therefore:

```java
int prevMax = currentMax;
int prevMin = currentMin;
```

Then update:

```java
currentMax = ...;
currentMin = ...;
```

If we update `currentMax` first and use the updated value while calculating `currentMin`, the calculation becomes incorrect.

---

## Example

Consider:

```text
nums = [2, 3, -2, 4]
```

Start:

```text
currentMax = 2
currentMin = 2
answer = 2
```

For `3`:

```text
max(3, 2 × 3, 2 × 3) = 6
min(3, 2 × 3, 2 × 3) = 3
```

For `-2`:

```text
max(-2, 6 × -2, 3 × -2) = -2
min(-2, 6 × -2, 3 × -2) = -12
```

For `4`:

```text
max(4, -2 × 4, -12 × 4) = 4
```

The global maximum remains:

```text
6
```

---

## Negative Number Example

```text
nums = [-2, 3, -4]
```

After `-2`:

```text
currentMax = -2
currentMin = -2
```

After `3`:

```text
currentMax = 3
currentMin = -6
```

After `-4`:

```text
3 × -4  = -12
-6 × -4 = 24
```

Therefore:

```text
currentMax = 24
```

This demonstrates why `currentMin` is necessary.

---

## Zero

Zero can break the current product chain.

Example:

```text
[-5, 0, -2]
```

When processing `0`:

```text
currentMax = 0
currentMin = 0
```

The next element can start a completely new subarray.

The transition automatically handles this because `num` itself is always considered.

---

## Complexity

### Brute Force

```text
Time Complexity:  O(n²)
Space Complexity: O(1)
```

### Optimal

```text
Time Complexity:  O(n)
Space Complexity: O(1)
```

---

## Key Interview Pattern

When solving a maximum product problem with negative numbers:

```text
Track maximum + minimum
```

because:

```text
negative × minimum negative
        ↓
large positive
```

### Remember

```text
Maximum Product Subarray
        ↓
Track currentMax
        ↓
Track currentMin
        ↓
Consider num, max × num, min × num
        ↓
Update global maximum
```
