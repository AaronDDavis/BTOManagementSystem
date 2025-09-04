package databasemgr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.IDatabase;
import misc.CheckType;
import project.Project;
import user.HDBOfficer;
import user.User;

/**
 * Manages operations on a database of {@link Project} objects.
 * <p>
 * This class extends {@link ItemDatabaseMgr} and provides specialized methods for
 * retrieving and filtering project data based on the user's role and
 * specific conditions. It uses Java Streams for efficient data filtering.
 * </p>
 */
public class ProjectDatabaseMgr extends ItemDatabaseMgr<Project> {

	/**
	 * Retrieves a list of projects relevant to a given user.
	 * <p>
	 * This method defaults to a {@code condition} of {@code false}, which
	 * may be used by overloaded methods for specific filtering logic.
	 * </p>
	 *
	 * @param database The database containing all project data.
	 * @param user     The user whose projects are to be retrieved.
	 * @return A {@link List} of filtered {@link Project} objects.
	 */
	@Override
	public List<Project> getData(IDatabase<Project> database, User user) {
		return getData(database, user, false);
	}
	
	/**
	 * Retrieves a list of projects based on the user's role and a specified condition.
	 * <p>
	 * The filtering logic depends on the user's type:
	 * <ul>
	 * <li>If the user is an {@link HDBManager}, all projects are returned.</li>
	 * <li>If the user is an {@link HDBOfficer}, they see all visible projects
	 * except those they are prohibited from.</li>
	 * <li>If the user is an {@link Applicant}, they see all visible projects.</li>
	 * </ul>
	 * The {@code condition} parameter is currently not used for all user types,
	 * but is intended for future use or to distinguish specific behaviors, such
	 * as filtering for projects created by an HDB Manager.
	 * </p>
	 *
	 * @param database  The database containing all project data.
	 * @param user      The user whose projects are to be retrieved.
	 * @param condition A boolean flag used for specific filtering logic (e.g., as 'createdProjects' for HDB Managers).
	 * @return A {@link List} of filtered {@link Project} objects.
	 */
	public List<Project> getData(IDatabase<Project> database, User user, boolean condition) {
		ArrayList<Project> matchedProjects = new ArrayList<>();
		
		if (CheckType.isHDBManager(user)) {
			// HDB Managers can see all projects in the database.
			matchedProjects.addAll(database.getDataList());
		} else if (CheckType.isHDBOfficer(user)) {
			// HDB Officers see all visible projects, excluding those they are prohibited from.
			matchedProjects.addAll(database.getDataList()
					.stream()
					.filter(Project::isVisible)
					.filter(project -> !((HDBOfficer) user).getProhibitedProjects().contains(project))
					.collect(Collectors.toList()));
		} else if (CheckType.isApplicant(user)) {
			// Applicants only see projects that are marked as visible.
			matchedProjects.addAll(database.getDataList()
					.stream()
					.filter(Project::isVisible)
					.collect(Collectors.toList()));
		}

		return matchedProjects;
	}
	
	/**
	 * Retrieves a single {@link Project} object by its unique ID.
	 *
	 * @param projectDatabase The database containing all project data.
	 * @param projectID       The unique ID of the project to retrieve.
	 * @return The {@link Project} object with the matching ID.
	 */
	public Project getData(IDatabase<Project> projectDatabase, String projectID) {
		ArrayList<Project> matchedProjects = new ArrayList<>();
		matchedProjects.addAll(projectDatabase.getDataList()
				.stream()
				.filter(project -> project.getID().equals(projectID))
				.collect(Collectors.toList()));
				
		// It's safer to check if the list is not empty before returning the first element.
		if (!matchedProjects.isEmpty()) {
			return matchedProjects.get(0);
		}
		
		return null; // Or throw an exception if not found.
	}
}