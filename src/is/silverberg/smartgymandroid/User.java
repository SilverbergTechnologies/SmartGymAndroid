package is.silverberg.smartgymandroid;

/**
 * Class containing private members for storage of database 
 * entries about users of the program.
 * @author Kjartan B. Kristjansson
 *
 */
public class User {
	private int _id;
	private String _Name;
	private String _Email;
	private String _Password;
	private String _Salt;
	
	/**
	 * Constructor if no arguments are given
	 */
	public User() {
		
	}
	
	/**
	 * Constructor if all arguments are given.
	 * @param id
	 * @param Name
	 * @param Email
	 * @param Password
	 * @param Salt
	 */
	public User(int id, String Name, String Email, String Password, String Salt) {
		this._id = id;
		this._Name = Name;
		this._Email = Email;
		this._Password = Password;
		this._Salt = Salt;
	}
	
	/**
	 * Default constructor since id and Salt are normally auto-generated.
	 * @param Name
	 * @param Email
	 * @param Password
	 */
	public User(String Name, String Email, String Password) {
		this._Name = Name;
		this._Email = Email;
		this._Password = Password;
	}
	
	/**
	 * Method to set id of individual User.
	 * @param id
	 */
	public void setID( int id ) {
		this._id = id;
	}
	
	/**
	 * Method to get id of individual User.
	 * @return
	 */
	public int getID() {
		return this._id;
	}
	
	/**
	 * Method to get name of individual User.
	 * @param Name
	 */
	public void setName( String Name ) {
		this._Name = Name;
	}
	
	/**
	 * Method to set name of individual User.
	 * @return
	 */
	public String getName() {
		return this._Name;
	}

	/**
	 * Method to set Email of individual User.
	 * @param Lname
	 */
	public void setEmail( String Email ) {
		this._Email = Email;
	}
	
	/**
	 * Method to get Email of individual User.
	 * @return
	 */
	public String getEmail() {
		return this._Email;
	}
	
	/**
	 * Method to set Password of individual User.
	 * @param Password
	 */
	public void setPassword( String Password ) {
		this._Password = Password;
	}
	
	/**
	 * Method to get Password of individual User.
	 * @return
	 */
	public String getPassword() {
		return this._Password;
	}
	
	/**
	 * Method to set Salt of individual User.
	 * @param Salt
	 */
	public void setSalt( String Salt ) {
		this._Salt = Salt;
	}
	
	/**
	 * Method to get Salt of individual User.
	 * @return
	 */
	public String getSalt() {
		return this._Salt;
	}
}

