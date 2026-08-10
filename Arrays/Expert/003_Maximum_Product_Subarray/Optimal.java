class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxProduct = nums[0];
        int currentMax = nums[0];
        int currentMin = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];

            // Store previous values before updating
            int prevMax = currentMax;
            int prevMin = currentMin;

            // Three possibilities:
            // 1. Start a new subarray
            // 2. Extend previous maximum
            // 3. Extend previous minimum
            currentMax = Math.max(
                num,
                Math.max(prevMax * num, prevMin * num)
            );

            currentMin = Math.min(
                num,
                Math.min(prevMax * num, prevMin * num)
            );

            // Update global maximum
            maxProduct = Math.max(maxProduct, currentMax);
        }

        return maxProduct;
    }
}
