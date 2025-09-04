package misc;

import application.Application;
import application.ApplicationType;
import user.User;

/**
 * A utility class for checking the type of a user or an application.
 * <p>
 * This class provides a set of static methods to determine the specific subclass
 * of a {@link User} or {@link Application} object by checking its user type or
 * application type. This helps to enforce type-safe operations without
 * requiring explicit casting or instanceof checks in the main business logic.
 * </p>
 */
public class CheckType {

	/**
	 * Checks if a given user is an {@link Applicant}.
	 *
	 * @param user The user object to check.
	 * @return {@code true} if the user's type is {@code APPLICANT}, {@code false} otherwise.
	 */
	public static boolean isApplicant(User user) {
		return user.getUserType().equals(User.USER_TYPE.APPLICANT);
	}
	
	/**
	 * Checks if a given user is an {@link HDBOfficer}.
	 *
	 * @param user The user object to check.
	 * @return {@code true} if the user's type is {@code HDB_OFFICER}, {@code false} otherwise.
	 */
	public static boolean isHDBOfficer(User user) {
		return user.getUserType().equals(User.USER_TYPE.HDB_OFFICER);
	}

	/**
	 * Checks if a given user is an {@link HDBManager}.
	 *
	 * @param user The user object to check.
	 * @return {@code true} if the user's type is {@code HDB_MANAGER}, {@code false} otherwise.
	 */
	public static boolean isHDBManager(User user) {
		return user.getUserType().equals(User.USER_TYPE.HDB_MANAGER);
	}
	
	/**
	 * Checks if a given application is a {@link BTOApplication}.
	 *
	 * @param application The application object to check.
	 * @return {@code true} if the application's type is {@code BTO_APPLICATION}, {@code false} otherwise.
	 */
	public static boolean isBTOApplication(Application application) {
		return application.getApplicationType().equals(ApplicationType.BTO_APPLICATION);
	}

	/**
	 * Checks if a given application is a {@link ProjectRegistration}.
	 *
	 * @param application The application object to check.
	 * @return {@code true} if the application's type is {@code PROJECT_REGISTRATION}, {@code false} otherwise.
	 */
	public static boolean isProjectRegistrationApplication(Application application) {
		return application.getApplicationType().equals(ApplicationType.PROJECT_REGISTRATION);
	}

	/**
	 * Checks if a given application is a {@link WithdrawalApplication}.
	 *
	 * @param application The application object to check.
	 * @return {@code true} if the application's type is {@code WITHDRAWAL_APPLICATION}, {@code false} otherwise.
	 */
	public static boolean isWithdrawalApplication(Application application) {
		return application.getApplicationType().equals(ApplicationType.WITHDRAWAL_APPLICATION);
	}
}