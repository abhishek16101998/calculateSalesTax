package co.aisle.costCalculator.util;

import java.util.ArrayList;

import co.aisle.costCalculator.productInfo.Products;

/**
 * Print the receipt according to the format
 *
 */
public class PrintReceipt {

	public void printReceipt(ArrayList<Products> products, double tax, double totalAmount) {
		for (int i = 0; i < products.size(); i++) {
			System.out.println("1" + products.get(i).prodName() + "at " + products.get(i).priceInfo());
		}

		System.out.printf("Sales Tax: %.2f\n", tax);
		System.out.println("Total: " + totalAmount);
	}
}
