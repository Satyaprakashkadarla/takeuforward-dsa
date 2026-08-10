class Solution {

```
public int[] mergeSort(int[] nums) {
    if (nums == null || nums.length <= 1) {
        return nums;
    }

    int[] temp = new int[nums.length];

    mergeSortHelper(nums, temp, 0, nums.length - 1);

    return nums;
}

private void mergeSortHelper(
        int[] nums,
        int[] temp,
        int left,
        int right) {

    if (left >= right) {
        return;
    }

    int mid = left + (right - left) / 2;

    // Sort left half
    mergeSortHelper(nums, temp, left, mid);

    // Sort right half
    mergeSortHelper(nums, temp, mid + 1, right);

    // Merge sorted halves
    merge(nums, temp, left, mid, right);
}

private void merge(
        int[] nums,
        int[] temp,
        int left,
        int mid,
        int right) {

    int i = left;
    int j = mid + 1;
    int k = left;

    // Compare elements from both sorted halves
    while (i <= mid && j <= right) {

        if (nums[i] <= nums[j]) {
            temp[k++] = nums[i++];
        } else {
            temp[k++] = nums[j++];
        }
    }

    // Copy remaining elements from left half
    while (i <= mid) {
        temp[k++] = nums[i++];
    }

    // Copy remaining elements from right half
    while (j <= right) {
        temp[k++] = nums[j++];
    }

    // Copy sorted elements back to original array
    for (i = left; i <= right; i++) {
        nums[i] = temp[i];
    }
}
```

}
