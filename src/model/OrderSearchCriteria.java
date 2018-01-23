package model;

import java.util.HashMap;

/** Used to store criteria for filtering and sorting Order objects **/
public class OrderSearchCriteria implements SearchCriteria {
	public static final String SORT_QUANTITY = "sortQuantity";
	public static final String SORT_DATE = "sortOrderDate";
	public static final String FILTER_USERNAME = "filterUsername";
	public static final String FILTER_STATUS = "filterStatus";
	public static final String FILTER_ORDER_ID = "filterOrderId";

	private HashMap<String, String> criteriaMap = new HashMap<>();

	@Override
	public void putCriteria(String criteria, String value) {
		if (value.equals("")) {
			// do nothing
		} else {
			criteriaMap.put(criteria, value);
		}
	}

	@Override
	public HashMap<String, String> getCriteriaMap() {
		return criteriaMap;
	}
}
