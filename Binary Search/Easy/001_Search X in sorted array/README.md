Search X in Sorted Array

Problem Statement

Given a sorted array of integers nums with 0-based indexing, find the index of a specified target integer.

If target exists in the array, return its index.

If target does not exist, return -1.

The array is sorted in ascending order.

Examples

Example 1

Input

nums = [-1, 0, 3, 5, 9, 12]
target = 9

Output

4

Explanation: 9 is present at index 4.

Example 2

Input

nums = [-1, 0, 3, 5, 9, 12]
target = 2

Output

-1

Explanation: 2 is not present in the array.

Example 3

Input

nums = [-1, 0, 3, 5, 9, 12]
target = -1

Output

0

Constraints

1 <= nums.length <= 10^5

-10^5 < nums[i], target < 10^5

nums is sorted in ascending order.

Approach

Because the array is sorted, we can use Binary Search instead of checking every element one by one.

Maintain two pointers:

left → beginning of the current search range

right → end of the current search range

Calculate the middle index:

mid = left + (right - left) / 2

Then compare nums[mid] with target:

If nums[mid] == target, return mid.

If nums[mid] < target, the target can only exist on the right side, so:

left = mid + 1

If nums[mid] > target, the target can only exist on the left side, so:

right = mid - 1

If left > right, the search space is empty and the target does not exist, so return -1.

Complexity

Brute Force

Time: O(n)

Space: O(1)

Binary Search

Time: O(log n)

Space: O(1)

Key Learning

This problem demonstrates the fundamental Binary Search pattern for searching in a sorted array.

The important condition is:

Whenever the search space is sorted and we can eliminate half of the elements after each comparison, Binary Search can reduce the time complexity from O(n) to O(log n).
