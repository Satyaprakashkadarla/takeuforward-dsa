### 3. `optimal.java`

```java
class Solution {

    public int findBitonicPoint(int[] arr) {
        int low = 0;
        int high = arr.length - 1;

        while (low < high) {

            int mid = low + (high - low) / 2;

            // Increasing side
            if (arr[mid] < arr[mid + 1]) {
                low = mid + 1;
            }
            // Decreasing side or peak
            else {
                high = mid;
            }
        }

        return arr[low];
    }
}
```

### Complexity

* **Time:** `O(log n)`
* **Space:** `O(1)`
