package userctrl;

import java.util.List;
import java.util.stream.Collectors;

import application.Application;
import application.ApplicationStatus;
import database.IDatabase;
import databasemgr.ApplicationDatabaseMgr;
import databasemgr.ProjectDatabaseMgr;
import display.ApplicationDisplayer;
import display.ProjectDisplayer;
import display.ReportDisplayer;
import misc.CheckType;
import project.Project;
import project.ProjectInterface;
import project.ProjectMgr;
import user.Applicant;
import user.HDBManager;
import user.User;

/**
 * Manages operations that an HDB Manager can perform within the system.
 * This includes viewing, creating, editing, and removing projects,
 * managing applications, updating application statuses, and generating reports on applicants.
 */
public class HDBManagerMgr {

	/**
	 * Sorts a list of projects by name.
	 *
	 * @param projectList   The list of projects to sort.
	 * @param isAscending   {@code true} to sort projects in ascending order, {@code false} for descending.
	 */
	public void sortProjects(List<Project> projectList, boolean isAscending) {
		(new ProjectMgr()).sort(projectList, isAscending);
	}
	
	/**
	 * Retrieves a list of all projects in the database.
	 *
	 * @param manager         The HDB Manager performing the action (for context, though not directly used in the implementation).
	 * @param projectDatabase The database containing project information.
	 * @return A {@code List} of {@code Project} objects.
	 */
	public List<Project> getProjects(HDBManager manager, IDatabase<Project> projectDatabase) {
		return new ProjectDatabaseMgr().getData(projectDatabase, manager);
	}
	
	/**
	 * Retrieves a filtered list of all projects in the database.
	 *
	 * @param manager         The HDB Manager performing the action.
	 * @param projectDatabase The database containing project information.
	 * @param attribute_name  The name of the project attribute to filter by (e.g., "neighbourhood").
	 * @param value           The value to filter the attribute by.
	 * @return A {@code List} of {@code Project} objects, filtered by the specified attribute and value.
	 */
	public List<Project> getProjects(HDBManager manager, IDatabase<Project> projectDatabase, String attribute_name, Object value) {
		return new ProjectMgr().filter(
				new ProjectDatabaseMgr().getData(projectDatabase, manager),
				attribute_name, value);
	}

	/**
	 * Displays a list of projects to the console.
	 *
	 * @param projectList The list of {@code Project} objects to display.
	 */
	public void displayProject(List<Project> projectList) {
		(new ProjectDisplayer()).display(projectList);
	}

	/**
	 * Displays details of a single project to the console.
	 *
	 * @param project The {@code Project} object to display.
	 */
	public void displayProject(Project project) {
		(new ProjectDisplayer()).display(project);
	}

	/**
	 * Creates a new project by interacting with the user via a command-line interface.
	 * <p>
	 * This method delegates the project creation logic to the {@link ProjectInterface}
	 * and then adds the newly created project to the project database.
	 * </p>
	 *
	 * @param manager         The HDB Manager creating the project.
	 * @param projectDatabase The database to store the new project.
	 * @param userDatabase    The database containing user information (for assigning officers to the project).
	 */
	public void createProject(HDBManager manager, IDatabase<Project> projectDatabase, IDatabase<User> userDatabase) {
		(new ProjectDatabaseMgr()).add(projectDatabase, (new ProjectInterface()).createProject(manager, userDatabase));
	}
	
	/**
	 * Edits an existing project by interacting with the user.
	 * <p>
	 * This method delegates the editing process to the {@link ProjectInterface},
	 * which prompts the user for which details to modify.
	 * </p>
	 *
	 * @param project The project to be edited.
	 */
	public void editProject(Project project) {
		(new ProjectInterface()).editProject(project);
	}
	
	/**
	 * Toggles the visibility status of a project.
	 * <p>
	 * This method calls the {@link ProjectMgr} to change the visibility of the
	 * project and returns a descriptive string of the new status.
	 * </p>
	 *
	 * @param project The project whose visibility is to be toggled.
	 * @return A string indicating the new visibility status ("visible" or "not visible").
	 */
	public String toggleProjectVisibility(Project project) {
		new ProjectMgr().toggleVisibility(project);
		return project.isVisible() ? "visible" : "not visible";
	}
	
	/**
	 * Removes a project from the project database.
	 *
	 * @param projectDatabase  The database from which to remove the project.
	 * @param removableProject The project to be removed.
	 */
	public void removeProject(IDatabase<Project> projectDatabase, Project removableProject) {
		projectDatabase.getDataList().remove(removableProject);
	}
	
