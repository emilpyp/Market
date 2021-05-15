import java.time.LocalDate;

public interface IMarketable {
    void getSales();
    void getSales(long id);
    void getSales(LocalDate forTheDate);
    void getSales(LocalDate from, LocalDate to);
    void getSales(double min, double max);

    void newSale(SaleItem item);
    void returnItem(long id);
    void voidSale(long id);

    void getProducts();
    void newProduct();
}
