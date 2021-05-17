public class Product {
    private final long id = counter ++;
    private static long counter = 0;

    private String name;
    private String category = "misc";
    private double price;
    private double inStock;
    private Unit unit;
    private boolean discontinued;


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

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString(){
        String productInfo = String.format("|%10d|%20s|%20s|%10.2f|%10.2f|%5s|%7s|\n",
                this.getId(), this.getName(), this.getCategory(),
                this.getPrice(), this.getInStock(), this.unit, this.isDiscontinued());
        return productInfo;
    }

}
