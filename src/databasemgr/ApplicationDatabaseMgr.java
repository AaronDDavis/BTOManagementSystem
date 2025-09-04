package databasemgr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.Application;
import application.ApplicationStatus;
import application.ApplicationType;
import database.IDatabase;
import misc.CheckType;
import user.HDBManager;
import user.HDBOfficer;
import user.User;

/**
 * Manages operations on a database of {@link Application} objects.
 * <p>
 * This class extends {@link ItemDatabaseMgr} and provides specific methods for
 * retrieving and filtering application data based on the user's role and
 * other criteria. It uses Java Streams to efficiently filter the data list.
 * </p>
 */
public class ApplicationDatabaseMgr extends ItemDatabaseMgr<Application> {
	
	/**
	 * Retrieves a list of applications relevant to a given user.
	 * <p>
	 * This method defaults to not processing the user as an applicant.
	 * </p>
	 *
	 * @param applicationDatabase The database containing all application data.
	 * @param user                The user whose applications are to be retrieved.
	 * @return A {@link List} of filtered {@link Application} objects.
	 */
	@Override
	public List<Application> getData(IDatabase<Application> applicationDatabase, User user) {
		return getData(applicationDatabase, user, false);
	}

	/**
	 * Retrieves a list of applications relevant to a given user, with an option to
	 * process the user as an applicant.
	 * <p>
	 * The filtering logic depends on the user's type:
	 * <ul>
	 * <li>If the user is an {@link HDBManager}, it returns all 'PENDING' applications
	 * for projects they manage.</li>
	 * <li>If the user is an {@link HDBOfficer} and not an applicant, it returns all
	 * 'BTO_APPLICATION' types that are not 'BOOKED' for projects they have joined.</li>
	 * </ul>
	 * </p>
	 *
	 * @param applicationDatabase The database containing all application data.
	 * @param user                The user whose applications are to be retrieved.
	 * @param processAsApplicant  A flag to determine if the user should be treated as an applicant.
	 * @return A {@link List} of filtered {@link Application} objects.
	 */
	public List<Application> getData(IDatabase<Application> applicationDatabase, User user, boolean processAsApplicant) {
		List<Application> applicationList = new ArrayList<>();
		if (CheckType.isHDBManager(user)) {
			applicationList = applicationDatabase.getDataList()
					.stream()
					.filter(application -> application.getProject().getManager().equals((HDBManager) user))
					.filter(application -> application.getStatus().equals(ApplicationStatus.PENDING))
					.collect(Collectors.toList());
		} else if (CheckType.isHDBOfficer(user) && !processAsApplicant) {
			applicationList = applicationDatabase.getDataList()
					.stream()
					.filter(application -> ((HDBOfficer) user).getJoinedProjects().contains(application.getProject()))
					.filter(application -> application.getApplicationType().equals(ApplicationType.BTO_APPLICATION))
					.filter(application -> !application.getStatus().equals(ApplicationStatus.BOOKED))
					.collect(Collectors.toList());
		}
		
		return applicationList;
	}

	/**
	 * Retrieves a single {@link Application} object by its unique ID.
	 *
	 * @param applicationDatabase The database containing all application data.
	 * @param applicationID       The unique ID of the application to retrieve.
	 * @return The {@link Application} object with the matching ID, or {@code null}
	 * if no such application is found.
	 */
	public Application getData(IDatabase<Application> applicationDatabase, String applicationID) {
		List<Application> applicationList = applicationDatabase.getDataList().stream()
			.filter(application -> application.getID().equals(applicationID))
			.collect(Collectors.toList());
		
		if (!applicationList.isEmpty()) {
			return applicationList.get(0);
		}
		
		return null; // Or throw an exception if the item is not found.
	}
}