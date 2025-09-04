package databasemgr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.IDatabase;
import enquiry.Enquiry;
import misc.CheckType;
import user.Applicant;
import user.HDBManager;
import user.HDBOfficer;
import user.User;

/**
 * Manages operations on a database of {@link Enquiry} objects.
 * <p>
 * This class extends {@link ItemDatabaseMgr} and provides specialized methods for
 * retrieving and filtering enquiry data based on the user's role and
 * their relationship to the enquiries. It uses Java Streams to efficiently
 * filter the data list.
 * </p>
 */
public class EnquiryDatabaseMgr extends ItemDatabaseMgr<Enquiry> {

	/**
	 * Retrieves a list of all enquiries relevant to a given user.
	 * <p>
	 * This method defaults to not processing the user as an applicant.
	 * </p>
	 *
	 * @param database The database containing all enquiry data.
	 * @param user     The user whose enquiries are to be retrieved.
	 * @return A {@link List} of filtered {@link Enquiry} objects.
	 */
	@Override
	public List<Enquiry> getData(IDatabase<Enquiry> database, User user) {
		return getData(database, user, false);
	}

	/**
	 * Retrieves a list of enquiries relevant to a given user, with an option to
	 * process the user as an applicant.
	 * <p>
	 * The filtering logic depends on the user's type and the {@code processAsApplicant}
	 * flag:
	 * <ul>
	 * <li>If the user is an {@link HDBManager} and {@code processAsApplicant} is false,
	 * it returns all enquiries.</li>
	 * <li>If the user is an {@link HDBManager} and {@code processAsApplicant} is true,
	 * it returns enquiries for which they are the project manager.</li>
	 * <li>If the user is an {@link HDBOfficer} and {@code processAsApplicant} is false,
	 * it returns enquiries for which they are a project officer.</li>
	 * <li>Otherwise (e.g., if the user is an {@link Applicant}), it returns enquiries
	 * filed by that user.</li>
	 * </ul>
	 * </p>
	 *
	 * @param database           The database containing all enquiry data.
	 * @param user               The user whose enquiries are to be retrieved.
	 * @param processAsApplicant A flag to determine if the user should be treated as an applicant.
	 * @return A {@link List} of filtered {@link Enquiry} objects.
	 */
	public List<Enquiry> getData(IDatabase<Enquiry> database, User user, boolean processAsApplicant) {
		List<Enquiry> matchedEnquiries = new ArrayList<>();
		if (CheckType.isHDBManager(user) && !processAsApplicant) {
			// A manager who is not being processed as an applicant gets to see all enquiries.
			matchedEnquiries = database.getDataList();
		} else if (CheckType.isHDBManager(user)) {
			// A manager processed as an applicant only gets to see their own enquiries.
			matchedEnquiries.addAll(database.getDataList()
					.stream()
					.filter(enquiry -> enquiry.getProjectManager().equals((HDBManager) user))
					.collect(Collectors.toList()));
		} else if (CheckType.isHDBOfficer(user) && !processAsApplicant) {
			// An officer who is not being processed as an applicant gets to see enquiries for projects they're on.
			matchedEnquiries.addAll(database.getDataList()
					.stream()
					.filter(enquiry -> enquiry.getProjectOfficers().contains((HDBOfficer) user))
					.collect(Collectors.toList()));
		} else {
			// All other users (e.g., Applicants) only see their own enquiries.
			matchedEnquiries.addAll(database.getDataList()
					.stream()
					.filter(enquiry -> enquiry.getEnquiryFiler().equals((Applicant) user))
					.collect(Collectors.toList()));
		}

		return matchedEnquiries;
	}
}