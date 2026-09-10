# Frog Jump with K Distances

## Problem Statement
A frog wants to climb a staircase with `n` steps. You are given an integer array `heights`, where `heights[i]` represents the height of the $i^{th}$ step, and an integer `k`.

To jump from the $i^{th}$ step to the $j^{th}$ step, the frog spends `abs(heights[i] - heights[j])` energy. From step $i$, the frog can jump to any step in the range $[i + 1, i + k]$, provided the target step exists.

Return the **minimum total energy** required by the frog to reach the $(n-1)^{th}$ step starting from the $0^{th}$ step.

---

## Examples

### Example 1
- **Input:** `heights = [10, 5, 20, 0, 15]`, `k = 2`
- **Output:** `15`
- **Explanation:**
  - $0^{th} \rightarrow 2^{nd}$ step: `abs(10 - 20) = 10`
  - $2^{nd} \rightarrow 4^{th}$ step: `abs(20 - 15) = 5`
  - **Total Cost:** `10 + 5 = 15`

### Example 2
- **Input:** `heights = [15, 4, 1, 14, 15]`, `k = 3`
- **Output:** `2`
- **Explanation:**
  - $0^{th} \rightarrow 3^{rd}$ step: `abs(15 - 14) = 1`
  - $3^{rd} \rightarrow 4^{th}$ step: `abs(14 - 15) = 1`
  - **Total Cost:** `1 + 1 = 2`

### Example 3
- **Input:** `heights = [15, 4, 1, 14, 15]`, `k = 4`
- **Output:** `2`
- **Explanation:**
  - $0^{th} \rightarrow 3^{rd}$ step: `abs(15 - 14) = 1`
  - $3^{rd} \rightarrow 4^{th}$ step: `abs(14 - 15) = 1`
  - **Total Cost:** `1 + 1 = 2`

---

## Constraints
- $1 \le n \le 10^4$
- $1 \le k \le 10$
- $0 \le heights[i] \le 10^4$

---

## Complexity Overview

| Approach | Time Complexity | Space Complexity |
| :--- | :--- | :--- |
| **Brute Force (Recursion)** | $\mathcal{O}(k^n)$ | $\mathcal{O}(n)$ (Call Stack) |
| **Optimal (Dynamic Programming - Tabulation)** | $\mathcal{O}(n \times k)$ | $\mathcal{O}(n)$ |
