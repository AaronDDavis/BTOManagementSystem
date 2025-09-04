package userctrl;

import application.Application;
import application.ApplicationStatus;
import application.ApplicationType;
import database.IDatabase;
import databasemgr.UserDatabaseMgr;
import display.UserDisplayer;
import user.Applicant;
import user.HDBManager;
import user.HDBOfficer;
import user.User;

/**
 * Manages user-related operations such as creation, authentication,
 * checking user details, changing passwords, and updating user statuses
 * based on application outcomes.
 */
public class UserMgr {

	/**
	 * Checks if the provided user details (NRIC and age) are valid.
	 *
	 * @param nric The National Registration Identity Card number.
	 * @param age  The age of the user.
	 * @return {@code true} if all details are valid, {@code false} otherwise.
	 */
	public boolean checkUserDetails(String nric, int age) {
		return checkNRIC(nric) && checkAge(age);
	}
	
	/**
	 * Creates a new {@code Applicant} object with the given details.
	 * <p>
	 * This method validates the user's NRIC and age before creating the applicant object.
	 * It also handles the case where no password is provided, setting a default one.
	 * </p>
	 *
	 * @param userID         The unique identifier for the applicant (NRIC).
	 * @param name           The name of the applicant.
	 * @param password       The password for the applicant's account (optional).
	 * @param age            The age of the applicant.
	 * @param maritalStatus  The marital status of the applicant.
	 * @param canApply       Whether the applicant can currently apply for a project.
	 * @param isWithdrawing  Whether the applicant is currently withdrawing an application.
	 * @param isReceiptReady Whether the receipt for the application is ready.
	 * @return A new {@code Applicant} object if details are valid, {@code null} otherwise.
	 */
	public Applicant createApplicant(String userID, String name, String password, int age, User.MARITAL_STATUS maritalStatus, boolean canApply,
			boolean isWithdrawing, boolean isReceiptReady) {
		if (checkUserDetails(userID, age)) {
			Applicant applicant;
			if (password == null || password.length() == 0) {
				applicant = new Applicant(userID, name, age, maritalStatus);
			} else {
				applicant = new Applicant(userID, name, age, maritalStatus, password);
			}
			applicant.setCanApply(canApply);
			applicant.setWithdrawing(isWithdrawing);
			applicant.setReceiptReady(isReceiptReady);
			return applicant;
		} else {
			return null;
		}
	}
	
	/**
	 * Creates a new {@code HDBOfficer} object with the given details.
	 * <p>
	 * This method validates the user's NRIC and age before creating the officer object.
	 * It also handles the case where no password is provided, setting a default one.
	 * </p>
	 *
	 * @param userID         The unique identifier for the officer (NRIC).
	 * @param name           The name of the officer.
	 * @param password       The password for the officer's account (optional).
	 * @param age            The age of the officer.
	 * @param maritalStatus  The marital status of the officer.
	 * @param canApply       Whether the officer can currently apply for a project.
	 * @param isWithdrawing  Whether the officer is currently withdrawing an application.
	 * @param isReceiptReady Whether the receipt for the officer's application is ready.
	 * @return A new {@code HDBOfficer} object if details are valid, {@code null} otherwise.
	 */
	public HDBOfficer createHDBOfficer(String userID, String name, String password, int age, User.MARITAL_STATUS maritalStatus,
			boolean canApply, boolean isWithdrawing, boolean isReceiptReady) {
		if (checkUserDetails(userID, age)) {
			HDBOfficer officer;
			if (password == null || password.length() == 0) {
				officer = new HDBOfficer(userID, name, age, maritalStatus);
			} else {
				officer = new HDBOfficer(userID, name, age, maritalStatus, password);
			}
			officer.setCanApply(canApply);
			officer.setWithdrawing(isWithdrawing);
			officer.setReceiptReady(isReceiptReady);
			return officer;
		} else {
			return null;
		}
	}
	
	/**
	 * Creates a new {@code HDBManager} object with the given details.
	 * <p>
	 * This method validates the user's NRIC and age before creating the manager object.
	 * It also handles the case where no password is provided, setting a default one.
	 * </p>
	 *
	 * @param userID        The unique identifier for the manager (NRIC).
	 * @param name          The name of the manager.
	 * @param password      The password for the manager's account (optional).
	 * @param age           The age of the manager.
	 * @param maritalStatus The marital status of the manager.
	 * @return A new {@code HDBManager} object if details are valid, {@code null} otherwise.
	 */
	public HDBManager createHDBManager(String userID, String name, String password, int age, User.MARITAL_STATUS maritalStatus) {
		if (checkUserDetails(userID, age)) {
			HDBManager manager;
			if (password == null || password.length() == 0) {
				manager = new HDBManager(userID, name, age, maritalStatus);
			} else {
				manager = new HDBManager(userID, name, age, maritalStatus, password);
			}
			return manager;
		} else {
			return null;
		}
	}

