package recommend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * this class processes the data
 * @author Yichen
 *
 */
public class Data {
	//userArray has all users 
	private HashMap<Integer, User> userArray = new HashMap<>();
	//itemArray has all items
	private HashMap<Integer, Item> itemArray = new HashMap<>();
	double overallAverage;

	/**
	 * item array getter
	 * @return Arraylist
	 */
	public HashMap<Integer, Item> getItemArray() {
		return itemArray;
	}
	/**
	 * item array setter
	 * @param itemArray
	 */
	public void setItemArray(HashMap<Integer, Item> itemArray) {
		this.itemArray = itemArray;
	}
	/**
	 * user array getter
	 * @return arrayList
	 */
	public HashMap<Integer, User> getUserArray() {
		return userArray;
	}
	/**
	 * user array setter
	 * @param userArray
	 */
	public void setUserArray(HashMap<Integer, User> userArray) {
		this.userArray = userArray;
	}
	

	/**
	 * process the data file(.csv), store data into userArray and itemArray
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	public void processDataCSV(String ratingFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(ratingFile));
		scanner.nextLine();
		int lines = 0;
		double ratingSum = 0;
		while (scanner.hasNextLine()) {
			lines++;
			String line = scanner.nextLine();
			String[] elements = line.split(",");
			int name = Integer.valueOf(elements[0]);
			int itemID = Integer.valueOf(elements[1]);
			double rating = Double.valueOf(elements[2]);
			ratingSum += rating;
			if (!userArray.containsKey(name)){
				User user = new User(name);
				//add item-rating tuple to user's hashMap
				user.getRatingCollection().put(itemID, rating);
				user.getSeenMovies().add(itemID);		
				//add user to item's userList
				if(!itemArray.containsKey(itemID)){
					Item item = new Item(itemID);
					item.getUserList().add(user);
					itemArray.put(itemID, item);
				}else{
					itemArray.get(itemID).getUserList().add(user);
				}
				userArray.put(name, user);
			}else {
				User user = userArray.get(name);
				user.getRatingCollection().put(itemID, rating);
				user.getSeenMovies().add(itemID);
				if(!itemArray.containsKey(itemID)){
					Item item = new Item(itemID);
					item.getUserList().add(user);
					itemArray.put(itemID, item);
				}else{
					itemArray.get(itemID).getUserList().add(user);
				}
			}
		}
		overallAverage = ratingSum / lines;
		scanner.close();
	}
	/**
	 * process the data file, store data into userArray and itemArray
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public void processData(String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filename));
		int lines = 0;
		double ratingSum = 0;
		while (scanner.hasNextLine()) {
			lines++;
			String line = scanner.nextLine();
			String[] elements = line.split("::");
			int name = Integer.valueOf(elements[0]);
			int itemID = Integer.valueOf(elements[1]);
			double rating = Double.valueOf(elements[2]);
			ratingSum += rating;
			if (!userArray.containsKey(name)){
				User user = new User(name);
				//add item-rating tuple to user's hashMap
				user.getRatingCollection().put(itemID, rating);
				user.getSeenMovies().add(itemID);		
				//add user to item's userList
				if(!itemArray.containsKey(itemID)){
					Item item = new Item(itemID);
					item.getUserList().add(user);
					itemArray.put(itemID, item);
				}else{
					itemArray.get(itemID).getUserList().add(user);
				}
				userArray.put(name, user);
			}else {
				User user = userArray.get(name);
				user.getRatingCollection().put(itemID, rating);
				user.getSeenMovies().add(itemID);
				if(!itemArray.containsKey(itemID)){
					Item item = new Item(itemID);
					item.getUserList().add(user);
					itemArray.put(itemID, item);
				}else{
					itemArray.get(itemID).getUserList().add(user);
				}
			}
		}
		overallAverage = ratingSum / lines;
		scanner.close();
	}




}
