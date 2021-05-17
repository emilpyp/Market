import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketDriverSales {

    public static void printSalesHeader(){
        System.out.println("-".repeat(50));
        System.out.printf("|%10s|%10s|%12s|%12s|%9s|\n", "ID", "Amount", "Item Count", "Date", "Is Void");
        System.out.println("-".repeat(50));
    }

    public static void printBasketContents(Map<Long, SaleItem> basket, IMarketable market){

        System.out.println("Contents of your basket: \n" + "-".repeat(70));
        System.out.printf("|%-10s|%-20s|%-10s|%-5s|%-10s|%-10s|\n",
                "ID", "Name", "Count", "Unit", "Unit price", "Total");
        System.out.println("-".repeat(70));

        for(Long productId: basket.keySet()){

            Product p = market.getProductById(productId);
            double costPerUnit = basket.get(productId).getCostPerUnit();
            double count = basket.get(productId).getQuantity();

            String output = String.format("|%-10d|%-20s|%-10.2f|%-5s|%-10.2f|%-10.2f|",
                    productId, p.getName(), count,
                    p.getUnit(), costPerUnit, costPerUnit * count);
            System.out.println(output);
        }
        System.out.println();
    }

    public static void newSale(IMarketable market){
        Map<Long, SaleItem> basket = new HashMap<>();
        while(true){
            // List all products.
            MarketDriverProducts.listAllProducts(market);
            // Get the product ID from user.
            System.out.print("Please enter the ID of the item: ");
            String productIdStr = Utilities.promptForId();
            // option [b] go back.
            if(productIdStr.equals("b")) return;
            // parse string to get id.
            long productId = Long.parseLong(productIdStr);
            // get product from products list.
            Product product = market.getProductById(productId);
            // if product doesn't exist or is discontinued start over.
            if(product == null || product.isDiscontinued()){
                System.out.printf("Product with id [%d] doesn't exist.\n", productId);
                continue;
            }
            boolean starOver = false;
            double amount = 0;
            // get amount to buy from user.
            while(true){
                System.out.println("Please enter amount to buy: ");
                String amountStr = Utilities.promptForDouble();
                if(amountStr.equals("b")) {
                    starOver = true;
                    break;
                }
                amount = Double.parseDouble(amountStr);
                if((product.getUnit().equals(Unit.ITEM)) && (amount % 1 != 0)){
                    System.out.println("( [Item] units must be whole numbers )");
                    continue;
                }
                if(amount > product.getInStock()){
                    System.out.printf("Only [%.2f][%s][%s] left in stock.\n",
                            product.getInStock(), product.getUnit(), product.getName() );
                    System.out.println("Please adjust amount accordingly.");
                    continue;
                }
                break;

            }
            if(starOver) continue;

            SaleItem newSaleItem = new SaleItem(productId, product.getName(), amount, product.getPrice());
            basket.put(productId, newSaleItem);
            System.out.printf("[%f][%s] added to basket.\n\n", amount, product.getName());

            boolean repeat = true;
            while(repeat){
                printBasketContents(basket, market);

                System.out.println("How would you like to proceed ?");
                String options = "" +
                        "[1] Add another item to basket. \n" +
                        "[2] Remove item from basket. \n" +
                        "[3] Finalize and pay. \n" +
                        "[B] Remove all and go back. \n" +
                        "Choice: ";

                System.out.print(options);
                String validOptionRegEx = "[1-3Bb]";
                String validOptionsError = "Please enter a valid option: [1], [2], [3] or [B]";
                String input = Utilities.promptUser(validOptionRegEx, validOptionsError);

                switch (input){
                    case "1":{
                        repeat = false;
                        break;
                    }
                    case "2":{
                        System.out.print("Enter ID of product you want to remove from basket.\n ");
                        String idToRemove = Utilities.promptForId();
                        if(idToRemove.equals("b")) continue;

                        long id = Long.parseLong(idToRemove);
                        if(!basket.containsKey(id)){
                            System.out.printf("Product with id[%d] is not in your basket.\n\n", id);
                            continue;
                        }
                        Product productModify = market.getProductById(id);
                        SaleItem saleModify = basket.get(id);
                        double count = 0;
                        boolean validUnit = false;
                        while(!validUnit){
                            System.out.print("How many of them you want to remove? : \n");
                            String countToRemove = Utilities.promptForDouble();
                            if(countToRemove.equals("b")) break;

                            count = Double.parseDouble(countToRemove);

                            if((productModify.getUnit().equals(Unit.ITEM)) && (count % 1 != 0)){
                                System.out.println("( [Item] units must be whole numbers )");
                                continue;
                            }

                            if(count > saleModify.getQuantity()){
                                System.out.printf("You only have [%f] [%s] in your basket.\n\n",
                                        saleModify.getQuantity(), saleModify.getProductName());
                                continue;
                            }
                            validUnit = true;
                        }

                        if(count == saleModify.getQuantity()) {
                            basket.remove(id);
                            break;
                        }
                        else{
                            SaleItem newModified = new SaleItem(saleModify.getProductId(),
                                    saleModify.getProductName(), saleModify.getQuantity() - count,
                                    saleModify.getCostPerUnit());
                            basket.put(id, newModified);
                        }
                        break;
                    }
                    case "3":{
                        double transactionCost = market.newSale(basket);
                        printBasketContents(basket, market);
                        System.out.println("-".repeat(70));
                        System.out.printf("|%60s%10.2f|\n\n", "Total Cost: ", transactionCost);
                    }
                    case "b":{
                        return;
                    }
                }

            }

        }

    }

    public static void returnProduct(IMarketable market){
        while(true) {
            System.out.println("Enter ID of the sale or [B] to go back.");
            String saleIdStr = Utilities.promptForId();
            if (saleIdStr.equals("b")) return;
            long saleId = Long.parseLong(saleIdStr);
            Sale sale = market.getSale(saleId);

            if(sale == null){
                System.out.printf("\"Sale with id [%d] is does not exist.\n\n", saleId);
                continue;
            }
            if (sale.isVoid()) {
                System.out.printf("Sale with id [%d] is void.\n\n", saleId);
                continue;
            }
            while (true) {
                System.out.println("Enter product id to return or [B] to go back");
                String productIdStr = Utilities.promptForId();
                if(productIdStr.equals("b")) {
                    break;
                }

                long productId = Long.parseLong(productIdStr);
                if(!sale.containsProduct(productId)){
                    System.out.printf("Sale with id [%d] does not contain product with id [%d].\n\n",
                            saleId, productId);
                    continue;
                }

                double count = 0;
                boolean validUnit = false;

                Product             productToReturn = market.getProductById(productId);
                Map<Long, SaleItem> soldProducts = sale.getItemsSold();
                SaleItem            saleItemProduct = soldProducts.get(productId);

                while(!validUnit){
                    System.out.print("How many of them you want to return? : \n");
                    String countToReturn = Utilities.promptForDouble();
                    if(countToReturn.equals("b")) break;

                    count = Double.parseDouble(countToReturn);

                    if((productToReturn.getUnit().equals(Unit.ITEM)) && (count % 1 != 0)){
                        System.out.println("( [Item] units must be whole numbers )");
                        continue;
                    }

                    if(count > saleItemProduct.getQuantity()){
                        System.out.printf("There are only [%f] [%s] in your sale.\n\n",
                                saleItemProduct.getQuantity(), saleItemProduct.getProductName());
                        continue;
                    }
                    validUnit = true;
                }
                market.returnItem(saleId, productId, count);
                System.out.println("Item return is successful");
            }
        }
    }

    public static void voidSale(IMarketable market){
        while(true){
            System.out.println("Enter ID of the sale to void.");
            String input = Utilities.promptForId();
            if(input.equals("b")) return;
            long id = Long.parseLong(input);
            int result = market.voidSale(id);
            if(result == 0){
                System.out.printf("Sale with id [%d] is already void.\n\n", id);
            }
            else if(result == 1){
                System.out.printf("Sale with id [%d] has been successfully voided. \n\n", id);
            }
            else if(result == -1){
                System.out.printf("Sale with id [%d] does not exist.\n\n", id);
            }
        }
    }

    public static void listAllSales(IMarketable market){
        System.out.println(" ".repeat(14) + "List of all sales");
        printSalesHeader();
        for(Sale s: market.getSales().values()){
            System.out.println(s);
        }
        System.out.println();
    }

    public static void listSalesById(IMarketable market){
        while(true) {
            System.out.println("Enter ID of the sale to void or [B] to go back.");
            String input = Utilities.promptForId();
            if (input.equals("b")) return;

            long id = Long.parseLong(input);
            Sale sale = market.getSale(id);
            if(sale == null){
                System.out.printf("Sale with id [%d] does not exist.\n\n", id);
            }
            else{
                printSalesHeader();
                System.out.println(sale);
            }
        }
    }

    public static void listSalesByPriceRange(IMarketable market) {
        while (true) {
            TwoTuple<Double> minAndMax = Utilities.getMinMaxPrice();
            if (minAndMax == null) return;

            List<Sale> lst = market.getSales(minAndMax.getMin(), minAndMax.getMax());

            if (lst == null || lst.isEmpty()) {
                System.out.printf("No Sales in price range [%.2f]-[%.2f]\n\n", minAndMax.getMin(), minAndMax.getMax());
                continue;
            }
            printSalesHeader();
            for (Sale s : lst) {
                System.out.println(s);
            }
            System.out.println();
        }
    }

    public static void listSalesByDateRange(IMarketable market){
        while (true){
            TwoTuple<LocalDate> fromTo = Utilities.promptForFromToDate();
            if(fromTo == null) return;

            List<Sale> lst = market.getSales(fromTo.getMin(), fromTo.getMax());

            if (lst == null || lst.isEmpty()){
                System.out.printf("No Sales in date range [%s]-[%s]\n\n", fromTo.getMin(), fromTo.getMax());
                continue;
            }
            printSalesHeader();
            for(Sale s: lst){
                System.out.println(s);
            }
            System.out.println();
        }
    }

    public static void listSalesForDate(IMarketable market){
        while (true){
            System.out.println("Please enter the date: ");
            String inputDate = Utilities.promptForDate();
            if(inputDate.equals("b")) return;

            LocalDate date = LocalDate.parse(inputDate);
            if(date.isAfter(LocalDate.now())){
                System.out.printf("Please enter a date before today, today included:");
                continue;
            }
            List<Sale> lst = market.getSales(date);
            if(lst == null || lst.isEmpty()){
                System.out.printf("No sales found for date[%s]\n\n", date);
            }
            printSalesHeader();
            for(Sale s : lst){
                System.out.println(s);
            }
        }
    }

    public static void populateSales(IMarketable market) {
        Map<Long, SaleItem> basket = new HashMap<>();

        SaleItem ananas = new SaleItem(10L, "ananas", 3, 10);
        SaleItem et = new SaleItem(4L, "et", 5, 5);
        SaleItem fanta = new SaleItem(1L, "fanta", 10, 2);
        SaleItem armud = new SaleItem(9L, "armud", 5, 5);

        basket.put(10L, ananas);
        basket.put(4L, et);
        basket.put(1L, fanta);
        basket.put(9L, armud);
        LocalDate date = LocalDate.of(2021,3,2);
        Sale sale = new Sale(basket, date);
        market.getSales().put(sale.getId(), sale);

        basket.clear();

        SaleItem baliq = new SaleItem(3L, "baliq", 3, 7);
        et = new SaleItem(4L, "et", 35, 6);
        SaleItem pepsi = new SaleItem(2L, "pepsi", 10, 3);
        SaleItem xiyar = new SaleItem(12L, "xiyar", 5, 5);

        basket.put(3L, baliq);
        basket.put(4L, et);
        basket.put(2L, pepsi);
        basket.put(12L, xiyar);
        date = LocalDate.of(2020,12, 5);

        sale = new Sale(basket, date);
        market.getSales().put(sale.getId(), sale);

        basket.clear();

        basket.put(10L, ananas);
        basket.put(4L, et);
        basket.put(1L, fanta);
        basket.put(9L, armud);
        basket.put(12L,xiyar);
        date = LocalDate.of(2021,4,2);

        sale = new Sale(basket, date);
        market.getSales().put(sale.getId(), sale);

    }

}
