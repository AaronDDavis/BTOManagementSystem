package display;

import user.User;

/**
 * A class responsible for displaying information about a {@link User} object.
 * <p>
 * This class implements the {@link IDisplayer} interface and provides a specific
 * implementation for formatting and printing the details of a user to the
 * console. It is designed to present key user information in a clear and
 * structured format.
 * </p>
 */
public class UserDisplayer implements IDisplayer<User> {

	/**
	 * Displays the details of a given {@link User} object to the standard output.
	 * <p>
	 * The output includes the user's name, user ID, password, age, and marital status.
	 * The display is formatted for readability with newlines and tabs.
	 * </p>
	 *
	 * @param user The {@link User} object to be displayed.
	 */
	@Override
	public void display(User user) {
		System.out.println("\n\n\n\f");
		System.out.println("User Details:");
		System.out.println("\tName:\t\t" + user.getName());
		System.out.println("\tUser ID:\t" + user.getUserID());
		System.out.println("\tPassword:\t" + user.getPassword());
		System.out.println("\tAge:\t\t" + user.getAge());
		System.out.println("\tMarital Status:\t" + user.getMaritalStatus().name().toLowerCase());
		System.out.println("\n\n");
	}
}