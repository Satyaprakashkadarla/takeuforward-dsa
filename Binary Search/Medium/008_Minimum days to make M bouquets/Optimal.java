class Solution {
    public int roseGarden(int n, int[] nums, int k, int m) {
        // If total roses needed > available roses
        if (m * k > n) return -1;
        
        int left = 1, right = 0;
        
        // Find maximum day
        for (int day : nums) {
            right = Math.max(right, day);
        }
        
        int result = -1;
        
        // Binary search for minimum days
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canMakeBouquets(nums, k, m, mid)) {
                result = mid;
                right = mid - 1; // Try to find smaller days
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private boolean canMakeBouquets(int[] nums, int k, int m, int days) {
        int bouquets = 0;
        int consecutive = 0;
        
        for (int bloomDay : nums) {
            if (bloomDay <= days) {
                consecutive++;
                if (consecutive == k) {
                    bouquets++;
                    consecutive = 0;
                    if (bouquets >= m) {
                        return true;
                    }
                }
            } else {
                consecutive = 0;
            }
        }
        
        return bouquets >= m;
    }
}
