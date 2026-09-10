# Frog Jump

## Problem Statement
A frog wants to climb a staircase with `n` steps. You are given an integer array `heights`, where `heights[i]` represents the height of the $i^{th}$ step.

To jump from the $i^{th}$ step to the $j^{th}$ step, the frog spends `abs(heights[i] - heights[j])` energy. From step $i$, the frog can jump to either:
- The next step ($i + 1$)
- Two steps ahead ($i + 2$)

Return the **minimum total energy** required by the frog to go from the $0^{th}$ step to the $(n-1)^{th}$ step.

---

## Examples

### Example 1
- **Input:** `heights = [2, 1, 3, 5, 4]`
- **Output:** `2`
- **Explanation:**
  - $0^{th} \rightarrow 2^{nd}$ step: `abs(2 - 3) = 1`
  - $2^{nd} \rightarrow 4^{th}$ step: `abs(3 - 4) = 1`
  - **Total Cost:** `1 + 1 = 2`

### Example 2
- **Input:** `heights = [7, 5, 1, 2, 6]`
- **Output:** `9`
- **Explanation:**
  - $0^{th} \rightarrow 1^{st}$ step: `abs(7 - 5) = 2`
  - $1^{st} \rightarrow 3^{rd}$ step: `abs(5 - 2) = 3`
  - $3^{rd} \rightarrow 4^{th}$ step: `abs(2 - 6) = 4`
  - **Total Cost:** `2 + 3 + 4 = 9`

### Example 3
- **Input:** `heights = [3, 10, 3, 11, 3]`
- **Output:** `0`
- **Explanation:**
  - $0^{th} \rightarrow 2^{nd}$ step: `abs(3 - 3) = 0`
  - $2^{nd} \rightarrow 4^{th}$ step: `abs(3 - 3) = 0`
  - **Total Cost:** `0 + 0 = 0`

---

## Constraints
- $1 \le n \le 10^4$
- $0 \le heights[i] \le 10^4$

---

## Complexity Overview

| Approach | Time Complexity | Space Complexity |
| :--- | :--- | :--- |
| **Brute Force (Recursion)** | $\mathcal{O}(2^n)$ | $\mathcal{O}(n)$ |
| **Optimal (Space Optimized DP)** | $\mathcal{O}(n)$ | $\mathcal{O}(1)$ |
