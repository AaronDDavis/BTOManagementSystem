package userctrl;

import java.util.List;

import application.Application;
import application.ApplicationMgr;
import application.ApplicationStatus;
import application.ApplicationType;
import database.IDatabase;
import databasemgr.ApplicationDatabaseMgr;
import databasemgr.IItemDatabaseMgr;
import databasemgr.ProjectDatabaseMgr;
import display.ApplicationDisplayer;
import display.ProjectDisplayer;
import project.Project;
import project.ProjectMgr;
import user.Applicant;
import user.HDBOfficer;

/**
 * Manages operations that an HDB Officer can perform within the system.
 * This includes viewing projects, registering for projects, viewing registered and joined projects,
 * managing applications, and booking flats for applicants.
 */
public class HDBOfficerMgr {

	/**
	 * Updates the application status of an HDB Officer. If the officer is no longer eligible to apply,
	 * the currently applied project (if any) is added to their list of prohibited projects.
	 *
	 * @param officer The HDB Officer whose status is to be updated.
	 */
	public void updateStatus(HDBOfficer officer) {
		if (!officer.canApply()) {
			if (!officer.getProhibitedProjects().contains(officer.getAppliedProject())) {
				officer.getProhibitedProjects().add(officer.getAppliedProject());
			}
		}
	}
	
	/**
	 * Retrieves a list of projects visible to the HDB Officer.
	 *
	 * @param officer         The HDB Officer viewing the projects.
	 * @param projectDatabase The database containing project information.
	 * @return A {@code List} of {@code Project} objects visible to the officer.
	 */
	public List<Project> getProjects(HDBOfficer officer, IDatabase<Project> projectDatabase) {
		return new ProjectDatabaseMgr().getData(projectDatabase, officer);
	}
	
	/**
	 * Displays a list of projects.
	 *
	 * @param projectList The list of {@code Project} objects to display.
	 */
	public void displayProject(List<Project> projectList) {
		(new ProjectDisplayer()).display(projectList);
	}

	/**
	 * Displays details of a single project.
	 *
	 * @param project The {@code Project} object to display.
	 */
	public void displayProject(Project project) {
		(new ProjectDisplayer()).display(project);
	}

	/**
	 * Allows an HDB Officer to register for a project. Checks if the officer is eligible to join
	 * based on the application start and end dates of their currently joined projects.
	 *
	 * @param officer           The HDB Officer registering for the project.
	 * @param project           The project the officer wishes to register for.
	 * @param applicationDatabase The database to store the project registration application.
	 * @return {@code true} if the registration was successful, {@code false} otherwise (e.g., ineligible, invalid index).
	 */
	public boolean registerForProject(HDBOfficer officer, Project project, IDatabase<Application> applicationDatabase) {
		IItemDatabaseMgr<Application> mgr = new ApplicationDatabaseMgr();
		ApplicationMgr appMgr = new ApplicationMgr();
		Application projectApplication;
		
		if (checkJoinEligibility(officer, project)) {
			projectApplication = appMgr.create(officer, project, ApplicationType.PROJECT_REGISTRATION);
			if (mgr.add(applicationDatabase, projectApplication)) {
				officer.getRegisteredProjects().add(project);
				officer.getProhibitedProjects().add(project);
				officer.getProjectRegistration().add(projectApplication);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if an HDB Officer is eligible to join a new project based on the application
	 * start and end dates of the projects they have already joined. An officer cannot join a project
	 * if its application period overlaps with any of their currently joined projects.
	 *
	 * @param officer The HDB Officer attempting to join the project.
	 * @param project The project the officer wants to join.
	 * @return {@code true} if the officer is eligible to join, {@code false} otherwise.
	 */
	public boolean checkJoinEligibility(HDBOfficer officer, Project project) {
		for (Project registeredProject : officer.getJoinedProjects()) {
			if (
					(registeredProject.getApplicationStartDate()
							.isAfter(project.getApplicationStartDate())
							&& registeredProject.getApplicationStartDate()
							.isBefore(project.getApplicationEndDate()))
					||
					(registeredProject.getApplicationEndDate()
							.isAfter(project.getApplicationStartDate())
							&& registeredProject.getApplicationEndDate()
							.isBefore(project.getApplicationEndDate()))
					) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Displays the projects that the HDB Officer has registered for.
	 *
	 * @param officer The HDB Officer whose registered projects are to be displayed.
	 */
	public void displayRegisteredProjects(HDBOfficer officer) {
		displayProject(officer.getRegisteredProjects());
	}
	
	/**
	 * Displays the projects that the HDB Officer has joined.
	 *
	 * @param officer The HDB Officer whose joined projects are to be displayed.
	 */
	public void displayJoinedProjects(HDBOfficer officer) {
		displayProject(officer.getJoinedProjects());
	}
	
	/**
	 * Displays a list of applications.
	 *
	 * @param applicationList The list of {@code Application} objects to display.
	 */
	public void displayApplications(List<Application> applicationList) {
		(new ApplicationDisplayer()).display(applicationList);
	}
	
	/**
	 * Views the project registration applications handled by the HDB Officer.
	 *
	 * @param officer The HDB Officer whose project registrations are to be viewed.
	 */
	public void viewApplicationStatus(HDBOfficer officer) {
		displayApplications(officer.getProjectRegistration());
	}
	
	/**
	 * Retrieves a list of all applications submitted by applicants.
	 *
	 * @param officer           The HDB Officer retrieving the applications (for context).
	 * @param applicationDatabase The database containing application information.
	 * @return A {@code List} of {@code Application} objects submitted by applicants.
	 */
	public List<Application> getApplicantApplications(HDBOfficer officer, IDatabase<Application> applicationDatabase) {
		IItemDatabaseMgr<Application> appDBMgr = new ApplicationDatabaseMgr();
		return appDBMgr.getData(applicationDatabase, officer);
	}

	/**
	 * Allows an HDB Officer to book a flat for an applicant based on their application.
	 * <p>
	 * This method first checks if there are available flats in the project. If so, it
	 * decreases the available count and updates the application status to "Booked". It also
	 * triggers the generation of a receipt for the applicant.
	 * </p>
	 *
	 * @param officer     The HDB Officer performing the booking.
	 * @param application The application for which the flat is to be booked.
	 * @return {@code true} if the booking was successful, {@code false} otherwise (e.g., no flats left).
	 */
	public boolean bookApplicantFlat(HDBOfficer officer, Application application) {
		ProjectMgr projectMgr = new ProjectMgr();
		
		if (projectMgr.decreaseCount(application.getProject())) {
			application.updateStatus(ApplicationStatus.BOOKED);
			((Applicant) application.getUser()).generateReceipt();
			application.getProject().setCount(application.getProject().getCount() - 1);
			return true;
		} else {
			return false;
		}
	}
}