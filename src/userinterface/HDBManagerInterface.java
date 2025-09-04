package userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.Application;
import application.ApplicationStatus;
import database.IDatabase;
import enquiry.Enquiry;
import project.Project;
import user.HDBManager;
import user.User;
import userctrl.HDBManagerMgr;

/**
 * Provides a command-line interface for HDB Managers to interact with the system.
 * Allows managers to manage projects, applications, enquiries, and generate reports.
 */
public class HDBManagerInterface {

	/**
	 * Manages the main menu for an HDB Manager.
	 * <p>
	 * This method presents the primary options to the manager and delegates
	 * to specific methods for handling projects, applications, enquiries, and reports.
	 * </p>
	 *
	 * @param manager The logged-in HDB Manager.
	 * @param projectDatabase The database containing project information.
	 * @param applicationDatabase The database containing application information.
	 * @param enquiryDatabase The database containing enquiry information.
	 * @param userDatabase The database containing user information.
	 */
	public void manage(HDBManager manager, IDatabase<Project> projectDatabase,
			IDatabase<Application> applicationDatabase, IDatabase<Enquiry> enquiryDatabase,
			IDatabase<User> userDatabase) {
		Scanner sc = new Scanner(System.in);
		int choice;
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. Manage Projects");
			System.out.println("2. Manage Applications");
			System.out.println("3. Manage Enquiries");
			System.out.println("4. Generate Report");
			System.out.println("5. Return to Previous Page");
			
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
			case 1:
				manageProjects(sc, manager, projectDatabase, userDatabase);
				break;
			case 2:
				manageApplications(sc, manager, applicationDatabase);
				break;
			case 3:
				manageEnquiries(manager, enquiryDatabase);
				break;
			case 4:
				generateReport(sc, userDatabase);
				break;
			case 5:
				System.out.println("Returning to previous page");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}

	/**
	 * Manages project-related operations for an HDB Manager (viewing, creating, editing, deleting).
	 *
	 * @param sc					The Scanner Object
	 * @param manager 				The logged-in HDB Manager.
	 * @param projectDatabase 		The database containing project information.
	 * @param userDatabase 			The database containing user information (for assigning manager/officers).
	 */
	public void manageProjects(Scanner sc, HDBManager manager, IDatabase<Project> projectDatabase, IDatabase<User> userDatabase) {
		int choice;
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. View Projects");
			System.out.println("2. Create a Project");
			System.out.println("3. Edit a Project");
			System.out.println("4. Delete a Project");
			System.out.println("5. Toggle Project Visibility");
			System.out.println("6. Return to previous page");
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				viewProjects(sc, manager, projectDatabase);
				break;
			case 2:
				createProject(manager, projectDatabase, userDatabase);
				break;
			case 3:
				editProject(sc, manager, projectDatabase);
				break;
			case 4:
				deleteProject(sc, manager, projectDatabase);
				break;
			case 5:
				toggleProject(sc, manager, projectDatabase);
				break;
			case 6:
				System.out.println("Returning to previous page");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}
	
	/**
	 * Displays a list of projects to the HDB Manager, with options to filter and sort.
	 *
	 * @param sc				The Scanner Object
	 * @param manager			The logged-in HDB Manager.
	 * @param projectDatabase 	The database containing project information.
	 */
	private void viewProjects(Scanner sc, HDBManager manager, IDatabase<Project> projectDatabase) {
		HDBManagerMgr mgr = new HDBManagerMgr();
		
		int filterChoice, intValue;
		boolean sortAscending;
		String value;
		List<Project> projectList = new ArrayList<>();

		System.out.println("View Options:");

		System.out.println("Filter by:");
		System.out.println("1. None");
		System.out.println("2. Name");
		System.out.println("3. Neighbourhood");
		System.out.println("4. Room Type");
		System.out.println("5. Created by you");

		filterChoice = sc.nextInt();
		
		sortAscending = new UserInterfaceHelper().getSortOrder(sc);
		
		sc.nextLine(); // to collect the newline
		
		switch (filterChoice) {
		case 1:
			projectList = mgr.getProjects(manager, projectDatabase);
			break;
		case 2:
			System.out.println("Enter project name:");
			value = sc.nextLine();
			projectList = mgr.getProjects(manager, projectDatabase, "Name", value);
			break;
		case 3:
			System.out.println("Enter neighbourhood name:");
			value = sc.nextLine();
			projectList = mgr.getProjects(manager, projectDatabase, "Neighbourhood", value);
			break;
		case 4:
			System.out.println("Enter flat-type:");
			System.out.println("2. 2-Room");
			System.out.println("3. 3-Room");
			intValue = sc.nextInt();
			sc.nextLine();
			
			if (intValue != 2 && intValue != 3) {
				System.out.println("Invalid input");
				break;
			}
			projectList = mgr.getProjects(manager, projectDatabase, "RoomType", ((intValue == 2) ? Project.ROOM_TYPE._2Room : Project.ROOM_TYPE._3Room));
			break;
		case 5:
			projectList = mgr.getOwnProjects(manager, projectDatabase);
			break;
		default:
			System.out.println("Invalid filter choice.");
			return;
		}

		mgr.sortProjects(projectList, sortAscending);
		
		System.out.println();
		System.out.println("Projects are as follows:");
		System.out.println();
		mgr.displayProject(projectList);
	}
	
