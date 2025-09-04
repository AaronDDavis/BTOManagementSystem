package display;

import enquiry.Enquiry;

/**
 * A class responsible for displaying information about an {@link Enquiry} object.
 * <p>
 * This class extends {@link ItemDisplayer} and provides a specific implementation
 * for formatting and printing the details of an enquiry to the console. It
 * presents key information, including the related project, the filer, the question,
 * and the reply, in a clear and structured format.
 * </p>
 */
public class EnquiryDisplayer extends ItemDisplayer<Enquiry> {

	/**
	 * Displays the details of a given {@link Enquiry} object to the standard output.
	 * <p>
	 * The output includes the name of the project the enquiry is about, the name
	 * of the user who filed it, the question asked, and the reply given.
	 * </p>
	 *
	 * @param enquiry The {@link Enquiry} object to be displayed.
	 */
	@Override
	public void display(Enquiry enquiry) {
		System.out.println("Enquiry:");
		System.out.println("\tRegarding Project:\n\t\t" + enquiry.getProject().getName());
		System.out.println("\tFiled by:\n\t\t" + enquiry.getEnquiryFiler().getName());
		System.out.println("\tQuestion:\n\t\t" + enquiry.getQuestion());
		System.out.println("\tReply:\n\t\t" + enquiry.getReply());
	}
}