# Notes

## Problem Summary

Find the total number of contiguous subarrays whose XOR is exactly `k`.

This problem is similar to:

**Count Subarrays With Given Sum**

but instead of using addition and prefix sums, we use the **XOR operation** and prefix XOR.

---

# Brute Force Approach

## Idea

Generate every possible subarray.

For each starting index:

- Initialize XOR to `0`.
- Extend the subarray to the right.
- XOR every new element.
- If the XOR becomes `k`, increase the count.

### Example

```text
nums = [4, 2, 2]
```

Starting from index `0`:

```text
4
4 ^ 2
4 ^ 2 ^ 2
```

Continue this for every possible starting index.

---

## Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

Every possible starting position is explored, resulting in quadratic time.

---

# Optimal Approach

## Prefix XOR + HashMap

Maintain a running XOR:

```text
xor = nums[0] ^ nums[1] ^ ... ^ nums[i]
```

Suppose:

```text
currentXor = XOR from index 0 to i
```

and:

```text
previousXor = XOR from index 0 to j
```

Then:

```text
currentXor ^ previousXor
```

gives the XOR of the subarray from:

```text
j + 1 to i
```

We need this XOR to equal `k`.

Therefore:

```text
currentXor ^ previousXor = k
```

Using the XOR property:

```text
a ^ b ^ b = a
```

we get:

```text
previousXor = currentXor ^ k
```

Therefore, at every index we check:

```text
currentXor ^ k
```

in the HashMap.

---

# Why Use a HashMap?

The HashMap stores:

```text
prefixXor → frequency
```

The frequency tells us how many previous prefix XORs can form a valid subarray ending at the current index.

If:

```text
map[currentXor ^ k] = 3
```

then there are exactly 3 subarrays ending at the current index whose XOR equals `k`.

Therefore:

```text
count += frequency
```

---

# Why `map.put(0, 1)`?

We initialize:

```java
map.put(0, 1);
```

This represents the prefix XOR before the array starts.

It is necessary to correctly count subarrays that begin at index `0`.

---

# Example Walkthrough

Consider:

```text
nums = [4, 2, 2, 6, 4]
k = 6
```

Initial:

```text
map = {0=1}
xor = 0
count = 0
```

### Element 4

```text
xor = 4
xor ^ k = 4 ^ 6 = 2
```

`2` is not present.

Store:

```text
4 → 1
```

---

### Element 2

```text
xor = 4 ^ 2 = 6
xor ^ k = 6 ^ 6 = 0
```

`0` exists once.

Therefore:

```text
count = 1
```

This corresponds to:

```text
[4, 2]
```

---

### Continue

As the array is processed, previously occurring prefix XOR values allow the algorithm to identify all valid subarrays.

Final answer:

```text
4
```

---

# Important XOR Properties

### Property 1

```text
a ^ a = 0
```

### Property 2

```text
a ^ 0 = a
```

### Property 3

```text
a ^ b ^ b = a
```

### Property 4

XOR is associative:

```text
(a ^ b) ^ c = a ^ (b ^ c)
```

These properties make Prefix XOR possible.

---

# Difference From Count Subarrays With Given Sum

### Sum Problem

Use:

```text
previousPrefix = currentPrefix - k
```

HashMap:

```text
prefixSum → frequency
```

---

### XOR Problem

Use:

```text
previousXor = currentXor ^ k
```

HashMap:

```text
prefixXor → frequency
```

---

# Edge Cases

- No valid subarray
- Entire array has XOR equal to `k`
- Multiple overlapping subarrays
- Repeated values
- Repeated prefix XOR values
- Single element equal to `k`
- `k = 0` when allowed

---

# Complexity

| Approach | Time | Space |
|----------|------|-------|
| Brute Force | O(n²) | O(1) |
| Prefix XOR + HashMap | O(n) | O(n) |

---

# Key Concepts

- Prefix XOR
- XOR Properties
- HashMap
- Frequency Counting
- Contiguous Subarrays
- Running XOR

---

# Interview Tip

Remember the key equation:

```text
currentXor ^ previousXor = k
```

Therefore:

```text
previousXor = currentXor ^ k
```

At every index:

1. Update the current XOR.
2. Calculate `currentXor ^ k`.
3. Add its frequency to the answer.
4. Store the frequency of the current XOR.

---

# Takeaway

The Prefix XOR + HashMap approach reduces the brute-force O(n²) solution to O(n).

The key idea is:

```text
Current Prefix XOR ^ Previous Prefix XOR = Subarray XOR
```

Therefore, finding a previous prefix XOR equal to:

```text
Current Prefix XOR ^ k
```

allows us to count every subarray whose XOR is exactly `k`.
