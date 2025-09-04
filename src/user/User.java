package user;

/**
 * Represents a generic user in the BTO Management System.
 * <p>
 * This class serves as a base for different types of users (e.g., Applicant, HDB Officer, HDB Manager).
 * It holds common user information such as name, user ID, password, age, and marital status.
 * It provides constructors for creating a user with or without a pre-defined password,
 * and includes getter and setter methods for accessing and modifying the user's data.
 * </p>
 */
public class User {
	
	/**
	 * The unique identifier for the user. This is often an NRIC or a similar ID.
	 */
	protected String userID;
	
	/**
	 * The name of the user.
	 */
	protected String name;
	
	/**
	 * The user's password.
	 */
	protected String password;
	
	/**
	 * The user's age.
	 */
	protected int age;
	
	/**
	 * Enumeration for the marital status of the user.
	 */
	public enum MARITAL_STATUS {
		SINGLE, 
		MARRIED
	};
	
	/**
	 * The marital status of the user.
	 */
	protected MARITAL_STATUS maritalStatus;
	
	/**
	 * Enumeration for the type of user.
	 */
	public enum USER_TYPE {
		APPLICANT, 
		HDB_OFFICER, 
		HDB_MANAGER
	};
	
	/**
	 * The type of the user.
	 */
	protected USER_TYPE userType;
	
	
	/**
	 * Constructs a new {@code User} with the provided information.
	 * Sets the default password to "password".
	 *
	 * @param userID        The unique identifier for the user.
	 * @param name          The name of the user.
	 * @param age           The age of the user.
	 * @param maritalStatus The marital status of the user.
	 */
	public User(String userID, String name, int age, MARITAL_STATUS maritalStatus) {
		this.name = name;
		this.userID = userID;
		this.password = "password";
		this.age = age;
		this.maritalStatus = maritalStatus;
	}

	/**
	 * Constructs a new {@code User} with the provided information, including a custom password.
	 *
	 * @param userID        The unique identifier for the user.
	 * @param name          The name of the user.
	 * @param age           The age of the user.
	 * @param maritalStatus The marital status of the user.
	 * @param password      The custom password for the user.
	 */
	public User(String userID, String name, int age, MARITAL_STATUS maritalStatus, String password) {
		this(userID, name, age, maritalStatus);
		this.password = password;
	}
	
	/*
	 * Age, name, and userID are considered permanent characteristics and cannot be changed
	 * after the User object is created. Therefore, there are no public setter methods for them.
	 */

	/**
	 * Retrieves the name of the user.
	 *
	 * @return The user's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the unique user ID.
	 *
	 * @return The user's ID.
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Retrieves the user's password.
	 *
	 * @return The user's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets a new password for the user.
	 *
	 * @param password The new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Retrieves the age of the user.
	 *
	 * @return The user's age.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Checks if the user's marital status is "Married".
	 *
	 * @return {@code true} if the user is married, {@code false} otherwise.
	 */
	public boolean isMarried() {
		return maritalStatus.equals(MARITAL_STATUS.MARRIED);
	}

	/**
	 * Retrieves the marital status of the user.
	 *
	 * @return The user's {@link MARITAL_STATUS}.
	 */
	public MARITAL_STATUS getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * Retrieves the type of the user.
	 *
	 * @return The user's {@link USER_TYPE}.
	 */
	public USER_TYPE getUserType() {
		return userType;
	}
}