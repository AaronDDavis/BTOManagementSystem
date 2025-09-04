package user;

import java.util.ArrayList;
import java.util.List;

import application.Application;
import project.Project;

/**
 * Represents an HDB officer, who is also an applicant and has additional
 * attributes related to project management and registration.
 * HDB Officers can join projects, register projects, and have a list of
 * projects they are prohibited from applying to. They can also have project registrations.
 */
public class HDBOfficer extends Applicant implements HDBOfficial
{
	private List<Project> joinedProjects;
	private List<Project> registeredProjects;
	private List<Project> prohibitedProjects;
	private List<Application> projectRegistration;

	/**
	 * Constructs a new {@code HDBOfficer} with basic user information.
	 * Initializes the lists for joined, registered, prohibited projects,
	 * and project registrations.
	 *
	 * @param userID    The unique identifier for the HDB officer.
	 * @param name      The name of the HDB officer.
	 * @param age       The age of the HDB officer.
	 * @param isMarried The marital status of the HDB officer.
	 */
	public HDBOfficer(String userID, String name, int age, MARITAL_STATUS maritalStatus)
	{
		super(userID, name, age, maritalStatus);
		userType = User.USER_TYPE.HDB_OFFICER;
		joinedProjects = new ArrayList<>();
		registeredProjects = new ArrayList<>();
		prohibitedProjects = new ArrayList<>();
		projectRegistration = new ArrayList<>();
	}

	/**
	 * Constructs a new {@code HDBOfficer} with basic user information and a password.
	 * Initializes the lists for joined, registered, prohibited projects,
	 * and project registrations.
	 *
	 * @param userID    The unique identifier for the HDB officer.
	 * @param name      The name of the HDB officer.
	 * @param age       The age of the HDB officer.
	 * @param isMarried The marital status of the HDB officer.
	 * @param password  The password for the HDB officer's account.
	 */
	public HDBOfficer(String userID, String name, int age, MARITAL_STATUS maritalStatus, String password)
	{
		super(userID, name, age, maritalStatus, password);
		userType = User.USER_TYPE.HDB_OFFICER;
		joinedProjects = new ArrayList<>();
		registeredProjects = new ArrayList<>();
		prohibitedProjects = new ArrayList<>();
		projectRegistration = new ArrayList<>();
	}
	
	/**
	 * Returns the list of projects the HDB officer has joined.
	 *
	 * @return An {@code List} of {@code Project} objects.
	 */
	public List<Project> getJoinedProjects() {
		return joinedProjects;
	}

	/**
	 * Sets the list of projects the HDB officer has joined.
	 *
	 * @param joinedProjects An {@code List} of {@code Project} objects to set.
	 */
	public void setJoinedProjects(List<Project> joinedProjects) {
		this.joinedProjects = joinedProjects;
	}

	/**
	 * Returns the list of projects the HDB officer has registered.
	 *
	 * @return An {@code List} of {@code Project} objects.
	 */
	public List<Project> getRegisteredProjects() {
		return registeredProjects;
	}

	/**
	 * Sets the list of projects the HDB officer has registered.
	 *
	 * @param registeredProjects An {@code List} of {@code Project} objects to set.
	 */
	public void setRegisteredProjects(List<Project> registeredProjects) {
		this.registeredProjects = registeredProjects;
	}
	
	/**
	 * Returns the list of projects the HDB officer is prohibited from applying to.
	 * This list is derived from the joined and registered projects.
	 *
	 * @return An {@code List} of {@code Project} objects.
	 */
	public List<Project> getProhibitedProjects() {
		return prohibitedProjects;
	}

	/**
	 * Sets the list of projects the HDB officer is prohibited from applying to.
	 * Note: It's generally better to update joined or registered projects, which will then
	 * update the prohibited projects list.
	 *
	 * @param prohibitedProjects An {@code List} of {@code Project} objects to set.
	 */
	public void setProhibitedProjects(List<Project> prohibitedProjects) {
		this.prohibitedProjects = prohibitedProjects;
	}

	/**
	 * Returns the list of project applications handled by this HDB officer.
	 *
	 * @return An {@code List} of {@code Application} objects.
	 */
	public List<Application> getProjectRegistration() {
		return projectRegistration;
	}

	/**
	 * Sets the list of project applications handled by this HDB officer.
	 *
	 * @param projectRegistration An {@code List} of {@code Application} objects to set.
	 */
	public void setProjectRegistration(List<Application> projectRegistration) {
		this.projectRegistration = projectRegistration;
	}
	
}
