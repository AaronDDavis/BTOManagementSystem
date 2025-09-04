package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import enquiry.Enquiry;

/**
 * Writes a list of {@code Enquiry} objects to a CSV file.
 * It's crucial that the question and reply do not contain commas to maintain the file format.
 * The data is saved in the following format:
 * enquiryID, applicantFilerID, projectID, question, reply
 */
public class EnquiryWriter implements IWriter<Enquiry>
{
	/*
	 * Make sure no comma in question or reply when inputting
	 * Follows this format:
	 * enquiryID, applicantFilerID, projectID, question, reply
	 */

	/**
	 * Writes the provided list of enquiries to the enquiry data file.
	 * Each enquiry's information is written on a new line, with fields separated by commas.
	 * The format includes enquiry ID, the User ID of the applicant who filed the enquiry,
	 * the ID of the associated project, the enquiry question, and the reply to the enquiry.
	 * If the question or reply is null, an empty string is written to the file.
	 *
	 * @param enquiryList The {@code ArrayList} of {@code Enquiry} objects to write.
	 */
	@Override
	public void write(List<Enquiry> enquiryList)
	{
		String line;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(enquiryFile)))
        {
    		for(Enquiry enquiry: enquiryList)
    		{
    			line = enquiry.getID() + ", "
    					+ enquiry.getEnquiryFiler().getUserID() + ", "
    					+ enquiry.getProject().getID() + ", "
    					+ (enquiry.getQuestion() == null ? "" : enquiry.getQuestion()) + ", "
    					+ (enquiry.getReply() == null ? "" : enquiry.getReply());
        		writer.write(line);
        		writer.newLine();
    		}
        }
        catch (IOException e) {
            System.err.println("Error writing enquiry file: " + e.getMessage());
        }

	}

}
