package userctrl;

import java.util.List;

import database.IDatabase;
import databasemgr.EnquiryDatabaseMgr;
import display.EnquiryDisplayer;
import enquiry.Enquiry;
import misc.CheckType;
import user.HDBOfficial;
import user.User;

/**
 * Manages operations that HDB Officials (Managers and Officers) can perform
 * related to enquiries. This includes viewing and replying to enquiries.
 */
public class HDBOfficialMgr {
	
	/**
	 * Displays a list of enquiries to the console.
	 *
	 * @param enquiryList The list of {@code Enquiry} objects to display.
	 */
	public void displayEnquiries(List<Enquiry> enquiryList) {
		(new EnquiryDisplayer()).display(enquiryList);
	}

	/**
	 * Views the enquiries associated with a specific HDB Official.
	 * <p>
	 * Managers see enquiries related to their managed projects,
	 * while Officers see enquiries related to the projects they are assigned to.
	 * This method fetches the relevant enquiries from the database and displays them.
	 * </p>
	 *
	 * @param official        The HDB Official viewing the enquiries.
	 * @param enquiryDatabase The database containing enquiry information.
	 */
	public void viewEnquiries(HDBOfficial official, IDatabase<Enquiry> enquiryDatabase) {
		displayEnquiries((new EnquiryDatabaseMgr()).getData(enquiryDatabase, (User) official));
	}
	
	/**
	 * Retrieves a list of enquiries that the HDB Official can reply to.
	 * <p>
	 * HDB Managers can reply to enquiries for their managed projects,
	 * while HDB Officers can reply to enquiries for the projects they are assigned to.
	 * The method uses {@link CheckType} to determine the official's role and fetches the
	 * appropriate enquiries.
	 * </p>
	 *
	 * @param official        The HDB Official retrieving the repliable enquiries.
	 * @param enquiryDatabase The database containing enquiry information.
	 * @return A {@code List} of {@code Enquiry} objects that the official can reply to.
	 */
	public List<Enquiry> getRepliableEnquiries(HDBOfficial official, IDatabase<Enquiry> enquiryDatabase) {
		if (CheckType.isHDBManager((User) official)) {
			return (new EnquiryDatabaseMgr()).getData(enquiryDatabase, (User) official, true);
		} else {
			return (new EnquiryDatabaseMgr()).getData(enquiryDatabase, (User) official, false);
		}
	}

	/**
	 * Allows an HDB Official to reply to a specific enquiry.
	 * <p>
	 * This method sets the reply string for the given enquiry. It always returns
	 * {@code true} as the operation of setting the reply is assumed to be successful.
	 * </p>
	 *
	 * @param enquiry The enquiry to reply to.
	 * @param reply   The reply to the enquiry.
	 * @return {@code true} if the reply was successfully added.
	 */
	public boolean replyTo(Enquiry enquiry, String reply) {
		enquiry.setReply(reply);
		return true;
	}
}