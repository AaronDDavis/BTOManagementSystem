package application;

/**
 * Represents the possible statuses of an application for an HDB project.
 * This enum is used to track the lifecycle of an application from submission
 * to its final resolution.
 */
public enum ApplicationStatus {
	/**
     * The application has been submitted and is currently awaiting review
     * by an HDB Manager. This is the initial status for all new applications.
     */
	PENDING,
	
	/**
     * The application has been reviewed and successfully approved.
     */
	SUCCESSFUL,
	
	/**
     * The application has been reviewed and rejected.
     */
	UNSUCCESSFUL,
	
	/**
     * The applicant has successfully "booked" a flat.
     * This is a final status that signifies the successful completion of the
     * application process, leading to a confirmed booking.
     */
	BOOKED,

	/**
     * The applicant has officially withdrawn their application.
     * This is a final status, indicating that the applicant has voluntarily
     * removed their application from consideration.
     */
	WITHDRAWN;
}
