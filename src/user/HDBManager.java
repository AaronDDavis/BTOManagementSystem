package user;

/**
 * Represents a manager within the HDB system.
 * HDB Managers inherit basic user properties and implement the {@code HDBOfficial} interface,
 * signifying their role within the HDB organization.
 */
public class HDBManager extends User implements HDBOfficial
{
	/**
	 * Constructs a new {@code HDBManager} with basic user information.
	 *
	 * @param userID    The unique identifier for the HDB Manager.
	 * @param name      The name of the HDB Manager.
	 * @param age       The age of the HDB Manager.
	 * @param isMarried The marital status of the HDB Manager.
	 */
	public HDBManager(String userID, String name, int age,MARITAL_STATUS maritalStatus)
	{
		super(userID, name, age, maritalStatus);
		userType = User.USER_TYPE.HDB_MANAGER;
	}

	/**
	 * Constructs a new {@code HDBManager} with basic user information and a password.
	 *
	 * @param userID    The unique identifier for the HDB Manager.
	 * @param name      The name of the HDB Manager.
	 * @param age       The age of the HDB Manager.
	 * @param isMarried The marital status of the HDB Manager.
	 * @param password  The password for the HDB Manager's account.
	 */
	public HDBManager(String userID, String name, int age, MARITAL_STATUS maritalStatus, String password)
	{
		super(userID, name, age, maritalStatus, password);
		userType = User.USER_TYPE.HDB_MANAGER;
	}
	
}
