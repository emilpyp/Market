import java.util.LinkedList;
import java.util.List;

public class MarketDriverProducts {

    /*
    Adds new product to the market products list.
    User is prompted to select or add new category for the new product.
    After the category is established user is prompted for the details of the product.
     */
    public static void addNewProduct(IMarketable market){
        while(true){
            // Print information
            System.out.println("- Type a category name to add product to OR \n" +
                    "Type \"B\" to go back. ");
            System.out.println("- If the category name doesn't exist you can add it.");

            listAllCategories(market);

            // Prompt until valid choice is entered. Category name or back option.
            String userInput = Utilities.promptForCategory();

            // Go back to Products menu.
            if(userInput.equals("b")) return;
            String productCategory = userInput;
            // Check if entered category exist, if not offer to add it.
            if(!market.containsCategory(productCategory)){
                System.out.println("Category doesn't exist, would you like to add it? [y]/[n]");
                String answer = Utilities.promptForYN();
                // Add category.
                if(answer.equals("y")) {
                    market.addCategory(productCategory);
                    System.out.printf("Category [%s] has been added successfully.\n", productCategory);
                }
                // Go back to the beginning of loop
                else{
                    continue;
                }
            }
            // Get the name of the product.
            System.out.println("Enter the name of the product(Max 20 Characters): ");
            userInput = Utilities.promptForName();
            if(userInput.equals("b")) return;
            String productName = userInput;

            // Get the price of the product.
            System.out.println("Enter the price of the product: ");
            userInput = Utilities.promptForDouble();
            if(userInput.equals("b")) return;
            double productPrice = Double.parseDouble(userInput);

            // Get the unit of the product.
            System.out.println("Choose measurement unit of product.");
            System.out.println("Type [1] for [kg] or [2] for [item]: ");
            userInput = Utilities.promptForUnit();
            if(userInput.equals("b")) return;
            Unit productUnit;
            productUnit = (userInput.equals("1")) ? Unit.KG : Unit.ITEM;

            boolean validUnit = false;
            double productAmount = 0;
            while(!validUnit){
                // Get the amount to initialize the stock.
                System.out.println("Enter the amount of the product: ");
                userInput = Utilities.promptForDouble();
                if(userInput.equals("b")) break;
                productAmount = Double.parseDouble(userInput);

                if((productUnit.equals(Unit.ITEM)) && (productAmount % 1 != 0)){
                    System.out.println("( [Item] units must be whole numbers )");
                    continue;
                }
                validUnit = true;
            }
            market.addNewProduct(productName, productCategory, productPrice,
                    productUnit, productAmount);

            System.out.printf("[%.2f][%s] [%s] with price [%.2f] added successfully.\n\n",
                    productAmount, productUnit, productName, productPrice);
            System.out.println("-".repeat(50) + "\n");
        }

    }

