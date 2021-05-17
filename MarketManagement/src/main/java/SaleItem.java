public final class SaleItem {
    private final double quantity;
    private final double costPerUnit;
    private final long productId;
    private final String productName;

    SaleItem(long productId, String productName, double quantity, double costPerUnit){
        this.productName = productName;
        this.quantity = quantity;
        this.costPerUnit = costPerUnit;
        this.productId = productId;

    }

    SaleItem(SaleItem item, double quantity){
        this.productName = item.getProductName();
        this.quantity = quantity;
        this.costPerUnit = item.getCostPerUnit();
        this.productId = item.getProductId();
    }

    public String getProductName(){
        return this.productName;
    }
    public double getQuantity() {
        return quantity;
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public long getProductId() {
        return productId;
    }
}
