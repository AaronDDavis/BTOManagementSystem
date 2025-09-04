package application;

import project.Project;
import user.User;

/**
 * Represents a specific type of application for withdrawing from a project.
 * <p>
 * This class extends the generic {@link Application} class, providing a concrete
 * implementation for the withdrawal process. It sets its application type to
 * {@code WITHDRAWAL_APPLICATION} and defines specific rules for status updates.
 * </p>
 *
 */
public class WithdrawalApplication extends Application {

	/**
	 * Constructs a new WithdrawalApplication instance.
	 *
	 * @param ID      The unique identifier for the application.
	 * @param user    The user who submitted the withdrawal request.
	 * @param project The project from which the user is withdrawing.
	 */
	public WithdrawalApplication(String ID, User user, Project project) {
		super(ID, user, project);
		this.applicationType = ApplicationType.WITHDRAWAL_APPLICATION;
	}

	/**
	 * Updates the status of the withdrawal application.
	 * <p>
	 * This method enforces specific business logic, preventing the status
	 * of a withdrawal application from being set to 'BOOKED' or 'WITHDRAWN'.
	 * This ensures that these status changes are handled by other, more
	 * appropriate business processes.
	 * </p>
	 *
	 * @param status The new {@link APPLICATION_STATUS} to be set.
	 * @return {@code true} if the status was successfully updated (i.e., not
	 * 'BOOKED' or 'WITHDRAWN'), {@code false} otherwise.
	 */
	@Override
	public boolean updateStatus(ApplicationStatus status) {
		if (!(status.equals(ApplicationStatus.BOOKED) || status.equals(ApplicationStatus.WITHDRAWN))) {
			this.setStatus(status);
			return true;
		} else {
			return false;
		}
	}
}