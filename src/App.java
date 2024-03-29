import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Interfaz Observer
interface Observer {
    void update(String productName);
}

// Interfaz Subject
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notify(String productName);
}

// Clase que representa el Subject
class ShopSubject implements Subject {
    private List<Observer> observers;
    private List<String> products;

    public ShopSubject() {
        observers = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notify(String productName) {
        for (Observer observer : observers) {
            observer.update(productName);
        }
    }

    public void addProduct(String productName) {
        products.add(productName);
        notify(productName);
    }

    public List<String> getProducts() {
        return products;
    }
}

// Clase que representa el Observer
class UserObserver implements Observer {
    private String name;

    public UserObserver(String name) {
        this.name = name;
    }

    public void update(String productName) {
        System.out.println("Hola " + name + ", ¡Nuevo producto agregado! Nombre del producto: " + productName);
    }
}

// Interfaz para la estrategia de cálculo de precio
interface PriceCalculationStrategy {
    double calculatePrice(double basePrice, int quantity);
}

// Implementación concreta de la estrategia de cálculo de precio simple (precio base * cantidad)
class SimplePriceCalculationStrategy implements PriceCalculationStrategy {
    public double calculatePrice(double basePrice, int quantity) {
        return basePrice * quantity;
    }
}

// Implementación concreta de la estrategia de cálculo de precio con descuento
class DiscountPriceCalculationStrategy implements PriceCalculationStrategy {
    public double calculatePrice(double basePrice, int quantity) {
        final double discountPercentage = 0.9; // 10% de descuento
        return basePrice * quantity * discountPercentage;
    }
}



// Clase que representa el carrito de compras
class ShoppingCart {
    private static ShoppingCart instance;
    private List<Product> products;

    private ShoppingCart() {
        products = new ArrayList<>();
    }

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addProduct(Product product, int quantity) {
        product.setQuantity(quantity);
        products.add(product);
        System.out.println(quantity + " " + product.getName() + "(s) agregado(s) al carrito.");
    }

    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            System.out.println("Contenido del carrito:");
            double total = 0;
            for (Product product : products) {
                System.out.println("- " + product.getName() + " (Cantidad: " + product.getQuantity() + ") - Precio: " + product.getPrice());
                total += product.getPrice();
            }
            System.out.println("Total del carrito: " + total);
        }
    }

    public void clearCart() {
        products.clear();
        System.out.println("El carrito ha sido vaciado.");
    }

    public void finalizeOrder(String address, String phoneNumber) {
        if (!products.isEmpty()) {
            System.out.println("Pedido finalizado:");
            System.out.println("Dirección de envío: " + address);
            System.out.println("Número de teléfono: " + phoneNumber);
            System.out.println("¡Pedido finalizado!");
            products.clear();
        } else {
            System.out.println("No puedes finalizar el pedido porque el carrito está vacío.");
        }
    }
}
public class App {

	public static void main(String[] args) {
		
		
		System.out.print("El producto 2 hace un descuento del 10% con strategy" );
		System.out.println(); 
		
        Scanner scanner = new Scanner(System.in);
   

        // Crear productos con estrategias de precio
        PriceCalculationStrategy simplePriceStrategy = new SimplePriceCalculationStrategy();
        PriceCalculationStrategy discountPriceStrategy = new DiscountPriceCalculationStrategy();

        Product product1 = new Product("Producto 1", 10.0, simplePriceStrategy);
        Product product2 = new Product("Producto 2", 20.0, discountPriceStrategy);

        // Crear carrito de compras
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        // Crear Subject y Observers
        ShopSubject shopSubject = new ShopSubject();
        Observer user1 = new UserObserver("Usuario1");
        Observer user2 = new UserObserver("Usuario2");

        // Suscribir Observers al Subject
        shopSubject.attach(user1);
        shopSubject.attach(user2);

        // Agregar productos al Subject
        shopSubject.addProduct(product1.getName());
        shopSubject.addProduct(product2.getName());

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Agregar producto al carrito");
            System.out.println("2. Ver carrito");
            System.out.println("3. Consultar productos");
            System.out.println("4. Finalizar pedido");
            System.out.println("5. Salir");
            System.out.print("\nSeleccione una opción: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("\nIngrese el nombre del producto: ");
                    String productName = scanner.nextLine();
                    System.out.print("Ingrese la cantidad de productos: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea
                    Product product;
                    if (productName.equals(product1.getName())) {
                        product = product1;
                    } else if (productName.equals(product2.getName())) {
                        product = product2;
                    } else {
                        System.out.println("Producto no válido.");
                        continue;
                    }
                    shoppingCart.addProduct(product, quantity);
                    break;
                case "2":
                    shoppingCart.viewCart();
                    break;
                case "3":
                    System.out.println("\nProductos disponibles:");
                    System.out.println("- " + product1.getName() + " - Precio: " + product1.getBasePrice());
                    System.out.println("- " + product2.getName() + " - Precio: " + product2.getBasePrice());
                    break;
                case "4":
                    System.out.print("\nIngrese la dirección de envío: ");
                    String address = scanner.nextLine();
                    System.out.print("Ingrese el número de teléfono: ");
                    String phoneNumber = scanner.nextLine();
                    shoppingCart.finalizeOrder(address, phoneNumber);
                    break;
                case "5":
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
        scanner.close();
      
	}

}
