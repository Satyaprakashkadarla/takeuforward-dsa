# Frog Jump with K Distances

## Problem Statement

A frog wants to climb a staircase with `n` steps.

You are given:

- An integer array `heights`, where `heights[i]` represents the height of the `i-th` step.
- An integer `k`, representing the maximum number of steps the frog can jump.

The frog can jump from step `i` to any step in the range:

[i + 1, i + k]

provided that the destination step exists.

The energy required for a jump from step `i` to step `j` is:

```text
abs(heights[i] - heights[j])
