package application;

import project.Project;
import user.User;

/**
 * An abstract base class representing a generic application.
 * 
 * This class provides a foundational structure for various types of applications,
 * such as BTO applications or project registrations. It encapsulates essential
 * information common to all applications, including a unique identifier, the
 * associated user and project, and the application's current status and type.
 * 
 */
public abstract class Application {
	
	/**
	 * The unique identifier for the application.
	 */
	private String ID;
	
	/**
	 * The project associated with this application.
	 */
	private Project project;
	
	/**
	 * The user who submitted this application.
	 */
	private User user;
		
	/**
	 * The current status of the application.
	 */
	protected ApplicationStatus status;
	
	/**
	 * The type of the application.
	 */
	protected ApplicationType applicationType;
	
	/**
	 * Constructs a new Application instance.
	 *
	 * @param ID      The unique identifier for the application.
	 * @param user    The user who submitted the application.
	 * @param project The project to which the application is related.
	 */
	public Application(String ID, User user, Project project) {
		this.ID = ID;
		this.user = user;
		this.project = project;
		this.status = ApplicationStatus.PENDING;
	}
	
	/**
	 * Retrieves the unique identifier of the application.
	 *
	 * @return The application's ID.
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Retrieves the project associated with this application.
	 *
	 * @return The related Project object.
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Retrieves the user who submitted this application.
	 *
	 * @return The User object for the applicant.
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Retrieves the current status of the application.
	 *
	 * @return The current APPLICATION_STATUS.
	 */
	public ApplicationStatus getStatus() {
		return status;
	}

	/**
	 * Retrieves the type of the application.
	 *
	 * @return The APPLICATION_TYPE of this application.
	 */
	public ApplicationType getApplicationType() {
		return applicationType;
	}

	/**
	 * Sets a new status for the application.
	 *
	 * @param status The new APPLICATION_STATUS to be set.
	 */
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
	
	/**
	 * Abstract method to update the status of the application.
	 * <p>
	 * Concrete subclasses must implement this method to define the specific
	 * logic for status transitions.
	 * </p>
	 *
	 * @param status The new status to update to.
	 * @return {@code true} if the status was successfully updated, {@code false} otherwise.
	 */
	public abstract boolean updateStatus(ApplicationStatus status);
}