package user;

import application.Application;
import project.Project;

/**
 * Represents an applicant who can apply for HDB projects.
 * Applicants have information about the project they have applied for,
 * their application status, and whether they are eligible to apply.
 */
public class Applicant extends User {

	protected Project appliedProject;
	protected Application projectApplication, withdrawalApplication;
	protected boolean canApply, isWithdrawing, isReceiptReady;
	protected String receipt;

	/**
	 * Constructs a new {@code Applicant} with basic user information.
	 *
	 * @param userID      The unique identifier for the applicant.
	 * @param name        The name of the applicant.
	 * @param age         The age of the applicant.
	 * @param maritalStatus The marital status of the applicant.
	 */
	public Applicant(String userID, String name, int age, MARITAL_STATUS maritalStatus) {
		super(userID, name, age, maritalStatus);
		userType = User.USER_TYPE.APPLICANT;
		this.canApply = true;
		this.isWithdrawing = false;
		this.isReceiptReady = false;
	}

	/**
	 * Constructs a new {@code Applicant} with basic user information and a password.
	 *
	 * @param userID      The unique identifier for the applicant.
	 * @param name        The name of the applicant.
	 * @param age         The age of the applicant.
	 * @param maritalStatus The marital status of the applicant.
	 * @param password    The password for the applicant's account.
	 */
	public Applicant(String userID, String name, int age, MARITAL_STATUS maritalStatus, String password) {
		super(userID, name, age, maritalStatus, password);
		userType = User.USER_TYPE.APPLICANT;
		this.canApply = true;
		this.isWithdrawing = false;
		this.isReceiptReady = false;
	}

	/**
	 * Returns the project the applicant has applied for.
	 *
	 * @return The {@code Project} object the applicant has applied for, or {@code null} if none.
	 */
	public Project getAppliedProject() {
		return appliedProject;
	}
	
	/**
	 * Returns the application submitted for a project.
	 *
	 * @return The {@code Application} object for the project application, or {@code null} if none.
	 */
	public Application getProjectApplication() {
		return projectApplication;
	}

	/**
	 * Returns the application submitted for withdrawal.
	 *
	 * @return The {@code Application} object for the withdrawal application, or {@code null} if none.
	 */
	public Application getWithdrawalApplication() {
		return withdrawalApplication;
	}

	/**
	 * Checks if the applicant can currently apply for a project.
	 *
	 * @return {@code true} if the applicant can apply, {@code false} otherwise.
	 */
	public boolean canApply() {
		return canApply;
	}

	/**
	 * Checks if the applicant is currently in the process of withdrawing an application.
	 *
	 * @return {@code true} if the applicant is withdrawing, {@code false} otherwise.
	 */
	public boolean isWithdrawing() {
		return isWithdrawing;
	}

	/**
	 * Checks if the receipt for the application is ready.
	 *
	 * @return {@code true} if the receipt is ready, {@code false} otherwise.
	 */
	public boolean isReceiptReady() {
		return isReceiptReady;
	}

	/**
	 * Returns the receipt details for the application.
	 *
	 * @return The receipt string, or {@code null} if not ready.
	 */
	public String getReceipt() {
		return receipt;
	}

	/**
	 * Sets the project the applicant has applied for. This also sets {@code canApply} to {@code false}
	 * and {@code isWithdrawing} to {@code false}.
	 *
	 * @param appliedProject The {@code Project} object the applicant is applying for.
	 */
	public void setAppliedProject(Project appliedProject) {
		if (appliedProject != null) {
			this.appliedProject = appliedProject;
			setCanApply(false);
			setWithdrawing(false);
		}
	}

	/**
	 * Sets the project application for the applicant. This also sets {@code canApply} to {@code false}
	 * and {@code isWithdrawing} to {@code false}.
	 *
	 * @param projectApplication The {@code Application} object for the project application.
	 */
	public void setProjectApplication(Application projectApplication) {
		if (projectApplication != null) {
			this.projectApplication = projectApplication;
			setCanApply(false);
			setWithdrawing(false);
		}
	}

	/**
	 * Sets the withdrawal application for the applicant.
	 *
	 * @param withdrawalApplication The {@code Application} object for the withdrawal application.
	 */
	public void setWithdrawalApplication(Application withdrawalApplication) {
		this.withdrawalApplication = withdrawalApplication;
		setWithdrawing(true);
	}

	/**
	 * Sets whether the applicant can apply for a project.
	 *
	 * @param canApply {@code true} if the applicant can apply, {@code false} otherwise.
	 */
	public void setCanApply(boolean canApply) {
		this.canApply = canApply;
	}
	
	/**
	 * Sets whether the applicant is currently withdrawing an application.
	 *
	 * @param isWithdrawing {@code true} if the applicant is withdrawing, {@code false} otherwise.
	 */
	public void setWithdrawing(boolean isWithdrawing) {
		this.isWithdrawing = isWithdrawing;
	}

	/**
	 * Sets whether the receipt for the application is ready.
	 *
	 * @param isReceiptReady {@code true} if the receipt is ready, {@code false} otherwise.
	 */
	public void setReceiptReady(boolean isReceiptReady) {
		this.isReceiptReady = isReceiptReady;
	}

	/**
	 * Sets the receipt details for the application.
	 *
	 * @param receipt The receipt string.
	 */
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	
	/**
	 * Generates a receipt string for the applicant's successful application.
	 *
	 * @param applicant The {@code Applicant} for whom to generate the receipt.
	 * @return A formatted receipt string containing applicant details and applied project.
	 */
	public static String generateReceipt(Applicant applicant) {
		return "Name: " + applicant.getName() + "\n"
				+ "NRIC: " + applicant.getUserID() + "\n"
				+ "Age: " + applicant.getAge() + "\n"
				+ "Marital Status:" + (applicant.isMarried() ? "Married" : "Single") + "\n"
				+ "Project: " + applicant.getAppliedProject().getName();
	}
	
	/**
	 * Generates and sets a receipt for the applicant's application.
	 * <p>
	 * This method populates the internal receipt string with the applicant's details
	 * and the name of the project they applied for. It also sets the receipt status
	 * to ready. The receipt includes the applicant's name, NRIC (user ID), age,
	 * marital status, and the name of the applied project.
	 * </p>
	 */
	public void generateReceipt() {
		this.setReceiptReady(true);
		this.setReceipt("Name: " + this.getName() + "\n"
				+ "NRIC: " + this.getUserID() + "\n"
				+ "Age: " + this.getAge() + "\n"
				+ "Marital Status:" + (this.isMarried() ? "Married" : "Single") + "\n"
				+ "Project: " + this.getAppliedProject().getName());
	}
}