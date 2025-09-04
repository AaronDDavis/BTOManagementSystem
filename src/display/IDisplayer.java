package display;

/**
 * A generic interface for classes that are responsible for displaying an object.
 * <p>
 * This interface defines a single method, {@code display}, which a class must implement
 * to provide a way to format and present information about an object of type {@code T}.
 * This promotes a consistent display mechanism across different types of objects.
 * </p>
 */
public interface IDisplayer<T> {
	
	/**
	 * Displays the details of a given object.
	 *
	 * @param ob The object of type {@code T} to be displayed.
	 */
	public abstract void display(T ob);
}