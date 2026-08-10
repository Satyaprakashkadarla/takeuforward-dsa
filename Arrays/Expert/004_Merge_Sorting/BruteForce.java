import java.util.Arrays;

class Solution {
public int[] sortArray(int[] nums) {
Arrays.sort(nums);
return nums;
}
}
or this specific problem, there isn't a meaningful hand-written O(N²) brute-force sorting algorithm that adds value. Arrays.sort() is included here only as a baseline/reference implementation. The required algorithm is Merge Sort.