    public static void modifyProduct(IMarketable market){
        String modifyOptions = "Please select what information to modify: \n" +
                "   [1] Name. \n" +
                "   [2] Category. \n" +
                "   [3] Price. \n" +
                "   [4] Amount in stock. \n" +
                "   [5] Unit. \n" +
                "   [6] Remove from inventory. \n" +
                "   [7] Put back into inventory. \n" +
                "   [B] Go Back.\n" +
                "Option: ";

        while(true){
            System.out.println("\nEnter the ID of the product to modify or [B] to go back: ");
            String idStr = Utilities.promptForId();
            if(idStr.equals("b")) return;

            long id = Long.parseLong(idStr);
            Product toModify = market.getProductById(id);
            if(toModify == null) {
                System.out.printf("Item with id [%d] doesn't exist.\n", id);
                continue;
            }
            String regExModifyOptions = "[1-7Bb]";
            String errorModifyOptions = "Please enter a valid option: [1]-[7] or [B].";
            System.out.print(modifyOptions);
            switch (Utilities.promptUser(regExModifyOptions, errorModifyOptions)){
                case "1":{
                    System.out.printf("Current name for [%d] is [%s].\n", id, toModify.getName());
                    String newName = Utilities.promptForName();

                    if(newName.equals("b")) break;

                    String oldName = market.changeProductName(id, newName);
                    System.out.printf("Name changed from [%s] to [%s] for product with id [%d]\n",
                            oldName, newName, id);
                    break;
                }

                case "2":{
                    System.out.printf("Current category for [%d] is [%s].\n", id, toModify.getCategory());
                    String newCategory = Utilities.promptForCategory();

                    if(newCategory.equals("b")) break;

                    if(!market.containsCategory(newCategory)){
                        System.out.println("Category doesn't exist, would you like to add it? [y]/[n]");
                        String answer = Utilities.promptForYN();
                        if(answer.equals("y")) market.addCategory(newCategory);
                        else break;
                    }

                    String oldCategory = market.changeProductCategory(id, newCategory);
                    System.out.printf("Name changed from [%s] to [%s] for product with id [%d]\n",
                            oldCategory, newCategory, id);
                    break;
                }
                case "3":{
                    System.out.printf("Current price for [%d] is [%f].\n", id, toModify.getPrice());
                    String newPriceStr = Utilities.promptForDouble();

                    if(newPriceStr.equals("b")) break;

                    double newPrice = Double.parseDouble(newPriceStr);
                    double oldPrice = market.changeProductPrice(id, newPrice);
                    System.out.printf("Price changed from [%f] to [%f] for product with id [%d]\n",
                            oldPrice, newPrice, id);
                    break;
                }
                case "4" : {
                    System.out.printf("Current stock number for [%d] is [%f].\n", id, toModify.getInStock());

                    String newStockStr = Utilities.promptForDouble();

                    if(newStockStr.equals("b")) break;

                    double newStock = Double.parseDouble(newStockStr);
                    double oldStock = market.changeProductCount(id, newStock);
                    System.out.printf("Price changed from [%f] to [%f] for product with id [%d]\n",
                            oldStock, newStock, id);
                    break;
                }
                case "5":{
                    System.out.printf("Current unit for [%d] is [%s].\n", id, toModify.getUnit());
                    System.out.println("Choose measurement unit of product.");
                    System.out.println("Type [1] for [kg] or [2] for [item]: ");
                    String unit = Utilities.promptForUnit();

                    if(unit.equals("b")) return;

                    Unit newUnit;
                    newUnit = (unit.equals("1")) ? Unit.KG : Unit.ITEM;

                    System.out.printf("Unit changed from [%s] to [%s] for product with id [%d]\n",
                            toModify.getUnit(), newUnit, id);
                    toModify.setUnit(newUnit);
                    break;
                }
                case "6": {
                    toModify.setDiscontinued(true);
                    System.out.printf("Item with id: [%d] has been removed from inventory.\n", id);
                    break;
                }
                case "7": {
                    toModify.setDiscontinued(false);
                    System.out.printf("Item with id: [%d] is in active inventory.\n", id);
                    break;
                }
                case "b" : {
                    return;
                }
            }
        }
    }

    public static void removeProduct(IMarketable market){
        while(true){
            System.out.println("Enter the ID of the product to remove or [B] to go back: ");
            String idStr = Utilities.promptForId();
            if(idStr.equals("b")) return;

            long id = Long.parseLong(idStr);
            Product toRemove = market.getProductById(id);
            if(toRemove == null || toRemove.isDiscontinued()) {
                System.out.printf("Item with id [%d] doesn't exist.\n", id);
                continue;
            }
            toRemove.setDiscontinued(true);
            System.out.printf("Item with id: [%d] and name: [%s] has been removed from inventory.\n",
                    toRemove.getId(), toRemove.getName());
        }
    }

    public static void addNewCategory(IMarketable market){
        while(true){
            System.out.println("Please enter a category name to add. ");
            String userInput = Utilities.promptForCategory();

            if(userInput.equals("b")) return;

            if(market.getCategories().contains(userInput)){
                System.out.printf("Category [%s] already exist.\n", userInput);
                continue;
            }
            market.addCategory(userInput);
            System.out.printf("Category [%s] has been added successfully.\n", userInput);
        }

    }

    public static void removeCategory(IMarketable market){
        while (true){
            listAllCategories(market);
            System.out.println("Please enter a category name to remove: ");
            String categoryToRemove = Utilities.promptForCategory();
            if(categoryToRemove.equals("b")) return;

            if(!market.containsCategory(categoryToRemove)) {
                System.out.printf("Category [%s] does not exist.\n", categoryToRemove);
                continue;
            }
            market.removeCategory(categoryToRemove);
        }
    }

