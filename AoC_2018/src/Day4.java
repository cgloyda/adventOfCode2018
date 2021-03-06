import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class Day4 {
	
	/**
	 * Solution for Advent of Code 2018 - Day 4 pt 1
	 * 
	 * @param input - String denoting sleeping guard data
	 * @return 		- int denoting Id of guard who sleeps most multiplied by Minute he most frequently was asleep
	 */
	public static int solution1(String input) {
		
		String[] lines = input.split("\n");
		int[] sleepRecords = new int[10000];
		int[][] minutesAsleep = new int[10000][60];
		int id = -1;
		int timeSlept = 0;
		Date thisDate = new Date();
		Date sleepDate = new Date();
		Date wakeDate = new Date();
		ArrayList<Date> orderedList = new ArrayList<Date>();
		HashMap<Date, String> dateToString = new HashMap<Date, String>();
		
		// parse data, create date and message for each line
		for (String line : lines) {
			String date = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
			String[] temp = date.split("-");
			int year = Integer.parseInt(temp[0]);
			int month = Integer.parseInt(temp[1]);
			int day = Integer.parseInt(temp[2].substring(0, 2));
			int hours = Integer.parseInt(temp[2].substring(3, 5));
			int minutes = Integer.parseInt(date.substring(date.indexOf(":") + 1, date.indexOf(":") + 3));
			
			// date and message for this line
			thisDate = new Date(year, month, day, hours, minutes);
			String message = line.substring(line.indexOf("]") + 2, line.length());
			
			// add date to list, and map date to its corresponding message
			orderedList.add(thisDate);
			dateToString.put(thisDate, message);
			
		}
		
		// sort the dates in the list
		Collections.sort(orderedList);
		
		// based on each message, increment this guard's sleep time, and increment each minute they slept
		for (Date date : orderedList) {
			
			String message = dateToString.get(date);
			timeSlept = 0;
			
			// update with new guard
			if (message.contains("Guard")) {
				id = Integer.parseInt(message.substring(message.indexOf("#") + 1, message.indexOf("b")).trim());
			}
			// mark date when guard falls asleep
			else if (message.contains("falls")) {
				sleepDate = date;
			}
			// mark date when guard wakes up, increment total time slept, and increment each minute slept for this guard by one
			else if (message.contains("wakes")) {
				wakeDate = date;
				timeSlept = wakeDate.getMinutes() - sleepDate.getMinutes();
				for (int i = sleepDate.getMinutes(); i < wakeDate.getMinutes(); i++) {
					minutesAsleep[id][i]++;
				}
				sleepRecords[id] += timeSlept;
			}
			
		}
		
		int mostAsleepId = 0;
		// determine which guard was asleep most
		for (int i = 0; i < sleepRecords.length; i++) {
			if (sleepRecords[mostAsleepId] < sleepRecords[i]) {
				mostAsleepId = i;
			}
		}
		
		int sleepiestMinute = 0;
		// determine maximum sleeping minute
		for (int i = 0; i < minutesAsleep[mostAsleepId].length; i++) {
			if (minutesAsleep[mostAsleepId][sleepiestMinute] < minutesAsleep[mostAsleepId][i]) {
				sleepiestMinute = i;
			}
		}
		
		System.out.println("ID: " + mostAsleepId + "        Sleepiest Minute: " + sleepiestMinute);
		return mostAsleepId * sleepiestMinute;
		
	}
	
	/**
	 * Solution for Advent of Code 2018 - Day 4 pt 2
	 * 
	 * @param input - String denoting sleeping guard data
	 * @return 		- int denoting minute most slept by a single guard multiplied by their id
	 */
	public static int solution2(String input) {
		
		String[] lines = input.split("\n");
		int[] sleepRecords = new int[10000];
		int[][] minutesAsleep = new int[10000][60];
		int id = -1;
		int timeSlept = 0;
		Date thisDate = new Date();
		Date sleepDate = new Date();
		Date wakeDate = new Date();
		ArrayList<Date> orderedList = new ArrayList<Date>();
		HashMap<Date, String> dateToString = new HashMap<Date, String>();
		
		// parse data 
		for (String line : lines) {
			String date = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
			String[] temp = date.split("-");
			int year = Integer.parseInt(temp[0]);
			int month = Integer.parseInt(temp[1]);
			int day = Integer.parseInt(temp[2].substring(0, 2));
			int hours = Integer.parseInt(temp[2].substring(3, 5));
			int minutes = Integer.parseInt(date.substring(date.indexOf(":") + 1, date.indexOf(":") + 3));
			
			// date and message for this line
			thisDate = new Date(year, month, day, hours, minutes);
			String message = line.substring(line.indexOf("]") + 2, line.length());
			
			// add date to list, and map date to its message
			orderedList.add(thisDate);
			dateToString.put(thisDate, message);
			
		}
		
		// sort the dates in the list
		Collections.sort(orderedList);
		
		for (Date date : orderedList) {
			
			String message = dateToString.get(date);
			timeSlept = 0;
			
			// update with new guard
			if (message.contains("Guard")) {
				id = Integer.parseInt(message.substring(message.indexOf("#") + 1, message.indexOf("b")).trim());
			}
			// mark date when guard falls asleep
			else if (message.contains("falls")) {
				sleepDate = date;
			}
			// mark date when guard wakes up, increment total time slept, and increment each minute slept for this guard by one
			else if (message.contains("wakes")) {
				wakeDate = date;
				timeSlept = wakeDate.getMinutes() - sleepDate.getMinutes();
				for (int i = sleepDate.getMinutes(); i < wakeDate.getMinutes(); i++) {
					minutesAsleep[id][i]++;
				}
				sleepRecords[id] += timeSlept;
			}
			
		}
		
		
		int guardId = 0;
		int minute = 0;
		// loop through all guards
		for (int i = 0; i < minutesAsleep.length; i++) {
			// loop through the minutes this guard has slept (0-60) and their counts
			for (int j = 0; j < minutesAsleep[i].length; j++) {
				// update which guard is most frequently asleep on the same minute
				if (minutesAsleep[guardId][minute] < minutesAsleep[i][j]) {
					minute = j;
					guardId = i;
				}
			}
		}
		
		
		System.out.println("ID: " + guardId + " Most slept minute: " + minute);
		return minute * guardId;
			
	}

	public static void main(String[] args) {
		
		// test input
		String input = "[1518-07-31 00:54] wakes up\n" + 
				"[1518-04-09 00:01] Guard #3407 begins shift\n" + 
				"[1518-04-03 00:36] wakes up\n" + 
				"[1518-10-24 00:03] Guard #1049 begins shift\n" + 
				"[1518-03-15 00:11] falls asleep\n" + 
				"[1518-09-25 00:04] Guard #293 begins shift\n" + 
				"[1518-07-29 00:38] falls asleep\n" + 
				"[1518-06-05 00:37] wakes up\n" + 
				"[1518-10-10 23:59] Guard #3203 begins shift\n" + 
				"[1518-09-17 00:50] wakes up\n" + 
				"[1518-04-18 00:32] falls asleep\n" + 
				"[1518-05-19 00:39] falls asleep\n" + 
				"[1518-10-15 00:00] Guard #3119 begins shift\n" + 
				"[1518-03-22 00:43] falls asleep\n" + 
				"[1518-08-31 00:54] wakes up\n" + 
				"[1518-04-27 00:51] falls asleep\n" + 
				"[1518-11-11 00:24] falls asleep\n" + 
				"[1518-05-15 00:49] falls asleep\n" + 
				"[1518-07-15 00:58] wakes up\n" + 
				"[1518-06-29 00:43] wakes up\n" + 
				"[1518-06-26 00:39] wakes up\n" + 
				"[1518-04-12 00:53] wakes up\n" + 
				"[1518-02-21 00:02] Guard #613 begins shift\n" + 
				"[1518-06-28 00:46] falls asleep\n" + 
				"[1518-08-25 00:57] wakes up\n" + 
				"[1518-06-19 23:56] Guard #223 begins shift\n" + 
				"[1518-11-12 00:59] wakes up\n" + 
				"[1518-04-17 00:58] wakes up\n" + 
				"[1518-09-26 00:54] wakes up\n" + 
				"[1518-08-16 00:42] wakes up\n" + 
				"[1518-02-24 00:22] falls asleep\n" + 
				"[1518-06-07 23:56] Guard #293 begins shift\n" + 
				"[1518-07-12 23:56] Guard #1567 begins shift\n" + 
				"[1518-09-04 00:25] falls asleep\n" + 
				"[1518-06-26 23:58] Guard #1747 begins shift\n" + 
				"[1518-04-04 23:59] Guard #613 begins shift\n" + 
				"[1518-07-23 00:52] wakes up\n" + 
				"[1518-09-17 00:56] wakes up\n" + 
				"[1518-04-20 00:51] falls asleep\n" + 
				"[1518-04-21 00:00] falls asleep\n" + 
				"[1518-08-23 23:56] Guard #887 begins shift\n" + 
				"[1518-02-10 00:54] wakes up\n" + 
				"[1518-08-15 00:48] falls asleep\n" + 
				"[1518-03-13 00:51] wakes up\n" + 
				"[1518-04-21 00:43] falls asleep\n" + 
				"[1518-04-24 00:54] falls asleep\n" + 
				"[1518-05-22 00:04] Guard #2341 begins shift\n" + 
				"[1518-11-15 00:56] wakes up\n" + 
				"[1518-05-07 00:57] wakes up\n" + 
				"[1518-09-14 00:38] wakes up\n" + 
				"[1518-04-17 23:59] Guard #431 begins shift\n" + 
				"[1518-08-27 00:02] falls asleep\n" + 
				"[1518-07-12 00:04] falls asleep\n" + 
				"[1518-03-13 00:57] falls asleep\n" + 
				"[1518-04-22 00:03] Guard #2357 begins shift\n" + 
				"[1518-05-22 23:59] Guard #887 begins shift\n" + 
				"[1518-06-08 00:10] falls asleep\n" + 
				"[1518-10-27 00:21] falls asleep\n" + 
				"[1518-07-30 00:55] wakes up\n" + 
				"[1518-07-04 00:03] Guard #1031 begins shift\n" + 
				"[1518-02-18 00:01] Guard #3407 begins shift\n" + 
				"[1518-08-13 00:53] wakes up\n" + 
				"[1518-10-19 00:18] wakes up\n" + 
				"[1518-08-16 00:29] wakes up\n" + 
				"[1518-02-24 23:59] Guard #1747 begins shift\n" + 
				"[1518-02-15 00:41] falls asleep\n" + 
				"[1518-03-15 00:56] wakes up\n" + 
				"[1518-04-16 00:56] wakes up\n" + 
				"[1518-05-18 23:48] Guard #541 begins shift\n" + 
				"[1518-08-20 00:58] wakes up\n" + 
				"[1518-11-14 00:48] wakes up\n" + 
				"[1518-08-20 00:36] falls asleep\n" + 
				"[1518-04-29 23:56] Guard #613 begins shift\n" + 
				"[1518-08-03 00:27] wakes up\n" + 
				"[1518-02-26 00:38] wakes up\n" + 
				"[1518-03-03 00:35] falls asleep\n" + 
				"[1518-06-04 00:59] wakes up\n" + 
				"[1518-02-18 23:49] Guard #3203 begins shift\n" + 
				"[1518-05-15 00:56] wakes up\n" + 
				"[1518-02-19 00:17] wakes up\n" + 
				"[1518-09-17 00:04] Guard #1049 begins shift\n" + 
				"[1518-05-21 00:56] wakes up\n" + 
				"[1518-08-19 00:39] wakes up\n" + 
				"[1518-02-22 00:17] falls asleep\n" + 
				"[1518-02-27 00:32] wakes up\n" + 
				"[1518-04-30 00:43] falls asleep\n" + 
				"[1518-03-22 00:11] falls asleep\n" + 
				"[1518-10-09 00:11] falls asleep\n" + 
				"[1518-09-12 00:25] falls asleep\n" + 
				"[1518-03-16 00:21] wakes up\n" + 
				"[1518-08-15 00:52] wakes up\n" + 
				"[1518-07-19 00:44] wakes up\n" + 
				"[1518-03-02 00:07] falls asleep\n" + 
				"[1518-04-28 00:35] wakes up\n" + 
				"[1518-10-13 00:17] falls asleep\n" + 
				"[1518-08-08 00:33] falls asleep\n" + 
				"[1518-10-01 23:56] Guard #1031 begins shift\n" + 
				"[1518-06-21 00:51] wakes up\n" + 
				"[1518-08-08 00:03] falls asleep\n" + 
				"[1518-05-03 00:30] falls asleep\n" + 
				"[1518-10-08 00:59] wakes up\n" + 
				"[1518-07-27 00:25] wakes up\n" + 
				"[1518-11-07 00:48] wakes up\n" + 
				"[1518-05-25 00:45] wakes up\n" + 
				"[1518-05-27 00:34] falls asleep\n" + 
				"[1518-08-10 00:31] falls asleep\n" + 
				"[1518-08-08 00:48] wakes up\n" + 
				"[1518-11-01 00:34] falls asleep\n" + 
				"[1518-07-19 00:38] falls asleep\n" + 
				"[1518-06-02 00:06] falls asleep\n" + 
				"[1518-10-15 00:29] wakes up\n" + 
				"[1518-08-22 00:18] falls asleep\n" + 
				"[1518-08-27 00:56] wakes up\n" + 
				"[1518-10-05 00:48] falls asleep\n" + 
				"[1518-05-19 00:13] wakes up\n" + 
				"[1518-05-21 00:55] falls asleep\n" + 
				"[1518-03-26 00:41] wakes up\n" + 
				"[1518-10-25 00:49] wakes up\n" + 
				"[1518-05-23 00:51] wakes up\n" + 
				"[1518-03-25 00:21] falls asleep\n" + 
				"[1518-10-14 00:21] wakes up\n" + 
				"[1518-06-01 00:44] falls asleep\n" + 
				"[1518-02-27 00:39] falls asleep\n" + 
				"[1518-07-22 00:57] wakes up\n" + 
				"[1518-09-07 23:50] Guard #503 begins shift\n" + 
				"[1518-08-01 23:49] Guard #503 begins shift\n" + 
				"[1518-09-11 00:02] Guard #3119 begins shift\n" + 
				"[1518-07-20 00:27] falls asleep\n" + 
				"[1518-09-09 00:47] wakes up\n" + 
				"[1518-10-10 00:01] Guard #431 begins shift\n" + 
				"[1518-05-08 23:56] Guard #541 begins shift\n" + 
				"[1518-08-28 00:33] falls asleep\n" + 
				"[1518-11-01 00:51] wakes up\n" + 
				"[1518-05-19 00:04] falls asleep\n" + 
				"[1518-04-20 23:50] Guard #503 begins shift\n" + 
				"[1518-07-05 00:55] wakes up\n" + 
				"[1518-06-18 00:22] falls asleep\n" + 
				"[1518-07-24 00:41] wakes up\n" + 
				"[1518-07-15 00:13] falls asleep\n" + 
				"[1518-05-06 00:46] falls asleep\n" + 
				"[1518-06-26 00:47] wakes up\n" + 
				"[1518-06-25 00:47] wakes up\n" + 
				"[1518-05-16 00:47] wakes up\n" + 
				"[1518-05-14 00:18] wakes up\n" + 
				"[1518-11-12 00:33] falls asleep\n" + 
				"[1518-06-29 23:57] Guard #3137 begins shift\n" + 
				"[1518-04-05 00:55] wakes up\n" + 
				"[1518-03-11 00:44] wakes up\n" + 
				"[1518-04-29 00:47] wakes up\n" + 
				"[1518-09-03 00:03] wakes up\n" + 
				"[1518-07-18 23:56] Guard #3203 begins shift\n" + 
				"[1518-06-21 00:43] falls asleep\n" + 
				"[1518-06-14 00:13] falls asleep\n" + 
				"[1518-10-08 00:37] falls asleep\n" + 
				"[1518-11-07 00:33] falls asleep\n" + 
				"[1518-02-25 23:56] Guard #3119 begins shift\n" + 
				"[1518-10-18 00:09] falls asleep\n" + 
				"[1518-05-23 00:33] falls asleep\n" + 
				"[1518-08-11 00:44] wakes up\n" + 
				"[1518-05-27 00:50] wakes up\n" + 
				"[1518-10-27 00:00] Guard #2803 begins shift\n" + 
				"[1518-06-19 00:00] Guard #1567 begins shift\n" + 
				"[1518-08-02 23:48] Guard #1049 begins shift\n" + 
				"[1518-02-18 00:50] wakes up\n" + 
				"[1518-05-22 00:50] wakes up\n" + 
				"[1518-10-15 00:38] falls asleep\n" + 
				"[1518-08-02 00:36] falls asleep\n" + 
				"[1518-09-22 00:00] Guard #3119 begins shift\n" + 
				"[1518-05-16 00:51] falls asleep\n" + 
				"[1518-05-13 00:03] Guard #1049 begins shift\n" + 
				"[1518-03-25 00:45] falls asleep\n" + 
				"[1518-04-18 00:57] wakes up\n" + 
				"[1518-05-27 00:42] falls asleep\n" + 
				"[1518-04-07 00:49] wakes up\n" + 
				"[1518-07-22 00:01] Guard #223 begins shift\n" + 
				"[1518-05-31 00:56] wakes up\n" + 
				"[1518-09-28 00:03] Guard #1061 begins shift\n" + 
				"[1518-08-13 00:03] Guard #1567 begins shift\n" + 
				"[1518-06-06 00:01] Guard #3137 begins shift\n" + 
				"[1518-08-15 00:25] wakes up\n" + 
				"[1518-11-20 00:24] falls asleep\n" + 
				"[1518-09-26 00:17] falls asleep\n" + 
				"[1518-02-14 00:02] falls asleep\n" + 
				"[1518-11-17 00:24] falls asleep\n" + 
				"[1518-08-24 00:28] wakes up\n" + 
				"[1518-06-27 00:53] wakes up\n" + 
				"[1518-02-13 00:04] Guard #2803 begins shift\n" + 
				"[1518-07-13 00:36] falls asleep\n" + 
				"[1518-07-10 00:16] falls asleep\n" + 
				"[1518-02-16 00:00] Guard #1747 begins shift\n" + 
				"[1518-10-06 00:53] wakes up\n" + 
				"[1518-02-12 00:42] falls asleep\n" + 
				"[1518-03-31 00:48] falls asleep\n" + 
				"[1518-03-28 00:54] wakes up\n" + 
				"[1518-09-05 23:58] Guard #887 begins shift\n" + 
				"[1518-03-29 00:18] falls asleep\n" + 
				"[1518-03-09 23:57] Guard #503 begins shift\n" + 
				"[1518-04-10 00:59] wakes up\n" + 
				"[1518-09-14 00:24] falls asleep\n" + 
				"[1518-06-25 23:49] Guard #293 begins shift\n" + 
				"[1518-06-10 23:56] Guard #1487 begins shift\n" + 
				"[1518-09-23 00:42] falls asleep\n" + 
				"[1518-10-28 00:57] wakes up\n" + 
				"[1518-11-06 00:11] falls asleep\n" + 
				"[1518-05-02 00:03] falls asleep\n" + 
				"[1518-03-19 00:14] wakes up\n" + 
				"[1518-06-29 00:53] falls asleep\n" + 
				"[1518-04-14 00:54] wakes up\n" + 
				"[1518-10-04 00:52] wakes up\n" + 
				"[1518-05-14 00:59] wakes up\n" + 
				"[1518-05-13 00:40] falls asleep\n" + 
				"[1518-04-06 00:46] falls asleep\n" + 
				"[1518-09-08 00:29] wakes up\n" + 
				"[1518-10-30 00:40] falls asleep\n" + 
				"[1518-03-03 00:59] wakes up\n" + 
				"[1518-05-15 00:03] Guard #2341 begins shift\n" + 
				"[1518-06-10 00:00] falls asleep\n" + 
				"[1518-05-14 00:34] falls asleep\n" + 
				"[1518-03-22 00:04] Guard #1567 begins shift\n" + 
				"[1518-03-27 00:00] falls asleep\n" + 
				"[1518-04-07 00:19] falls asleep\n" + 
				"[1518-08-22 00:36] wakes up\n" + 
				"[1518-11-16 00:51] wakes up\n" + 
				"[1518-08-26 00:45] falls asleep\n" + 
				"[1518-09-04 00:05] falls asleep\n" + 
				"[1518-07-01 00:00] falls asleep\n" + 
				"[1518-08-02 00:53] falls asleep\n" + 
				"[1518-05-16 00:05] falls asleep\n" + 
				"[1518-09-27 00:25] wakes up\n" + 
				"[1518-09-19 00:58] wakes up\n" + 
				"[1518-11-21 00:41] falls asleep\n" + 
				"[1518-03-21 00:59] wakes up\n" + 
				"[1518-02-16 00:21] falls asleep\n" + 
				"[1518-03-21 00:47] falls asleep\n" + 
				"[1518-02-25 00:57] wakes up\n" + 
				"[1518-04-11 00:00] falls asleep\n" + 
				"[1518-05-28 00:31] wakes up\n" + 
				"[1518-07-18 00:21] wakes up\n" + 
				"[1518-03-20 00:15] wakes up\n" + 
				"[1518-09-26 00:01] falls asleep\n" + 
				"[1518-11-14 00:33] falls asleep\n" + 
				"[1518-08-02 00:29] wakes up\n" + 
				"[1518-10-07 00:59] wakes up\n" + 
				"[1518-05-19 00:55] wakes up\n" + 
				"[1518-04-14 00:10] falls asleep\n" + 
				"[1518-10-12 00:56] wakes up\n" + 
				"[1518-07-31 23:56] Guard #2803 begins shift\n" + 
				"[1518-04-03 23:51] Guard #1049 begins shift\n" + 
				"[1518-06-30 00:13] falls asleep\n" + 
				"[1518-11-02 00:03] falls asleep\n" + 
				"[1518-07-03 00:01] Guard #2003 begins shift\n" + 
				"[1518-06-24 00:43] falls asleep\n" + 
				"[1518-10-29 00:46] wakes up\n" + 
				"[1518-08-30 23:46] Guard #223 begins shift\n" + 
				"[1518-03-24 00:22] wakes up\n" + 
				"[1518-10-12 00:46] falls asleep\n" + 
				"[1518-06-23 23:56] Guard #1747 begins shift\n" + 
				"[1518-08-27 00:24] wakes up\n" + 
				"[1518-11-09 00:45] wakes up\n" + 
				"[1518-05-29 23:57] Guard #887 begins shift\n" + 
				"[1518-11-18 23:52] Guard #3119 begins shift\n" + 
				"[1518-09-25 00:13] falls asleep\n" + 
				"[1518-10-30 00:02] Guard #223 begins shift\n" + 
				"[1518-03-28 00:34] wakes up\n" + 
				"[1518-09-16 00:43] wakes up\n" + 
				"[1518-07-30 00:34] falls asleep\n" + 
				"[1518-09-13 00:35] falls asleep\n" + 
				"[1518-08-06 00:00] Guard #2341 begins shift\n" + 
				"[1518-02-10 00:30] falls asleep\n" + 
				"[1518-07-09 00:56] wakes up\n" + 
				"[1518-02-25 00:55] falls asleep\n" + 
				"[1518-10-24 00:54] wakes up\n" + 
				"[1518-08-10 00:57] wakes up\n" + 
				"[1518-07-12 00:05] wakes up\n" + 
				"[1518-07-02 00:01] Guard #3407 begins shift\n" + 
				"[1518-03-13 00:03] Guard #613 begins shift\n" + 
				"[1518-11-15 00:21] wakes up\n" + 
				"[1518-03-10 00:54] falls asleep\n" + 
				"[1518-05-09 00:17] falls asleep\n" + 
				"[1518-11-08 00:59] wakes up\n" + 
				"[1518-09-04 23:56] Guard #887 begins shift\n" + 
				"[1518-09-10 00:39] wakes up\n" + 
				"[1518-05-14 00:06] falls asleep\n" + 
				"[1518-02-12 00:34] wakes up\n" + 
				"[1518-09-20 00:36] falls asleep\n" + 
				"[1518-04-23 00:46] wakes up\n" + 
				"[1518-08-16 00:04] falls asleep\n" + 
				"[1518-03-08 00:03] Guard #3137 begins shift\n" + 
				"[1518-08-31 00:37] falls asleep\n" + 
				"[1518-08-07 00:35] falls asleep\n" + 
				"[1518-06-26 00:50] falls asleep\n" + 
				"[1518-04-05 23:57] Guard #2803 begins shift\n" + 
				"[1518-09-14 00:02] Guard #541 begins shift\n" + 
				"[1518-06-25 00:04] falls asleep\n" + 
				"[1518-03-29 00:00] Guard #2003 begins shift\n" + 
				"[1518-02-28 23:46] Guard #1487 begins shift\n" + 
				"[1518-04-25 00:01] Guard #541 begins shift\n" + 
				"[1518-02-26 00:41] falls asleep\n" + 
				"[1518-06-06 00:21] falls asleep\n" + 
				"[1518-02-22 23:52] Guard #1031 begins shift\n" + 
				"[1518-07-28 00:36] wakes up\n" + 
				"[1518-05-29 00:26] falls asleep\n" + 
				"[1518-10-31 00:39] falls asleep\n" + 
				"[1518-09-13 00:41] wakes up\n" + 
				"[1518-11-05 00:06] falls asleep\n" + 
				"[1518-10-26 00:56] wakes up\n" + 
				"[1518-07-06 00:57] wakes up\n" + 
				"[1518-07-11 00:27] falls asleep\n" + 
				"[1518-10-18 00:00] Guard #3407 begins shift\n" + 
				"[1518-09-05 00:46] wakes up\n" + 
				"[1518-10-06 00:25] falls asleep\n" + 
				"[1518-06-21 00:29] wakes up\n" + 
				"[1518-09-05 00:49] falls asleep\n" + 
				"[1518-09-30 23:57] Guard #431 begins shift\n" + 
				"[1518-04-25 23:56] Guard #293 begins shift\n" + 
				"[1518-03-23 00:53] wakes up\n" + 
				"[1518-05-10 23:56] Guard #3407 begins shift\n" + 
				"[1518-06-03 00:36] wakes up\n" + 
				"[1518-03-04 00:58] wakes up\n" + 
				"[1518-07-27 00:33] falls asleep\n" + 
				"[1518-04-22 23:54] Guard #223 begins shift\n" + 
				"[1518-08-06 00:35] falls asleep\n" + 
				"[1518-07-10 00:04] Guard #1487 begins shift\n" + 
				"[1518-07-27 23:51] Guard #3011 begins shift\n" + 
				"[1518-02-11 00:16] wakes up\n" + 
				"[1518-09-11 00:28] falls asleep\n" + 
				"[1518-08-19 00:03] Guard #3119 begins shift\n" + 
				"[1518-05-10 00:40] falls asleep\n" + 
				"[1518-09-20 00:04] Guard #613 begins shift\n" + 
				"[1518-10-02 00:37] falls asleep\n" + 
				"[1518-05-10 00:04] Guard #431 begins shift\n" + 
				"[1518-06-11 00:54] wakes up\n" + 
				"[1518-05-07 00:28] falls asleep\n" + 
				"[1518-10-09 00:50] falls asleep\n" + 
				"[1518-07-17 00:39] wakes up\n" + 
				"[1518-11-23 00:39] wakes up\n" + 
				"[1518-05-28 00:48] falls asleep\n" + 
				"[1518-10-18 00:49] falls asleep\n" + 
				"[1518-11-04 00:04] falls asleep\n" + 
				"[1518-03-30 00:31] falls asleep\n" + 
				"[1518-02-10 23:56] Guard #2003 begins shift\n" + 
				"[1518-08-03 23:57] Guard #2357 begins shift\n" + 
				"[1518-03-10 23:59] Guard #431 begins shift\n" + 
				"[1518-03-20 00:02] falls asleep\n" + 
				"[1518-03-25 00:41] wakes up\n" + 
				"[1518-09-16 00:26] falls asleep\n" + 
				"[1518-10-27 00:13] falls asleep\n" + 
				"[1518-03-19 00:13] falls asleep\n" + 
				"[1518-02-19 23:58] Guard #3407 begins shift\n" + 
				"[1518-05-01 00:36] falls asleep\n" + 
				"[1518-05-25 23:57] Guard #887 begins shift\n" + 
				"[1518-08-09 00:14] falls asleep\n" + 
				"[1518-07-06 00:01] Guard #2803 begins shift\n" + 
				"[1518-08-15 23:50] Guard #1567 begins shift\n" + 
				"[1518-02-22 00:45] wakes up\n" + 
				"[1518-07-20 00:45] wakes up\n" + 
				"[1518-04-14 00:04] Guard #1487 begins shift\n" + 
				"[1518-08-16 00:54] falls asleep\n" + 
				"[1518-07-02 00:24] falls asleep\n" + 
				"[1518-03-03 00:21] falls asleep\n" + 
				"[1518-11-22 00:15] falls asleep\n" + 
				"[1518-10-13 00:46] wakes up\n" + 
				"[1518-03-31 23:57] Guard #541 begins shift\n" + 
				"[1518-07-10 00:49] wakes up\n" + 
				"[1518-02-12 00:11] falls asleep\n" + 
				"[1518-06-01 00:26] wakes up\n" + 
				"[1518-04-09 00:17] falls asleep\n" + 
				"[1518-07-21 00:45] wakes up\n" + 
				"[1518-08-09 00:03] Guard #503 begins shift\n" + 
				"[1518-10-14 00:04] Guard #503 begins shift\n" + 
				"[1518-11-15 00:00] falls asleep\n" + 
				"[1518-06-11 23:53] Guard #3119 begins shift\n" + 
				"[1518-10-16 23:47] Guard #223 begins shift\n" + 
				"[1518-03-22 00:54] wakes up\n" + 
				"[1518-07-10 23:58] Guard #613 begins shift\n" + 
				"[1518-05-31 00:03] Guard #2341 begins shift\n" + 
				"[1518-09-30 00:04] Guard #1049 begins shift\n" + 
				"[1518-02-10 00:03] Guard #3119 begins shift\n" + 
				"[1518-05-14 00:38] wakes up\n" + 
				"[1518-11-18 00:58] wakes up\n" + 
				"[1518-10-04 00:40] falls asleep\n" + 
				"[1518-09-13 00:03] Guard #3137 begins shift\n" + 
				"[1518-02-24 00:00] Guard #223 begins shift\n" + 
				"[1518-10-13 00:23] wakes up\n" + 
				"[1518-05-20 00:18] falls asleep\n" + 
				"[1518-10-01 00:46] falls asleep\n" + 
				"[1518-07-09 00:00] Guard #3011 begins shift\n" + 
				"[1518-11-16 23:56] Guard #613 begins shift\n" + 
				"[1518-05-29 00:50] wakes up\n" + 
				"[1518-09-24 00:50] wakes up\n" + 
				"[1518-11-06 00:56] wakes up\n" + 
				"[1518-06-03 00:51] falls asleep\n" + 
				"[1518-09-22 00:48] wakes up\n" + 
				"[1518-04-17 00:04] Guard #1487 begins shift\n" + 
				"[1518-07-24 00:58] wakes up\n" + 
				"[1518-04-28 00:28] falls asleep\n" + 
				"[1518-04-26 00:59] wakes up\n" + 
				"[1518-04-11 23:50] Guard #1049 begins shift\n" + 
				"[1518-07-04 00:53] falls asleep\n" + 
				"[1518-03-18 00:52] wakes up\n" + 
				"[1518-05-26 00:43] falls asleep\n" + 
				"[1518-06-23 00:56] falls asleep\n" + 
				"[1518-11-23 00:19] falls asleep\n" + 
				"[1518-06-25 00:25] wakes up\n" + 
				"[1518-11-12 00:49] falls asleep\n" + 
				"[1518-08-18 00:52] wakes up\n" + 
				"[1518-06-23 00:16] falls asleep\n" + 
				"[1518-05-03 00:20] wakes up\n" + 
				"[1518-06-26 00:00] falls asleep\n" + 
				"[1518-11-16 00:40] falls asleep\n" + 
				"[1518-11-09 00:04] Guard #1049 begins shift\n" + 
				"[1518-04-06 00:41] wakes up\n" + 
				"[1518-08-14 00:03] falls asleep\n" + 
				"[1518-11-13 00:11] falls asleep\n" + 
				"[1518-03-05 00:32] falls asleep\n" + 
				"[1518-03-08 00:15] falls asleep\n" + 
				"[1518-05-02 23:57] Guard #431 begins shift\n" + 
				"[1518-11-15 00:38] wakes up\n" + 
				"[1518-05-25 00:09] falls asleep\n" + 
				"[1518-03-07 00:38] wakes up\n" + 
				"[1518-11-13 00:46] wakes up\n" + 
				"[1518-07-05 00:36] wakes up\n" + 
				"[1518-08-23 00:54] wakes up\n" + 
				"[1518-06-22 00:31] falls asleep\n" + 
				"[1518-06-24 23:49] Guard #3137 begins shift\n" + 
				"[1518-10-18 23:56] Guard #2003 begins shift\n" + 
				"[1518-07-08 00:00] Guard #1031 begins shift\n" + 
				"[1518-09-24 00:00] falls asleep\n" + 
				"[1518-07-30 00:30] wakes up\n" + 
				"[1518-04-05 00:31] falls asleep\n" + 
				"[1518-10-16 00:17] falls asleep\n" + 
				"[1518-08-08 00:20] falls asleep\n" + 
				"[1518-03-01 00:56] wakes up\n" + 
				"[1518-06-27 00:26] falls asleep\n" + 
				"[1518-04-27 00:00] Guard #293 begins shift\n" + 
				"[1518-09-26 00:10] wakes up\n" + 
				"[1518-03-19 00:28] falls asleep\n" + 
				"[1518-10-28 00:37] falls asleep\n" + 
				"[1518-03-19 00:58] wakes up\n" + 
				"[1518-07-24 00:52] falls asleep\n" + 
				"[1518-10-02 00:31] wakes up\n" + 
				"[1518-07-25 00:00] Guard #503 begins shift\n" + 
				"[1518-06-13 00:41] wakes up\n" + 
				"[1518-07-05 00:50] falls asleep\n" + 
				"[1518-04-27 00:48] wakes up\n" + 
				"[1518-05-27 23:58] Guard #503 begins shift\n" + 
				"[1518-05-29 00:01] Guard #1049 begins shift\n" + 
				"[1518-03-12 00:52] wakes up\n" + 
				"[1518-10-13 00:41] falls asleep\n" + 
				"[1518-07-16 00:03] Guard #541 begins shift\n" + 
				"[1518-07-08 00:50] wakes up\n" + 
				"[1518-11-17 00:57] wakes up\n" + 
				"[1518-04-19 00:00] Guard #3407 begins shift\n" + 
				"[1518-04-19 00:37] wakes up\n" + 
				"[1518-11-14 23:51] Guard #3119 begins shift\n" + 
				"[1518-08-09 23:57] Guard #3011 begins shift\n" + 
				"[1518-07-25 00:31] falls asleep\n" + 
				"[1518-06-01 00:19] falls asleep\n" + 
				"[1518-06-15 23:50] Guard #223 begins shift\n" + 
				"[1518-04-21 00:55] wakes up\n" + 
				"[1518-10-04 00:00] Guard #613 begins shift\n" + 
				"[1518-10-25 23:57] Guard #1049 begins shift\n" + 
				"[1518-04-24 00:13] falls asleep\n" + 
				"[1518-08-30 00:50] wakes up\n" + 
				"[1518-05-19 23:59] Guard #3407 begins shift\n" + 
				"[1518-09-02 00:01] Guard #613 begins shift\n" + 
				"[1518-06-06 23:56] Guard #3119 begins shift\n" + 
				"[1518-11-18 00:36] falls asleep\n" + 
				"[1518-07-18 00:05] falls asleep\n" + 
				"[1518-10-09 00:33] wakes up\n" + 
				"[1518-10-03 00:20] falls asleep\n" + 
				"[1518-02-17 00:33] wakes up\n" + 
				"[1518-04-21 00:32] wakes up\n" + 
				"[1518-04-29 00:42] falls asleep\n" + 
				"[1518-09-17 00:49] falls asleep\n" + 
				"[1518-08-22 00:02] Guard #293 begins shift\n" + 
				"[1518-06-11 00:12] falls asleep\n" + 
				"[1518-07-14 00:55] wakes up\n" + 
				"[1518-05-17 00:09] wakes up\n" + 
				"[1518-08-07 00:57] wakes up\n" + 
				"[1518-04-26 00:49] wakes up\n" + 
				"[1518-11-10 23:59] Guard #1487 begins shift\n" + 
				"[1518-07-07 00:59] wakes up\n" + 
				"[1518-07-29 23:59] Guard #503 begins shift\n" + 
				"[1518-03-14 00:41] falls asleep\n" + 
				"[1518-03-26 23:50] Guard #1567 begins shift\n" + 
				"[1518-06-03 00:53] wakes up\n" + 
				"[1518-11-02 00:28] wakes up\n" + 
				"[1518-09-15 00:29] wakes up\n" + 
				"[1518-11-18 00:00] Guard #3203 begins shift\n" + 
				"[1518-10-05 00:03] falls asleep\n" + 
				"[1518-09-11 00:51] falls asleep\n" + 
				"[1518-09-06 00:53] wakes up\n" + 
				"[1518-03-15 23:58] Guard #3011 begins shift\n" + 
				"[1518-07-01 00:59] wakes up\n" + 
				"[1518-07-14 00:47] falls asleep\n" + 
				"[1518-10-07 00:02] falls asleep\n" + 
				"[1518-02-25 00:50] wakes up\n" + 
				"[1518-06-27 00:51] falls asleep\n" + 
				"[1518-07-02 00:11] falls asleep\n" + 
				"[1518-03-28 00:42] falls asleep\n" + 
				"[1518-07-26 00:06] falls asleep\n" + 
				"[1518-04-11 00:28] falls asleep\n" + 
				"[1518-06-26 00:54] wakes up\n" + 
				"[1518-06-12 00:56] falls asleep\n" + 
				"[1518-09-18 00:44] falls asleep\n" + 
				"[1518-08-31 00:23] wakes up\n" + 
				"[1518-03-12 00:55] falls asleep\n" + 
				"[1518-06-27 00:40] wakes up\n" + 
				"[1518-04-23 00:56] falls asleep\n" + 
				"[1518-11-20 23:46] Guard #2341 begins shift\n" + 
				"[1518-07-29 00:35] wakes up\n" + 
				"[1518-05-18 00:34] falls asleep\n" + 
				"[1518-07-23 00:18] falls asleep\n" + 
				"[1518-06-01 00:46] wakes up\n" + 
				"[1518-08-28 23:57] Guard #2341 begins shift\n" + 
				"[1518-08-04 23:52] Guard #1487 begins shift\n" + 
				"[1518-02-27 00:29] falls asleep\n" + 
				"[1518-04-10 23:54] Guard #3011 begins shift\n" + 
				"[1518-06-22 00:02] Guard #223 begins shift\n" + 
				"[1518-06-05 00:12] wakes up\n" + 
				"[1518-10-05 00:43] wakes up\n" + 
				"[1518-08-09 00:31] wakes up\n" + 
				"[1518-07-12 00:40] wakes up\n" + 
				"[1518-11-15 00:24] falls asleep\n" + 
				"[1518-04-24 00:48] wakes up\n" + 
				"[1518-02-18 00:37] falls asleep\n" + 
				"[1518-10-11 00:28] falls asleep\n" + 
				"[1518-11-10 00:01] Guard #3011 begins shift\n" + 
				"[1518-07-13 00:41] wakes up\n" + 
				"[1518-04-17 00:08] falls asleep\n" + 
				"[1518-03-20 00:51] falls asleep\n" + 
				"[1518-07-26 00:30] wakes up\n" + 
				"[1518-02-11 00:12] falls asleep\n" + 
				"[1518-06-25 00:28] falls asleep\n" + 
				"[1518-03-24 00:12] falls asleep\n" + 
				"[1518-09-05 00:10] falls asleep\n" + 
				"[1518-07-20 00:04] Guard #223 begins shift\n" + 
				"[1518-03-26 00:33] falls asleep\n" + 
				"[1518-06-18 00:59] wakes up\n" + 
				"[1518-10-06 00:00] Guard #1049 begins shift\n" + 
				"[1518-09-09 23:51] Guard #2803 begins shift\n" + 
				"[1518-07-22 00:08] falls asleep\n" + 
				"[1518-06-05 00:06] falls asleep\n" + 
				"[1518-04-20 00:11] falls asleep\n" + 
				"[1518-08-06 00:56] wakes up\n" + 
				"[1518-10-31 00:10] falls asleep\n" + 
				"[1518-03-07 00:29] wakes up\n" + 
				"[1518-04-02 00:21] falls asleep\n" + 
				"[1518-05-24 00:55] wakes up\n" + 
				"[1518-04-06 00:54] wakes up\n" + 
				"[1518-11-19 00:59] wakes up\n" + 
				"[1518-02-22 00:01] Guard #1567 begins shift\n" + 
				"[1518-04-13 00:00] Guard #613 begins shift\n" + 
				"[1518-03-19 23:54] Guard #1031 begins shift\n" + 
				"[1518-09-11 00:08] falls asleep\n" + 
				"[1518-09-21 00:52] wakes up\n" + 
				"[1518-04-08 00:14] falls asleep\n" + 
				"[1518-07-21 00:49] falls asleep\n" + 
				"[1518-10-31 00:19] falls asleep\n" + 
				"[1518-10-14 00:35] wakes up\n" + 
				"[1518-06-03 00:04] Guard #1487 begins shift\n" + 
				"[1518-05-22 00:48] falls asleep\n" + 
				"[1518-08-08 00:21] wakes up\n" + 
				"[1518-06-22 00:40] wakes up\n" + 
				"[1518-08-03 00:36] falls asleep\n" + 
				"[1518-09-01 00:42] wakes up\n" + 
				"[1518-03-01 00:05] falls asleep\n" + 
				"[1518-07-02 00:18] wakes up\n" + 
				"[1518-07-18 00:36] falls asleep\n" + 
				"[1518-06-23 00:04] Guard #293 begins shift\n" + 
				"[1518-10-03 00:29] wakes up\n" + 
				"[1518-09-21 00:41] falls asleep\n" + 
				"[1518-09-19 00:29] falls asleep\n" + 
				"[1518-06-02 00:00] Guard #1049 begins shift\n" + 
				"[1518-04-28 23:58] Guard #613 begins shift\n" + 
				"[1518-11-05 00:56] wakes up\n" + 
				"[1518-06-09 00:52] wakes up\n" + 
				"[1518-11-20 00:25] wakes up\n" + 
				"[1518-05-11 00:14] falls asleep\n" + 
				"[1518-09-02 23:52] Guard #1031 begins shift\n" + 
				"[1518-02-26 00:44] wakes up\n" + 
				"[1518-04-28 00:01] Guard #1487 begins shift\n" + 
				"[1518-03-04 00:04] falls asleep\n" + 
				"[1518-04-20 00:52] wakes up\n" + 
				"[1518-05-24 00:45] falls asleep\n" + 
				"[1518-05-15 23:48] Guard #2803 begins shift\n" + 
				"[1518-07-16 23:58] Guard #2803 begins shift\n" + 
				"[1518-03-14 00:56] wakes up\n" + 
				"[1518-10-14 00:07] falls asleep\n" + 
				"[1518-07-13 00:58] wakes up\n" + 
				"[1518-06-23 00:35] wakes up\n" + 
				"[1518-10-15 00:16] falls asleep\n" + 
				"[1518-09-25 00:29] wakes up\n" + 
				"[1518-10-21 00:12] falls asleep\n" + 
				"[1518-07-27 00:47] wakes up\n" + 
				"[1518-04-30 00:48] wakes up\n" + 
				"[1518-05-15 00:52] wakes up\n" + 
				"[1518-11-06 00:01] Guard #1049 begins shift\n" + 
				"[1518-02-16 00:51] wakes up\n" + 
				"[1518-04-16 00:27] falls asleep\n" + 
				"[1518-05-12 00:00] Guard #3119 begins shift\n" + 
				"[1518-11-08 00:48] falls asleep\n" + 
				"[1518-08-29 00:57] wakes up\n" + 
				"[1518-04-15 00:24] falls asleep\n" + 
				"[1518-05-22 00:34] wakes up\n" + 
				"[1518-07-21 00:53] wakes up\n" + 
				"[1518-05-30 00:57] wakes up\n" + 
				"[1518-04-14 23:56] Guard #3407 begins shift\n" + 
				"[1518-07-08 00:10] falls asleep\n" + 
				"[1518-08-30 00:27] falls asleep\n" + 
				"[1518-08-09 00:39] falls asleep\n" + 
				"[1518-06-19 00:46] falls asleep\n" + 
				"[1518-08-14 23:56] Guard #887 begins shift\n" + 
				"[1518-11-20 00:58] wakes up\n" + 
				"[1518-08-06 23:56] Guard #3011 begins shift\n" + 
				"[1518-05-31 00:42] wakes up\n" + 
				"[1518-09-11 00:15] wakes up\n" + 
				"[1518-03-10 00:57] wakes up\n" + 
				"[1518-09-03 23:51] Guard #431 begins shift\n" + 
				"[1518-07-13 00:47] falls asleep\n" + 
				"[1518-10-25 00:14] falls asleep\n" + 
				"[1518-07-12 00:08] falls asleep\n" + 
				"[1518-08-28 00:02] Guard #3119 begins shift\n" + 
				"[1518-09-22 00:27] falls asleep\n" + 
				"[1518-04-13 00:49] wakes up\n" + 
				"[1518-06-26 00:35] falls asleep\n" + 
				"[1518-05-27 00:55] falls asleep\n" + 
				"[1518-03-07 00:54] wakes up\n" + 
				"[1518-11-15 00:52] falls asleep\n" + 
				"[1518-03-12 00:56] wakes up\n" + 
				"[1518-11-09 00:28] falls asleep\n" + 
				"[1518-06-04 00:37] falls asleep\n" + 
				"[1518-08-02 00:01] falls asleep\n" + 
				"[1518-07-31 00:45] falls asleep\n" + 
				"[1518-08-27 00:54] falls asleep\n" + 
				"[1518-05-16 00:30] wakes up\n" + 
				"[1518-08-14 00:43] wakes up\n" + 
				"[1518-05-30 00:51] falls asleep\n" + 
				"[1518-08-31 00:03] falls asleep\n" + 
				"[1518-06-09 23:53] Guard #293 begins shift\n" + 
				"[1518-06-19 00:47] wakes up\n" + 
				"[1518-11-15 23:57] Guard #223 begins shift\n" + 
				"[1518-06-20 00:58] wakes up\n" + 
				"[1518-10-11 23:56] Guard #503 begins shift\n" + 
				"[1518-05-26 00:50] wakes up\n" + 
				"[1518-05-26 23:57] Guard #887 begins shift\n" + 
				"[1518-06-06 00:43] falls asleep\n" + 
				"[1518-06-03 00:31] falls asleep\n" + 
				"[1518-09-04 00:21] wakes up\n" + 
				"[1518-03-25 00:58] wakes up\n" + 
				"[1518-06-21 00:19] falls asleep\n" + 
				"[1518-02-14 00:42] falls asleep\n" + 
				"[1518-11-13 00:04] Guard #3407 begins shift\n" + 
				"[1518-03-24 00:35] falls asleep\n" + 
				"[1518-06-14 00:52] wakes up\n" + 
				"[1518-08-16 00:58] wakes up\n" + 
				"[1518-07-30 23:59] Guard #431 begins shift\n" + 
				"[1518-03-30 00:00] Guard #223 begins shift\n" + 
				"[1518-06-23 00:57] wakes up\n" + 
				"[1518-11-03 23:53] Guard #3137 begins shift\n" + 
				"[1518-03-15 00:55] falls asleep\n" + 
				"[1518-03-09 00:33] wakes up\n" + 
				"[1518-11-07 00:00] Guard #3011 begins shift\n" + 
				"[1518-03-06 00:26] falls asleep\n" + 
				"[1518-10-19 00:10] falls asleep\n" + 
				"[1518-11-08 00:04] Guard #1567 begins shift\n" + 
				"[1518-09-06 00:13] falls asleep\n" + 
				"[1518-05-07 00:00] Guard #541 begins shift\n" + 
				"[1518-05-13 00:56] wakes up\n" + 
				"[1518-08-30 00:46] falls asleep\n" + 
				"[1518-07-28 23:58] Guard #1747 begins shift\n" + 
				"[1518-06-28 00:02] Guard #541 begins shift\n" + 
				"[1518-11-10 00:31] falls asleep\n" + 
				"[1518-06-06 00:47] wakes up\n" + 
				"[1518-08-02 00:44] wakes up\n" + 
				"[1518-03-23 00:07] falls asleep\n" + 
				"[1518-06-08 00:27] wakes up\n" + 
				"[1518-10-22 00:59] wakes up\n" + 
				"[1518-08-24 00:20] falls asleep\n" + 
				"[1518-09-22 00:59] wakes up\n" + 
				"[1518-05-01 00:29] wakes up\n" + 
				"[1518-10-12 00:39] wakes up\n" + 
				"[1518-03-02 00:48] wakes up\n" + 
				"[1518-02-14 00:31] wakes up\n" + 
				"[1518-07-08 00:42] falls asleep\n" + 
				"[1518-04-25 00:22] wakes up\n" + 
				"[1518-08-08 00:53] wakes up\n" + 
				"[1518-06-30 00:43] wakes up\n" + 
				"[1518-08-07 23:46] Guard #3203 begins shift\n" + 
				"[1518-10-31 00:36] wakes up\n" + 
				"[1518-08-09 00:41] wakes up\n" + 
				"[1518-06-20 00:55] falls asleep\n" + 
				"[1518-09-03 00:52] wakes up\n" + 
				"[1518-05-28 00:38] wakes up\n" + 
				"[1518-08-23 00:00] Guard #1031 begins shift\n" + 
				"[1518-05-27 00:56] wakes up\n" + 
				"[1518-06-12 00:05] falls asleep\n" + 
				"[1518-06-17 23:58] Guard #887 begins shift\n" + 
				"[1518-07-04 23:57] Guard #1031 begins shift\n" + 
				"[1518-02-21 00:25] falls asleep\n" + 
				"[1518-08-29 00:54] falls asleep\n" + 
				"[1518-08-19 00:54] wakes up\n" + 
				"[1518-09-15 00:04] Guard #1049 begins shift\n" + 
				"[1518-03-22 00:21] wakes up\n" + 
				"[1518-09-18 00:51] wakes up\n" + 
				"[1518-03-23 23:58] Guard #431 begins shift\n" + 
				"[1518-10-10 00:25] falls asleep\n" + 
				"[1518-02-12 00:44] wakes up\n" + 
				"[1518-02-15 00:12] falls asleep\n" + 
				"[1518-11-03 00:53] wakes up\n" + 
				"[1518-09-06 00:16] wakes up\n" + 
				"[1518-11-11 00:57] wakes up\n" + 
				"[1518-02-10 00:11] falls asleep\n" + 
				"[1518-06-02 00:40] wakes up\n" + 
				"[1518-08-28 00:17] falls asleep\n" + 
				"[1518-07-14 00:36] wakes up\n" + 
				"[1518-06-01 00:02] Guard #3203 begins shift\n" + 
				"[1518-05-05 00:03] Guard #1567 begins shift\n" + 
				"[1518-05-28 00:54] wakes up\n" + 
				"[1518-10-15 00:48] wakes up\n" + 
				"[1518-07-05 00:27] falls asleep\n" + 
				"[1518-06-04 00:00] Guard #541 begins shift\n" + 
				"[1518-07-14 00:06] falls asleep\n" + 
				"[1518-09-11 00:53] wakes up\n" + 
				"[1518-11-04 23:56] Guard #503 begins shift\n" + 
				"[1518-03-13 00:46] falls asleep\n" + 
				"[1518-07-08 00:37] wakes up\n" + 
				"[1518-07-24 00:28] falls asleep\n" + 
				"[1518-04-25 00:09] falls asleep\n" + 
				"[1518-04-26 00:20] falls asleep\n" + 
				"[1518-08-05 00:37] wakes up\n" + 
				"[1518-09-19 00:00] Guard #2803 begins shift\n" + 
				"[1518-03-27 00:23] wakes up\n" + 
				"[1518-04-08 00:03] Guard #503 begins shift\n" + 
				"[1518-02-12 00:03] Guard #431 begins shift\n" + 
				"[1518-07-06 00:24] falls asleep\n" + 
				"[1518-04-06 00:30] falls asleep\n" + 
				"[1518-02-15 00:57] wakes up\n" + 
				"[1518-10-20 00:35] falls asleep\n" + 
				"[1518-02-16 23:56] Guard #3137 begins shift\n" + 
				"[1518-03-22 00:47] falls asleep\n" + 
				"[1518-06-24 00:44] wakes up\n" + 
				"[1518-08-13 23:48] Guard #3407 begins shift\n" + 
				"[1518-05-17 00:25] falls asleep\n" + 
				"[1518-08-20 00:01] Guard #613 begins shift\n" + 
				"[1518-11-02 00:37] falls asleep\n" + 
				"[1518-02-20 00:23] falls asleep\n" + 
				"[1518-06-15 00:37] falls asleep\n" + 
				"[1518-08-19 00:24] falls asleep\n" + 
				"[1518-05-10 00:32] wakes up\n" + 
				"[1518-10-24 00:46] falls asleep\n" + 
				"[1518-06-20 23:59] Guard #613 begins shift\n" + 
				"[1518-03-26 00:03] Guard #1049 begins shift\n" + 
				"[1518-08-15 00:44] falls asleep\n" + 
				"[1518-05-01 00:18] falls asleep\n" + 
				"[1518-11-13 23:56] Guard #3407 begins shift\n" + 
				"[1518-05-18 00:18] falls asleep\n" + 
				"[1518-04-03 00:03] Guard #3407 begins shift\n" + 
				"[1518-04-19 00:56] wakes up\n" + 
				"[1518-05-15 00:55] falls asleep\n" + 
				"[1518-11-10 00:40] wakes up\n" + 
				"[1518-08-29 00:22] falls asleep\n" + 
				"[1518-07-17 23:54] Guard #1049 begins shift\n" + 
				"[1518-03-05 23:56] Guard #1049 begins shift\n" + 
				"[1518-05-12 00:28] falls asleep\n" + 
				"[1518-08-26 23:53] Guard #1487 begins shift\n" + 
				"[1518-08-30 00:37] wakes up\n" + 
				"[1518-02-13 23:47] Guard #3011 begins shift\n" + 
				"[1518-05-02 00:34] wakes up\n" + 
				"[1518-09-08 00:02] falls asleep\n" + 
				"[1518-10-05 00:50] wakes up\n" + 
				"[1518-05-06 00:58] wakes up\n" + 
				"[1518-04-28 00:50] falls asleep\n" + 
				"[1518-07-16 00:51] falls asleep\n" + 
				"[1518-07-18 00:41] wakes up\n" + 
				"[1518-09-12 00:04] Guard #613 begins shift\n" + 
				"[1518-05-30 00:43] wakes up\n" + 
				"[1518-05-04 00:26] falls asleep\n" + 
				"[1518-06-05 00:56] wakes up\n" + 
				"[1518-08-02 00:57] wakes up\n" + 
				"[1518-11-20 00:38] falls asleep\n" + 
				"[1518-05-30 00:17] falls asleep\n" + 
				"[1518-09-03 00:23] falls asleep\n" + 
				"[1518-03-23 00:04] Guard #613 begins shift\n" + 
				"[1518-09-29 00:59] wakes up\n" + 
				"[1518-06-05 00:54] falls asleep\n" + 
				"[1518-10-23 00:55] falls asleep\n" + 
				"[1518-09-20 00:51] wakes up\n" + 
				"[1518-08-11 00:28] falls asleep\n" + 
				"[1518-06-14 00:25] falls asleep\n" + 
				"[1518-10-19 00:40] wakes up\n" + 
				"[1518-08-21 00:48] wakes up\n" + 
				"[1518-05-31 00:52] falls asleep\n" + 
				"[1518-07-23 23:58] Guard #2003 begins shift\n" + 
				"[1518-08-20 23:56] Guard #1049 begins shift\n" + 
				"[1518-09-23 23:46] Guard #293 begins shift\n" + 
				"[1518-05-23 00:37] wakes up\n" + 
				"[1518-04-09 00:58] wakes up\n" + 
				"[1518-05-04 00:57] wakes up\n" + 
				"[1518-03-15 00:34] wakes up\n" + 
				"[1518-08-20 00:38] wakes up\n" + 
				"[1518-06-12 00:41] wakes up\n" + 
				"[1518-08-07 00:11] falls asleep\n" + 
				"[1518-10-26 00:50] wakes up\n" + 
				"[1518-03-20 00:54] wakes up\n" + 
				"[1518-04-11 00:13] wakes up\n" + 
				"[1518-07-14 00:03] Guard #3011 begins shift\n" + 
				"[1518-03-24 00:48] wakes up\n" + 
				"[1518-04-13 00:44] falls asleep\n" + 
				"[1518-03-07 00:17] falls asleep\n" + 
				"[1518-08-01 00:35] falls asleep\n" + 
				"[1518-08-24 00:42] wakes up\n" + 
				"[1518-02-16 00:33] wakes up\n" + 
				"[1518-10-16 00:54] wakes up\n" + 
				"[1518-09-30 00:20] falls asleep\n" + 
				"[1518-02-23 00:01] falls asleep\n" + 
				"[1518-09-21 00:59] wakes up\n" + 
				"[1518-04-20 00:44] wakes up\n" + 
				"[1518-06-30 23:50] Guard #3119 begins shift\n" + 
				"[1518-04-15 00:41] wakes up\n" + 
				"[1518-11-18 00:39] wakes up\n" + 
				"[1518-05-17 00:06] falls asleep\n" + 
				"[1518-05-20 00:31] wakes up\n" + 
				"[1518-05-16 00:52] wakes up\n" + 
				"[1518-02-27 23:56] Guard #223 begins shift\n" + 
				"[1518-11-19 00:40] falls asleep\n" + 
				"[1518-11-21 00:05] falls asleep\n" + 
				"[1518-06-24 00:59] wakes up\n" + 
				"[1518-05-23 00:54] falls asleep\n" + 
				"[1518-07-10 00:52] falls asleep\n" + 
				"[1518-03-02 00:00] Guard #613 begins shift\n" + 
				"[1518-06-13 00:00] Guard #503 begins shift\n" + 
				"[1518-05-27 00:39] wakes up\n" + 
				"[1518-05-03 00:47] wakes up\n" + 
				"[1518-05-07 23:58] Guard #887 begins shift\n" + 
				"[1518-04-12 00:04] falls asleep\n" + 
				"[1518-08-29 00:45] wakes up\n" + 
				"[1518-05-08 00:29] falls asleep\n" + 
				"[1518-06-17 00:38] wakes up\n" + 
				"[1518-10-01 00:47] wakes up\n" + 
				"[1518-04-07 00:24] wakes up\n" + 
				"[1518-06-24 00:57] falls asleep\n" + 
				"[1518-08-15 00:45] wakes up\n" + 
				"[1518-08-08 00:51] falls asleep\n" + 
				"[1518-06-17 00:00] Guard #223 begins shift\n" + 
				"[1518-06-12 00:58] wakes up\n" + 
				"[1518-04-30 00:56] wakes up\n" + 
				"[1518-03-03 23:48] Guard #887 begins shift\n" + 
				"[1518-06-08 23:58] Guard #3203 begins shift\n" + 
				"[1518-05-01 23:46] Guard #613 begins shift\n" + 
				"[1518-07-26 23:57] Guard #2003 begins shift\n" + 
				"[1518-10-30 23:58] Guard #3011 begins shift\n" + 
				"[1518-02-23 00:56] wakes up\n" + 
				"[1518-03-13 23:58] Guard #223 begins shift\n" + 
				"[1518-05-11 00:38] wakes up\n" + 
				"[1518-08-30 00:01] Guard #613 begins shift\n" + 
				"[1518-05-03 00:08] falls asleep\n" + 
				"[1518-06-13 00:07] falls asleep\n" + 
				"[1518-06-10 00:55] wakes up\n" + 
				"[1518-10-29 00:17] falls asleep\n" + 
				"[1518-08-11 23:57] Guard #1061 begins shift\n" + 
				"[1518-10-21 00:49] wakes up\n" + 
				"[1518-03-28 00:00] Guard #3203 begins shift\n" + 
				"[1518-09-19 00:56] falls asleep\n" + 
				"[1518-05-05 00:23] falls asleep\n" + 
				"[1518-04-20 00:00] Guard #1487 begins shift\n" + 
				"[1518-04-19 00:40] falls asleep\n" + 
				"[1518-07-03 00:45] wakes up\n" + 
				"[1518-07-28 00:59] wakes up\n" + 
				"[1518-09-07 00:37] wakes up\n" + 
				"[1518-02-16 00:38] falls asleep\n" + 
				"[1518-05-24 00:48] wakes up\n" + 
				"[1518-08-26 00:50] wakes up\n" + 
				"[1518-07-29 00:53] wakes up\n" + 
				"[1518-05-17 00:52] wakes up\n" + 
				"[1518-10-31 00:53] wakes up\n" + 
				"[1518-06-26 00:23] wakes up\n" + 
				"[1518-08-15 00:17] falls asleep\n" + 
				"[1518-06-28 00:51] wakes up\n" + 
				"[1518-07-30 00:17] falls asleep\n" + 
				"[1518-03-28 00:11] falls asleep\n" + 
				"[1518-10-26 00:55] falls asleep\n" + 
				"[1518-04-09 23:52] Guard #2803 begins shift\n" + 
				"[1518-08-28 00:26] wakes up\n" + 
				"[1518-09-08 23:56] Guard #3011 begins shift\n" + 
				"[1518-03-03 00:00] Guard #2803 begins shift\n" + 
				"[1518-08-17 00:28] wakes up\n" + 
				"[1518-09-09 00:07] falls asleep\n" + 
				"[1518-07-21 00:09] falls asleep\n" + 
				"[1518-03-31 00:03] Guard #223 begins shift\n" + 
				"[1518-11-11 23:56] Guard #3119 begins shift\n" + 
				"[1518-10-20 00:48] wakes up\n" + 
				"[1518-10-12 23:57] Guard #1747 begins shift\n" + 
				"[1518-02-27 00:56] wakes up\n" + 
				"[1518-10-17 00:58] wakes up\n" + 
				"[1518-10-10 00:33] wakes up\n" + 
				"[1518-02-21 00:32] wakes up\n" + 
				"[1518-06-16 00:00] falls asleep\n" + 
				"[1518-05-23 23:59] Guard #3407 begins shift\n" + 
				"[1518-06-14 23:57] Guard #3011 begins shift\n" + 
				"[1518-05-05 23:51] Guard #1031 begins shift\n" + 
				"[1518-09-02 00:50] wakes up\n" + 
				"[1518-03-05 00:39] wakes up\n" + 
				"[1518-05-09 00:49] wakes up\n" + 
				"[1518-08-08 00:12] wakes up\n" + 
				"[1518-03-07 00:50] falls asleep\n" + 
				"[1518-07-11 23:47] Guard #3407 begins shift\n" + 
				"[1518-05-10 00:55] wakes up\n" + 
				"[1518-03-29 00:21] wakes up\n" + 
				"[1518-05-18 00:52] wakes up\n" + 
				"[1518-09-11 00:47] wakes up\n" + 
				"[1518-05-28 00:36] falls asleep\n" + 
				"[1518-05-23 00:57] wakes up\n" + 
				"[1518-05-14 00:49] falls asleep\n" + 
				"[1518-10-15 23:59] Guard #887 begins shift\n" + 
				"[1518-06-09 00:46] falls asleep\n" + 
				"[1518-09-26 23:51] Guard #3407 begins shift\n" + 
				"[1518-10-12 00:27] falls asleep\n" + 
				"[1518-09-21 00:00] Guard #503 begins shift\n" + 
				"[1518-11-03 00:26] falls asleep\n" + 
				"[1518-09-02 00:36] falls asleep\n" + 
				"[1518-04-01 00:52] wakes up\n" + 
				"[1518-04-24 00:56] wakes up\n" + 
				"[1518-04-24 00:03] Guard #1567 begins shift\n" + 
				"[1518-10-08 23:59] Guard #3407 begins shift\n" + 
				"[1518-06-15 00:55] falls asleep\n" + 
				"[1518-09-12 00:58] wakes up\n" + 
				"[1518-02-27 00:04] Guard #1049 begins shift\n" + 
				"[1518-08-26 00:00] Guard #613 begins shift\n" + 
				"[1518-03-15 00:02] Guard #2803 begins shift\n" + 
				"[1518-03-09 00:26] falls asleep\n" + 
				"[1518-05-28 00:23] falls asleep\n" + 
				"[1518-11-21 00:35] falls asleep\n" + 
				"[1518-03-06 00:52] wakes up\n" + 
				"[1518-02-20 00:48] wakes up\n" + 
				"[1518-06-28 23:58] Guard #1487 begins shift\n" + 
				"[1518-04-16 00:00] Guard #541 begins shift\n" + 
				"[1518-03-05 00:01] Guard #887 begins shift\n" + 
				"[1518-10-11 00:49] wakes up\n" + 
				"[1518-05-16 00:34] falls asleep\n" + 
				"[1518-08-28 00:41] wakes up\n" + 
				"[1518-02-26 00:17] falls asleep\n" + 
				"[1518-03-04 00:56] falls asleep\n" + 
				"[1518-07-04 00:59] wakes up\n" + 
				"[1518-03-06 23:59] Guard #541 begins shift\n" + 
				"[1518-04-02 00:00] Guard #3137 begins shift\n" + 
				"[1518-10-18 00:45] wakes up\n" + 
				"[1518-04-23 00:11] wakes up\n" + 
				"[1518-10-21 00:00] Guard #223 begins shift\n" + 
				"[1518-11-04 00:45] wakes up\n" + 
				"[1518-03-17 00:00] Guard #541 begins shift\n" + 
				"[1518-05-17 23:56] Guard #1487 begins shift\n" + 
				"[1518-05-18 00:21] wakes up\n" + 
				"[1518-07-28 00:52] falls asleep\n" + 
				"[1518-04-23 00:34] falls asleep\n" + 
				"[1518-10-22 00:02] Guard #1031 begins shift\n" + 
				"[1518-11-22 23:58] Guard #223 begins shift\n" + 
				"[1518-11-19 23:57] Guard #1747 begins shift\n" + 
				"[1518-08-24 00:39] falls asleep\n" + 
				"[1518-05-06 00:03] falls asleep\n" + 
				"[1518-09-10 00:36] falls asleep\n" + 
				"[1518-10-19 23:58] Guard #2341 begins shift\n" + 
				"[1518-04-01 00:06] falls asleep\n" + 
				"[1518-05-12 00:49] wakes up\n" + 
				"[1518-03-18 00:02] Guard #3119 begins shift\n" + 
				"[1518-09-16 00:02] Guard #2803 begins shift\n" + 
				"[1518-03-08 00:49] falls asleep\n" + 
				"[1518-07-11 00:58] wakes up\n" + 
				"[1518-07-10 00:58] wakes up\n" + 
				"[1518-09-29 00:22] falls asleep\n" + 
				"[1518-04-02 00:42] wakes up\n" + 
				"[1518-05-20 23:58] Guard #293 begins shift\n" + 
				"[1518-07-09 00:24] falls asleep\n" + 
				"[1518-03-11 23:58] Guard #431 begins shift\n" + 
				"[1518-10-03 00:02] Guard #3137 begins shift\n" + 
				"[1518-06-05 00:02] Guard #2803 begins shift\n" + 
				"[1518-07-03 00:25] falls asleep\n" + 
				"[1518-10-09 00:51] wakes up\n" + 
				"[1518-10-22 23:59] Guard #2341 begins shift\n" + 
				"[1518-09-01 00:07] falls asleep\n" + 
				"[1518-06-06 00:29] wakes up\n" + 
				"[1518-11-18 00:42] falls asleep\n" + 
				"[1518-05-24 00:52] falls asleep\n" + 
				"[1518-09-23 00:00] Guard #1031 begins shift\n" + 
				"[1518-09-22 00:54] falls asleep\n" + 
				"[1518-08-05 00:01] falls asleep\n" + 
				"[1518-03-11 00:21] falls asleep\n" + 
				"[1518-10-19 00:30] falls asleep\n" + 
				"[1518-05-17 00:02] Guard #3011 begins shift\n" + 
				"[1518-04-29 00:53] wakes up\n" + 
				"[1518-09-07 00:26] falls asleep\n" + 
				"[1518-07-16 00:55] wakes up\n" + 
				"[1518-04-07 00:02] Guard #1747 begins shift\n" + 
				"[1518-03-12 00:21] falls asleep\n" + 
				"[1518-08-17 00:01] Guard #293 begins shift\n" + 
				"[1518-09-29 00:02] Guard #613 begins shift\n" + 
				"[1518-11-19 00:24] wakes up\n" + 
				"[1518-04-04 00:05] falls asleep\n" + 
				"[1518-02-28 00:21] falls asleep\n" + 
				"[1518-11-21 00:37] wakes up\n" + 
				"[1518-04-10 00:33] wakes up\n" + 
				"[1518-07-28 00:03] falls asleep\n" + 
				"[1518-05-22 00:22] falls asleep\n" + 
				"[1518-03-07 00:36] falls asleep\n" + 
				"[1518-03-16 00:20] falls asleep\n" + 
				"[1518-06-16 00:54] wakes up\n" + 
				"[1518-03-13 00:58] wakes up\n" + 
				"[1518-10-27 00:15] wakes up\n" + 
				"[1518-08-03 00:01] falls asleep\n" + 
				"[1518-08-18 00:03] Guard #1487 begins shift\n" + 
				"[1518-09-23 00:52] wakes up\n" + 
				"[1518-05-02 00:42] falls asleep\n" + 
				"[1518-04-07 00:53] falls asleep\n" + 
				"[1518-10-26 00:30] falls asleep\n" + 
				"[1518-07-02 00:39] wakes up\n" + 
				"[1518-02-13 00:11] falls asleep\n" + 
				"[1518-10-28 23:59] Guard #2003 begins shift\n" + 
				"[1518-11-21 00:59] wakes up\n" + 
				"[1518-05-23 00:40] falls asleep\n" + 
				"[1518-07-25 00:38] wakes up\n" + 
				"[1518-04-19 00:34] falls asleep\n" + 
				"[1518-03-21 00:50] wakes up\n" + 
				"[1518-10-28 00:01] Guard #223 begins shift\n" + 
				"[1518-09-04 00:38] wakes up\n" + 
				"[1518-03-09 00:00] Guard #2803 begins shift\n" + 
				"[1518-07-07 00:04] Guard #223 begins shift\n" + 
				"[1518-11-03 00:00] Guard #3119 begins shift\n" + 
				"[1518-09-03 00:00] falls asleep\n" + 
				"[1518-07-22 23:59] Guard #3137 begins shift\n" + 
				"[1518-08-23 00:13] falls asleep\n" + 
				"[1518-10-02 00:14] falls asleep\n" + 
				"[1518-05-31 00:08] falls asleep\n" + 
				"[1518-08-16 00:34] falls asleep\n" + 
				"[1518-10-30 00:58] wakes up\n" + 
				"[1518-07-17 00:10] falls asleep\n" + 
				"[1518-10-04 23:51] Guard #613 begins shift\n" + 
				"[1518-05-06 00:30] wakes up\n" + 
				"[1518-05-05 00:39] wakes up\n" + 
				"[1518-06-29 00:21] falls asleep\n" + 
				"[1518-09-10 00:02] falls asleep\n" + 
				"[1518-08-18 00:22] falls asleep\n" + 
				"[1518-04-11 00:58] wakes up\n" + 
				"[1518-06-29 00:54] wakes up\n" + 
				"[1518-11-12 00:43] wakes up\n" + 
				"[1518-08-19 00:52] falls asleep\n" + 
				"[1518-09-05 00:50] wakes up\n" + 
				"[1518-11-02 00:49] wakes up\n" + 
				"[1518-08-11 00:04] Guard #3137 begins shift\n" + 
				"[1518-08-03 00:59] wakes up\n" + 
				"[1518-06-17 00:34] falls asleep\n" + 
				"[1518-05-01 00:00] Guard #2003 begins shift\n" + 
				"[1518-07-29 00:28] falls asleep\n" + 
				"[1518-04-23 00:01] falls asleep\n" + 
				"[1518-08-05 00:54] wakes up\n" + 
				"[1518-07-21 00:00] Guard #503 begins shift\n" + 
				"[1518-10-25 00:03] Guard #541 begins shift\n" + 
				"[1518-11-01 00:01] Guard #1031 begins shift\n" + 
				"[1518-03-22 00:44] wakes up\n" + 
				"[1518-09-06 00:34] falls asleep\n" + 
				"[1518-07-27 00:18] falls asleep\n" + 
				"[1518-10-31 00:12] wakes up\n" + 
				"[1518-04-27 00:53] wakes up\n" + 
				"[1518-03-25 00:03] Guard #887 begins shift\n" + 
				"[1518-09-21 00:56] falls asleep\n" + 
				"[1518-06-07 00:55] wakes up\n" + 
				"[1518-06-14 00:00] Guard #223 begins shift\n" + 
				"[1518-03-03 00:31] wakes up\n" + 
				"[1518-11-01 23:48] Guard #613 begins shift\n" + 
				"[1518-04-03 00:09] falls asleep\n" + 
				"[1518-06-15 00:52] wakes up\n" + 
				"[1518-08-25 00:00] Guard #503 begins shift\n" + 
				"[1518-08-21 00:17] falls asleep\n" + 
				"[1518-04-10 00:05] falls asleep\n" + 
				"[1518-04-30 00:51] falls asleep\n" + 
				"[1518-03-17 00:38] falls asleep\n" + 
				"[1518-07-14 23:58] Guard #1487 begins shift\n" + 
				"[1518-10-06 23:50] Guard #503 begins shift\n" + 
				"[1518-08-13 00:18] falls asleep\n" + 
				"[1518-09-07 00:04] Guard #1487 begins shift\n" + 
				"[1518-02-13 00:32] wakes up\n" + 
				"[1518-03-30 00:44] wakes up\n" + 
				"[1518-06-15 00:59] wakes up\n" + 
				"[1518-08-05 00:53] falls asleep\n" + 
				"[1518-10-23 00:59] wakes up\n" + 
				"[1518-06-26 00:43] falls asleep\n" + 
				"[1518-08-07 00:28] wakes up\n" + 
				"[1518-04-29 00:50] falls asleep\n" + 
				"[1518-05-04 00:04] Guard #1747 begins shift\n" + 
				"[1518-07-07 00:48] falls asleep\n" + 
				"[1518-02-19 00:02] falls asleep\n" + 
				"[1518-06-05 00:24] falls asleep\n" + 
				"[1518-04-07 00:39] falls asleep\n" + 
				"[1518-03-18 00:25] falls asleep\n" + 
				"[1518-10-08 00:00] Guard #293 begins shift\n" + 
				"[1518-04-28 00:57] wakes up\n" + 
				"[1518-05-10 00:16] falls asleep\n" + 
				"[1518-10-02 00:51] wakes up\n" + 
				"[1518-09-19 00:36] wakes up\n" + 
				"[1518-10-27 00:39] wakes up\n" + 
				"[1518-11-22 00:00] Guard #3137 begins shift\n" + 
				"[1518-02-15 00:21] wakes up\n" + 
				"[1518-03-17 00:56] wakes up\n" + 
				"[1518-11-19 00:04] falls asleep\n" + 
				"[1518-09-30 00:49] wakes up\n" + 
				"[1518-03-04 00:46] wakes up\n" + 
				"[1518-03-08 00:54] wakes up\n" + 
				"[1518-08-20 00:44] falls asleep\n" + 
				"[1518-02-25 00:14] falls asleep\n" + 
				"[1518-09-25 23:48] Guard #1487 begins shift\n" + 
				"[1518-08-01 00:47] wakes up\n" + 
				"[1518-04-07 00:54] wakes up\n" + 
				"[1518-04-04 00:40] wakes up\n" + 
				"[1518-09-17 00:54] falls asleep\n" + 
				"[1518-06-07 00:20] falls asleep\n" + 
				"[1518-10-14 00:24] falls asleep\n" + 
				"[1518-02-17 00:15] falls asleep\n" + 
				"[1518-02-10 00:19] wakes up\n" + 
				"[1518-05-13 23:58] Guard #1567 begins shift\n" + 
				"[1518-05-08 00:52] wakes up\n" + 
				"[1518-11-21 00:29] wakes up\n" + 
				"[1518-04-10 00:44] falls asleep\n" + 
				"[1518-04-27 00:18] falls asleep\n" + 
				"[1518-05-01 00:54] wakes up\n" + 
				"[1518-04-23 00:59] wakes up\n" + 
				"[1518-08-25 00:33] falls asleep\n" + 
				"[1518-02-24 00:29] wakes up\n" + 
				"[1518-11-22 00:56] wakes up\n" + 
				"[1518-09-18 00:01] Guard #3203 begins shift\n" + 
				"[1518-03-21 00:02] Guard #431 begins shift\n" + 
				"[1518-10-18 00:53] wakes up\n" + 
				"[1518-02-28 00:59] wakes up\n" + 
				"[1518-03-31 00:55] wakes up\n" + 
				"[1518-09-10 00:29] wakes up\n" + 
				"[1518-03-08 00:44] wakes up\n" + 
				"[1518-07-26 00:01] Guard #3407 begins shift\n" + 
				"[1518-06-14 00:18] wakes up\n" + 
				"[1518-05-02 00:59] wakes up\n" + 
				"[1518-03-18 23:58] Guard #3011 begins shift\n" + 
				"[1518-03-21 00:56] falls asleep\n" + 
				"[1518-04-26 00:55] falls asleep\n" + 
				"[1518-09-15 00:13] falls asleep\n" + 
				"[1518-05-25 00:00] Guard #887 begins shift\n" + 
				"[1518-09-01 00:04] Guard #223 begins shift\n" + 
				"[1518-02-14 23:56] Guard #223 begins shift\n" + 
				"[1518-10-17 00:03] falls asleep\n" + 
				"[1518-08-17 00:17] falls asleep\n" + 
				"[1518-09-27 00:04] falls asleep\n" + 
				"[1518-10-22 00:48] falls asleep\n" + 
				"[1518-02-14 00:57] wakes up\n" + 
				"[1518-04-08 00:52] wakes up";
		
		// exercise solution given test input
		long startTime = System.currentTimeMillis();
		int soln = solution2(input);
		// int soln = solution2(input);
		long endTime = System.currentTimeMillis();

		// print solution and runtime
		long totalTime = endTime - startTime;
		System.out.println("Solution Output: " + soln);
		System.out.println("Solution Runtime: " + totalTime + " ms");
	}
}
