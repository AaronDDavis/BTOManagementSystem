package display;

import application.Application;

/**
 * A class responsible for displaying information about an {@link Application} object.
 * <p>
 * This class extends {@link ItemDisplayer} and provides a specific implementation
 * for formatting and printing the details of an application to the console. It
 * is designed to present key information in a clear and organized manner.
 * </p>
 */
public class ApplicationDisplayer extends ItemDisplayer<Application> {
	
	/**
	 * Displays the details of a given {@link Application} object to the standard output.
	 * <p>
	 * The output includes the application's type, the name of the user who issued it,
	 * the name of the project it concerns, and its current status.
	 * </p>
	 *
	 * @param application The {@link Application} object to be displayed.
	 */
	@Override
    public void display(Application application) {
		System.out.println(application.getApplicationType());
		System.out.println("\tIssued by:\t" + application.getUser().getName());
		System.out.println("\tConcerning Project:\t" + application.getProject().getName());
		System.out.println("\tApplication Status:\t" + application.getStatus());
    }
}