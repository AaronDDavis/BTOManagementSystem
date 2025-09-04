package project;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import misc.IDCreator;
import user.HDBManager;
import user.HDBOfficer;

/**
 * Manages the business logic for {@link Project} objects.
 * <p>
 * This class provides methods for creating, filtering, sorting, and modifying
 * project data. It contains the core logic for operations that are independent
 * of user interface concerns, such as validation and data manipulation.
 * </p>
 */
public class ProjectMgr {
	
	/**
	 * Creates a new {@link Project} object with a system-generated unique ID.
	 * <p>
	 * This method uses {@link IDCreator} to generate a unique ID for the project
	 * and then instantiates a new {@link Project} object. It also automatically
	 * links the newly created project to the assigned HDB Officers.
	 * </p>
	 *
	 * @param name                 The name of the project.
	 * @param count                The number of housing units.
	 * @param neighbourhood        The neighbourhood of the project.
	 * @param roomType             The room type.
	 * @param sellingPrice         The selling price.
	 * @param applicationStartDate The start date of the application period.
	 * @param applicationEndDate   The end date of the application period.
	 * @param officerSlot          The number of officer slots.
	 * @param officers             A list of assigned HDB Officers.
	 * @param isVisible            The visibility status.
	 * @param manager              The HDB Manager for the project.
	 * @return The newly created {@link Project} instance.
	 */
	public Project create(String name, int count, String neighbourhood,
			Project.ROOM_TYPE roomType, double sellingPrice, LocalDate applicationStartDate,
			LocalDate applicationEndDate, int officerSlot, List<HDBOfficer> officers,
			boolean isVisible, HDBManager manager) {
		Project project = new Project(
				IDCreator.createProjectID(), name, count, neighbourhood, roomType,
				sellingPrice, applicationStartDate, applicationEndDate,
				manager, officerSlot, officers, isVisible);
		
		for (HDBOfficer officer : officers) {
			officer.getJoinedProjects().add(project);
		}
		
		return project;
	}
	
	/**
	 * Creates a new {@link Project} object with a pre-determined unique ID.
	 * <p>
	 * This overloaded method is useful for loading projects from a data source
	 * where IDs are already assigned.
	 * </p>
	 *
	 * @param ID                   The unique ID for the project.
	 * @param name                 The name of the project.
	 * @param count                The number of housing units.
	 * @param neighbourhood        The neighbourhood of the project.
	 * @param roomType             The room type.
	 * @param sellingPrice         The selling price.
	 * @param applicationStartDate The start date of the application period.
	 * @param applicationEndDate   The end date of the application period.
	 * @param officerSlot          The number of officer slots.
	 * @param officers             A list of assigned HDB Officers.
	 * @param isVisible            The visibility status.
	 * @param manager              The HDB Manager for the project.
	 * @return The newly created {@link Project} instance.
	 */
	public Project create(String ID, String name, int count, String neighbourhood,
			Project.ROOM_TYPE roomType, double sellingPrice, LocalDate applicationStartDate,
			LocalDate applicationEndDate, int officerSlot, List<HDBOfficer> officers,
			boolean isVisible, HDBManager manager) {
		Project project = new Project(
				ID, name, count, neighbourhood, roomType, sellingPrice,
				applicationStartDate, applicationEndDate,
				manager, officerSlot, officers, isVisible);

		for (HDBOfficer officer : officers) {
			officer.getJoinedProjects().add(project);
		}

		return project;
	}
	
	/**
	 * Filters a list of projects based on a specific attribute and a value.
	 * <p>
	 * This method uses Java Streams to filter the list of projects by name,
	 * neighbourhood, or room type. The comparison is case-sensitive for string attributes.
	 * </p>
	 *
	 * @param projectList   The list of projects to be filtered.
	 * @param attributeName The name of the attribute to filter by (e.g., "Name", "Neighbourhood", "RoomType").
	 * @param value         The value to match against the attribute.
	 * @return A new {@link List} containing only the projects that match the filter criteria.
	 */
	public List<Project> filter(List<Project> projectList, String attributeName, Object value) {
		Stream<Project> projectStream = projectList.stream();
		
		if (attributeName.equals("Name")) {
			projectStream = projectStream.filter(project -> project.getName().equals(value));
		} else if (attributeName.equals("Neighbourhood")) {
			projectStream = projectStream.filter(project -> project.getNeighbourhood().equals(value));
		} else if (attributeName.equalsIgnoreCase("RoomType")) {
			projectStream = projectStream.filter(project -> project.getRoomType().equals(value));
		}
		
		return projectStream.collect(Collectors.toList());
	}
	
	/**
	 * Sorts a list of projects alphabetically by name.
	 * <p>
	 * The sorting is done in-place. The order can be specified as ascending or descending.
	 * </p>
	 *
	 * @param projectList The list of projects to be sorted.
	 * @param isAscending {@code true} for ascending order, {@code false} for descending.
	 */
	public void sort(List<Project> projectList, boolean isAscending) {
		projectList.sort(Comparator.comparing(Project::getName));
		if (!isAscending) {
			projectList = projectList.reversed();
		}
	}

	/**
	 * Decrements the unit count of a project by one.
	 *
	 * @param project The project whose count is to be decreased.
	 * @return {@code true} if the count was successfully decreased (i.e., not zero), {@code false} otherwise.
	 */
	public boolean decreaseCount(Project project) {
		if (project.getCount() != 0) {
			project.setCount(project.getCount() - 1);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validates if the number of officer slots is within the acceptable range.
	 *
	 * @param officerSlot The number of officer slots to validate.
	 * @return {@code true} if the number is between 0 and 10 (inclusive), {@code false} otherwise.
	 */
	public boolean isValidOfficerSlot(int officerSlot) {
		return 0 <= officerSlot && officerSlot <= 10;
	}
	
	/**
	 * Retrieves the valid range for officer slots as a string.
	 *
	 * @return A string representation of the valid range.
	 */
	public String getOfficerSlotValidRange() {
		return "0 to 10";
	}

	/**
	 * Validates that the application end date is after the start date.
	 *
	 * @param appSDate The application start date.
	 * @param appEDate The application end date.
	 * @return {@code true} if the end date is after the start date, {@code false} otherwise.
	 */
	public boolean isValidApplicationEndDate(LocalDate appSDate, LocalDate appEDate) {
		return appSDate.isBefore(appEDate);
	}
	
	/**
	 * Toggles the visibility of a project.
	 * <p>
	 * If the project is currently visible, this method makes it not visible, and vice versa.
	 * </p>
	 *
	 * @param project The project whose visibility is to be toggled.
	 */
	public void toggleVisibility(Project project) {
		project.setVisibility(!project.isVisible());
	}
}