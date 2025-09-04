package enquiry;

import misc.IDCreator;
import project.Project;
import user.Applicant;

/**
 * Manages the creation of {@link Enquiry} objects.
 * <p>
 * This class serves as a factory for creating new enquiries, providing overloaded
 * methods to handle different creation scenarios. It encapsulates the logic for
 * generating a unique enquiry ID and associating an enquiry with a user and a project.
 * </p>
 */
public class EnquiryMgr {

	/**
	 * Creates a new enquiry with a generated unique ID and a question.
	 * <p>
	 * The reply is set to {@code null} by default.
	 * </p>
	 *
	 * @param enquiryFiler The applicant who filed the enquiry.
	 * @param project      The project the enquiry is about.
	 * @param question     The question posed in the enquiry.
	 * @return A new {@link Enquiry} instance.
	 */
	public Enquiry create(Applicant enquiryFiler, Project project, String question) {
		return create(IDCreator.createEnquiryID(), enquiryFiler, project, question);
	}

	/**
	 * Creates a new enquiry with a provided ID and a question.
	 * <p>
	 * The reply is set to {@code null} by default.
	 * </p>
	 *
	 * @param ID           The unique ID for the new enquiry.
	 * @param enquiryFiler The applicant who filed the enquiry.
	 * @param project      The project the enquiry is about.
	 * @param question     The question posed in the enquiry.
	 * @return A new {@link Enquiry} instance.
	 */
	public Enquiry create(String ID, Applicant enquiryFiler, Project project, String question) {
		return create(ID, enquiryFiler, project, question, null);
	}

	/**
	 * Creates a new enquiry with a provided ID, question, and reply.
	 * <p>
	 * This method acts as the core factory method, instantiating the {@link Enquiry}
	 * object with all necessary details, including automatically fetching the
	 * project's manager and officers.
	 * </p>
	 *
	 * @param ID           The unique ID for the new enquiry.
	 * @param enquiryFiler The applicant who filed the enquiry.
	 * @param project      The project the enquiry is about.
	 * @param question     The question posed in the enquiry.
	 * @param reply        The reply to the enquiry. Can be {@code null}.
	 * @return A new {@link Enquiry} instance.
	 */
	public Enquiry create(String ID, Applicant enquiryFiler, Project project, String question, String reply) {
		Enquiry enquiry = new Enquiry(ID, enquiryFiler, project, project.getManager(), project.getOfficers(), question, reply);
		return enquiry;
	}
}