# Bitonic Point in an Array

## Difficulty

**Easy+ / Medium-**

## Pattern

**Binary Search on Bitonic / Mountain Array**

---

# 1. Problem Statement

Given a **bitonic array** `arr[]`, find the **bitonic point**.

A bitonic array is an array that:

1. Strictly increases.
2. Reaches a maximum element.
3. Strictly decreases.

The maximum element is called the **bitonic point**.

### Example

```text
[1, 3, 8, 12, 9, 5, 2]
```

The array follows:

```text
1 < 3 < 8 < 12 > 9 > 5 > 2
              ^
              |
       Bitonic Point
```

Answer:

```text
12
```

---

# 2. Examples

## Example 1

### Input

```text
[1, 3, 8, 12, 9, 5, 2]
```

### Output

```text
12
```

### Explanation

```text
1 < 3 < 8 < 12 > 9 > 5 > 2
              ^
```

The maximum element is `12`.

---

## Example 2

### Input

```text
[2, 4, 6, 10, 7, 3]
```

### Output

```text
10
```

---

## Example 3

### Input

```text
[1, 5, 9, 15, 20, 17, 10, 4]
```

### Output

```text
20
```

---

# 3. Constraints

Typical constraints:

```text
1 <= n <= 10^5
```

* The array is guaranteed to be bitonic.
* Elements are distinct.

---

# 4. Understanding the Pattern

A bitonic array looks like:

```text
                    Peak
                     /\
                    /  \
                   /    \
                  /      \
                 /        \
                /          \
               /            \
--------------/--------------\--------------
       Increasing              Decreasing
```

The array has two parts:

```text
Left of peak  -> Increasing
Right of peak -> Decreasing
```

The answer is the peak.

---

# 5. Brute Force Approach

## Idea

The bitonic point is simply the maximum element.

So we can traverse the entire array and keep track of the maximum.

### Algorithm

```text
1. Set max = arr[0].
2. Traverse the array.
3. If arr[i] > max:
       update max.
4. Return max.
```

### Code

```java
class Solution {

    public int findBitonicPoint(int[] arr) {

        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        return max;
    }
}
```

### Complexity

```text
Time  : O(n)
Space : O(1)
```

---

# 6. Why Brute Force Is Not Optimal

The array gives us additional information.

We know:

```text
Increasing → Peak → Decreasing
```

Therefore, we don't need to inspect every element.

We can use **Binary Search** to find the peak in:

```text
O(log n)
```

---

# 7. Optimal Approach - Binary Search

The key comparison is:

```java
arr[mid] < arr[mid + 1]
```

There are two cases.

---

# 8. Case 1 - Increasing Side

If:

```java
arr[mid] < arr[mid + 1]
```

we are still climbing toward the peak.

Example:

```text
[1, 3, 8, 12, 15, 11, 7]
       ^
      mid

8 < 12
```

Since the next element is larger:

```text
mid cannot be the peak
```

Therefore, the peak must be to the right.

```java
low = mid + 1;
```

### Rule

```text
arr[mid] < arr[mid + 1]
             ↓
        Peak is RIGHT
             ↓
       low = mid + 1
```

---

# 9. Case 2 - Decreasing Side or Peak

If:

```java
arr[mid] > arr[mid + 1]
```

we are either:

1. At the peak, or
2. On the decreasing side.

Example:

```text
[1, 3, 8, 12, 15, 11, 7]
                ^
               mid

15 > 11
```

The peak is at `mid` or somewhere to the left.

Therefore:

```java
high = mid;
```

### Rule

```text
arr[mid] > arr[mid + 1]
             ↓
      Peak is LEFT or MID
             ↓
          high = mid
```

---

# 10. Critical Point: `high = mid`

This is one of the most important things to remember.

Do **not** write:

```java
high = mid - 1;
```

Why?

Because `mid` itself can be the peak.

Example:

```text
[1, 3, 8, 12, 9, 5, 2]
             ^
            mid
```

Here:

```text
arr[mid] = 12
arr[mid + 1] = 9
```

Since:

```text
12 > 9
```

`mid` could be the answer.

Therefore:

```java
high = mid;
```

---

# 11. Why `low = mid + 1`?

If:

```java
arr[mid] < arr[mid + 1]
```

then:

```text
arr[mid + 1] > arr[mid]
```

Therefore `mid` cannot be the maximum.

So we can safely remove `mid`:

```java
low = mid + 1;
```

---

# 12. Optimal Algorithm

```text
low = 0
high = n - 1

while low < high:

    mid = low + (high - low) / 2

    if arr[mid] < arr[mid + 1]:

        // Increasing side
        low = mid + 1

    else:

        // Decreasing side or peak
        high = mid

return arr[low]
```

---

# 13. Optimal Java Code

```java
class Solution {

    public int findBitonicPoint(int[] arr) {

        int low = 0;
        int high = arr.length - 1;

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (arr[mid] < arr[mid + 1]) {
                // Increasing side
                low = mid + 1;
            } else {
                // Decreasing side or peak
                high = mid;
            }
        }

        return arr[low];
    }
}
```

---

# 14. Dry Run

Consider:

```text
arr = [1, 3, 8, 12, 9, 5, 2]
```

Indexes:

```text
Index :  0  1  2   3  4  5  6
Value :  1  3  8  12  9  5  2
```

Expected answer:

```text
12
```

---

## Iteration 1

```text
low = 0
high = 6
```

