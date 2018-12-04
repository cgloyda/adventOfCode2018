import java.util.HashSet;

public class Day1 {
	
	/**
	 * Solution for Advent of Code 2018 - Day 1
	 * 
	 * @param input - String denoting frequency changes
	 * @return 		- int denoting final frequency (pt1) or first duplicate frequency (pt2)
	 */
	public static int solution(String input) {
		
		String[] frequencyChangeArray = input.split("\n");				// frequency changes (input)
		HashSet<Integer> frequenciesReached = new HashSet<Integer>();	// frequencies resulting from changes
		boolean isFrequencyUnique = true;								// false when frequency reached twice
		int frequency = 0;												// beginning frequency
		
		String prefix = "";												// + or -
		String number = "";												// amount frequency should change
		
		// iterate over frequency change list until a duplicate frequency is found
		while (isFrequencyUnique) {
			for (String frequencyChange : frequencyChangeArray) {
				
				prefix = frequencyChange.substring(0, 1);
				number = frequencyChange.substring(1);
				
				if (prefix.equals("-")) {
					frequency -= Integer.parseInt(number);
				}
				else {
					frequency += Integer.parseInt(number);
				}
				
				// hashset doesn't allow duplicate keys, adding returns false
				isFrequencyUnique = frequenciesReached.add(frequency);
				
				if (!isFrequencyUnique) {
					return frequency;
				}
				
			}
		}
		
		return 0;
		
	}

	public static void main(String[] args) {
		
		// test input
		String input = "+7\n"
				+ "+7\n"
				+ "-2\n"
				+ "-7\n"
				+ "-4";
		
		// exercise solution given test input
		long startTime = System.currentTimeMillis();
		int soln = solution(input);
		long endTime   = System.currentTimeMillis();
		
		// print solution and runtime
		long totalTime = endTime - startTime;
		System.out.println("Solution Output: " + soln);
		System.out.println("Solution Runtime: " + totalTime + " ms");
	}

}
