/*
Problem : Merge Overlapping Subintervals

Approach : Compare Every Interval with Remaining Intervals

Time Complexity : O(n²)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public List<List<Integer>> mergeOverlap(List<List<Integer>> intervals) {

        intervals.sort((a, b) -> a.get(0) - b.get(0));

        List<List<Integer>> answer = new ArrayList<>();

        boolean[] visited = new boolean[intervals.size()];

        for (int i = 0; i < intervals.size(); i++) {

            if (visited[i]) {
                continue;
            }

            int start = intervals.get(i).get(0);
            int end = intervals.get(i).get(1);

            for (int j = i + 1; j < intervals.size(); j++) {

                int nextStart = intervals.get(j).get(0);
                int nextEnd = intervals.get(j).get(1);

                if (nextStart <= end) {

                    end = Math.max(end, nextEnd);
                    visited[j] = true;

                } else {
                    break;
                }

            }

            answer.add(Arrays.asList(start, end));

        }

        return answer;

    }

}
