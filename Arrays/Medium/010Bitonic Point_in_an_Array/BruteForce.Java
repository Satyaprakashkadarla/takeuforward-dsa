### 2. `brute.java`

```java
class Solution {

    public int findBitonicPoint(int[] arr) {
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        return max;
    }
}
```

### Complexity

* **Time:** `O(n)`
* **Space:** `O(1)`
