package application;

import project.Project;
import user.User;

/**
 * Represents a specific type of application for project registration.
 * <p>
 * This class extends the generic {@link Application} class, providing a concrete
 * implementation for project registration-specific logic. It sets its
 * application type to {@code PROJECT_REGISTRATION} and provides a constrained
 * implementation of the {@code updateStatus} method.
 * </p>
 *
 */
public class ProjectRegistration extends Application {
	
	/**
	 * Constructs a new ProjectRegistration instance.
	 *
	 * @param ID      The unique identifier for the application.
	 * @param user    The user who submitted the application.
	 * @param project The project to which the registration is related.
	 */
	public ProjectRegistration(String ID, User user, Project project) {
		super(ID, user, project);
		this.applicationType = ApplicationType.PROJECT_REGISTRATION;
	}

	/**
	 * Updates the status of the project registration application.
	 * <p>
	 * This method contains specific business logic to prevent the status from
	 * being updated to 'BOOKED' or 'WITHDRAWN' directly. This ensures that
	 * certain status transitions are controlled and handled elsewhere.
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