
public class Product {

	// Clase Producto con estrategia de cálculo de precio
	
	    private String name;
	    private double basePrice;
	    private int quantity;
	    private PriceCalculationStrategy priceStrategy;

	    public Product(String name, double basePrice, PriceCalculationStrategy priceStrategy) {
	        this.name = name;
	        this.basePrice = basePrice;
	        this.priceStrategy = priceStrategy;
	        this.quantity = 0;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public String getName() {
	        return name;
	    }

	    public double getBasePrice() {
	        return basePrice;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public double getPrice() {
	        return priceStrategy.calculatePrice(basePrice, quantity);
	    }
	
}
