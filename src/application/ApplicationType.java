package application;

/**
 * Represents the distinct types of applications handled by the system.
 * This enum ensures that all applications are correctly categorized,
 * simplifying management and processing based on their specific purpose.
 */
public enum ApplicationType {
	/**
     * An application submitted by an {@code Applicant} to ballot for a new
     * Build-To-Order (BTO) flat. This type is used for the primary housing
     * application process.
     */
	BTO_APPLICATION,
	
	/**
     * An application submitted by an {@code HDBOfficer} to register their
     * interest and availability to be assigned to a specific HDB project.
     */
	PROJECT_REGISTRATION,
	
	/**
     * An application submitted by an {@code Applicant} to formally withdraw
     * a previously submitted {@code BTO_APPLICATION}.
     */
	WITHDRAWAL_APPLICATION;
}
