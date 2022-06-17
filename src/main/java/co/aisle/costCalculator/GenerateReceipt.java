package co.aisle.costCalculator;

/**
 * Get the amount and the tax value based on the input
 *
 */
public class GenerateReceipt {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			throw new Exception("Provide the location for the input file");
		}
		ComputePrice computeAmt = new ComputePrice(args[0]);
		computeAmt.getBillAmount();
		computeAmt.printBill();
	}
}
