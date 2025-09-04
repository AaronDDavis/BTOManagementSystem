package userinterface;

import java.util.List;
import java.util.Scanner;

import database.IDatabase;
import enquiry.Enquiry;
import user.HDBOfficial;
import userctrl.HDBOfficialMgr;

/**
 * Provides a command-line interface for HDB Officials (Managers and Officers)
 * to manage enquiries. This includes viewing and replying to enquiries.
 */
public class HDBOfficialInterface {

	/**
	 * Manages the main menu for handling enquiries for an HDB Official.
	 * <p>
	 * This method provides a loop for the official to choose between viewing enquiries,
	 * replying to them, or returning to the previous menu.
	 * </p>
	 *
	 * @param official The logged-in HDB Official.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void manageEnquiries(HDBOfficial official, IDatabase<Enquiry> enquiryDatabase) {
		Scanner sc = new Scanner(System.in);
		int choice;
		
		while (true) {
			System.out.println("\n\n\n");
			System.out.println("Menu:");
			System.out.println("1. View Enquiries");
			System.out.println("2. Reply to Enquiries");
			System.out.println("3. Return to previous page");
			
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				viewEnquiries(official, enquiryDatabase);
				break;
			case 2:
				replyToEnquiries(sc, official, enquiryDatabase);
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
	 * Displays the enquiries that the HDB Official can view.
	 * <p>
	 * Managers see enquiries for their managed projects, while Officers see them for their assigned projects.
	 * </p>
	 *
	 * @param official The HDB Official viewing the enquiries.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void viewEnquiries(HDBOfficial official, IDatabase<Enquiry> enquiryDatabase) {
		System.out.println("All enquiries you can view:");
		(new HDBOfficialMgr()).viewEnquiries(official, enquiryDatabase);
	}

	/**
	 * Allows the HDB Official to reply to an enquiry from the list of repliable enquiries.
	 * <p>
	 * This method fetches the list of enquiries the official is authorized to reply to,
	 * displays them, and prompts for a selection and a reply. It then calls the
	 * {@link HDBOfficialMgr} to save the reply.
	 * </p>
	 *
	 * @param sc				The Scanner Object
	 * @param official The HDB Official replying to the enquiry.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void replyToEnquiries(Scanner sc, HDBOfficial official, IDatabase<Enquiry> enquiryDatabase) {
		HDBOfficialMgr mgr = new HDBOfficialMgr();
		System.out.println("All enquiries you can reply to:");

		int enquiryIndex;
		String reply;

		List<Enquiry> enquiryList = mgr.getRepliableEnquiries(official, enquiryDatabase);
		mgr.displayEnquiries(enquiryList);
		System.out.println();
		
		if (enquiryList.isEmpty()) {
			return;
		}
		
		System.out.println("Enter enquiry index number of enquiry you wish to reply to:");
		enquiryIndex = sc.nextInt();
		sc.nextLine();
		
		if (new UserInterfaceHelper().isValidIndex(enquiryList, enquiryIndex - 1)) {
			System.out.println("Enter reply:");
			reply = sc.nextLine().replace(',', ' ');
			
			if (mgr.replyTo(enquiryList.get(enquiryIndex - 1), reply)) {
				System.out.println("Successfully saved reply!");
			} else {
				System.out.println("Unsuccessful.");
			}
		} else {
			System.out.println("Invalid index");
		}
	}
}