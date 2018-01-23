package model;

import java.util.HashMap;

/** Used store criteria for filtering User objects **/
public class UserSearchCriteria implements SearchCriteria {
	public static final String FILTER_USERNAME = "filterUsername";

	private HashMap<String, String> listOfCriteria = new HashMap<>();

	@Override
	public void putCriteria(String criteria, String value) {
		listOfCriteria.put(criteria, value);
	}

	@Override
	public HashMap<String, String> getCriteriaMap() {
		return listOfCriteria;
	}
}
