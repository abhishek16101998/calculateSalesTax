package co.aisle.costCalculator.util;

public class CheckForItemExempted {
	public int validateExemption(String pdInfo, String[] item) {
		int index = -1;
		Boolean itemFound=false;
		for (int i = 0; i < item.length; i++) {

			index = pdInfo.indexOf(item[i]);

			if (index != -1)
			{
				itemFound=true;
				return i;
			}

		}
		
		if(!itemFound)
		return -1;
		else
			return index;

	}
}
