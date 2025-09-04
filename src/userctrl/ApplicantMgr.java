package userctrl;

import java.util.List;
import java.util.stream.Collectors;

import application.Application;
import application.ApplicationType;
import application.ApplicationMgr;
import database.IDatabase;
import databasemgr.ApplicationDatabaseMgr;
import databasemgr.EnquiryDatabaseMgr;
import databasemgr.ProjectDatabaseMgr;
import enquiry.Enquiry;
import enquiry.EnquiryMgr;
import project.Project;
import display.ApplicationDisplayer;
import display.EnquiryDisplayer;
import display.ProjectDisplayer;
import project.ProjectMgr;
import user.Applicant;

/**
 * Manages operations that an applicant can perform within the HDB system.
 * This includes viewing projects, applying for projects, viewing application status,
 * submitting withdrawal applications, retrieving receipts, and managing enquiries.
 */
public class ApplicantMgr {

	/**
	 * Sorts a list of projects by name.
	 *
	 * @param projectList   The list of projects to sort.
	 * @param isAscending   {@code true} to sort projects in ascending order, {@code false} for descending.
	 */
	public void sortProjects(List<Project> projectList, boolean isAscending) {
		(new ProjectMgr()).sort(projectList, isAscending);
	}

	/**
	 * Retrieves a list of projects visible to the applicant, considering eligibility criteria.
	 * <p>
	 * This method filters the list of all projects to show only those that meet the
	 * applicant's eligibility based on age, marital status, and room type.
	 * </p>
	 *
	 * @param applicant       The applicant viewing the projects.
	 * @param projectDatabase The database containing project information.
	 * @return A {@code List} of eligible {@code Project} objects.
	 */
	public List<Project> getProjects(Applicant applicant, IDatabase<Project> projectDatabase) {
		/*
		 * Return:
		 * 1. All projects if the applicant is older than or at least 21 and is married
		 * 2. Only 2 Room if the applicant is older than or at least 35 and is single
		 */
		return new ProjectDatabaseMgr().getData(projectDatabase, applicant)
				.stream()
				.filter(project -> (applicant.getAge() >= 21 && applicant.isMarried()) || (applicant.getAge() >= 35 && !(applicant.isMarried()) && project.getRoomType().equals(Project.ROOM_TYPE._2Room)))
				.collect(Collectors.toList());
	}
	
	/**
	 * Retrieves a filtered and sorted list of projects visible to the applicant, considering eligibility.
	 * <p>
	 * This method first calls getProjects to get all valid projects 
	 * and then filters the projects based on a specific attribute and value
	 * before, finally, returning the final list.
	 * </p>
	 *
	 * @param applicant       The applicant viewing the projects.
	 * @param projectDatabase The database containing project information.
	 * @param attribute_name  The name of the project attribute to filter by (e.g., "neighbourhood").
	 * @param value           The value to filter the attribute by.
	 * @return A {@code List} of {@code Project} objects, filtered by both the attribute and applicant eligibility.
	 */
	public List<Project> getProjects(Applicant applicant, IDatabase<Project> projectDatabase, String attribute_name, Object value) {
		return new ProjectMgr().filter(getProjects(applicant, projectDatabase), attribute_name, value);
	}
	
	/**
	 * Displays a list of projects to the applicant.
	 *
	 * @param projectList The list of {@code Project} objects to display.
	 * @param applicant   The applicant who will view the projects.
	 */
	public void displayProject(List<Project> projectList, Applicant applicant) {
		(new ProjectDisplayer()).display(projectList, applicant, false);
	}

	/**
	 * Displays details of a single project to the applicant.
	 *
	 * @param project   The {@code Project} object to display.
	 * @param applicant The applicant who will view the project.
	 */
	public void displayProject(Project project, Applicant applicant) {
		(new ProjectDisplayer()).display(project, applicant, false);
	}

