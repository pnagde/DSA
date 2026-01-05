import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dp {

    public static void main(String[] args) {
        var a = 9;
        System.out.println(a);
    }

    public static int lengthOfLongestSubstring(String s) {
        int maxLengthOfSubstring = 0;
        StringBuilder ans = new StringBuilder();
        int n = s.length();

        HashMap<Character, Integer> maps = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (!maps.containsKey(s.charAt(i))) {
                ans.append(s.charAt(i));
                maps.put(s.charAt(i), i);
            } else {
//                ans.append(s, i);
            }
            System.out.println(ans);
            maxLengthOfSubstring = Math.max(maxLengthOfSubstring, ans.length());
        }
        return maxLengthOfSubstring;
    }

    // 198. House Robber
    public static int rob(int[] nums) {
        int house1 = 0;
        int house2 = 0;
        for (int i = 0; i < nums.length; i = i + 2) {
            house1 += nums[i];
        }
        for (int i = 1; i < nums.length; i = i + 2) {
            house2 += nums[i];
        }
        return Math.max(house1, house2);
    }

    public static int trap(int[] height) {
        int water = 0;

        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];

        leftMax[0] = height[0];

        for (int i = 1; i < height.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        rightMax[height.length - 1] = height[height.length - 1];

        for (int i = height.length - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        for (int i = 0; i < height.length; i++) {
            // min of (left,right)
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return water;
    }

    public static int min(int[] height, int i) {
        int tallestLeft = -1;
        int tallestRight = -1;
        for (int j = 0; j < i; j++) {
            if (tallestLeft <= height[j]) {
                tallestLeft = height[j];
            }
        }
        for (int j = i; j < height.length; j++) {
            if (tallestRight <= height[j]) {
                tallestRight = height[j];
            }
        }
        if (tallestLeft < 0) tallestLeft = tallestRight;
        if (tallestRight < 0) tallestRight = tallestLeft;

        return Math.min(tallestRight, tallestLeft);
    }


    public static long fib(int n, long[] dp) {
        if (n <= 1L) {
            return n;
        }

        if (dp[n] != -1L) {
            return dp[n];
        }
        dp[n] = fib(n - 1, dp) + fib(n - 2, dp);
        return dp[n];
    }

    public static int fibTab(int n) {
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static int climbStairs(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        dp[1] = 2;
        return steps(n, dp);
    }

    public static int steps(int n, int[] dp) {
        if (n <= 2) {
            return n;
        }

        if (dp[n - 1] != -1) return dp[n];

        dp[n - 1] = steps(n - 1, dp) + steps(n - 2, dp);
        return dp[n - 1];
    }

    public static int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 1];
        Arrays.fill(dp, -1);
        return Math.min(costcalculate(cost, dp, 0), costcalculate(cost, dp, 1));
    }

    public static int costcalculate(int[] cost, int[] dp, int i) {
        if (i > cost.length) return 0;

        if (dp[i] != -1) return dp[i];

        dp[i] = cost[i] + Math.min(dp[i + 1], dp[i + 2]);

        return costcalculate(cost, dp, i + 1);
    }
}
