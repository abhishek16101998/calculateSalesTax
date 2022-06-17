package co.aisle.costCalculator.productInfo;

public class Products {

	private String name;
	private float price;
	private PRODUCTDETAILS info;
	
	public Products( PRODUCTDETAILS pd,String name, float price){
		this.name = name;
		this.storePrice(price);
		
		this.info = pd;
		
	}
	
	public String toString(){
		return this.name + this.priceInfo();
	}

	public float priceInfo() {
		return price;
	}

	public void storePrice(float price) {
		this.price = price;
	}
	
	public String prodName() {
		return name;
	}

	public void enterName(String name) {
		this.name = name;
	}
	
	public boolean includeTax() {
		return !this.info.includeTax();
	}

	public boolean isImport() {
		return this.info.isImport();
	}
}
