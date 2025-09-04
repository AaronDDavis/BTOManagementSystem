package userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.Application;
import application.ApplicationType;
import database.IDatabase;
import enquiry.Enquiry;
import project.Project;
import user.Applicant;
import userctrl.ApplicantMgr;

/**
 * Provides a command-line interface for applicants to interact with the HDB system.
 * Allows applicants to manage projects, applications, and enquiries.
 */
public class ApplicantInterface {
	
	/**
	 * Manages the main menu for an applicant.
	 * <p>
	 * This method presents the main menu options to the applicant and handles
	 * navigation to the various sub-menus for managing projects, applications, and enquiries.
	 * It also provides an option to generate a receipt if one is ready.
	 * </p>
	 *
	 * @param applicant           The logged-in applicant.
	 * @param projectDatabase     The database containing project information.
	 * @param applicationDatabase The database containing application information.
	 * @param enquiryDatabase     The database containing enquiry information.
	 */
	public void manage(Applicant applicant, IDatabase<Project> projectDatabase,
			IDatabase<Application> applicationDatabase, IDatabase<Enquiry> enquiryDatabase) {
		Scanner sc = new Scanner(System.in);
		int choice;
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. Manage Projects");
			System.out.println("2. Manage Applications");
			System.out.println("3. Manage Enquiries");
			if (applicant.isReceiptReady()) {
				System.out.println("4. Generate Receipt");
				System.out.println("5. Return to previous page");
			} else {
				System.out.println("4. Return to previous page");
			}
			
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				manageProjects(sc, applicant, projectDatabase, applicationDatabase);
				break;
			case 2:
				manageApplications(sc, applicant, applicationDatabase);
				break;
			case 3:
				manageEnquiries(sc, applicant, enquiryDatabase);
				break;
			case 4:
				if (applicant.isReceiptReady()) {
					printReceipt(applicant);
					break;
				} else {
					System.out.println("Returning to previous page");
					return;
				}
			case 5:
				if (applicant.isReceiptReady()) {
					System.out.println("Returning to previous page");
					return;
				}
			default:
				System.out.println("Invalid choice. Please try again.");
				continue;
			}
		}
	}

	/**
	 * Manages the project-related operations for an applicant (viewing and applying).
	 * <p>
	 * The options presented to the applicant depend on whether they can still apply for a project.
	 * If they can, they can view and apply for projects. Otherwise, they can only view their
	 * currently applied project.
	 * </p>
	 *
	 * @param sc				  The Scanner Object
	 * @param applicant           The logged-in applicant.
	 * @param projectDatabase     The database containing project information.
	 * @param applicationDatabase The database containing application information.
	 */
	public void manageProjects(Scanner sc, Applicant applicant, IDatabase<Project> projectDatabase, IDatabase<Application> applicationDatabase) {
		int choice;
		
		while (true) {
			System.out.println("\n\n");
			System.out.println("Menu:");
			if (applicant.canApply()) {
				System.out.println("1. View Projects");
				System.out.println("2. Apply for a Project");
				System.out.println("3. Return to previous page");
				choice = sc.nextInt();
				
				switch (choice) {
				case 1:
					viewProjects(sc, applicant, projectDatabase);
					break;
				case 2:
					applyForProject(sc, applicant, projectDatabase, applicationDatabase);
					break;
				case 3:
					System.out.println("Returning to previous page");
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
				}
			} else {
				System.out.println("1. View Applied Project");
				System.out.println("2. Return to previous page");
				choice = sc.nextInt();
				
				switch (choice) {
				case 1:
					viewAppliedProject(applicant);
					break;
				case 2:
					System.out.println("Returning to previous page");
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
					continue;
				}
			}
		}
	}
	
	/**
	 * Displays a list of available projects to the applicant, with options to filter and sort.
	 * <p>
	 * This method prompts the user to choose a filter (by name, neighbourhood, or room type)
	 * and a sorting order before displaying the filtered and sorted list of projects.
	 * </p>
	 *
	 * @param sc				  The Scanner Object
	 * @param applicant       The logged-in applicant.
	 * @param projectDatabase The database containing project information.
	 */
	private void viewProjects(Scanner sc, Applicant applicant, IDatabase<Project> projectDatabase) {
		ApplicantMgr appMgr = new ApplicantMgr();
		
		int filterChoice, intValue;
		boolean sortAscending;
		String value;
		List<Project> projectList = new ArrayList<>();

		System.out.println("\n\n");
		System.out.println("View Options:");

		System.out.println("Filter by:");
		System.out.println("1. None");
		System.out.println("2. Name");
		System.out.println("3. Neighbourhood");
		System.out.println("4. Room Type");
		
		filterChoice = sc.nextInt();
		
		sortAscending = new UserInterfaceHelper().getSortOrder(sc);
		
		switch (filterChoice) {
		case 1:
			projectList = appMgr.getProjects(applicant, projectDatabase);
			break;
		case 2:
			System.out.println("Enter project name:");
			value = sc.nextLine();
			projectList = appMgr.getProjects(applicant, projectDatabase, "Name", value);
			break;
		case 3:
			System.out.println("Enter neighbourhood name:");
			value = sc.nextLine();
			projectList = appMgr.getProjects(applicant, projectDatabase, "Neighbourhood", value);
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
			projectList = appMgr.getProjects(applicant, projectDatabase, "RoomType", ((intValue == 2) ? Project.ROOM_TYPE._2Room : Project.ROOM_TYPE._3Room));
			break;
		default:
			System.out.println("Invalid filter choice.");
			return;
		}
		
		appMgr.sortProjects(projectList, sortAscending);
		
		System.out.println();
		System.out.println("Available Projects are as follows:");
		System.out.println();
		appMgr.displayProject(projectList, applicant);
	}
	
	/**
	 * Allows the applicant to apply for a selected project from the list of available projects.
	 * <p>
	 * This method first displays the list of eligible projects and then prompts the applicant
	 * to select one to apply for. It then calls the {@link ApplicantMgr} to handle the application logic.
	 * </p>
	 *
	 * @param sc				  The Scanner Object
	 * @param applicant           The logged-in applicant.
	 * @param projectDatabase     The database containing project information.
	 * @param applicationDatabase The database to store the application.
	 */
	private void applyForProject(Scanner sc, Applicant applicant, IDatabase<Project> projectDatabase, IDatabase<Application> applicationDatabase) {
		ApplicantMgr appMgr = new ApplicantMgr();
		
		int projectIndex;
		List<Project> projectList = new ArrayList<>();

		System.out.println("\n\n");
		projectList = appMgr.getProjects(applicant, projectDatabase);

		System.out.println("List of available projects:");
		appMgr.displayProject(projectList, applicant);
		
		System.out.println("\n"
				+ "Enter project number of project you wish to apply to:");
		projectIndex = sc.nextInt();
		
		if (new UserInterfaceHelper().isValidIndex(projectList, projectIndex - 1)) {
			if (appMgr.applyForProject(applicant, projectList.get(projectIndex - 1), applicationDatabase)) {
				System.out.println("Applied for Project Successfully!");
			} else {
				System.out.println("Unable to apply for project.");
			}
		} else {
			System.out.println("Invalid index");
		}
	}
	
	/**
	 * Displays the project that the applicant has currently applied for.
	 *
	 * @param applicant The logged-in applicant.
	 */
	private void viewAppliedProject(Applicant applicant) {
		System.out.println("\n\n");
		(new ApplicantMgr()).viewAppliedProject(applicant);
	}
	
	/**
	 * Manages the application-related operations for an applicant (viewing status and submitting withdrawal).
	 * <p>
	 * The options change based on whether the applicant has already applied for a project
	 * and if they have submitted a withdrawal application.
	 * </p>
	 *
	 * @param sc				  The Scanner Object
	 * @param applicant           The logged-in applicant.
	 * @param applicationDatabase The database containing application information.
	 */
	public void manageApplications(Scanner sc, Applicant applicant, IDatabase<Application> applicationDatabase) {
		ApplicantMgr appMgr = new ApplicantMgr();
		
		int choice;
		
		while (true) {
			System.out.println("\n\n");
			System.out.println("Menu:");
			System.out.println("1. View BTO Project Application Status");
			if (!applicant.canApply()) {
				if (!applicant.isWithdrawing()) {
					System.out.println("2. Submit Withdrawal Application");
				} else {
					System.out.println("2. View Withdrawal Application Status");
				}
				System.out.println("3. Return to previous page");
			} else {
				System.out.println("2. Return to previous page");
			}
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				appMgr.viewApplicationStatus(applicant, ApplicationType.BTO_APPLICATION);
				break;
			case 2:
				if (applicant.canApply()) {
					System.out.println("Returning to previous page");
					return;
				} else if (!applicant.isWithdrawing()) {
					if (appMgr.submitWithdrawalApplication(applicant, applicationDatabase)) {
						System.out.println("Withdrawal Application successfully submitted");
					} else {
						System.out.println("Error. Could not create Withdrawal Application");
					}
				} else {
					appMgr.viewApplicationStatus(applicant, ApplicationType.WITHDRAWAL_APPLICATION);
				}
				break;
			case 3:
				if (!applicant.canApply()) {
					System.out.println("Returning to previous page");
					return;
				}
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}
	
	/**
	 * Prints the receipt for a successful application, if it is ready for the applicant.
	 *
	 * @param applicant The logged-in applicant.
	 */
	public void printReceipt(Applicant applicant) {
		System.out.println("\n\n");
		System.out.println("Receipt:");
		System.out.println((new ApplicantMgr()).getReceipt(applicant));
	}
	
	/**
	 * Manages the enquiry-related operations for an applicant (viewing, adding, editing, and deleting).
	 *
	 * @param sc			  The Scanner Object
	 * @param applicant       The logged-in applicant.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void manageEnquiries(Scanner sc, Applicant applicant, IDatabase<Enquiry> enquiryDatabase) {
		int choice;
		
		while (true) {
			System.out.println("\n\n");
			System.out.println("Menu:");
			System.out.println("1. View Enquiries");
			System.out.println("2. Add Enquiry");
			System.out.println("3. Edit enquiry");
			System.out.println("4. Delete enquiry");
			System.out.println("5. Return to previous page");
			
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
			case 1:
				viewEnquiries(applicant, enquiryDatabase);
				break;
			case 2:
				addEnquiry(sc, applicant, enquiryDatabase);
				break;
			case 3:
				editEnquiry(sc, applicant, enquiryDatabase);
				break;
			case 4:
				deleteEnquiry(sc, applicant, enquiryDatabase);
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
	 * Displays the enquiries submitted by the applicant.
	 *
	 * @param applicant       The logged-in applicant.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	private void viewEnquiries(Applicant applicant, IDatabase<Enquiry> enquiryDatabase) {
		ApplicantMgr appMgr = new ApplicantMgr();
		
		System.out.println("\n\n");
		System.out.println("Enquiries:");		
		appMgr.displayEnquiry(appMgr.getEnquiries(applicant, enquiryDatabase));
	}
	
	/**
	 * Allows the applicant to add a new enquiry.
	 *
	 * @param sc			  The Scanner Object
	 * @param applicant       The logged-in applicant.
	 * @param enquiryDatabase The database to store the new enquiry.
	 */
	private void addEnquiry(Scanner sc, Applicant applicant, IDatabase<Enquiry> enquiryDatabase) {
		System.out.println("\n\n");
		System.out.println("Enter enquiry question:");
		
		if ((new ApplicantMgr()).addEnquiry(applicant, enquiryDatabase, sc.nextLine())) {
			System.out.println("Enquiry successfully added!");
		} else {
			System.out.println("Failed to add enquiry.");
		}
	}
	
	/**
	 * Allows the applicant to edit an existing enquiry.
	 *
	 * @param sc			  The Scanner Object
	 * @param applicant       The logged-in applicant.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	private void editEnquiry(Scanner sc, Applicant applicant, IDatabase<Enquiry> enquiryDatabase) {
		ApplicantMgr appMgr = new ApplicantMgr();
		List<Enquiry> enquiryList = appMgr.getEnquiries(applicant, enquiryDatabase);

		int enquiryIndex;

		System.out.println("\n\n");
		viewEnquiries(applicant, enquiryDatabase);
		
		System.out.println("\n\n");
		System.out.println("Enter number beside enquiry to be edited:");
		enquiryIndex = sc.nextInt();
		sc.nextLine();
		
		if (new UserInterfaceHelper().isValidIndex(enquiryList, enquiryIndex - 1)) {
			System.out.println("Enter edited enquiry question:");

			if (appMgr.editEnquiry(enquiryList.get(enquiryIndex - 1), sc.nextLine())) {
				System.out.println("Enquiry successfully edited!");
			} else {
				System.out.println("Failed to edit enquiry. This may be because it has already been answered.");
			}
		} else {
			System.out.println("Invalid index");
		}
	}
	
	/**
	 * Allows the applicant to delete an existing enquiry.
	 *
	 * @param sc			  The Scanner Object
	 * @param applicant       The logged-in applicant.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	private void deleteEnquiry(Scanner sc, Applicant applicant, IDatabase<Enquiry> enquiryDatabase) {
		ApplicantMgr appMgr = new ApplicantMgr();
		List<Enquiry> enquiryList = appMgr.getEnquiries(applicant, enquiryDatabase);

		int enquiryIndex;

		viewEnquiries(applicant, enquiryDatabase);
		
		System.out.println("Enter number beside enquiry to be removed:");
		enquiryIndex = sc.nextInt();
		sc.nextLine();

		if (new UserInterfaceHelper().isValidIndex(enquiryList, enquiryIndex - 1)) {
			if (appMgr.deleteEnquiry(enquiryDatabase, enquiryList.get(enquiryIndex - 1))) {
				System.out.println("Enquiry successfully removed!");
			} else {
				// The original code has a logical flaw here, as it prints a message suggesting
				// the enquiry cannot be deleted if the reply is blank, which is the reverse
				// of what a user would expect. The Javadoc should reflect the code's behavior.
				System.out.println("Enquiry has already been answered. Enquiry cannot be deleted.");
			}
		} else {
			System.out.println("Invalid index.");
		}
	}
}