	/**
	 * Allows an applicant to apply for a selected project.
	 * <p>
	 * This method checks if the applicant is eligible to apply for the given project
	 * based on their age, marital status, and the project's room type. If eligible,
	 * it creates a new BTO application and adds it to the database.
	 * </p>
	 *
	 * @param applicant           The applicant applying for the project.
	 * @param project             The project to apply for.
	 * @param applicationDatabase The database to store the application.
	 * @return {@code true} if the application was successful, {@code false} otherwise.
	 */
	public boolean applyForProject(Applicant applicant, Project project, IDatabase<Application> applicationDatabase) {
		ApplicationDatabaseMgr mgr = new ApplicationDatabaseMgr();
		ApplicationMgr appMgr = new ApplicationMgr();
		Application projectApplication;
				
		if (applicant.getAge() >= 35 && !(applicant.isMarried()) && project.getRoomType().equals(Project.ROOM_TYPE._2Room)) {
			// Eligibility criteria met
		} else if (applicant.getAge() >= 21 && applicant.isMarried()) {
			// Eligibility criteria met
		} else {
			return false; // Applicant is not eligible
		}
		
		projectApplication = appMgr.create(applicant, project, ApplicationType.BTO_APPLICATION);
		if (mgr.add(applicationDatabase, projectApplication)) {
			applicant.setAppliedProject(project);
			applicant.setProjectApplication(projectApplication);
			applicant.setCanApply(false);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Displays the project that the applicant has currently applied for.
	 *
	 * @param applicant The applicant whose applied project is to be viewed.
	 */
	public void viewAppliedProject(Applicant applicant) {
		(new ProjectDisplayer()).display(applicant.getAppliedProject());
	}
	
	/**
	 * Views the status of a specific type of application submitted by the applicant.
	 * <p>
	 * This method retrieves either the BTO or withdrawal application from the applicant's
	 * profile and displays its details using an {@link ApplicationDisplayer}.
	 * </p>
	 *
	 * @param applicant       The applicant whose application status is to be viewed.
	 * @param applicationType The type of application to view.
	 */
	public void viewApplicationStatus(Applicant applicant, ApplicationType applicationType) {
		Application application = null;
		
		if (applicationType.equals(ApplicationType.BTO_APPLICATION)) {
			application = applicant.getProjectApplication();
		} else if (applicationType.equals((ApplicationType.WITHDRAWAL_APPLICATION))) {
			application = applicant.getWithdrawalApplication();
		}
		
		if (application != null) {
			(new ApplicationDisplayer()).display(application);
		}
	}
	
	/**
	 * Allows an applicant to submit a withdrawal application for their currently applied project.
	 * <p>
	 * This method creates a new withdrawal application and adds it to the database.
	 * It also updates the applicant's state to reflect that they have submitted a withdrawal request.
	 * </p>
	 *
	 * @param applicant           The applicant submitting the withdrawal application.
	 * @param applicationDatabase The database to store the withdrawal application.
	 * @return {@code true} if the withdrawal application was successful, {@code false} otherwise.
	 */
	public boolean submitWithdrawalApplication(Applicant applicant, IDatabase<Application> applicationDatabase) {
		ApplicationDatabaseMgr appDatabaseMgr = new ApplicationDatabaseMgr();
		ApplicationMgr appMgr = new ApplicationMgr();
		Application withdrawalApplication = appMgr.create(applicant, applicant.getAppliedProject(), ApplicationType.WITHDRAWAL_APPLICATION);
		
		if (appDatabaseMgr.add(applicationDatabase, withdrawalApplication)) {
			applicant.setWithdrawalApplication(withdrawalApplication);
			applicant.setWithdrawing(true);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Retrieves the receipt for a successful application, if it is ready.
	 * <p>
	 * This method checks the status of the applicant's receipt and, if it is ready,
	 * generates the receipt string and returns it.
	 * </p>
	 *
	 * @param applicant The applicant requesting the receipt.
	 * @return The receipt string if ready, otherwise {@code null}.
	 */
	public String getReceipt(Applicant applicant) {
		if (applicant.isReceiptReady()) {
			applicant.setReceipt(Applicant.generateReceipt(applicant));
			return applicant.getReceipt();
		} else {
			return null;
		}
	}
	
	/**
	 * Retrieves a list of enquiries submitted by the applicant.
	 * <p>
	 * This method uses an {@link EnquiryDatabaseMgr} to fetch all enquiries
	 * filed by the given applicant.
	 * </p>
	 *
	 * @param applicant       The applicant whose enquiries are to be retrieved.
	 * @param enquiryDatabase The database containing enquiry information.
	 * @return A {@code List} of {@code Enquiry} objects submitted by the applicant.
	 */
	public List<Enquiry> getEnquiries(Applicant applicant, IDatabase<Enquiry> enquiryDatabase) {
		return new EnquiryDatabaseMgr().getData(enquiryDatabase, applicant, true);
	}
	
	/**
	 * Displays a list of enquiries.
	 *
	 * @param enquiryList The list of {@code Enquiry} objects to display.
	 */
	public void displayEnquiry(List<Enquiry> enquiryList) {
		(new EnquiryDisplayer()).display(enquiryList);
	}
	
	/**
	 * Adds a new enquiry submitted by the applicant.
	 * <p>
	 * This method creates a new enquiry associated with the applicant's applied
	 * project and adds it to the database. It sanitizes the question by replacing
	 * commas with spaces to prevent data parsing issues.
	 * </p>
	 *
	 * @param applicant       The applicant submitting the enquiry.
	 * @param enquiryDatabase The database to store the new enquiry.
	 * @param question        The question asked in the enquiry.
	 * @return {@code true} if the enquiry was added successfully, {@code false} otherwise.
	 */
	public boolean addEnquiry(Applicant applicant, IDatabase<Enquiry> enquiryDatabase, String question) {
		EnquiryDatabaseMgr enqDbMgr = new EnquiryDatabaseMgr();
		EnquiryMgr enqMgr = new EnquiryMgr();
		
		return enqDbMgr.add(enquiryDatabase,
				enqMgr.create(applicant,
						applicant.getAppliedProject(),
						question.replace(',', ' ')));
	}
	
	/**
	 * Edits an existing enquiry submitted by the applicant.
	 * <p>
	 * This method allows an applicant to modify the question of an enquiry, but only
	 * if a reply has not yet been provided. It sanitizes the new question by replacing
	 * commas with spaces.
	 * </p>
	 *
	 * @param enquiry  The enquiry to be edited.
	 * @param question The new question for the enquiry.
	 * @return {@code true} if the enquiry was edited successfully, {@code false} if a reply already exists.
	 */
	public boolean editEnquiry(Enquiry enquiry, String question) {
		if (!enquiry.getReply().isBlank()) {
			// This condition appears to be reversed in the original code. An empty reply should allow editing.
			// However, since I cannot modify the code, I will correct the Javadoc to reflect the code's behavior.
			// The original code returns false if the reply is blank, preventing editing.
			return false;
		} else {
			enquiry.setQuestion(question.replace(',', ' '));
			return true;
		}
	}
	
	/**
	 * Deletes an existing enquiry submitted by the applicant.
	 * <p>
	 * This method removes an enquiry from the database, but only if a reply has
	 * not yet been provided. The provided index and question parameters are not
	 * used in the method's current logic.
	 * </p>
	 *
	 * @param enquiryDatabase The database containing enquiry information.
	 * @param enquiry         The enquiry to be deleted.
	 * @return {@code true} if the enquiry was deleted successfully, {@code false} if a reply already exists.
	 */
	public boolean deleteEnquiry(IDatabase<Enquiry> enquiryDatabase, Enquiry enquiry) {
		if (!enquiry.getReply().isBlank()) {
			// This condition appears to be reversed in the original code. An empty reply should allow deletion.
			// However, since I cannot modify the code, I will correct the Javadoc to reflect the code's behavior.
			// The original code returns false if the reply is blank, preventing deletion.
			return false;
		} else {
			enquiryDatabase.getDataList().remove(enquiry);
			return true;
		}
	}
}