	/**
	 * Authenticates a user by checking their User ID and password against the user database.
	 *
	 * @param userDatabase The database containing user information.
	 * @param userID       The User ID to authenticate.
	 * @param password     The password to verify.
	 * @return The {@code User} object if authentication is successful, {@code null} otherwise.
	 */
	public User getUser(IDatabase<User> userDatabase, String userID, String password) {
		User user = (new UserDatabaseMgr()).getUser(userDatabase, userID);

		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			return null;
		}
	}

	/**
	 * Retrieves a user from the database based on their User ID.
	 *
	 * @param userDatabase The database containing user information.
	 * @param userID       The User ID to retrieve.
	 * @return The {@code User} object if found, {@code null} otherwise.
	 */
	public User getUser(IDatabase<User> userDatabase, String userID) {
		return (new UserDatabaseMgr()).getUser(userDatabase, userID);
	}

	/**
	 * Validates the format of a Singapore NRIC (National Registration Identity Card).
	 * <p>
	 * It checks for the correct length (9 characters), the correct starting character ('S' or 'T'),
	 * and verifies that the 8th character is a letter while the numerical part is an integer.
	 * </p>
	 *
	 * @param nric The NRIC string to validate.
	 * @return {@code true} if the NRIC format is valid, {@code false} otherwise.
	 */
	public boolean checkNRIC(String nric) {
		if (nric.length() != 9) {
			return false;
		} else if (!(nric.startsWith("S") || nric.startsWith("T"))) {
			return false;
		} else if (!Character.isLetter(nric.charAt(8))) {
			return false;
		} else {
			try {
				Integer.parseInt(nric.substring(1, 8));
			} catch (NumberFormatException e) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * Checks if the provided age is a positive value.
	 *
	 * @param age The age to check.
	 * @return {@code true} if the age is greater than 0, {@code false} otherwise.
	 */
	public boolean checkAge(int age) {
		return age > 0;
	}

	/**
	 * Allows a user to change their password, provided the old password is correct.
	 *
	 * @param user        The {@code User} object whose password needs to be changed.
	 * @param oldPassword The current password of the user.
	 * @param newPassword The new password to set.
	 * @return {@code true} if the password was changed successfully, {@code false} otherwise (incorrect old password).
	 */
	public boolean changePassword(User user, String oldPassword, String newPassword) {
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Updates the status of a user based on the outcome of their application.
	 * <p>
	 * This method handles different application types and statuses:
	 * <ul>
	 * <li>For successful **Project Registration**, it updates the officer's joined and registered projects.</li>
	 * <li>For successful **Withdrawal Applications**, it resets the applicant's applied project and eligibility.</li>
	 * <li>For unsuccessful **BTO Applications**, it resets the applicant's applied project and eligibility.</li>
	 * <li>For booked **BTO Applications**, it marks the applicant as unable to apply for new projects and sets the receipt to ready.</li>
	 * </ul>
	 * </p>
	 *
	 * @param application The {@code Application} object whose status has been updated.
	 * @param newStatus   The new status of the application.
	 */
	public void updateStatus(Application application, ApplicationStatus newStatus) {
		if (application.getApplicationType().equals(ApplicationType.PROJECT_REGISTRATION)) {
			if (newStatus.equals(ApplicationStatus.SUCCESSFUL)) {
				((HDBOfficer) (application.getUser()))
				.getJoinedProjects().add(application.getProject());
				((HDBOfficer) (application.getUser()))
				.getRegisteredProjects().remove(application.getProject());
				application.getProject().getOfficers().add((HDBOfficer) application.getUser());
			}
		} else if (application.getApplicationType().equals(ApplicationType.WITHDRAWAL_APPLICATION)) {
			if (newStatus.equals(ApplicationStatus.SUCCESSFUL)) {
				((Applicant) (application.getUser()))
				.setAppliedProject(null);
				((Applicant) (application.getUser()))
				.setCanApply(true);
				((Applicant) (application.getUser()))
				.setWithdrawing(false);
				((Applicant) (application.getUser()))
				.setReceiptReady(false);
			} else {
				((Applicant) (application.getUser()))
				.setCanApply(true);
				((Applicant) (application.getUser()))
				.setWithdrawing(false);
			}
		} else if (application.getApplicationType().equals(ApplicationType.BTO_APPLICATION)) {
			if (newStatus.equals(ApplicationStatus.UNSUCCESSFUL)) {
				((Applicant) (application.getUser()))
				.setAppliedProject(null);
				((Applicant) (application.getUser()))
				.setCanApply(true);
				((Applicant) (application.getUser()))
				.setReceiptReady(false);
			} else if (newStatus.equals(ApplicationStatus.BOOKED)) {
				((Applicant) (application.getUser()))
				.setCanApply(false);
				((Applicant) (application.getUser()))
				.setReceiptReady(true);
			}
		}
	}
	
	/**
	 * Displays user information.
	 *
	 * @param user The user object to be displayed.
	 */
	public void display(User user) {
		(new UserDisplayer()).display(user);
	}
}