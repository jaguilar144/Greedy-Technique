import java.util.*;

public class KnapsackSolver {

    static class Item {
        int number;
        int weight;
        int value;
        double ratio;

        Item(int number, int weight, int value) {
            this.number = number;
            this.weight = weight;
            this.value = value;
            this.ratio = (double) value / weight;
        }
    }

    public static void main(String[] args) {
        int capacity = 280;
        int[] weights = {20, 30, 40, 60, 70, 90};
        int[] values = {70, 80, 90, 110, 120, 200};
        int[] quantities = {1, 2, 1, 3, 1, 2};

        System.out.println("Original 0/1 Knapsack Problem");
        dynamicProgramming(weights, values, capacity);
        greedy(weights, values, capacity);

        System.out.println("\nBounded Knapsack Problem");
        boundedDynamicProgramming(weights, values, quantities, capacity);
        boundedGreedy(weights, values, quantities, capacity);
    }

    public static void dynamicProgramming(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        int steps = 0;

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                steps++;
                dp[i][w] = dp[i - 1][w];

                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                            values[i - 1] + dp[i - 1][w - weights[i - 1]]);
                }
            }
        }

        System.out.println("Dynamic Programming Maximum Value: " + dp[n][capacity]);
        System.out.println("DP Steps: " + steps);
        System.out.println("DP Complexity: O(nW)");
    }

    public static void greedy(int[] weights, int[] values, int capacity) {
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < weights.length; i++) {
            items.add(new Item(i + 1, weights[i], values[i]));
        }

        items.sort((a, b) -> Double.compare(b.ratio, a.ratio));

        int totalWeight = 0;
        int totalValue = 0;
        int steps = 0;

        System.out.print("Greedy Selected Items: ");

        for (Item item : items) {
            steps++;

            if (totalWeight + item.weight <= capacity) {
                totalWeight += item.weight;
                totalValue += item.value;
                System.out.print("Item " + item.number + " ");
            }
        }

        System.out.println();
        System.out.println("Greedy Total Weight: " + totalWeight);
        System.out.println("Greedy Maximum Value: " + totalValue);
        System.out.println("Greedy Steps: " + steps);
        System.out.println("Greedy Complexity: O(n log n)");
    }

    public static void boundedDynamicProgramming(int[] weights, int[] values, int[] quantities, int capacity) {
        ArrayList<Integer> expandedWeights = new ArrayList<>();
        ArrayList<Integer> expandedValues = new ArrayList<>();

        for (int i = 0; i < weights.length; i++) {
            for (int q = 0; q < quantities[i]; q++) {
                expandedWeights.add(weights[i]);
                expandedValues.add(values[i]);
            }
        }

        int n = expandedWeights.size();
        int[][] dp = new int[n + 1][capacity + 1];
        int steps = 0;

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                steps++;
                dp[i][w] = dp[i - 1][w];

                if (expandedWeights.get(i - 1) <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                            expandedValues.get(i - 1) + dp[i - 1][w - expandedWeights.get(i - 1)]);
                }
            }
        }

        System.out.println("Bounded DP Maximum Value: " + dp[n][capacity]);
        System.out.println("Bounded DP Steps: " + steps);
        System.out.println("Bounded DP Complexity: O(mW)");
    }

    public static void boundedGreedy(int[] weights, int[] values, int[] quantities, int capacity) {
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < weights.length; i++) {
            for (int q = 0; q < quantities[i]; q++) {
                items.add(new Item(i + 1, weights[i], values[i]));
            }
        }

        items.sort((a, b) -> Double.compare(b.ratio, a.ratio));

        int totalWeight = 0;
        int totalValue = 0;
        int steps = 0;

        System.out.print("Bounded Greedy Selected Items: ");

        for (Item item : items) {
            steps++;

            if (totalWeight + item.weight <= capacity) {
                totalWeight += item.weight;
                totalValue += item.value;
                System.out.print("Item " + item.number + " ");
            }
        }

        System.out.println();
        System.out.println("Bounded Greedy Total Weight: " + totalWeight);
        System.out.println("Bounded Greedy Maximum Value: " + totalValue);
        System.out.println("Bounded Greedy Steps: " + steps);
        System.out.println("Bounded Greedy Complexity: O(m log m)");
    }
}