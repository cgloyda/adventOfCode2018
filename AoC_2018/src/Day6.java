import java.util.HashMap;

public class Day6 {
	
	/**
	 * Solution for Advent of Code 2018 - Day 5 pt 1
	 * 
	 * @param input - String denoting polymer
	 * @return 		- int denoting length of polymer after reaction
	 */
	public static int solution1(String input) {
		
		int coordId = 1;
		int[][] grid = new int[500][500];
		String[] coordList = input.split("\n");
		HashMap<Integer, String> idAndCoordinate = new HashMap<Integer, String>();
		boolean isCoordinateEmpty = false;
		
		// mark coordinates on grid with corresponding id
		for (String coordinate : coordList) {
			
			idAndCoordinate.put(coordId, coordinate);
			
			String[] coord = coordinate.split(", ");
			
			// parse coordinates
			int xCoord = Integer.parseInt(coord[0]);
			int yCoord = Integer.parseInt(coord[1]);
			
			// mark coordId on grid, starting with 1
			grid[xCoord][yCoord] = coordId;
			
			// increment coordId for assigning to next coordinate
			coordId++;
		}
		
		// loop over points on map, calculate closest point
		for (int i = 0; i < grid.length; i++) {
			
			for (int j = 0; j < grid[i].length; j++) {
				
				int closestId = 0;
				int minDistance = Integer.MAX_VALUE;
				int currDistance = Integer.MAX_VALUE;
				isCoordinateEmpty = false;
				
				// do not modify points on the grid that are Id's
				if (grid[i][j] == 0) {
					isCoordinateEmpty = true;
				}
				
				for (int id = 1; id < coordId; id++) {
					
					if (isCoordinateEmpty) {
						
						// get coordinate from id
						String coordinates = idAndCoordinate.get(id);
						String[] coordString = coordinates.split(", ");
						
						// parse coordinates
						int xCoord = Integer.parseInt(coordString[0]);
						int yCoord = Integer.parseInt(coordString[1]);
						
						currDistance = manhattanDistance(i, j, xCoord, yCoord);
						
						// set minimum distance and corresponding closest id
						if (currDistance < minDistance) {
							minDistance = currDistance;
							closestId = id;
						}
						// if this point is equally close to two id's mark with -1
						else if (currDistance == minDistance) {
							closestId = -1;
						}
						
					}
					
				}
				// set the grid coordinates with the closest Id
				if (isCoordinateEmpty) {
					grid[i][j] = closestId;
				}
				
			}
			
		}
		
		// after marking the grid entirely, calculate areas for components that aren't infinite (don't touch border)
		boolean touchesBorder = false;
		int maxArea = 0;
		int idArea = 0;
		for (int id = 0; id < coordId; id++) {
			idArea = 0;
			touchesBorder = false;
			
			// determine whether id touches border
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if ((i == 0 || i == grid.length - 1 || j == 0 || j == grid[i].length - 1) && grid[i][j] == id) {
						touchesBorder = true;
					}
				}
			}
			
			if (!touchesBorder) {
				// determine whether id touches border
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[i].length; j++) {
						if (grid[i][j] == id) {
							idArea++;
						}
					}
				}
			}

			if (idArea > maxArea && !touchesBorder) {
				maxArea = idArea;
			}
			
		}
		
		return maxArea;
		
	}
	
	public static int manhattanDistance(int x1, int y1, int x2, int y2) {
		int diffX = x1 - x2;
		int diffY = y1 - y2;
				
		return Math.abs(diffX) + Math.abs(diffY);
		
	}

    public static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
        	for (int j = 0; j < grid[i].length; j++) {
        		
        		System.out.print(grid[i][j]);
        		System.out.print("\t");
        		
        	}
        	System.out.println();
        }
    }
	
	/**
	 * Solution for Advent of Code 2018 - Day 5 pt 2
	 * 
	 * @param input - String denoting polymer
	 * @return 		- int denoting polymer length after removing most problematic character
	 */
	public static int solution2(String input) {
		
		int coordId = 1;
		int[][] grid = new int[500][500];
		String[] coordList = input.split("\n");
		HashMap<Integer, String> idAndCoordinate = new HashMap<Integer, String>();
		int safeRegionArea = 0;
		int safeDistanceLimit = 10000;
		
		// mark coordinates on grid with corresponding id
		for (String coordinate : coordList) {
			
			idAndCoordinate.put(coordId, coordinate);
			
			String[] coord = coordinate.split(", ");
			
			// parse coordinates
			int xCoord = Integer.parseInt(coord[0]);
			int yCoord = Integer.parseInt(coord[1]);
			
			// mark coordId on grid, starting with 1
			grid[xCoord][yCoord] = coordId;
			
			// increment coordId for assigning to next coordinate
			coordId++;
		}	
		
		// loop over points on map, calculate closest point
		for (int i = 0; i < grid.length; i++) {
			
			for (int j = 0; j < grid[i].length; j++) {

				int distanceToCoordinates = 0;
				boolean isLocationSafe = true;
				
				for (int id = 1; id < coordId; id++) {
						
					// get coordinate from id
					String coordinates = idAndCoordinate.get(id);
					String[] coordString = coordinates.split(", ");
					
					// parse coordinates
					int xCoord = Integer.parseInt(coordString[0]);
					int yCoord = Integer.parseInt(coordString[1]);
					
					distanceToCoordinates += manhattanDistance(i, j, xCoord, yCoord);
					
					// set minimum distance and corresponding closest id
					if (distanceToCoordinates >= safeDistanceLimit) {
						isLocationSafe = false;
					}
					
				}
				// set the grid coordinates with the closest Id
				if (isLocationSafe) {
					safeRegionArea++;
				}
				
			}
			
		}
		
		return safeRegionArea;
			
	}

	public static void main(String[] args) {
		
		// test input
		String input = "278, 314\n" + 
				"282, 265\n" + 
				"252, 59\n" + 
				"62, 70\n" + 
				"192, 100\n" + 
				"299, 172\n" + 
				"310, 347\n" + 
				"283, 113\n" + 
				"342, 59\n" + 
				"293, 260\n" + 
				"288, 264\n" + 
				"341, 161\n" + 
				"238, 80\n" + 
				"212, 240\n" + 
				"53, 250\n" + 
				"335, 219\n" + 
				"217, 231\n" + 
				"123, 307\n" + 
				"40, 261\n" + 
				"340, 291\n" + 
				"176, 145\n" + 
				"323, 323\n" + 
				"164, 216\n" + 
				"288, 268\n" + 
				"103, 234\n" + 
				"84, 220\n" + 
				"279, 320\n" + 
				"289, 237\n" + 
				"43, 279\n" + 
				"221, 114\n" + 
				"230, 131\n" + 
				"53, 81\n" + 
				"148, 292\n" + 
				"85, 137\n" + 
				"73, 70\n" + 
				"119, 152\n" + 
				"335, 177\n" + 
				"353, 167\n" + 
				"57, 196\n" + 
				"111, 112\n" + 
				"256, 228\n" + 
				"53, 358\n" + 
				"220, 301\n" + 
				"345, 45\n" + 
				"93, 339\n" + 
				"152, 328\n" + 
				"252, 189\n" + 
				"347, 315\n" + 
				"326, 178\n" + 
				"213, 173";
		
		// exercise solution given test input
		long startTime = System.currentTimeMillis();
		int soln = solution2(input);
		long endTime = System.currentTimeMillis();

		// print solution and runtime
		long totalTime = endTime - startTime;
		System.out.println("Solution Output: " + soln);
		System.out.println("Solution Runtime: " + totalTime + " ms");
	}
}