```text
mid = 0 + (6 - 0) / 2
mid = 3
```

Compare:

```text
arr[3] = 12
arr[4] = 9
```

```text
12 > 9
```

We are at the peak or on the decreasing side.

Therefore:

```text
high = 3
```

New range:

```text
[0 ... 3]
```

---

## Iteration 2

```text
low = 0
high = 3
```

```text
mid = 1
```

Compare:

```text
arr[1] = 3
arr[2] = 8
```

```text
3 < 8
```

We are on the increasing side.

Therefore:

```text
low = mid + 1
low = 2
```

New range:

```text
[2 ... 3]
```

---

## Iteration 3

```text
low = 2
high = 3
```

```text
mid = 2
```

Compare:

```text
arr[2] = 8
arr[3] = 12
```

```text
8 < 12
```

Move right:

```text
low = mid + 1
low = 3
```

Now:

```text
low = 3
high = 3
```

Loop ends.

Answer:

```text
arr[3] = 12
```

---

# 15. Why `while (low < high)`?

We want to find one final position.

Initially:

```text
low = 0
high = n - 1
```

Every iteration reduces the search space.

Eventually:

```text
low == high
```

Only one candidate remains.

That candidate must be the peak.

Therefore:

```java
while (low < high)
```

is the correct loop condition.

---

# 16. Correctness Proof

We maintain this invariant:

> The bitonic point is always inside `[low, high]`.

### Case 1

If:

```text
arr[mid] < arr[mid + 1]
```

the array is increasing at `mid`.

Therefore `mid` cannot be the peak.

The peak must be in:

```text
[mid + 1, high]
```

So:

```java
low = mid + 1;
```

The invariant remains true.

---

### Case 2

If:

```text
arr[mid] > arr[mid + 1]
```

the array is decreasing at `mid`, or `mid` itself is the peak.

Therefore, the peak must be in:

```text
[low, mid]
```

So:

```java
high = mid;
```

The invariant remains true.

---

### Termination

The loop stops when:

```text
low == high
```

There is only one possible index.

Because the invariant guarantees that the peak is inside the range, this index must be the bitonic point.

Therefore:

```java
return arr[low];
```

is correct.

---

# 17. Complexity

## Brute Force

```text
Time  : O(n)
Space : O(1)
```

## Binary Search

```text
Time  : O(log n)
Space : O(1)
```

### Comparison

| Approach      |       Time |  Space |
| ------------- | ---------: | -----: |
| Brute Force   |     `O(n)` | `O(1)` |
| Binary Search | `O(log n)` | `O(1)` |

The Binary Search solution is optimal.

---

# 18. Edge Cases

## Single Element

```text
[10]
```

Answer:

```text
10
```

The code works because:

```text
low = 0
high = 0
```

The loop doesn't execute.

---

## Peak at Beginning

If the problem allows a decreasing-only form:

```text
[10, 8, 6, 4, 2]
```

Answer:

```text
10
```

---

## Peak at End

If the problem allows an increasing-only form:

```text
[1, 3, 5, 7, 10]
```

Answer:

```text
10
```

---

# 19. Common Mistakes

## Mistake 1

Using:

```java
high = mid - 1;
```

### Why is it wrong?

Because `mid` can itself be the peak.

### Correct

```java
high = mid;
```

---

## Mistake 2

Using:

```java
low = mid;
```

### Why is it wrong?

It can cause an infinite loop.

### Correct

```java
low = mid + 1;
```

---

## Mistake 3

Using:

```java
int mid = (low + high) / 2;
```

Prefer:

```java
int mid = low + (high - low) / 2;
```

The second version avoids possible integer overflow.

---

## Mistake 4

Using:

```java
while (low <= high)
```

For this implementation, use:

```java
while (low < high)
```

because we want to reduce the range to one position.

---

# 20. Recognition Pattern

Whenever you see:

```text
First increasing
Then decreasing
Find maximum / peak
```

Think:

```text
BITONIC / MOUNTAIN ARRAY
```

Then think:

```text
BINARY SEARCH
```

---

# 21. Memory Trick

Remember:

```text
Still climbing?
      ↓
    RIGHT
      ↓
low = mid + 1
```

```text
Going down?
      ↓
LEFT or MID
      ↓
high = mid
```

### One-Line Formula

```text
mid < next → RIGHT
mid > next → LEFT, keep mid
```

---

# 22. Interview Template

When solving this problem in an interview, follow this structure.

## Step 1 - Clarify

Ask/confirm:

> Is the array guaranteed to be bitonic?

If yes, proceed with Binary Search.

---

## Step 2 - Explain Brute Force

Say:

> Since the bitonic point is the maximum element, I can scan the entire array and find the maximum in O(n) time and O(1) space.

---

## Step 3 - Identify Optimization

Say:

> However, the array has a special increasing-then-decreasing structure, so I can use Binary Search to find the peak in O(log n).

---

## Step 4 - Explain the Decision

Say:

> I compare `arr[mid]` with `arr[mid + 1]`. If `arr[mid] < arr[mid + 1]`, I am on the increasing side, so the peak is to the right. Otherwise, I am at the peak or on the decreasing side, so the peak is at `mid` or to the left.

---

## Step 5 - State Updates

```java
if (arr[mid] < arr[mid + 1]) {
    low = mid + 1;
} else {
    high = mid;
}
```

---

## Step 6 - Explain Termination

Say:

> When `low == high`, only one candidate remains, and that index is the bitonic point.
