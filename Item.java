package recommend;

import java.util.ArrayList;
/**
 * this class is the item class, every item has a list of users who have seen the item
 * @author Yichen
 *
 */
public class Item {
	private int title;
	private ArrayList<User> userList = new ArrayList<User>();
	
	/**
	 * constructor
	 * @param title
	 */
	public Item(int title) {
		this.title = title;
	}
	/**
	 * title getter
	 * @return title
	 */
	public int getTitle() {
		return title;
	}
	/**
	 * title setter
	 * @param title
	 */
	public void setTitle(int title) {
		this.title = title;
	}
	/**
	 * user list getter
	 * @return arrayList
	 */
	public ArrayList<User> getUserList() {
		return userList;
	}
	/**
	 * user list setter
	 * @param userList
	 */
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	
	
	
	
	
}
