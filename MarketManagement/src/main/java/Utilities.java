import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utilities {
    public static String promptUser(String regExToMatch, String errorMessage) {
        Scanner in = new Scanner(System.in);
        String input;
        while (true) {
            input = (in.nextLine()).strip().toLowerCase();
            if (input.matches(regExToMatch)) {
                System.out.println();
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    public static String promptForDouble() {
        System.out.print("Enter digit: ");
        String regEx = "[bB]|[\\d]{1,8}\\.?[\\d]{0,2}";
        String error = "Please enter a valid digit format or [B] to go back. \n(Max 8 digits + double precision. eg. 5.18, 1314311.00)";
        return promptUser(regEx, error);
    }

    public static String promptForName() {
        System.out.print("Enter name: ");
        String regEx = "[bB]|[\\w]{2,20}";
        String error = "Please enter a valid name format or [B] to go back. \n" +
                "(Between 2 and 20 chars long): ";
        return promptUser(regEx, error);
    }

    public static String promptForCategory() {
        System.out.print("Category name: ");
        String regex = "[bB]|[\\w^_]{2,20}";
        String error = "Please enter a valid option [Category name](2-20 Chars) or \n " +
                "[B] to go back.";
        return promptUser(regex, error);
    }

    public static String promptForId() {
        System.out.print("Enter ID: ");
        String regEx = "[bB]|[\\d]+";
        String error = "Please enter a valid id format (1 or more numbers) or \n" +
                "[B] to go back";
        return promptUser(regEx, error);
    }

    public static String promptForYN() {
        System.out.print("Choice: ");
        String ynRegEx = "[yn]";
        String ynError = "Please enter a valid command [y] or [n] or [B] to go back.";
        return promptUser(ynRegEx, ynError);
    }

    public static String promptForUnit() {
        System.out.print("Choice: ");
        String regEx = "[bB12]";
        String error = "Please Type [1] for [kg] or [2] for [item]. \n" +
                "[B] to go back.";
        return promptUser(regEx, error);

    }

    public static TwoTuple<Double> getMinMaxPrice() {
        while (true) {
            System.out.println("Please enter min price or [B] to go back: ");
            String minPrice = Utilities.promptForDouble();
            if (minPrice.equals("b")) return null;

            System.out.println("Please enter max price or [B] to go back: ");
            String maxPrice = Utilities.promptForDouble();
            if (maxPrice.equals("b")) return null;

            double min = Double.parseDouble(minPrice);
            double max = Double.parseDouble(maxPrice);
            if (min > max) {
                System.out.printf("min [%f] is greater than max [%f], \n" +
                        " please make sure min <= max.\n", min, max);
                continue;
            }

            return new TwoTuple<>(min, max);

        }
    }

    public static String promptForDate(){
        String dateFormatRegEx = "[bB]|[\\d]{4}-[\\d]{2}-[\\d]{2}";
        String dateFormatError = "Please enter a valid date[ YYYY-MM-DD] or [B] to go back.";
        return promptUser(dateFormatRegEx, dateFormatError);
    }

    public static TwoTuple<LocalDate> promptForFromToDate(){
        while (true){
            System.out.print("Enter date. Format[ YYYY-MM-DD ] From: ");
            String from = promptForDate();
            if(from.equals("b")) return null;

            System.out.print("Enter date. Format[ YYYY-MM-DD ] To: ");
            String to = promptForDate();
            if(to.equals("b")) return null;

            LocalDate dateFrom = LocalDate.parse(from);
            LocalDate dateTo = LocalDate.parse(to);

            if(dateFrom.isAfter(dateTo)) {
                System.out.printf("From date[%s] is after to date[%s],\n" +
                        "Please make sure fromDate <= toDate\n\n", dateFrom, dateTo);
                continue;
            }
            return new TwoTuple<LocalDate>(dateFrom, dateTo);
        }

    }
}
class TwoTuple<V>{
    private final V min;
    private final V max;

    public TwoTuple(V min, V max){
        this.min = min;
        this.max = max;
    }

    public V getMin() {
        return min;
    }

    public V getMax() {
        return max;
    }
}