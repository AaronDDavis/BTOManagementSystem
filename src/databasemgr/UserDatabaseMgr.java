package databasemgr;

import java.util.List;
import java.util.stream.Collectors;

import database.IDatabase;
import user.User;

/**
 * Manages operations on a database of {@link User} objects.
 * <p>
 * This class provides methods for retrieving user data from a database,
 * specifically by a unique user ID. It implements the {@link IDatabaseMgr}
 * interface for managing {@link User} objects.
 * </p>
 */
public class UserDatabaseMgr implements IDatabaseMgr<User> {
	
	/**
	 * Retrieves a {@link User} object from the database using their unique user ID.
	 * <p>
	 * The method filters the database's data list to find a user with a matching
	 * ID. Since user IDs (like NRICs) are unique, it is expected to find at most
	 * one matching user.
	 * </p>
	 *
	 * @param database The database containing all user data.
	 * @param userID   The unique ID of the user to retrieve.
	 * @return The {@link User} object with the matching ID, or {@code null} if no
	 * user is found.
	 */
	public User getUser(IDatabase<User> database, String userID) {
		List<User> matchedUsers = database.getDataList()
				.stream()
				.filter(user -> user.getUserID().equals(userID))
				.collect(Collectors.toList());

		if (matchedUsers != null && !matchedUsers.isEmpty()) {
			// User IDs are unique, so there will be at most one match.
			return matchedUsers.get(0);
		} else {
			// Return null if no user is found with the given ID.
			return null;
		}
	}
}