	/**
	 * Displays a list of applications to the console.
	 *
	 * @param applicationList The list of {@code Application} objects to display.
	 */
	public void displayApplications(List<Application> applicationList) {
		(new ApplicationDisplayer()).display(applicationList);
	}
	
	/**
	 * Retrieves a list of all applications in the database.
	 *
	 * @param manager           The HDB Manager performing the action.
	 * @param applicationDatabase The database containing application information.
	 * @return A {@code List} of all {@code Application} objects.
	 */
	public List<Application> getApplications(HDBManager manager, IDatabase<Application> applicationDatabase) {
		return new ApplicationDatabaseMgr().getData(applicationDatabase, manager);
	}
	
	/**
	 * Updates the status of a specific application.
	 * <p>
	 * This method changes the application's status and then calls a method in {@link UserMgr}
	 * to update the related user's status accordingly (e.g., to grant receipt access).
	 * </p>
	 *
	 * @param application The application to update.
	 * @param newStatus   The new status to set for the application.
	 */
	public void updateStatus(Application application, ApplicationStatus newStatus) {
		application.updateStatus(newStatus);
		(new UserMgr()).updateStatus(application, newStatus);		
	}

	/**
	 * Retrieves a list of applicants who have a ready receipt.
	 * <p>
	 * This method filters the entire user database to find users who are applicants
	 * and whose receipt status is set to ready.
	 * </p>
	 *
	 * @param userDatabase The database containing all user information.
	 * @return A {@code List} of {@code User} objects who are applicants with ready receipts.
	 */
	public List<User> getReceiptReadyUsers(IDatabase<User> userDatabase) {
		return userDatabase.getDataList()
				.stream()
				.filter(user -> CheckType.isApplicant(user))
				.filter(user -> ((Applicant) user).isReceiptReady())
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves a filtered list of applicants with ready receipts based on project room type or marital status.
	 *
	 * @param userDatabase The database containing user information.
	 * @param filter       The attribute to filter by ("RoomType" or "Marital Status").
	 * @param value        The value to filter by.
	 * @return A {@code List} of {@code User} objects who are applicants with ready receipts, filtered as specified.
	 */
	public List<User> getReceiptReadyUsers(IDatabase<User> userDatabase, String filter, Object value) {
		if (filter.equalsIgnoreCase("RoomType")) {
			return getReceiptReadyUsers(userDatabase)
					.stream()
					.filter(user -> ((Applicant) user).getAppliedProject().getRoomType().equals(value))
					.collect(Collectors.toList());
		} else if (filter.equalsIgnoreCase("Marital Status")) {
			return getReceiptReadyUsers(userDatabase)
					.stream()
					.filter(user -> user.getMaritalStatus().equals(value))
					.collect(Collectors.toList());
		} else {
			return null;
		}
	}
	
	/**
	 * Converts a list of generic {@link User} objects to a list of {@link Applicant} objects.
	 * <p>
	 * This is a utility method for downcasting a list of users, assuming all users in the
	 * list are indeed applicants.
	 * </p>
	 *
	 * @param userList The list of users to convert.
	 * @return A {@code List} of {@code Applicant} objects.
	 */
	public List<Applicant> toApplicantList(List<User> userList) {
		return userList.stream().map(user -> (Applicant) user).collect(Collectors.toList());
	}

	/**
	 * Displays a report for a list of applicants.
	 * <p>
	 * This method uses a {@link ReportDisplayer} to print a formatted report for each
	 * applicant in the provided list.
	 * </p>
	 *
	 * @param applicantList The list of {@code Applicant} objects to display the report for.
	 */
	public void displayReport(List<Applicant> applicantList) {
		new ReportDisplayer().display(applicantList);
	}
	
	/**
	 * Displays a report for a single applicant.
	 *
	 * @param applicant The {@code Applicant} object to display the report for.
	 */
	public void displaySingleReport(Applicant applicant) {
		new ReportDisplayer().display(applicant);
	}

	/**
	 * Retrieves a list of projects that are directly managed by a given HDB Manager.
	 * <p>
	 * This method filters the list of all projects to find those where the
	 * {@link HDBManager} object is the same as the one provided.
	 * </p>
	 *
	 * @param manager         The HDB Manager whose projects are to be retrieved.
	 * @param projectDatabase The database containing project information.
	 * @return A {@code List} of {@code Project} objects managed by the HDB Manager.
	 */
	public List<Project> getOwnProjects(HDBManager manager, IDatabase<Project> projectDatabase) {
		List<Project> projectList = new ProjectDatabaseMgr().getData(projectDatabase, manager);

		return projectList.stream()
				.filter(project -> project.getManager() == manager)
				.collect(Collectors.toList());
	}
}