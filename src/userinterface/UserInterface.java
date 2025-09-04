package userinterface;

import java.util.Scanner;

import application.Application;
import database.IDatabase;
import enquiry.Enquiry;
import misc.CheckType;
import project.Project;
import user.Applicant;
import user.HDBManager;
import user.HDBOfficer;
import user.User;
import userctrl.UserMgr;

/**
 * Provides the initial user interface for login and basic user account management.
 * Redirects users to their specific interfaces based on their role.
 */
public class UserInterface {
	
	private Scanner sc = new Scanner(System.in);

	/**
	 * Allows a user to log in to the system by providing their User ID and password.
	 *
	 * @param userDatabase The database containing user information for authentication.
	 * @return The logged-in {@code User} object if credentials are valid, or {@code null} otherwise.
	 */
	public User login(IDatabase<User> userDatabase) {
		UserMgr userMgr = new UserMgr();

		String userID, password;

		System.out.println("Enter UserID:");
		userID = sc.next();
		
		System.out.println("Enter Password:");
		password = sc.next();
		
		return userMgr.getUser(userDatabase, userID, password);
	}
	
	/**
	 * Manages the main menu after a user has logged in. Provides options for
	 * viewing account details, changing password, accessing role-specific options, and logging out.
	 *
	 * @param user The logged-in {@code User} object.
	 * @param projectDatabase The database containing project information.
	 * @param applicationDatabase The database containing application information.
	 * @param enquiryDatabase The database containing enquiry information.
	 * @param userDatabase The database containing user information.
	 */
	public void manage(User user, IDatabase<Project> projectDatabase,
			IDatabase<Application> applicationDatabase, IDatabase<Enquiry> enquiryDatabase, IDatabase<User> userDatabase) {
		int choice;

		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. View Account Details");
			System.out.println("2. Change Password");
			System.out.println("3. View more Options");
			System.out.println("4. Logout");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
			case 1:
				(new UserMgr()).display(user);
				break;
			case 2:
				if (changePassword(user)) {
					System.out.println("\n\n\n\f");
					System.out.println("Password successfully changed!");
					System.out.println("Please relogin.");
					return;
				} else {
					System.out.println("Entered Incorrect Old Password");
				}
				break;
			case 3:
				if (CheckType.isHDBManager(user)) {
					(new HDBManagerInterface()).manage((HDBManager) user, projectDatabase, applicationDatabase, enquiryDatabase, userDatabase);
				} else if (CheckType.isHDBOfficer(user)) {
					System.out.println("Menu:");
					System.out.println("1. Proceed as Applicant");
					System.out.println("2. Proceed as HDBOfficer");
					choice = sc.nextInt();
					sc.nextLine();
		
					if (choice == 1) {
						(new ApplicantInterface()).manage((Applicant) user, projectDatabase, applicationDatabase, enquiryDatabase);
					} else if (choice == 2) {
						(new HDBOfficerInterface()).manage((HDBOfficer) user, projectDatabase, applicationDatabase, enquiryDatabase);
					} else {
						System.out.println("Invalid option");
					}
				} else { // Must be an Applicant
					(new ApplicantInterface()).manage((Applicant) user, projectDatabase, applicationDatabase, enquiryDatabase);
				}
				break;
			case 4:
				System.out.println("Logged out successfully.");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
		}
	}
	
	/**
	 * Allows the logged-in user to change their password.
	 * Prompts for the old password and the new password, and updates it if the old password is correct.
	 *
	 * @param user The logged-in {@code User} object.
	 * @return {@code true} if the password was changed successfully, {@code false} otherwise.
	 */
	private boolean changePassword(User user) {
		UserMgr userMgr = new UserMgr();
		
		System.out.println("Re-enter old password:");
		String oldPassword = sc.next();
		sc.nextLine();
		
		System.out.println("Enter new password:");
		String newPassword = sc.next();
		sc.nextLine();
		
		return userMgr.changePassword(user, oldPassword, newPassword);
	}
}