	/**
	 * Allows the HDB Manager to create a new project.
	 *
	 * @param manager The logged-in HDB Manager.
	 * @param projectDatabase The database to store the new project.
	 * @param userDatabase The database containing user information for assigning roles.
	 */
	private void createProject(HDBManager manager, IDatabase<Project> projectDatabase, IDatabase<User> userDatabase) {
		(new HDBManagerMgr()).createProject(manager, projectDatabase, userDatabase);
		System.out.println("Project Successfully created!");
	}
	
	/**
	 * Allows the HDB Manager to edit an existing project.
	 *
	 * @param sc				The Scanner Object
	 * @param manager 			The logged-in HDB Manager.
	 * @param projectDatabase 	The database containing project information.
	 */
	private void editProject(Scanner sc, HDBManager manager, IDatabase<Project> projectDatabase) {
		HDBManagerMgr mgr = new HDBManagerMgr();
		List<Project> projectList = mgr.getProjects(manager, projectDatabase);
		int projectIndex;

		System.out.println("List of all Projects:");
		mgr.displayProject(projectList);
		
		System.out.println("Enter index of project to be edited:");
		projectIndex = sc.nextInt();
		
		if (new UserInterfaceHelper().isValidIndex(projectList, projectIndex - 1)) {
			mgr.editProject(projectList.get(projectIndex - 1));
			System.out.println("Successfully edited project");
		} else {
			System.out.println("Invalid index.");
		}
	}
	
	/**
	 * Allows the HDB Manager to toggle visibility of an existing project.
	 *
	 * @param sc				The Scanner Object
	 * @param manager 			The logged-in HDB Manager.
	 * @param projectDatabase 	The database containing project information.
	 */
	private void toggleProject(Scanner sc, HDBManager manager, IDatabase<Project> projectDatabase) {
		HDBManagerMgr mgr = new HDBManagerMgr();
		List<Project> projectList;
		int projectIndex;

		System.out.println("List of all Projects:");
		projectList = mgr.getOwnProjects(manager, projectDatabase);
		mgr.displayProject(projectList);
		
		System.out.println("Enter index of project to be toggled:");
		projectIndex = sc.nextInt();
		
		if (new UserInterfaceHelper().isValidIndex(projectList, projectIndex - 1)) {
			System.out.println("Successfully toggled project visibility to " + mgr.toggleProjectVisibility(projectList.get(projectIndex - 1)));
		} else {
			System.out.println("Invalid index.");
		}
	}
	
	/**
	 * Allows the HDB Manager to delete an existing project.
	 *
	 * @param sc				The Scanner Object
	 * @param manager 			The logged-in HDB Manager.
	 * @param projectDatabase 	The database containing project information.
	 */
	private void deleteProject(Scanner sc, HDBManager manager, IDatabase<Project> projectDatabase) {
		HDBManagerMgr mgr = new HDBManagerMgr();
		List<Project> projectList = mgr.getProjects(manager, projectDatabase);
		int projectIndex;

		System.out.println("List of all Projects:");
		mgr.displayProject(projectList);
		
		System.out.println("Enter index of project to be deleted:");
		projectIndex = sc.nextInt();
		
		if (new UserInterfaceHelper().isValidIndex(projectList, projectIndex - 1)) {
			mgr.removeProject(projectDatabase, projectList.get(projectIndex - 1));
			System.out.println("Successfully deleted project");
		} else {
			System.out.println("Invalid index.");
		}
	}
	
