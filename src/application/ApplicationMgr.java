package application;

import misc.IDCreator;
import project.Project;
import user.User;

/**
 * Manages the creation of various types of application objects.
 * <p>
 * This class serves as a factory for creating {@link Application} objects,
 * encapsulating the logic for instantiating concrete subclasses based on the
 * specified application type. It handles the generation of unique application IDs
 * and provides overloaded methods to accommodate different creation scenarios,
 * such as creating an application with a predefined status or ID.
 * </p>
 *
 */
public class ApplicationMgr {
	
	/**
	 * Creates a new application with a generated ID and a specified status.
	 *
	 * @param user            The user submitting the application.
	 * @param project         The project associated with the application.
	 * @param applicationType The type of application to create (e.g., BTO, registration).
	 * @param applicationStatus The initial status to set for the new application.
	 * @return A new {@link Application} instance of the specified type.
	 */
	public Application create(User user, Project project, ApplicationType applicationType, ApplicationStatus applicationStatus) {
		return create(IDCreator.createApplicationID(), user, project, applicationType, applicationStatus);
	}

	/**
	 * Creates a new application with a provided ID and a specified status.
	 *
	 * @param applicationID     The unique ID for the new application.
	 * @param user              The user submitting the application.
	 * @param project           The project associated with the application.
	 * @param applicationType   The type of application to create.
	 * @param applicationStatus The initial status to set for the new application.
	 * @return A new {@link Application} instance of the specified type.
	 * @throws IllegalArgumentException if the provided application type is invalid.
	 */
	public Application create(String applicationID, User user, Project project, ApplicationType applicationType, ApplicationStatus applicationStatus) throws IllegalArgumentException {
		Application application = create(applicationID, user, project, applicationType);
		application.updateStatus(applicationStatus);
		return application;
	}

	/**
	 * Creates a new application with a generated ID and the default 'PENDING' status.
	 *
	 * @param user            The user submitting the application.
	 * @param project         The project associated with the application.
	 * @param applicationType The type of application to create.
	 * @return A new {@link Application} instance of the specified type.
	 */
	public Application create(User user, Project project, ApplicationType applicationType) {
		return create(IDCreator.createApplicationID(), user, project, applicationType);
	}

	/**
	 * Creates a new application with a provided ID and the default 'PENDING' status.
	 * <p>
	 * This method acts as the core factory method, instantiating the correct
	 * subclass based on the {@code applicationType} parameter.
	 * </p>
	 *
	 * @param applicationID     The unique ID for the new application.
	 * @param user              The user submitting the application.
	 * @param project           The project associated with the application.
	 * @param applicationType   The type of application to create.
	 * @return A new {@link Application} instance of the specified type.
	 * @throws IllegalArgumentException if the provided application type is invalid.
	 */
	public Application create(String applicationID, User user, Project project, ApplicationType applicationType) throws IllegalArgumentException {
		Application application;

		if (applicationType.equals(ApplicationType.BTO_APPLICATION))
			application = new BTOApplication(applicationID, user, project);
		else if (applicationType.equals(ApplicationType.PROJECT_REGISTRATION))
			application = new ProjectRegistration(applicationID, user, project);
		else if (applicationType.equals(ApplicationType.WITHDRAWAL_APPLICATION))
			application = new WithdrawalApplication(applicationID, user, project);
		else {
			throw new IllegalArgumentException("Invalid application type: " + applicationType);
		}

		return application;
	}
}