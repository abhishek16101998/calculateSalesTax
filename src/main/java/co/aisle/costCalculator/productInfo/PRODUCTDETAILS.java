package co.aisle.costCalculator.productInfo;

/**
 * ENUM to maintain type of products
 *
 */
public enum PRODUCTDETAILS {
	BOOKIMP(true, true), FOODIMP(true, true), MEDICALIMP(true, true), OTHERSIMP(false, true), MEDICAL(true, false),
	OTHERS(false, false), BOOK(true, false), FOOD(true, false);

	private boolean isExempted;
	private boolean isImported;

	private PRODUCTDETAILS(boolean exempted, boolean imported) {
		isExempted = exempted;
		isImported = imported;
	}

	public boolean isImport() {
		return isImported;
	}

	public boolean includeTax() {
		return isExempted;
	}
}
