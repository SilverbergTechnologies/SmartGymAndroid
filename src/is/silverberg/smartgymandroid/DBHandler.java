package is.silverberg.smartgymandroid;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PWHASH = "pwhash";
	public static final String COLUMN_PWSALT = "pwsalt";
	
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
	}


	@Override
	/**
	 * Run when an instance of the class is upgraded.
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
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
	 * 
	 * @param email
	 * @return
	 */
	public String getPassword( String email ) {
		User user = findUser( email );
		String password = user.getPassword();
		return password;
	}
	
	/**
	 * A method to convert a hexadecimal String to byte array
	 * @param hex
	 * @return
	 */
    private byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    /**
     * A method to convert a byte array to a hexadecimal String
     * @param array
     * @return
     */
	public String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else
        {
            return hex;
        }
    }
	
}

