package misc;

import java.util.UUID;

/**
 * A utility class for creating unique, formatted IDs for various objects.
 * <p>
 * This class uses Java's {@link UUID} to generate universally unique identifiers
 * and then customizes them by adding specific prefixes to easily distinguish
 * between different types of IDs, such as for applications, enquiries, and projects.
 * </p>
 */
public class IDCreator {
	
	/**
	 * Generates a standard universally unique identifier.
	 *
	 * @return A {@link String} representation of a UUID.
	 */
	private static String createID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Creates a unique ID specifically for an application.
	 * <p>
	 * The ID is formatted as "xxxxxxx-APPL-xxxx-xxxx-xxxxxxxxxxxx", where 'x'
	 * represents a hexadecimal digit from the UUID.
	 * </p>
	 *
	 * @return A unique, formatted ID for an application.
	 */
	public static String createApplicationID() {
		String uniqueID = createID();
		return uniqueID.substring(0, 7) + "-APPL" + uniqueID.substring(8);
	}

	/**
	 * Creates a unique ID specifically for an enquiry.
	 * <p>
	 * The ID is formatted as "xxxxxxx-ENQU-xxxx-xxxx-xxxxxxxxxxxx", where 'x'
	 * represents a hexadecimal digit from the UUID.
	 * </p>
	 *
	 * @return A unique, formatted ID for an enquiry.
	 */
	public static String createEnquiryID() {
		String uniqueID = createID();
		return uniqueID.substring(0, 7) + "-ENQU" + uniqueID.substring(8);
	}

	/**
	 * Creates a unique ID specifically for a project.
	 * <p>
	 * The ID is formatted as "xxxxxxx-PROJ-xxxx-xxxx-xxxxxxxxxxxx", where 'x'
	 * represents a hexadecimal digit from the UUID.
	 * </p>
	 *
	 * @return A unique, formatted ID for a project.
	 */
	public static String createProjectID() {
		String uniqueID = createID();
		return uniqueID.substring(0, 7) + "-PROJ" + uniqueID.substring(8);
	}
}