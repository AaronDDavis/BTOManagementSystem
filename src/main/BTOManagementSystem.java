package main;

import java.util.Scanner;
import java.util.stream.Collectors;

import application.*;
import database.*;
import enquiry.*;
import misc.CheckType;
import project.*;
import reader.*;
import user.*;
import userinterface.*;
import writer.*;

/**
 * The main class for the BTO Management System application.
 * <p>
 * This class serves as the entry point for the entire application. It manages the
 * core application loop, handles user login, and coordinates the loading and
 * saving of data to and from files. It initializes the main databases for users,
 * projects, applications, and enquiries, and orchestrates the flow of control
 * to the user interface based on user authentication.
 * </p>
 */
public class BTOManagementSystem {
	
	/**
	 * The static database for storing user objects.
	 */
	private static IDatabase<User> userDatabase = new Database<User>();
	
	/**
	 * The static database for storing project objects.
	 */
	private static IDatabase<Project> projectDatabase = new Database<Project>();
	
	/**
	 * The static database for storing application objects.
	 */
	private static IDatabase<Application> applicationDatabase = new Database<Application>();
	
	/**
	 * The static database for storing enquiry objects.
	 */
	private static IDatabase<Enquiry> enquiryDatabase = new Database<Enquiry>();

	/**
	 * The main method and entry point of the application.
	 * <p>
	 * This method sets up the application environment, loads existing data,
	 * and enters a loop to display the main menu, allowing users to log in or exit.
	 * Upon a successful login, it delegates control to the user interface to manage
	 * user-specific actions.
	 * </p>
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String args[]) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		UserInterface userInterface = new UserInterface();
		
		int choice;
		
		loadData(); // Load data from files

		outer:
		while (true) {
			System.out.println("\n\n\n\f");
			System.out.println("Select Choice:");
			System.out.println("1. Login");
			System.out.println("2. Exit Application");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
				case 1:
					System.out.print("\n\n\n\f");
					User currentUser = userInterface.login(userDatabase);
					
					if (currentUser != null) {
						System.out.print("\n\n\n\f");
						System.out.println("Login Successful!\n\nWelcome " + currentUser.getName() + "\n\n");
						userInterface.manage(currentUser, projectDatabase, applicationDatabase, enquiryDatabase, userDatabase);
					} else {
						System.out.println("Login Failed. Please try again.\n\n");
					}
					break;
				case 2:
					System.out.println("Thank you! Exiting...");
					break outer;
				default:
					System.out.println("Invalid option. Please try again.\n\n");
					continue;
			}
			saveData();
		}
	}

	/**
	 * Loads all data from their respective files into the in-memory databases.
	 * <p>
	 * This method uses various reader classes to deserialize user, project,
	 * application, and enquiry data. It also performs a post-loading update to
	 * link related objects (e.g., associating applicants and officers with their
	 * applications and projects).
	 * </p>
	 */
	public static void loadData() {
		ApplicantReader applicantReader = new ApplicantReader();
		HDBOfficerReader officerReader = new HDBOfficerReader();

		userDatabase.getDataList().addAll(applicantReader.read());
		userDatabase.getDataList().addAll(officerReader.read());
		userDatabase.getDataList().addAll((new HDBManagerReader()).read());
		projectDatabase.setDataList((new ProjectReader()).read(userDatabase));
		applicationDatabase.setDataList((new ApplicationReader()).read(userDatabase, projectDatabase));
		enquiryDatabase.setDataList((new EnquiryReader()).read(userDatabase, projectDatabase));
		
		applicantReader.updateApplicants(userDatabase, applicationDatabase, projectDatabase);
		officerReader.updateHDBOfficers(userDatabase, applicationDatabase, projectDatabase);
	}

	/**
	 * Saves all in-memory database data back to their respective files.
	 * <p>
	 * This method uses writer classes to serialize the current state of projects,
	 * enquiries, applications, and all user types back into files. It filters the
	 * master user list into separate lists for applicants, officers, and managers
	 * before writing.
	 * </p>
	 */
	public static void saveData() {
		(new ProjectWriter()).write(projectDatabase.getDataList());
		(new EnquiryWriter()).write(enquiryDatabase.getDataList());
		(new ApplicationWriter()).write(applicationDatabase.getDataList());
		
		// Filter and save different user types
		(new ApplicantWriter()).write(userDatabase.getDataList()
				.stream()
				.filter(user -> CheckType.isApplicant(user) && !CheckType.isHDBOfficer(user))
				.map(user -> (Applicant) user)
				.collect(Collectors.toList()));
		
		(new HDBOfficerWriter()).write(userDatabase.getDataList()
				.stream()
				.filter(user -> CheckType.isHDBOfficer(user))
				.map(user -> (HDBOfficer) user)
				.collect(Collectors.toList()));
		
		(new HDBManagerWriter()).write(userDatabase.getDataList()
				.stream()
				.filter(user -> CheckType.isHDBManager(user))
				.map(user -> (HDBManager) user)
				.collect(Collectors.toList()));
	}
}