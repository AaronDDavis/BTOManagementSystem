package misc;

/**
 * A generic interface for file-related operations, defining constants for file paths.
 * <p>
 * This interface serves as a centralized location for file path strings used
 * throughout the application. By defining these paths as static final constants,
 * it ensures that all classes accessing these files use a consistent, single
 * source of truth, making file management more robust and easier to update.
 * </p>
 */
public interface IFileWorker {
	
	/**
	 * The file path for storing enquiry data.
	 */
	static final String enquiryFile = "data/EnquiryFile.txt";
    
	/**
	 * The file path for storing project data.
	 */
	static final String projectFile = "data/ProjectFile.txt";
    
	/**
	 * The file path for storing applicant user data.
	 */
	static final String applicantFile = "data/ApplicantFile.txt";
    
	/**
	 * The file path for storing HDB officer user data.
	 */
	static final String HDBOfficerFile = "data/HDBOfficerFile.txt";
    
	/**
	 * The file path for storing HDB manager user data.
	 */
	static final String HDBManagerFile = "data/HDBManagerFile.txt";
    
	/**
	 * The file path for storing application data.
	 */
	static final String applicationFile = "data/ApplicationFile.txt";
}