    public static void listAllCategories(IMarketable market){
        // Print existing categories to choose from.
        int column = 0;
        if(market.getCategories().isEmpty()) {
            System.out.println("-".repeat(50));
            System.out.println("No categories to show.");
            System.out.println("-".repeat(50));
        }
        else{
            System.out.println("Available categories: ");
            System.out.println("-".repeat(50));
            for(String str : market.getCategories()){
                System.out.printf("|%-25s|", str);
                column ++;
                if(column % 3 == 0){
                    System.out.println();
                }
            }
            if(column % 3 > 0) System.out.println();
            System.out.println("-".repeat(50));
        }
    }

    public static void listAllProducts(IMarketable market){
        System.out.println("All products in inventory: ");
        String header = String.format("|%10s|%20s|%20s|%10s|%10s|%5s|%7s|\n",
                " ID ", " Name ", " Category ",
                " Price ", " Stock ", "Unit", " Removed ");
        System.out.print(header);
        System.out.println("-".repeat(110));
        List<Product> sortedProducts = new LinkedList<>();
        market.getProducts()
                .stream().sorted((p1, p2) -> {
                    if(p1.getCategory().equals(p2.getCategory())){
                        return p1.getName().compareTo(p2.getName());
                    }
                    else{
                        return p1.getCategory().compareTo(p2.getCategory());
                    }
                })
                .forEach(sortedProducts::add);
        for(Product p : sortedProducts){
            System.out.println(p);
        }
    }

    public static void listProductsByCategory(IMarketable market){
        while (true) {
            listAllCategories(market);
            System.out.println("Please enter a category to show products for: ");
            String category = Utilities.promptForCategory();
            if(category.equals("b")) return;
            if(!market.containsCategory(category)){
                System.out.println("Category doesn't exist");
                continue;
            }
            List<Product> filteredByCategory = market.getProductsByCategory(category);
            if(filteredByCategory == null){
                System.out.printf("No products under category [%s].\n", category);
                continue;
            }
            System.out.printf("Products under category [%s]: \n", category);
            System.out.println("-".repeat(110));
            for(Product p: filteredByCategory){
                System.out.println(p);
            }
            System.out.println();
        }


    }

    public static void listProductsByPriceRange(IMarketable market){
        while (true){
            TwoTuple<Double> minAndMax =Utilities.getMinMaxPrice();
            if(minAndMax == null) return;

            double min = minAndMax.getMin();
            double max = minAndMax.getMax();

            List<Product> sortedList = market.getProducts(min, max);
            if(sortedList == null || sortedList.isEmpty()){
                System.out.printf("No products in price range [%f]-[%f]\n", min, max);
                continue;
            }
            for(Product p: sortedList){
                System.out.println(p);
            }
            System.out.println();
        }

    }

    public static void listProductsByName(IMarketable market){
        while (true) {
            System.out.println("Please enter a name to show products for: ");
            String name = Utilities.promptForName();
            if(name.equals("b")) return;
            List<Product> filteredByName = market.getProductsByName(name);
            if(filteredByName == null){
                System.out.printf("No products with name [%s] exist.\n", name);
                continue;
            }
            System.out.printf("Products with name [%s]: \n", name);
            System.out.println("-".repeat(110));
            for(Product p: filteredByName){
                System.out.println(p);
            }
            System.out.println();
        }
    }

    public static void populateProducts(IMarketable market){
        market.addNewProduct("kola", "icki", 1, Unit.ITEM, 100);
        market.addNewProduct("fanta", "icki", 2, Unit.ITEM, 100);
        market.addNewProduct("pepsi", "icki", 3, Unit.ITEM, 100);
        market.addNewProduct("baliq", "qastronom", 5, Unit.KG, 50);
        market.addNewProduct("et", "qastronom", 6, Unit.KG, 50);
        market.addNewProduct("toyuq", "qastronom", 7, Unit.KG, 100);
        market.addNewProduct("alma", "meyve", 3, Unit.KG, 100);
        market.addNewProduct("yeralmasi", "meyve", 3, Unit.KG, 100);
        market.addNewProduct("albali", "meyve", 3, Unit.KG, 100);

        market.addNewProduct("armud", "meyve", 5, Unit.KG, 100);
        market.addNewProduct("ananas", "meyve", 10, Unit.ITEM, 100);
        market.addNewProduct("pomidor", "terevez", 2, Unit.KG, 100);
        market.addNewProduct("xiyar", "terevez", 5, Unit.KG, 100);
        market.addNewProduct("sogan", "terevez", 0.5, Unit.KG, 100);


    }
}
