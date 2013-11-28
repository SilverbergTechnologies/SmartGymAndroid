package is.silverberg.smartgymandroid;

import java.security.NoSuchAlgorithmException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Test class DBHandler handles local SQLite database connections.
 * @author Kjartan B. Kristjansson
 *
 */
public class DBHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "SmartGymDB.db";
	private static final String TABLE_USERS = "users";
	private static final String TABLE_EXERCISE = "exercise";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PWHASH = "pwhash";
	public static final String COLUMN_PWSALT = "pwsalt";
	
	public static final String COLUMN_EID = "_eid";
	public static final String COLUMN_XML = "xml";
	/**
	 * Constructor
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBHandler(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	/**
	 * Run when an instance of the class is created.
	 */
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS +
				"(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME +
				" TEXT," + COLUMN_EMAIL + " TEXT," + COLUMN_PWHASH + " TEXT," +
				COLUMN_PWSALT + " TEXT" + ")";
		db.execSQL(CREATE_USER_TABLE);
		String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE +
				"(" + COLUMN_EID + " INTEGER PRIMARY KEY," + COLUMN_XML + " TEXT UNIQUE" + ")";
		db.execSQL(CREATE_EXERCISE_TABLE);
	}


	@Override
	/**
	 * Run when an instance of the class is upgraded.
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
		onCreate(db);
	}
	
	/**
	 * A method to add an user to the database.
	 * @param user
	 * @throws NoSuchAlgorithmException
	 */
	public void addUser(User user) throws NoSuchAlgorithmException {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, user.getName());
		values.put(COLUMN_EMAIL, user.getEmail());
		String[] pw = new String[2];
		pw = Password.hashPassword(user.getPassword(), null);
		values.put(COLUMN_PWHASH, pw[0]);
		values.put(COLUMN_PWSALT, pw[1]);
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.insert(TABLE_USERS, null, values);
		db.close();
	}

	/**
	 * A method to find an user within the database.
	 * @param username
	 * @return User corresponding to first user with given username.
	 */
	public User findUser(String email) {
		String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = \"" + email + "\"";
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		User user = new User();
		
		if(cursor.moveToFirst()) {
			cursor.moveToFirst();
			user.setID(Integer.parseInt(cursor.getString(0)));
			user.setName(cursor.getString(1));
			user.setEmail(cursor.getString(2));
			user.setPassword(cursor.getString(3));
			user.setSalt(cursor.getString(4));
			cursor.close();
		} else {
			user = null;
		}
		db.close();
		return user;
	}
	
	/**
	 * A method to delete an user from the database.
	 * @param username
	 * @return boolean true if deletion successful
	 */
	public boolean deleteUser(String username) {
		boolean result = false;
		String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME +  " =  \"" + username + "\"";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		User user = new User();
		
		if(cursor.moveToFirst()) {
			user.setID(Integer.parseInt(cursor.getString(0)));
			db.delete(TABLE_USERS, COLUMN_ID + " = ?", new String[] { String.valueOf(user.getID()) });
			cursor.close();
			result = true;
		}
		db.close();
		return result;
	}
	
	/**
	 * A method to get hashed password of user from database
	 * @param email
	 * @return
	 */
	public String getPassword( String email ) {
		User user = findUser( email );
		String password = user.getPassword();
		return password;
	}
	
	/**
	 * A method to add workout results to database
	 * @param xml
	 */
	public void addWorkoutData(String xml) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_XML, xml);
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.insert(TABLE_EXERCISE, null, values);
		db.close();
	}
	
	/**
	 * A method to get workout data from database
	 * @param id
	 * @return XML String
	 */
	public String getWorkoutData(int id) {
		String result;
		String query = "SELECT * FROM " + TABLE_EXERCISE + " WHERE " + COLUMN_EID +  " = " + String.valueOf(id);
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()) {
			cursor.moveToFirst();
			result = cursor.getString(1);
			cursor.close();
		} else {
			result = null;
		}
		db.close();
		return result;
	}
	
	/**
	 * A method to get number of entries in workout database
	 * @return int result
	 */
	public int getWorkoutCount() {
		int result;
		String query = "SELECT count(*) FROM " + TABLE_EXERCISE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()) {
			cursor.moveToFirst();
			result = Integer.parseInt(cursor.getString(0));//		String query = "SELECT * FROM " + TABLE_EXERCISE;
			cursor.close();
		} else {
			result = 0;
		}
		db.close();
		return result;
	}
	
}

