package model;

import java.util.HashMap;

/**
 * Interface to be used when performing search criteria on tables in the persistence implementation.
 * There is a class implementing this interface for each type of relation in the persistence.
 */
public interface SearchCriteria {
	/**
	 * Adds a new criteria to the sort.
	 * Select criteria from a list of final strings in the given implementation
	 * and specify the string value of the criteria.
	 * <p>
	 * Sorts will determine the order of items.
	 * Filters will include specified value of items.
	 *
	 * @param criteria One of the static final strings of the given interface implementation.
	 * @param value    Value to filtered or sorted upon.
	 */
	void putCriteria(String criteria, String value);

	/**
	 * Returns the list of search criteria as a map.
	 * Keys correspond to sort and filter labels.
	 * Values correspond to specified value for sort or filter.
	 *
	 * @return Map
	 */
	HashMap<String, String> getCriteriaMap();
}