	/**
	 * Manages application-related operations for an HDB Manager (viewing and updating status).
	 *
	 * @param sc				The Scanner Object
	 * @param manager The logged-in HDB Manager.
	 * @param applicationDatabase The database containing application information.
	 */
	public void manageApplications(Scanner sc, HDBManager manager, IDatabase<Application> applicationDatabase) {
		HDBManagerMgr mgr = new HDBManagerMgr();
		
		int choice;
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. View Applications");
			System.out.println("2. Update Applications");
			System.out.println("3. Return to previous page");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
			case 1:
				mgr.displayApplications(mgr.getApplications(manager, applicationDatabase));
				break;
			case 2:
				updateApplications(sc, manager, applicationDatabase);
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
	 * Allows the HDB Manager to update the status of an application.
	 *
	 * @param sc				The Scanner Object
	 * @param manager The logged-in HDB Manager.
	 * @param applicationDatabase The database containing application information.
	 */
	private void updateApplications(Scanner sc, HDBManager manager, IDatabase<Application> applicationDatabase) {
		HDBManagerMgr mgr = new HDBManagerMgr();
		List<Application> applicationList = mgr.getApplications(manager, applicationDatabase);

		int applicationIndex;
		ApplicationStatus newStatus;
		
		System.out.println("Applications for Projects for which you have been registered: ");
		mgr.displayApplications(applicationList);
		System.out.println();
		
		if (applicationList.isEmpty()) {
			return;
		}

		System.out.println("Enter application index number of application you wish to update:");
		applicationIndex = sc.nextInt();
		sc.nextLine();
		
		if (new UserInterfaceHelper().isValidIndex(applicationList, applicationIndex - 1)) {
			while (true) {
				System.out.println("Update status to?");
				System.out.println("1. Successful");
				System.out.println("2. Unsuccessful");
				System.out.println("3. Cancel updation");
				
				switch (sc.nextInt()) {
				case 1:
					newStatus = ApplicationStatus.SUCCESSFUL;
					break;
				case 2:
					newStatus = ApplicationStatus.UNSUCCESSFUL;
					break;
				case 3:
					System.out.println("Application update was unsuccessful.");
					return;
				default:
					System.out.println("Invalid input. Please try again.");
					continue;
				}
				break;
			}
			
			mgr.updateStatus(applicationList.get(applicationIndex - 1), newStatus);
			System.out.println("Successfully updated Application!");
		} else {
			System.out.println("Invalid index.");
		}
	}
	
	/**
	 * Navigates to the enquiry management interface for the HDB Manager.
	 *
	 * @param manager The logged-in HDB Manager.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void manageEnquiries(HDBManager manager, IDatabase<Enquiry> enquiryDatabase) {
		(new HDBOfficialInterface()).manageEnquiries(manager, enquiryDatabase);
	}
	
	/**
	 * Allows the HDB Manager to generate a report on applicants based on various filters.
	 *
	 * @param sc				The Scanner Object
	 * @param userDatabase The database containing user information.
	 */
	public void generateReport(Scanner sc, IDatabase<User> userDatabase) {
		HDBManagerMgr mgr = new HDBManagerMgr();
		
		List<User> userList = new ArrayList<>();
		int filterChoice, value;
		
		System.out.println("View Options:");

		System.out.println("Filter by:");
		System.out.println("1. None");
		System.out.println("2. Flat Type");
		System.out.println("3. Marital Status");
		filterChoice = sc.nextInt();
		
		sc.nextLine(); // to collect the newline
		
		switch (filterChoice) {
		case 1:
			userList = mgr.getReceiptReadyUsers(userDatabase);
			break;
		case 2:
			System.out.println("Enter flat-type:");
			System.out.println("2. 2-Room");
			System.out.println("3. 3-Room");
			value = sc.nextInt();
			sc.nextLine();
			
			if (value != 2 && value != 3) {
				System.out.println("Invalid input");
				break;
			}
			userList = mgr.getReceiptReadyUsers(userDatabase, "RoomType", (value == 2) ? Project.ROOM_TYPE._2Room : Project.ROOM_TYPE._3Room);
			break;
		case 3:
			System.out.println("Enter Marital Status:");
			System.out.println("1. Single");
			System.out.println("2. Married");
			value = sc.nextInt();
			sc.nextLine();

			if (value != 1 && value != 2) {
				System.out.println("Invalid input");
				break;
			}
			userList = mgr.getReceiptReadyUsers(userDatabase, "Marital Status", (value == 1) ? User.MARITAL_STATUS.SINGLE : User.MARITAL_STATUS.MARRIED);
			break;
		default:
			System.out.println("Invalid filter choice.");
			return;
		}
		
		System.out.println("\n\n");
		System.out.println("Report:");
		System.out.println();
		mgr.displayReport(mgr.toApplicantList(userList));
	}
}