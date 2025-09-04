package reader;

import user.User;

/**
 * A specialized generic interface for readers that are responsible for reading
 * data of a specific type {@code T}, where {@code T} is a subclass of {@code User}.
 * This interface extends the generic {@code IReader<T>} interface, inheriting
 * the file path constants from {@code IFileWorker} through {@code IReader}.
 * It serves to provide a common type for all user-related readers in the application.
 *
 * @param <T> The type of {@code User} subclass that the reader is responsible for reading.
 */
public interface IUserReader<T extends User> extends IReader<T>
{
}
