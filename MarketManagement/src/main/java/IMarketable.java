import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IMarketable {
    // Sales.
    Map<Long, Sale> getSales();
    Sale getSale(long saleId);
    List<Sale> getSales(LocalDate forTheDate);
    List<Sale> getSales(LocalDate from, LocalDate to);
    List<Sale> getSales(double min, double max);

    double newSale(Map<Long, SaleItem> productIdToSaleItem);
    void returnItem(long saleId, long id, double count);
    int voidSale(long saleId);

    // Products.
    Collection<Product> getProducts();
    Product getProductById(long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByName(String name);
    List<Product> getProducts(double min, double max);


    void addNewProduct(String name, String category, double price, Unit unit, double stock);
    String changeProductCategory(long id, String newCategory);
    String changeProductName(long id, String newName);
    double changeProductCount(long id, double newCount);
    double changeProductPrice(long id, double newPrice);


    // Categories.
    void addCategory(String category);
    void removeCategory(String category);
    Set<String> getCategories();
    boolean containsCategory(String category);
}
