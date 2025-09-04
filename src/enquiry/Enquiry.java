package enquiry;

import java.util.List;

import project.Project;
import user.Applicant;
import user.HDBManager;
import user.HDBOfficer;

/**
 * Represents an enquiry submitted by an applicant regarding a specific project.
 * <p>
 * This class encapsulates all the details of an enquiry, including its unique ID,
 * the user who filed it, the project it pertains to, the project's manager and officers,
 * the question asked, and the corresponding reply. It provides constructors for
 * creating new enquiries with or without initial question and reply details.
 * </p>
 */
public class Enquiry {
	
	/**
	 * The unique identifier for the enquiry.
	 */
	private String ID;
	
	/**
	 * The applicant who filed this enquiry.
	 */
	private Applicant enquiryFiler;
	
	/**
	 * The project that this enquiry is about.
	 */
	private Project project;
	
	/**
	 * The manager of the project.
	 */
	private HDBManager projectManager;
	
	/**
	 * A list of HDB officers assigned to the project.
	 */
	private List<HDBOfficer> projectOfficers;
	
	/**
	 * The question asked by the applicant.
	 */
	private String question;
	
	/**
	 * The reply provided by an HDB staff member.
	 */
	private String reply;

	/**
	 * Constructs a new Enquiry instance with a basic set of information.
	 * <p>
	 * The question and reply are initially set to {@code null}.
	 * </p>
	 *
	 * @param ID            The unique identifier for the enquiry.
	 * @param enquiryFiler  The applicant who filed the enquiry.
	 * @param project       The project the enquiry is about.
	 * @param projectManager The manager of the project.
	 * @param projectOfficers The list of officers assigned to the project.
	 */
	public Enquiry(String ID, Applicant enquiryFiler, Project project, HDBManager projectManager,
			List<HDBOfficer> projectOfficers) {
		this.ID = ID;
		this.enquiryFiler = enquiryFiler;
		this.project = project;
		this.projectManager = projectManager;
		this.projectOfficers = projectOfficers;
		this.question = null;
		this.reply = null;
	}
	
	/**
	 * Constructs a new Enquiry instance with a question and a reply.
	 *
	 * @param ID            The unique identifier for the enquiry.
	 * @param enquiryFiler  The applicant who filed the enquiry.
	 * @param project       The project the enquiry is about.
	 * @param projectManager The manager of the project.
	 * @param projectOfficers The list of officers assigned to the project.
	 * @param question      The question asked in the enquiry.
	 * @param reply         The reply to the enquiry.
	 */
	public Enquiry(String ID, Applicant enquiryFiler, Project project, HDBManager projectManager,
			List<HDBOfficer> projectOfficers, String question, String reply) {
		this(ID, enquiryFiler, project, projectManager, projectOfficers);
		this.question = question;
		this.reply = reply;
	}
	
	/**
	 * Retrieves the unique ID of the enquiry.
	 *
	 * @return The enquiry's unique ID.
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Retrieves the applicant who filed the enquiry.
	 *
	 * @return The {@link Applicant} object who filed the enquiry.
	 */
	public Applicant getEnquiryFiler() {
		return enquiryFiler;
	}

	/**
	 * Retrieves the project related to the enquiry.
	 *
	 * @return The {@link Project} object the enquiry is about.
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Retrieves the project manager associated with the enquiry's project.
	 *
	 * @return The {@link HDBManager} object.
	 */
	public HDBManager getProjectManager() {
		return projectManager;
	}

	/**
	 * Retrieves the list of project officers assigned to the enquiry's project.
	 *
	 * @return A {@link List} of {@link HDBOfficer} objects.
	 */
	public List<HDBOfficer> getProjectOfficers() {
		return projectOfficers;
	}

	/**
	 * Retrieves the question asked in the enquiry.
	 *
	 * @return The question as a {@link String}.
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Retrieves the reply to the enquiry.
	 *
	 * @return The reply as a {@link String}.
	 */
	public String getReply() {
		return reply;
	}
	
	/**
	 * Sets the question for the enquiry.
	 *
	 * @param question The question to be set.
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Sets the reply for the enquiry.
	 *
	 * @param reply The reply to be set.
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}
}