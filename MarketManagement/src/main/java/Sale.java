import java.time.LocalDate;
import java.util.*;

public final class Sale {
    private final long id = counter ++;
    private static long counter = 0;

    private final Map<Long, SaleItem> itemsSold;
    private final LocalDate date;
    private boolean isVoid = false;

    public Sale(Map<Long, SaleItem> items){
        this.itemsSold = new HashMap<>(items);
        this.date = LocalDate.now();
    }

    public Sale(Map<Long, SaleItem> items, LocalDate date){
        this.itemsSold = new HashMap<>(items);
        this.date = date;
    }

    public long getId(){
        return this.id;
    }

    public double getTransactionCost(){
        double sum = 0;
        for(SaleItem s: this.itemsSold.values()){
            sum += (s.getCostPerUnit() * s.getQuantity());
        }
        return sum;
    }

    public Map<Long, SaleItem> getItemsSold() {
        Map<Long, SaleItem> itemsSoldCopy = new HashMap<>();
        for(long id: this.itemsSold.keySet()){
            itemsSoldCopy.put(id, this.itemsSold.get(id));
        }
        return itemsSoldCopy;
    }

    public void voidSale(){
        this.isVoid = true;
    }

    public boolean isVoid(){
        return this.isVoid;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public int numberOfItems(){
        return this.itemsSold.size();
    }

    public boolean containsProduct(long productId){
        return this.getItemsSold().containsKey(productId);
    }

    @Override
    public String toString(){
        String str = String.format("|%10d|%10.2f|%12d|%12s|%9s|",
                this.getId(), this.getTransactionCost(), this.numberOfItems(), this.getDate(), this.isVoid());
        return str;
    }
}
