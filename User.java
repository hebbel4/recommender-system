package recommend;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is the user class, each user has a collection to store the movie-rating
 * tuple and a list of seenMovies.
 * given an item, can get the user's rating for the item, and the class also has method to 
 * calculate the average rating
 * @author Yichen
 *
 */
public class User {
	private int name;
	private HashMap<Integer, Double> ratingCollection = new HashMap<Integer, Double>();
	private ArrayList<Integer> seenMovies = new ArrayList<Integer>();
	 
	//getters and setters
	/**
	 * getSeenMovies arraylist
	 * @return arraylist
	 */
	public ArrayList<Integer> getSeenMovies() {
		return seenMovies;
	}
	/**
	 * seenMovies setter
	 * @param seenMovies
	 */
	public void setSeenMovies(ArrayList<Integer> seenMovies) {
		this.seenMovies = seenMovies;
	}
	/**
	 * ratingCollection getter
	 * @return hashmap
	 */
	public HashMap<Integer, Double> getRatingCollection() {
		return ratingCollection;
	}
	/**
	 * get name
	 * @return name
	 */
	public int getName() {
		return name;
	}
	/**
	 * set name
	 * @param name
	 */
	public void setName(int name) {
		this.name = name;
	}
	/**
	 * set rating collection
	 * @param ratingCollection
	 */
	public void setRatingCollection(HashMap<Integer, Double> ratingCollection) {
		this.ratingCollection = ratingCollection;
	}
	//constructor
	public User(int name) {
		this.name = name;
	}
	
	/**
	 * get user average rating
	 * @return average rating
	 */
	public double getAverageRating() {
		double numOfItem = ratingCollection.size();
		double ratingSum = 0;
		for (int s : ratingCollection.keySet()) {
			ratingSum = ratingSum + ratingCollection.get(s);
		}
		double average = ratingSum / numOfItem;
		return average;
	}
	
	/**
	 * get rating for an item
	 * @param title
	 * @return item rating
	 */
	public double getItemRating(int title) {
		double rating = ratingCollection.get(title);
		return rating;
	}
	
	
	
}
