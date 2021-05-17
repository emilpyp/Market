import java.util.Scanner;

public class App {
    // Singleton App class.
    private static App app = null;

    public static App app(){
        if (app == null){
            app = new App();
        }
        return app;
    }

    private IMarketable codeAcademyMarket;

    private App(){
        this.codeAcademyMarket = new Market();
    }

    // Menu contents to show.
    // Declared in class outside methods to avoid reinitializing in every method call.
    private final String mainMenuContent = "" +
            " Please choose one of the options:\n" +
            "----------------------------------\n" +
            "     [1] Operations on Products.\n" +
            "     [2] Operations on Sales\n" +
            "     [Q] Quit.\n" +
            " Option: ";

    private final String opsOnProductsContent = "" +
            " Operations on products: \n" +
            "     [1] Add new product. \n" +
            "     [2] Modify product. \n" +
            "     [3] Remove product. \n" +
            "     [4] Add new category. \n" +
            "     [5] Remove category. \n" +
            "     [6] List all categories.\n" +
            "     [7] List all products. \n" +
            "     [8] List products by category. \n" +
            "     [9] List by price range. \n" +
            "    [10] List by name. \n" +
            "     [B] Go back to main menu.\n" +
            "     [Q] Quit\n" +
            " Option: ";

    private final String opsOnSalesContent = "" +
            " Operations on sales: \n" +
            "     [1] New sale. \n" +
            "     [2] Return product. \n" +
            "     [3] Void sale. \n" +
            "     [4] List all sales. \n" +
            "     [5] List sales by date range. \n" +
            "     [6] List sales for date. \n" +
            "     [7] List sales by price range. \n" +
            "     [8] List sales by id. \n" +
            "     [B] Go back to main menu.\n" +
            "     [Q] Quit.\n" +
            " Option: ";



    public void start(){
        MarketDriverProducts.populateProducts(codeAcademyMarket);
        MarketDriverSales.populateSales(codeAcademyMarket);
        System.out.println("\n-----------------------------------------------");
        System.out.println("     Welcome to Market Management System.     ");
        System.out.println("-----------------------------------------------");
        mainMenu();
    }

    Scanner input = new Scanner(System.in);

    private void mainMenu(){
        String validOptions = "[1], [2] or [Q]";
        boolean exit = false;

        do{
            // Print the options anc wait for input.
            System.out.print(mainMenuContent);
            String inputStr = input.nextLine();
            System.out.println();

            switch (inputStr.toUpperCase()){
                case "1": {
                    exit = this.opsOnProductsMenu();
                    break;
                }
                case "2":{
                    exit = this.opsOnSalesMenu();
                    break;
                }
                case "Q":{
                    exit = true;
                    break;
                }
                default:{
                    System.out.println("Please enter a valid option: " + validOptions + "\n");
                }
            }
        }
        while(!exit);
    }

    private boolean opsOnProductsMenu(){
        String validOptions = "[1] - [10] or [B] or [Q]";
        boolean back = false;
        do{
            System.out.print(opsOnProductsContent);
            String inputStr = input.nextLine();
            System.out.println();

            switch (inputStr.toUpperCase()){
                case "1":{
                    MarketDriverProducts.addNewProduct(codeAcademyMarket);
                    break;
                }
                case "2":{
                    MarketDriverProducts.modifyProduct(codeAcademyMarket);
                    break;
                }
                case "3":{
                    MarketDriverProducts.removeProduct(codeAcademyMarket);
                    break;
                }
                case "4":{
                    MarketDriverProducts.addNewCategory(codeAcademyMarket);
                    break;
                }
                case "5":{
                    MarketDriverProducts.removeCategory(codeAcademyMarket);
                    break;
                }
                case "6":{
                    MarketDriverProducts.listAllCategories(codeAcademyMarket);
                    break;
                }
                case "7":{
                    MarketDriverProducts.listAllProducts(codeAcademyMarket);
                    break;
                }
                case "8":{
                    MarketDriverProducts.listProductsByCategory(codeAcademyMarket);
                    break;
                }
                case "9":{
                    MarketDriverProducts.listProductsByPriceRange(codeAcademyMarket);
                    break;
                }
                case "10":{
                    MarketDriverProducts.listProductsByName(codeAcademyMarket);
                    break;
                }
                case "B":{
                    back = true;
                    break;
                }
                case "Q":{
                    return true;
                }
                default:{
                    System.out.println("Please enter a valid option: " + validOptions + "\n");
                }
            }
        }
        while(!back);
        return false;
    }

    private boolean opsOnSalesMenu(){
        String validOptions = "[1] - [8] or [B] or [Q]";
        boolean back = false;
        do{
            System.out.print(opsOnSalesContent);
            String inputStr = input.nextLine();
            System.out.println();

            switch (inputStr.toUpperCase()){
                case "1":{
                    MarketDriverSales.newSale(codeAcademyMarket);
                    break;
                }
                case "2":{
                    MarketDriverSales.returnProduct(codeAcademyMarket);
                    break;
                }
                case "3":{
                    MarketDriverSales.voidSale(codeAcademyMarket);
                    break;
                }
                case "4":{
                    MarketDriverSales.listAllSales(codeAcademyMarket);
                    break;
                }
                case "5":{
                    MarketDriverSales.listSalesByDateRange(codeAcademyMarket);
                    break;
                }
                case "6":{
                    MarketDriverSales.listSalesForDate(codeAcademyMarket);
                    break;
                }
                case "7":{
                    MarketDriverSales.listSalesByPriceRange(codeAcademyMarket);
                    break;
                }
                case "8":{
                    MarketDriverSales.listSalesById(codeAcademyMarket);
                    break;
                }
                case "B":{
                    back = true;
                    break;
                }
                case "Q":{
                    return true;
                }
                default:{
                    System.out.println("Please enter a valid option: " + validOptions + "\n");
                }
            }
        }
        while(!back);
        return false;
    }
}
