/**
 * Problem: Longest Increasing Path in Matrix
 * Approach: Optimal - Topological Sort (Kahn's Algorithm, Level-by-Level BFS)
 *
 * Idea:
 *  - Model the matrix as a directed graph: each cell is a node, and
 *    there is an edge from a cell to each 4-directional neighbor
 *    with a STRICTLY LARGER value. Since values strictly increase
 *    along every edge, the graph can never contain a cycle, so it is
 *    a DAG (directed acyclic graph).
 *
 *  - The longest increasing path is then the LONGEST PATH in this
 *    DAG, measured in number of nodes.
 *
 *  - indegree[i][j] = number of neighbors with a strictly SMALLER
 *    value, i.e. the number of edges pointing INTO cell (i, j).
 *
 *  - Kahn's algorithm: start with all cells that have indegree 0
 *    (cells with no smaller neighbor, the "local minima"). These
 *    form layer 1. Process the queue one full layer at a time:
 *    every time a cell is removed, decrement the indegree of each
 *    larger neighbor; when a neighbor's indegree hits 0, all of the
 *    smaller cells that could precede it have been processed, so it
 *    joins the NEXT layer.
 *
 *  - Each BFS level corresponds to one more cell on a path, so the
 *    total number of levels processed equals the length of the
 *    longest increasing path.
 *
 *  - The queue is a plain int[n*m][2] array with front/rear
 *    pointers. Each cell is enqueued at most once, so n*m slots are
 *    always enough, and this avoids object overhead.
 *
 *  - No recursion is used, so there is no risk of StackOverflowError
 *    even on a 1000 x 1000 grid (unlike recursive DFS + memo).
 *
 * Time Complexity:  O(n * m) -> each cell is enqueued once, and each
 *                    cell looks at 4 neighbors a constant number of
 *                    times
 * Space Complexity: O(n * m) -> indegree grid plus the queue array
 */
class Solution {
    public int longIncPath(int[][] matrix, int n, int m) {
        int[][] indegree = new int[n][m];
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // Calculate indegree for edges from smaller to larger cells.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int d = 0; d < 4; d++) {
                    int x = i + dx[d];
                    int y = j + dy[d];

                    if (x >= 0 && x < n && y >= 0 && y < m
                            && matrix[x][y] < matrix[i][j]) {
                        indegree[i][j]++;
                    }
                }
            }
        }

        // Start with local minima (indegree = 0).
        int[][] queue = new int[n * m][2];
        int front = 0, rear = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (indegree[i][j] == 0) {
                    queue[rear][0] = i;
                    queue[rear++][1] = j;
                }
            }
        }

        int length = 0;

        // Each BFS level represents one element in the path.
        while (front < rear) {
            int size = rear - front;
            length++;

            while (size-- > 0) {
                int i = queue[front][0];
                int j = queue[front++][1];

                for (int d = 0; d < 4; d++) {
                    int x = i + dx[d];
                    int y = j + dy[d];

                    if (x >= 0 && x < n && y >= 0 && y < m
                            && matrix[x][y] > matrix[i][j]) {
                        indegree[x][y]--;

                        if (indegree[x][y] == 0) {
                            queue[rear][0] = x;
                            queue[rear++][1] = y;
                        }
                    }
                }
            }
        }

        return length;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] m1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(solution.longIncPath(m1, 3, 3));  // Expected: 5

        int[][] m2 = {{3, 4, 5}, {6, 2, 6}, {2, 2, 1}};
        System.out.println(solution.longIncPath(m2, 3, 3));  // Expected: 4

        int[][] m3 = {{1, 1}, {1, 1}};
        System.out.println(solution.longIncPath(m3, 2, 2));  // Expected: 1
    }
}
