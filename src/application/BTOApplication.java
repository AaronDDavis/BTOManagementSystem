package application;

import project.Project;
import user.User;

/**
 * Represents a specific type of application for a Build-To-Order (BTO) project.
 * <p>
 * This class extends the generic {@link Application} class, providing a concrete
 * implementation for BTO-specific logic. It sets its application type to
 * {@code BTO_APPLICATION} and provides its own implementation of the
 * {@code updateStatus} method.
 * </p>
 *
 */
public class BTOApplication extends Application {
	
	/**
	 * Constructs a new BTOApplication instance.
	 *
	 * @param ID      The unique identifier for the application.
	 * @param user    The user who submitted the application.
	 * @param project The BTO project to which the application is related.
	 */
	public BTOApplication(String ID, User user, Project project) {
		super(ID, user, project);
		this.applicationType = ApplicationType.BTO_APPLICATION;
	}

	/**
	 * Updates the status of the BTO application.
	 * <p>
	 * This implementation simply calls the superclass's {@code setStatus} method,
	 * as the status update logic for this specific application type is
	 * straightforward.
	 * </p>
	 *
	 * @param status The new {@link APPLICATION_STATUS} to be set.
	 * @return {@code true} indicating that the status was successfully updated.
	 */
	@Override
	public boolean updateStatus(ApplicationStatus status) {
		this.setStatus(status);
		return true;
	}
}