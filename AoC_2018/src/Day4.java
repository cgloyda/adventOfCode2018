
public class Day4 {
	
	/**
	 * Solution for Advent of Code 2018 - Day 4 pt 1
	 * 
	 * @param input - 
	 * @return 		- 
	 */
	public static int solution1(String input) {
		
		String[] lines = input.split("\n");
		int[][] claimArea = new int[1000][1000];
		
		for (String line : lines) {
		
		}
		
		return -1;
		
	}
	
	/**
	 * Solution for Advent of Code 2018 - Day 4 pt 2
	 * 
	 * @param input - 
	 * @return 		- 
	 */
	public static int solution2(String input) {
		
		String[] lines = input.split("\n");
		int[][] claimArea = new int[1000][1000];
		
		for (String line : lines) {
			
		}
		
		return -1;
			
	}

	public static void main(String[] args) {
		
		// test input
		String input = "<insert puzzle input here>";
		
		// exercise solution given test input
		long startTime = System.currentTimeMillis();
		int soln = solution1(input);
		// int soln = solution2(input);
		long endTime = System.currentTimeMillis();

		// print solution and runtime
		long totalTime = endTime - startTime;
		System.out.println("Solution Output: " + soln);
		System.out.println("Solution Runtime: " + totalTime + " ms");
	}
}
