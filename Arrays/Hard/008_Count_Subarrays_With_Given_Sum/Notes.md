# Notes

## Problem Summary

Find the total number of contiguous subarrays whose sum is exactly `k`.

---

## Brute Force Approach

### Idea

Generate all possible subarrays.

For every starting index:

- Maintain a running sum.
- Extend the subarray one element at a time.
- If the sum equals `k`, increase the count.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

Every possible starting position is considered and extended, resulting in quadratic time.

---

# Optimal Approach

## Prefix Sum + HashMap

Maintain a running prefix sum:

```text
prefix = sum of elements from index 0 to current index
```

Suppose:

```text
currentPrefix = prefix
```

and an earlier prefix sum is:

```text
previousPrefix
```

Then:

```text
currentPrefix - previousPrefix = subarray sum
```

We need the subarray sum to equal `k`.

Therefore:

```text
currentPrefix - previousPrefix = k
```

Rearranging:

```text
previousPrefix = currentPrefix - k
```

So at every index, we check:

```text
prefix - k
```

in the HashMap.

---

## Why Do We Store Frequencies?

This problem asks for the **number of subarrays**, not the longest subarray.

Therefore, the same prefix sum may occur multiple times.

For example:

```text
prefix sum → frequency
```

If:

```text
prefix - k
```

has appeared 3 times before, then there are 3 different subarrays ending at the current index whose sum equals `k`.

Therefore:

```text
count += frequency(prefix - k)
```

---

## Why `map.put(0, 1)`?

We initialize:

```java
map.put(0, 1);
```

This represents the prefix sum before the array starts.

It is necessary for subarrays that begin at index `0`.

For example:

```text
nums = [1, 1]
k = 2
```

After processing both elements:

```text
prefix = 2
```

Then:

```text
prefix - k = 0
```

Because `0` already exists in the map, `[1, 1]` is correctly counted.

---

# Example Walkthrough

Consider:

```text
nums = [1, 1, 1]
k = 2
```

Initial:

```text
map = {0=1}
count = 0
prefix = 0
```

### Index 0

```text
prefix = 1
prefix - k = -1
```

`-1` does not exist.

Store:

```text
1 → 1
```

---

### Index 1

```text
prefix = 2
prefix - k = 0
```

`0` exists once.

Therefore:

```text
count = 1
```

Store:

```text
2 → 1
```

---

### Index 2

```text
prefix = 3
prefix - k = 1
```

`1` exists once.

Therefore:

```text
count = 2
```

Final answer:

```text
2
```

---

# Difference From Longest Subarray With Sum K

These two problems use a similar Prefix Sum technique but store different information.

### Longest Subarray With Sum K

Store:

```text
prefixSum → first index
```

Why?

Because we want the **maximum length**.

---

### Count Subarrays With Given Sum

Store:

```text
prefixSum → frequency
```

Why?

Because we want to count **all valid subarrays**.

---

# Edge Cases

- No valid subarray
- Entire array has sum `k`
- Multiple valid subarrays
- Negative numbers
- Zero values
- `k = 0`
- Repeated prefix sums
- Subarrays beginning at index `0`

---

# Complexity

| Approach | Time | Space |
|----------|------|-------|
| Brute Force | O(n²) | O(1) |
| Prefix Sum + HashMap | O(n) | O(n) |

---

# Key Concepts

- Prefix Sum
- HashMap
- Frequency Counting
- Running Sum
- Contiguous Subarray

---

# Interview Tip

Remember this equation:

```text
currentPrefix - previousPrefix = k
```

Therefore:

```text
previousPrefix = currentPrefix - k
```

At every index:

1. Update the prefix sum.
2. Check `prefix - k`.
3. Add its frequency to the answer.
4. Increase the frequency of the current prefix sum.

---

# Takeaway

The Prefix Sum + HashMap approach reduces the time complexity from O(n²) to O(n).

The important distinction is:

```text
Longest Subarray → store first index
Count Subarrays  → store frequency
```
