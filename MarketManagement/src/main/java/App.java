import java.util.Scanner;

public class App {

    private static App app = null;

    public static App app(){
        if (app == null){
            app = new App();
        }
        return app;
    }

    private App(){}

    private final String mainMenu = "" +
            "Please choose one of the options:\n" +
            "---------------------------------\n" +
            "     1. Operations on Products.\n" +
            "     2. Operations on Sales\n" +
            "     Q. Quit.\n";

    private final String opsOnProducts = "" +
            "Operations of products: \n" +
            "     1. Add new product. \n" +
            "     2. Modify product. \n" +
            "     3. Remove product. \n" +
            "     4. Show all products. \n" +
            "     5. List products by category. \n" +
            "     6. List by price range. \n" +
            "     7. List by name. \n" +
            "     B. Go back to main menu.\n" +
            "     Q. Quit\n";

    private final String opsOnSales = "" +
            "Operations of sales: \n" +
            "     1. New sale. \n" +
            "     2. Return product. \n" +
            "     3. Void sale. \n" +
            "     4. List all sales. \n" +
            "     5. List sales by date range. \n" +
            "     6. List sales by date. \n" +
            "     7. List sales by amount. \n" +
            "     8. List sales by id. \n" +
            "     B. Go back to main menu." +
            "     Q. Quit.\n";

    
    private boolean quit = false;
    Scanner input = new Scanner(System.in);

    public void start(){
        mainMenu();
    }

    private void mainMenu(){
        do{
            System.out.println(mainMenu);

        }
        while(!quit);
    }
}
