package writer;

import user.User;

/**
 * A generic interface for writing user data to a persistent storage.
 * It extends the {@code IWriter} interface, specifically for classes that are subclasses of {@code User}.
 * This interface serves as a marker to identify writers that handle user-related data.
 *
 * @param <T> The specific type of {@code User} that this writer handles.
 */
public interface IUserWriter<T extends User> extends IWriter<T>
{
	// It inherits the 'write(ArrayList<T> list)' method from IWriter.
}
