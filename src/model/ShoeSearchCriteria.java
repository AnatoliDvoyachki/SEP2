package model;

import java.util.HashMap;

/** Used to store criteria for sorting and filtering Shoe objects **/
public class ShoeSearchCriteria implements SearchCriteria {
	public static final String SORT_PRICE = "sortPrice";
	public static final String SORT_SIZE = "sortSize";
	public static final String FILTER_SIZE = "filterSize";
	public static final String FILTER_COLOR = "filterColor";
	public static final String FILTER_BRAND = "filterBrand";
	public static final String FILTER_MODEL = "filterModel";

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
