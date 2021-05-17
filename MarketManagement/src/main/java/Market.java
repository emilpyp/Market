import java.time.LocalDate;
import java.util.*;

public class Market implements IMarketable{
    private Set<String> categories;
    private Map<Long, Product> products;
    private Map<Long, Sale> sales;

    public Market(){
        this.categories = new HashSet<>();
        this.products = new HashMap<>();
        this.sales = new HashMap<>();
    }

    @Override
    public void addNewProduct(String name, String category, double price, Unit unit, double stock) {
        Product newProduct = new Product(name, category, price, unit, stock);
        this.products.put(newProduct.getId(), newProduct);
        this.addCategory(category);
    }

    @Override
    public Map<Long, Sale> getSales() {
        return this.sales;
    }

    @Override
    public Sale getSale(long id) {
        return this.getSales().get(id);
    }

    @Override
    public List<Sale> getSales(LocalDate forTheDate) {
        return getSales(forTheDate, forTheDate);
    }

    @Override
    public List<Sale> getSales(LocalDate from, LocalDate to) {
        if(getSales().isEmpty()) return null;

        List<Sale> lst = new LinkedList<>();

        this.getSales()
                .values()
                .stream()
                .filter(sale -> (sale.getDate().compareTo(from) >= 0) && (sale.getDate().compareTo(to) <= 0))
                .forEach(lst::add);

        return lst;
    }

    @Override
    public List<Sale> getSales(double min, double max) {
        if(getSales().isEmpty()) return null;

        List<Sale> lst = new LinkedList<>();
        this.getSales().values()
                .stream()
                .filter(sale -> (sale.getTransactionCost() >= min) && (sale.getTransactionCost() <= max))
                .forEach(lst::add);
        if(lst.isEmpty()) return null;
        return lst;
    }

    @Override
    public double newSale(Map<Long, SaleItem> items) {
        Sale newSale = new Sale(items);
        this.sales.put(newSale.getId(), newSale);
        for(SaleItem item: items.values()){
            Product p = this.getProductById(item.getProductId());
            p.setInStock(p.getInStock() - item.getQuantity());
        }
        return newSale.getTransactionCost();
    }

    @Override
    public void returnItem(long saleId, long productId, double count) {

        Product productToReturn = this.getProductById(productId);

        Sale saleToModify = this.getSale(saleId);

        Map<Long, SaleItem> itemsInSale = saleToModify.getItemsSold();

        SaleItem soldProduct = itemsInSale.get(productId);

        productToReturn.addToStock(count);

        saleToModify.voidSale();

        if(count < soldProduct.getQuantity()){
            SaleItem renewedSaleItem = new SaleItem(soldProduct, soldProduct.getQuantity() - count);
            itemsInSale.put(productId, renewedSaleItem);
        }

        Sale newSale = new Sale(itemsInSale);

        this.sales.put(newSale.getId(), newSale);

    }

    @Override
    public int voidSale(long id) {
        Sale sale = this.getSales().get(id);
        if(sale == null){
            return -1;
        }
        else if(sale.isVoid()){
            return 0;
        }
        else{
            for(SaleItem s: sale.getItemsSold().values()){
                Product p = this.getProductById(s.getProductId());
                p.addToStock(s.getQuantity());
            }
            sale.voidSale();
            return 1;
        }
    }

    @Override
    public Collection<Product> getProducts() {
        return this.products.values();
    }

    @Override
    public Product getProductById(long id) {
        return this.products.get(id);
    }


    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> filteredByCategory = new LinkedList<>();
        Collection<Product> products = this.getProducts();
        if (products == null) {
            return null;
        }
        products.stream()
                .filter(p -> p.getCategory().equals(category))
                .sorted(Comparator.comparing(Product::getCategory))
                .forEach(filteredByCategory::add);

        if(filteredByCategory.isEmpty()) return null;
        return filteredByCategory;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> filteredByName = new LinkedList<>();
        Collection<Product> products = this.getProducts();
        if (products == null) {
            return null;
        }
        products.stream()
                .filter(p -> p.getName().contains(name))
                .sorted(Comparator.comparing(Product::getName))
                .forEach(filteredByName::add);

        if(filteredByName.isEmpty()) return null;
        return filteredByName;
    }

    @Override
    public List<Product> getProducts(double min, double max) {
        LinkedList<Product> sortedList = new LinkedList<>();
        Collection<Product> products = this.getProducts();
        if(products == null) return null;
        products.stream()
                .filter(p -> ((p.getPrice() >= min) && (p.getPrice() <= max)))
                .sorted((p1, p2) -> {
                    if(p1.getPrice() == p2.getPrice()){
                        return p1.getCategory().compareTo(p2.getCategory());
                    }
                    else if(p1.getPrice() > p2.getPrice()){
                        return 1;
                    }
                    else{
                        return -1;
                    }
                })
                .forEach(sortedList::add);
        if(sortedList.isEmpty()) return null;
        return sortedList;
    }

    @Override
    public String changeProductName(long id, String newName) {
        Product product = this.getProductById(id);
        String oldName = product.getName();
        product.setName(newName);
        return oldName;
    }

    @Override
    public double changeProductCount(long id, double newCount) {
        Product product = this.getProductById(id);
        double oldInStock = product.getInStock();
        product.setInStock(newCount);
        return oldInStock;
    }

    @Override
    public double changeProductPrice(long id, double newPrice) {
        Product product = this.getProductById(id);
        double oldPrice = product.getPrice();
        product.setPrice(newPrice);
        return oldPrice;
    }

    @Override
    public String changeProductCategory(long id, String newCategory) {
        Product product = this.getProductById(id);
        String oldCategory = product.getCategory();
        product.setCategory(newCategory);
        return oldCategory;
    }

    @Override
    public void addCategory(String category) {
        this.categories.add(category);
    }

    @Override
    public void removeCategory(String category) {
        this.categories.remove(category);
    }

    public Set<String> getCategories(){
        return this.categories;
    }

    public boolean containsCategory(String category){
        return this.categories.contains(category);
    }
}
