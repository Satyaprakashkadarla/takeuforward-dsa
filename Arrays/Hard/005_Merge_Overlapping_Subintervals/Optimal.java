/*
Problem : Merge Overlapping Subintervals

Approach : Sorting + Interval Merging

Time Complexity : O(n log n)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public List<List<Integer>> mergeOverlap(List<List<Integer>> intervals) {

        if (intervals == null || intervals.size() <= 1) {
            return intervals;
        }

        intervals.sort((a, b) -> Integer.compare(a.get(0), b.get(0)));

        List<List<Integer>> result = new ArrayList<>();

        int start = intervals.get(0).get(0);
        int end = intervals.get(0).get(1);

        for (int i = 1; i < intervals.size(); i++) {

            int currentStart = intervals.get(i).get(0);
            int currentEnd = intervals.get(i).get(1);

            if (currentStart <= end) {

                end = Math.max(end, currentEnd);

            } else {

                result.add(Arrays.asList(start, end));

                start = currentStart;
                end = currentEnd;

            }

        }

        result.add(Arrays.asList(start, end));

        return result;

    }

}
