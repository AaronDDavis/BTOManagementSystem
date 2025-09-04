package writer;

import java.io.IOException;
import java.util.List;

import misc.IFileWorker;

/**
 * A generic interface defining the contract for writing a list of objects to a persistent storage.
 * It extends the {@code IFileWorker} interface, implying that implementing classes will also handle
 * file-related operations like defining the file path.
 *
 * @param <T> The type of objects that this writer handles.
 */
public interface IWriter<T> extends IFileWorker
{
	/**
	 * Writes the provided list of objects to the designated file.
	 * Implementing classes will define the specific format and how the data is written.
	 *
	 * @param list The {@code ArrayList} of objects of type {@code T} to be written.
	 * @throws IOException If an error occurs during the writing process (e.g., file not found, permission issues).
	 */
	public abstract void write(List<T> list) throws IOException;
}
