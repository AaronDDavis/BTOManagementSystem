package userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.Application;
import database.IDatabase;
import enquiry.Enquiry;
import project.Project;
import user.HDBOfficer;
import userctrl.HDBOfficerMgr;

/**
 * Provides a command-line interface for HDB Officers to interact with the system.
 * Allows officers to manage projects they can register for, their applications, and enquiries.
 */
public class HDBOfficerInterface {

	/**
	 * Manages the main menu for an HDB Officer.
	 * <p>
	 * This method first updates the officer's status and then presents the main menu options
	 * for managing projects, applications, and enquiries.
	 * </p>
	 *
	 * @param officer The logged-in HDB Officer.
	 * @param projectDatabase The database containing project information.
	 * @param applicationDatabase The database containing application information.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void manage(HDBOfficer officer, IDatabase<Project> projectDatabase,
			IDatabase<Application> applicationDatabase, IDatabase<Enquiry> enquiryDatabase) {
		Scanner sc = new Scanner(System.in);
		int choice;
		
		(new HDBOfficerMgr()).updateStatus(officer);
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. Manage Projects");
			System.out.println("2. Manage Applications");
			System.out.println("3. Manage Enquiries");
			System.out.println("4. Return to previous page");
			
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
			case 1:
				manageProjects(sc, officer, projectDatabase, applicationDatabase);
				break;
			case 2:
				manageApplications(sc, officer, applicationDatabase);
				break;
			case 3:
				manageEnquiries(officer, enquiryDatabase);
				break;
			case 4:
				System.out.println("Returning to previous page");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
		}
	}
	
	/**
	 * Manages project-related operations for an HDB Officer (registering, viewing registered, viewing joined).
	 *
	 * @param sc				The Scanner Object
	 * @param officer The logged-in HDB Officer.
	 * @param projectDatabase The database containing project information.
	 * @param applicationDatabase The database containing application information.
	 */
	public void manageProjects(Scanner sc, HDBOfficer officer, IDatabase<Project> projectDatabase, IDatabase<Application> applicationDatabase) {
		int choice;
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. Register for a Project");
			System.out.println("2. View Registered Projects");
			System.out.println("3. View Joined Projects");
			System.out.println("4. Return to previous page");
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				registerForProject(sc, officer, projectDatabase, applicationDatabase);
				break;
			case 2:
				viewRegisteredProjects(officer);
				break;
			case 3:
				viewJoinedProjects(officer);
				break;
			case 4:
				System.out.println("Returning to previous page");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}

	/**
	 * Allows the HDB Officer to register for an available project.
	 * <p>
	 * This method fetches the list of available projects, displays them, and prompts the officer
	 * to select one to register for. It then calls the manager to handle the registration logic.
	 * </p>
	 *
	 * @param sc				The Scanner Object
	 * @param officer The logged-in HDB Officer.
	 * @param projectDatabase The database containing project information.
	 * @param applicationDatabase The database to store the registration application.
	 */
	private void registerForProject(Scanner sc, HDBOfficer officer, IDatabase<Project> projectDatabase, IDatabase<Application> applicationDatabase) {
		HDBOfficerMgr offMgr = new HDBOfficerMgr();
		
		int projectIndex;
		List<Project> projectList = new ArrayList<>();
		
		System.out.println("List of available projects:");
		projectList = offMgr.getProjects(officer, projectDatabase);
		offMgr.displayProject(projectList);
		
		if (projectList.isEmpty()) {
			return;
		}
		
		System.out.println("\n"
				+ "Enter project number of project you wish to apply to:");
		projectIndex = sc.nextInt();
		
		if (new UserInterfaceHelper().isValidIndex(projectList, projectIndex - 1)) {
			if (offMgr.registerForProject(officer, projectList.get(projectIndex - 1), applicationDatabase)) {
				System.out.println("Applied for Project Successfully!");
			} else {
				System.out.println("Unable to apply for project. This may be because of an overlapping application date with a project you have already joined.");
			}
		} else {
			System.out.println("Invalid index.");
		}
	}

	/**
	 * Displays the projects that the HDB Officer has registered for.
	 *
	 * @param officer The logged-in HDB Officer.
	 */
	private void viewRegisteredProjects(HDBOfficer officer) {
		System.out.println("Registered for following Projects:");
		
		(new HDBOfficerMgr()).displayRegisteredProjects(officer);
	}
	
	/**
	 * Displays the projects that the HDB Officer has joined.
	 *
	 * @param officer The logged-in HDB Officer.
	 */
	private void viewJoinedProjects(HDBOfficer officer) {
		System.out.println("Joined the following Projects:");
		
		(new HDBOfficerMgr()).displayJoinedProjects(officer);
	}
	
	/**
	 * Manages application-related operations for an HDB Officer (viewing registration status, updating applicant BTO applications).
	 *
	 * @param sc				The Scanner Object
	 * @param officer The logged-in HDB Officer.
	 * @param applicationDatabase The database containing application information.
	 */
	public void manageApplications(Scanner sc, HDBOfficer officer, IDatabase<Application> applicationDatabase) {
		int choice;
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. View Project Registration Application Status");
			System.out.println("2. Update Applicants' BTO Applications");
			System.out.println("3. Return to previous page");
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				(new HDBOfficerMgr()).viewApplicationStatus(officer);
				break;
			case 2:
				updateApplications(sc, officer, applicationDatabase);
				break;
			case 3:
				System.out.println("Returning to previous page");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}
	
	/**
	 * Allows the HDB Officer to update the status of applicants' BTO applications (e.g., book a flat).
	 *
	 * @param sc				The Scanner Object
	 * @param officer The logged-in HDB Officer.
	 * @param applicationDatabase The database containing application information.
	 */
	private void updateApplications(Scanner sc, HDBOfficer officer, IDatabase<Application> applicationDatabase) {
		HDBOfficerMgr mgr = new HDBOfficerMgr();
		List<Application> applicationList = mgr.getApplicantApplications(officer, applicationDatabase);

		int applicationIndex;
		
		if (applicationList.isEmpty()) {
			System.out.println("No applications to update.");
			return;
		} else {
			System.out.println("Applications for Projects which you have joined: ");
			mgr.displayApplications(applicationList);
			
			System.out.println("\n"
					+ "Enter application index number of application you wish to book:");
			applicationIndex = sc.nextInt();
			
			if (new UserInterfaceHelper().isValidIndex(applicationList, applicationIndex - 1)) {
				if (mgr.bookApplicantFlat(officer, applicationList.get(applicationIndex - 1))) {
					System.out.println("Successfully updated status to booked!");
				} else {
					System.out.println("Unable to update status to booked. This may be because there are no available flats left for this project.");
				}
			} else {
				System.out.println("Invalid index");
			}
		}
	}
	
	/**
	 * Navigates to the enquiry management interface for the HDB Officer.
	 *
	 * @param officer The logged-in HDB Officer.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void manageEnquiries(HDBOfficer officer, IDatabase<Enquiry> enquiryDatabase) {
		(new HDBOfficialInterface()).manageEnquiries(officer, enquiryDatabase);
	}
}