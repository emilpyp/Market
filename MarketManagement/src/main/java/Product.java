public class Product {
    private String name;
    private String category;
    private double price;
    private double inStock;
    private Unit unit;
    private boolean discontinued;

    private final long id = counter ++;
    private static long counter = 0;

    public Product(String name, String category, double price, Unit unit, double inStock){
        this(name, category, price, unit);
        this.inStock = inStock;
    }

    public Product(String name, String category, double price,  Unit unit){
        this.name = name;
        this.category = category;
        this.price = price;
        this.unit = unit;
        this.discontinued = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getInStock() {
        return inStock;
    }

    public void setInStock(double inStock) {
        this.inStock = inStock;
    }

    public void addToStock(double toAdd){
        this.inStock += toAdd;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

}
