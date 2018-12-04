
public class Day2 {

	/**
	 * Solution for Advent of Code 2018 - Day 2 Pt 1
	 * 
	 * @param input - String denoting list of box Id's
	 * @return 		- int denoting number of box Id's having a letter appear exactly twice * letter exactly thrice
	 */
	public static int solution1(String input) {
		
		int countTwice = 0;							// number of boxes where a letter appears exactly twice
		int countThrice = 0; 						// number of boxes where a letter appears exactly thrice
		boolean twoLetterFound = false;				// used to avoid double-incrementing if > 1 letter appears twice
		boolean threeLetterFound = false;			// used to avoid double-incrementing if > 1 letter appears thrice
		
		String[] boxIds = input.split("\n");		// array of each box Id
		int[] letterCounts = new int[128];			// 128 ASCII characters possible
		
		// check every id in the lists
		for (String id : boxIds) {
			
			char[] lettersInId = id.toCharArray();
			
			// reset the letterCounts
			for (int i = 0; i < letterCounts.length; i++) {
				letterCounts[i] = 0;
			}
			
			// count each letter in this box ID
			for (char letter : lettersInId) {
				
				letterCounts[letter]++;
				
			}
			
			// if a letter appears two or three times, increment this count respectively
			for (int letterCount : letterCounts) {
				
				if (letterCount == 3 && threeLetterFound == false) {
					countThrice++;
					threeLetterFound = true;
				}
				else if (letterCount == 2 && twoLetterFound == false) {
					countTwice++;
					twoLetterFound = true;
				}
				
			}
			
			// reset boolean values to track a new boxId's letters
			twoLetterFound = false;
			threeLetterFound = false;
			
		}
		
		// checksum
		return countTwice * countThrice;
		
	}
	
	/**
	 * Solution for Advent of Code 2018 - Day 2 Pt 2
	 * 
	 * @param input - String denoting list of box Id's
	 * @return		- String denoting the similar characters within two box Id's that differ by one character
	 */
	public static String solution2(String input) {
		
		int differingLetterIndex = 0;					// index of the single differing letter between two box Id's
		boolean oneDiffering = false;					// used to ensure two boxId's have exactly one differing letter
		
		String[] boxIds = input.split("\n");
		
		// loop through all boxIds
		for (int i = 0; i < boxIds.length; i++) {
			
			char[] id1 = boxIds[i].toCharArray();
			
			for (int j = 0; j < boxIds.length; j++) {
				
				char[] id2 = boxIds[j].toCharArray();
				
				// assume boxIds are of equal length
				for (int k = 0; k < id1.length; k++) {
					
					// set oneDiffering to true if exactly one letter different
					if (oneDiffering == false && id1[k] != id2[k]) {
						oneDiffering = true;
						differingLetterIndex = k;
					}
					// if more than one letter different, boxes not a match
					else if (oneDiffering == true && id1[k] != id2[k]) {
						oneDiffering = false;
						break;
					}
					
				}
				
				// if box id1 and id2 differ by exactly one letter, return the string without that letter
				if (oneDiffering == true) {
					
					StringBuilder matchingId = new StringBuilder();
					
					for (int k = 0; k < id2.length; k++) {
						if (k != differingLetterIndex) {
							matchingId.append(id1[k]);
						}
					}
					
					return matchingId.toString();
					
				}
				
			}
			
		}
		
		// no id's that differ by exactly one letter were found
		return "No match found";
		
	}

	public static void main(String[] args) {
		
		// test input
		String input = "<insert puzzle input here>";
		
		// exercise solution given test input
		long startTime = System.currentTimeMillis();
		// int soln = solution1(input);
		String soln = solution2(input);
		long endTime   = System.currentTimeMillis();
		
		// print solution and runtime
		long totalTime = endTime - startTime;
		System.out.println("Solution Output: " + soln);
		System.out.println("Solution Runtime: " + totalTime + " ms");
	}

}
