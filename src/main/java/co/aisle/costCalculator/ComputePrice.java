package co.aisle.costCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

import co.aisle.costCalculator.productInfo.PRODUCTDETAILS;
import co.aisle.costCalculator.productInfo.Products;
import co.aisle.costCalculator.util.CheckForItemExempted;
import co.aisle.costCalculator.util.PrintReceipt;

/**
 * Compute the tax and the total amount based on the import and exempted product
 * list
 *
 */
public class ComputePrice {
	private ArrayList<Products> products = new ArrayList<Products>();
	private CheckForItemExempted itemExem = new CheckForItemExempted();
	private double total;
	private double taxTotal;
	String[] notTaxableItems = new String[] { "pills", "book", "chocolate" };

	/**
	 * const
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public ComputePrice(String inputFileName) throws FileNotFoundException {
		Scanner itemsPurchased = new Scanner(System.in);
		File file = new File(inputFileName);
		itemsPurchased = new Scanner(file);
		while (itemsPurchased.hasNextLine()) {
			String line = itemsPurchased.nextLine();
			String[] words = line.split(" ");
			String exemptedType = null;
			if (itemExem.validateExemption(line, notTaxableItems) != -1) {
				exemptedType = notTaxableItems[itemExem.validateExemption(line, notTaxableItems)];
			}

			int splitIndex = line.lastIndexOf("at");
			float price = Float.parseFloat((line.substring(splitIndex + 2)));
			String name = line.substring(1, splitIndex);

			for (int i = 0; i < Integer.valueOf(words[0]); i++) {

				Products newProduct = null;
				if (isImport(line)) {
					if (exemptedType == null) {
						newProduct = new Products(PRODUCTDETAILS.OTHERSIMP, name, price);
					} else {
						if (exemptedType.equals("book")) {
							newProduct = new Products(PRODUCTDETAILS.BOOKIMP, name, price);
						} else if (exemptedType.equals("chocolate")) {
							newProduct = new Products(PRODUCTDETAILS.FOODIMP, name, price);
						} else if (exemptedType.equals("pills")) {
							newProduct = new Products(PRODUCTDETAILS.MEDICALIMP, name, price);
						}
					}

				} else {
					if (exemptedType == null) {
						newProduct = new Products(PRODUCTDETAILS.OTHERS, name, price);

					} else {
						if (exemptedType.equals("book")) {
							newProduct = new Products(PRODUCTDETAILS.BOOK, name, price);
						} else if (exemptedType.equals("chocolate")) {
							newProduct = new Products(PRODUCTDETAILS.FOOD, name, price);
						} else if (exemptedType.equals("pills")) {
							newProduct = new Products(PRODUCTDETAILS.MEDICAL, name, price);
						}
					}
				}
				products.add(newProduct);
			}
		}
		itemsPurchased.close();
	}

	/**
	 * Calculate total and taxable bill amounts
	 */
	public void getBillAmount() {

		BigDecimal totalAmount = new BigDecimal("0");
		BigDecimal totalTax = new BigDecimal("0");
		for (int i = 0; i < products.size(); i++) {

			totalTax = BigDecimal.valueOf(0);
			BigDecimal amtWithoutTax = new BigDecimal(String.valueOf(this.products.get(i).priceInfo()));
			totalAmount = totalAmount.add(amtWithoutTax);
			// If general tax to be included
			if (products.get(i).includeTax() == true) {
				BigDecimal taxPercentage = new BigDecimal(".10");
				BigDecimal salesTax = taxPercentage.multiply(amtWithoutTax);
				salesTax = roundOffToUpper(salesTax, BigDecimal.valueOf(0.05), RoundingMode.UP);
				totalTax = totalTax.add(salesTax);
			}
			// If import tax to be included
			if (products.get(i).isImport() == true) {
				BigDecimal taxPercentage = new BigDecimal(".05");
				BigDecimal importTax = taxPercentage.multiply(amtWithoutTax);
				importTax = roundOffToUpper(importTax, BigDecimal.valueOf(0.05), RoundingMode.UP);
				totalTax = totalTax.add(importTax);
			}

			products.get(i).storePrice(totalTax.floatValue() + products.get(i).priceInfo());
			taxTotal = taxTotal + totalTax.doubleValue();
			totalAmount = totalAmount.add(totalTax);
		}
		taxTotal = (double) Math.round(taxTotal * 100) / 100;
		total = totalAmount.doubleValue();
	}

	/**
	 * Print bill with the correct format
	 */
	public void printBill() {
		PrintReceipt receipt = new PrintReceipt();
		receipt.printReceipt(products, taxTotal, total);
	}

	/**
	 * Round off to the upper value
	 * 
	 * @param value
	 * @param increment
	 * @param roundingMode
	 * @return
	 */
	private BigDecimal roundOffToUpper(BigDecimal value, BigDecimal increment, RoundingMode roundingMode) {
		if (increment.signum() == 0) {
			return value;
		}

		BigDecimal divided = value.divide(increment, 0, roundingMode);
		BigDecimal result = divided.multiply(increment);
		result.setScale(2, RoundingMode.UNNECESSARY);
		return result;
	}

	/**
	 * Check if the product is imported
	 * 
	 * @param item
	 * @return
	 */
	private Boolean isImport(String item) {
		return item.contains("imported");
	}
}
