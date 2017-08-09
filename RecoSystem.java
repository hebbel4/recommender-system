package recommend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class mainly do two functions: to predict a user's preference for a given item
 * and give a recommendation list to a user
 * @author Yichen
 *
 */
public class RecoSystem {
	protected Data data = new Data();


	/**
	 * this method calculates baseline prediction for user u 
	 * @param name
	 * @return
	 */
	public double calBaselineU (int name) {
		User user = data.getUserArray().get(name);
		ArrayList<Integer> items = user.getSeenMovies();
		double overallAverating = data.overallAverage;
		double difference = 0;
		int Iu = items.size();
		for (int i : items) {
			difference += user.getItemRating(i) - overallAverating;
		}	
		return difference / Iu;
	}
	/**
	 * this method predicts preference using baseline predictor
	 * @param name
	 * @param title
	 * @return prediction
	 */
	public double baselinePredict(int name, int title) {
		if (!data.getUserArray().containsKey(name) || !data.getItemArray().containsKey(title)) {
			System.out.println("item/user does not exist.");
			return -1;
		}
		User givenUser = data.getUserArray().get(name);
		if (givenUser.getSeenMovies().contains(title)) {
			System.out.println("The user has already rated the item.");
			return givenUser.getItemRating(title);
		}
		Item item = data.getItemArray().get(title);
		ArrayList<User> userList = item.getUserList();
		double baselineU = calBaselineU(name);
		double difference = 0;
		double overallAverating = data.overallAverage;
		int Ui = userList.size();
		for (User u : userList) {
			difference += u.getItemRating(title) - calBaselineU(u.getName()) - overallAverating;
		}
		double baselineI = difference / Ui;
		return overallAverating + baselineU + baselineI;



	}

	/**
	 * this method calculates cosine similarity
	 * @param user1
	 * @param user2
	 * @return cosine similarity
	 */
	public double cosSimilarity(User user1, User user2) {
		double numerator = 0;
		double denominator = 0;
		double num1 = 0;
		double num2 = 0;
		ArrayList<Integer> sharedItems = new ArrayList<Integer>();
		//find movies they both saw
		for (int s1 : user1.getSeenMovies()) {
			if (user2.getSeenMovies().contains(s1)){
				sharedItems.add(s1);
			}
		}
		//if they have not seen a common movie, return 0
		if (sharedItems.size() == 0) {
			return 0;
		}
		for (int s2 : sharedItems) {
			double user1rating = user1.getItemRating(s2);
			double user2rating = user2.getItemRating(s2);
			numerator += user1rating * user2rating;
			num1 += user1rating * user1rating;
			num2 += user2rating * user2rating;
		}
		denominator = (Math.sqrt(num1))*(Math.sqrt(num2));
		if (denominator == 0) {
			return 0;
		}
		double result = numerator / denominator;
		return result;
	}

	/**
	 * given two users, and calculate similarity between them
	 * @param user1
	 * @param user2
	 * @return similarity
	 */
	public double calSimilarity(User user1, User user2) {
		double numerator = 0;
		double denominator = 0;
		double denom1 = 0;
		double denom2 = 0;
		ArrayList<Integer> sharedItems = new ArrayList<Integer>();
		//find movies they both saw
		for (int s1 : user1.getSeenMovies()) {
			if (user2.getSeenMovies().contains(s1)){
				sharedItems.add(s1);
			}
		}
		//if they have not seen a common movie, return 0
		if (sharedItems.size() == 0) {
			return 0;
		}
		//calculate numerator and denominator
		for (int s2 : sharedItems) {
			double difference1 = user1.getItemRating(s2) - user1.getAverageRating();
			double difference2 = user2.getItemRating(s2) - user2.getAverageRating();
			numerator += difference1 * difference2;
			denom1 = denom1 + Math.pow(difference1, 2);
			denom2 = denom2 + Math.pow(difference2, 2);	
		}
		denominator = (Math.sqrt(denom1))*(Math.sqrt(denom2));
		if (denominator == 0) {
			return 0;
		}
		double result = numerator / denominator;
		return result;
	}


