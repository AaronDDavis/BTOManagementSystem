package reader;

import misc.IFileWorker;

/**
 * A generic interface for readers that are responsible for reading data
 * of a specific type {@code T} from a data source (e.g., a file).
 * This interface extends {@code IFileWorker}, implying that implementing
 * classes will likely interact with files as their data source and will
 * have access to the file path constants defined in {@code IFileWorker}.
 *
 * @param <T> The type of object that the reader is responsible for reading.
 */
public interface IReader<T> extends IFileWorker
{
	// No specific methods are defined in this generic interface.
		// Concrete reader implementations will define their own read methods
		// that return a collection i.e. ArrayList of type T.
		// The purpose of this interface is to provide a common type for all readers
		// in the application and to inherit the file path constants.
}
