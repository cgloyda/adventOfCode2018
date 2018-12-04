
public class Day3 {

	/**
	 * Solution for Advent of Code 2018 - Day 3 pt 1
	 * 
	 * @param input - String denoting fabric dimensions
	 * @return 		- int denoting number of square inches covered by two or more claims
	 */
	public static int solution1(String input) {
		
		String[] lines = input.split("\n");
		int[][] claimArea = new int[1000][1000];
		int countCoveredByTwo = 0;
		
		for (String line : lines) {
			
			// parse the necessary fabric dimensions
			int inchesFromLeft = Integer.parseInt(line.substring(line.indexOf("@ ") + 2, line.indexOf(",")).trim());
			int inchesFromTop = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(":")).trim());
			int inchesWide = Integer.parseInt(line.substring(line.indexOf(": ") + 2, line.indexOf("x")).trim());
			int inchesTall = Integer.parseInt(line.substring(line.indexOf("x") + 1).trim());
			
			
			// lay fabric id's down on the grid, increment count for every layer added to a square inch
			for (int i = 0; i < inchesWide; i++) {
				for (int j = 0; j < inchesTall; j++) {
					
					claimArea[inchesFromLeft + i][inchesFromTop + j]++;
					
				}
			}
			
		}
		
		// count the number of square inches covered by two or more claims
		for (int i = 0; i < claimArea.length; i++) {
			for (int j = 0; j < claimArea[i].length; j++) {
				
				if (claimArea[i][j] >= 2) {
					countCoveredByTwo++;
				}
				
			}
		}
		
		return countCoveredByTwo;
		
	}
	
	/**
	 * Solution for Advent of Code 2018 - Day 3 pt 2
	 * 
	 * @param input - String denoting fabric dimensions
	 * @return 		- int denoting the id of a claim not overlapping any other claim
	 */
	public static int solution2(String input) {
		
		String[] lines = input.split("\n");
		int[][] claimArea = new int[1000][1000];
		
		for (String line : lines) {
			
			// parse the necessary fabric dimensions
			int inchesFromLeft = Integer.parseInt(line.substring(line.indexOf("@ ") + 2, line.indexOf(",")).trim());
			int inchesFromTop = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(":")).trim());
			int inchesWide = Integer.parseInt(line.substring(line.indexOf(": ") + 2, line.indexOf("x")).trim());
			int inchesTall = Integer.parseInt(line.substring(line.indexOf("x") + 1).trim());
			
			
			// lay fabric id's down on the grid, increment count for every layer added
			for (int i = 0; i < inchesWide; i++) {
				for (int j = 0; j < inchesTall; j++) {
					
					claimArea[inchesFromLeft + i][inchesFromTop + j]++;
					
				}
			}
			
		}
		
		for (String line : lines) {
			boolean isNotOverlapping = true;
			int id = Integer.parseInt(line.substring(line.indexOf("#") + 1, line.indexOf("@")).trim());
			int inchesFromLeft = Integer.parseInt(line.substring(line.indexOf("@ ") + 2, line.indexOf(",")).trim());
			int inchesFromTop = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(":")).trim());
			int inchesWide = Integer.parseInt(line.substring(line.indexOf(": ") + 2, line.indexOf("x")).trim());
			int inchesTall = Integer.parseInt(line.substring(line.indexOf("x") + 1).trim());
			
			
			// determine whether this claim is unique (overlaps any other claims)
			for (int i = 0; i < inchesWide; i++) {
				for (int j = 0; j < inchesTall; j++) {
					
					 if (claimArea[inchesFromLeft + i][inchesFromTop + j] > 1) {
						 isNotOverlapping = false;
					 }
					
				}
			}
			
			// if claim is not overlapping any other claims, return its ID
			if (isNotOverlapping) {
				return id;
			}
			
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