	/**
	 * predict a user's preference for an item
	 * @param name
	 * @param title
	 * @return preference
	 */
	public double calPreference(int name, int title, boolean cos) {
		HashMap<User, Double> simMap = new HashMap<User, Double>();
		ArrayList<Entry<User, Double>> neighbours = new ArrayList<Entry<User, Double>>();
		if (!data.getUserArray().containsKey(name) || !data.getItemArray().containsKey(title)) {
			System.out.println("item/user does not exist.");
			return -1;
		}
		User givenUser = data.getUserArray().get(name);
		Item givenItem = data.getItemArray().get(title);
		//get users who have seen the movie item
		ArrayList<User> ratedUsers = givenItem.getUserList();
		//if user has already seen the movie, print the message
		if (givenUser.getSeenMovies().contains(title)) {
			System.out.println("The user has already rated the item.");
			return givenUser.getItemRating(title);
		}

		if (cos) {
			for (User u : ratedUsers) {
				double similarity = this.cosSimilarity(givenUser, u);
				simMap.put(u, similarity);
			}
		}else{
			for (User u : ratedUsers) {
				double similarity = this.calSimilarity(givenUser, u);
				simMap.put(u, similarity);
			}
		}
		Set<Entry<User, Double>> set = simMap.entrySet();
		List<Entry<User, Double>> listOfEntry = new ArrayList<Entry<User, Double>>(set);
		//sort the list in descending order
		Collections.sort(listOfEntry, new SortMapDescending());
		//if list size < 20, all user is neighbour, if > 20, choose the top 20
		if (listOfEntry.size() <= 20) {
			neighbours = (ArrayList<Entry<User, Double>>) listOfEntry;
		}else {
			int check = 0;
			for (Map.Entry<User, Double> entry : listOfEntry) {
				check++;
				neighbours.add(entry);
				if (check == 20) {
					break;
				}
			}
		}
		//apply the formula to calculate the preference
		double averageRating = givenUser.getAverageRating();
		double numerator = 0;
		double denominator = 0;
		for (Map.Entry<User, Double> entry : neighbours) {
			numerator = numerator + entry.getValue()
			* (entry.getKey().getItemRating(title) - entry.getKey().getAverageRating());
			denominator = denominator + Math.abs(entry.getValue());
		}
		double result = 0;
		//if similarity is 0, return the user's average rating
		if (denominator == 0) {
			result = averageRating;
		}
		else{
			result = averageRating + numerator / denominator;
		}
		return result;

	}

	/**
	 * get a recommendation list for user
	 * @param name
	 * @param threshold
	 * @return a recommendation list
	 */
	public ArrayList<Integer> findRecommendation(int name, int threshold, int choice){
		HashMap<Integer, Double> preferMap = new HashMap<Integer, Double>();
		ArrayList<Integer> recoList = new ArrayList<Integer>();
		// if user not in data set, print the massage and return null
		if (!data.getUserArray().containsKey(name)) {
			System.out.println("user does not exist.");
			return null;
		}
		User givenUser = data.getUserArray().get(name);
		ArrayList<Integer> seenMovies = givenUser.getSeenMovies();
		//choose knn with pearson
		if (choice == 1) {
			for (Item item : data.getItemArray().values()) {
				//if it's the item the use already saw, go to next one
				if (seenMovies.contains(item.getTitle())) {
					continue;
				}
				double preference = this.calPreference(name, item.getTitle(), false);
				preferMap.put(item.getTitle(), preference);
			}
		}//choose knn with cosine
		else if (choice == 2) {
			for (Item item : data.getItemArray().values()) {
				//if it's the item the use already saw, go to next one
				if (seenMovies.contains(item.getTitle())) {
					continue;
				}
				double preference = this.calPreference(name, item.getTitle(), true);
				preferMap.put(item.getTitle(), preference);
			}
		}//choose baseline 
		else if (choice == 3) {
			for (Item item : data.getItemArray().values()) {
				//if it's the item the use already saw, go to next one
				if (seenMovies.contains(item.getTitle())) {
					continue;
				}
				double preference = this.baselinePredict(name, item.getTitle());
				preferMap.put(item.getTitle(), preference);
			}
		}else{
			return null;
		}
		
		Set<Entry<Integer, Double>> set = preferMap.entrySet();
		List<Entry<Integer, Double>> listOfEntry = new ArrayList<Entry<Integer, Double>>(set);
		//sort the hashmap
		Collections.sort(listOfEntry, new SortMapDescending2());
		if (listOfEntry.size() <= threshold) {
			for (Map.Entry<Integer, Double> entry : listOfEntry) {
				recoList.add(entry.getKey());
			}
		}else{
			int check = 0;
			for (Map.Entry<Integer, Double> entry : listOfEntry) {
				check++;
				recoList.add(entry.getKey());
				if (check == threshold) {
					break;
				}
			}
		}
		
		return recoList;
	}



	class SortMapDescending implements Comparator<Map.Entry<User, Double>> {
		/**
		 * comparator method to sort hashmap in descending order
		 * @param o1, o2
		 * @return int indicator
		 */
		@Override
		public int compare(Entry<User, Double> o1, Entry<User, Double> o2) {
			return o2.getValue().compareTo(o1.getValue());
		}

	}

	class SortMapDescending2 implements Comparator<Map.Entry<Integer, Double>> {
		/**
		 * 
		 * comparator method to sort hashmap in descending order
		 * @param o1, o2
		 * @return int indicator
		 */
		@Override
		public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
			return o2.getValue().compareTo(o1.getValue());
		}

	}

}
