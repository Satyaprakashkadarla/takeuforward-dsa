# Grid Unique Paths

## Problem Statement
There is a robot on an `m x n` grid. The robot is initially located at the top-left corner (`matrix[0][0]`) and tries to move to the bottom-right corner (`matrix[m-1][n-1]`). The robot can only move either **down** or **right** at any point in time.

Given the two integers `m` and `n`, return the number of **possible unique paths** that the robot can take to reach the bottom-right corner.

---

## Examples

### Example 1
- **Input:** `m = 3, n = 2`
- **Output:** `3`
- **Explanation:**
  - `right -> down -> down`
  - `down -> right -> down`
  - `down -> down -> right`

### Example 2
- **Input:** `m = 2, n = 4`
- **Output:** `4`
- **Explanation:**
  - `down -> right -> right -> right`
  - `right -> down -> right -> right`
  - `right -> right -> down -> right`
  - `right -> right -> right -> down`

### Example 3
- **Input:** `m = 3, n = 3`
- **Output:** `6`

---

## Constraints
- $1 \le m, n \le 100$
- The answer will be guaranteed to be $\le 2 \times 10^9$

---

## Complexity Overview

| Approach | Time Complexity | Space Complexity |
| :--- | :--- | :--- |
| **Brute Force (Recursion)** | $\mathcal{O}(2^{m+n})$ | $\mathcal{O}(m + n)$ |
| **DP (Tabulation)** | $\mathcal{O}(m \times n)$ | $\mathcal{O}(m \times n)$ |
| **Optimal (Combinatorics)** | $\mathcal{O}(\min(m, n))$ | $\mathcal{O}(1)$ |
