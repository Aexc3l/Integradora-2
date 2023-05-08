package ui;

import java.util.ArrayList;
import java.util.Scanner;

import exception.InvalidReferenceException;
import exception.UnvalidPriceException;
import model.StoreManager;

public class Store {

    private static final Store mercaLibre = new Store();
    private static StoreManager storeManager;
    private static Scanner scan;

    public Store() {
        storeManager = new StoreManager();
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) throws UnvalidPriceException, InvalidReferenceException {
        System.out.println("<-----<| Welcome to MercadoLibre |>----->");
        mercaLibre.startMenu();
    }

    private void startMenu() throws UnvalidPriceException, InvalidReferenceException {
        boolean exitCase = false;
        boolean configCase = false;

        while (!configCase) {
            System.out.println(
                    "\nBefore you start, Do you want to import data? \n1. Yes \n2. No"
            );

            int selection = scan.nextInt();
            scan.nextLine();
            switch (selection) {
                case 1 -> {
                    importData();
                    configCase = true;
                }
                case 2 -> configCase = true;
                default -> System.out.println("Invalid selection.");
            }
        }
        while (!exitCase) {
            System.out.println(
                    """
                                Please Select an Option\s
                                1. Add a product
                                2. Remove a product
                                3. Add an order
                                4. Remove orders
                                5. Search Product
                                6. Search Order
                                7. Quit"""
            );

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> addOrder();
                case 4 -> removeOrder();
                case 5 -> SearchProduct();
                case 6 -> SearchOrder();
                case 7 -> {
                    System.out.println("Before you quit, Do you want to export your data? \n1. Yes \n2. No");
                    int exit = scan.nextInt();
                    scan.nextLine();
                    switch (exit) {
                        case 1 -> {
                            exportData();
                            exitCase = true;
                        }
                        case 2 -> exitCase = true;
                        default -> System.out.println("Invalid selection.");
                    }
                }
            }
        }
    }

    private void addProduct() {
        System.out.println("Enter the name of the product:");
        String name = scan.nextLine();

        System.out.println("Enter the description of the product:");
        String description = scan.nextLine();

        System.out.println("Enter the price of the product:");
        double price = scan.nextDouble();

        System.out.println("Enter the quantity of the product:");
        int quantity = scan.nextInt();
        scan.nextLine();

        System.out.println("Enter the category of the product:");
        String category = scan.nextLine();

        storeManager.addProduct(name, description, price, quantity, category);

        System.out.println("Added Product");
    }

    private void removeProduct() {
        System.out.println("Enter the name of the product:");
        String name = scan.nextLine();

        if (storeManager.removeProduct(name)){
            System.out.println("Removed Product");
        }else{
            System.out.println("Something went wrong, the product could not be removed. Please try again");
        }
    }

    private void addOrder() {
        System.out.println("Enter buyer's name:");
        String buyerName = scan.nextLine();
        String productName = "";

        ArrayList<String> productNames = new ArrayList<>();
        while (!productName.equalsIgnoreCase("final")) {
            System.out.println("Enter product name (enter 'final' if finished):");
            productName = scan.nextLine();
            productNames.add(productName);
        }
        storeManager.addOrder(buyerName, productNames);

        System.out.println("Added Order");
    }

    private void removeOrder() {
        System.out.println("Enter the name of the order's buyer: ");
        String name = scan.nextLine();

        if (storeManager.removeOrder(name)){
            System.out.println("Order removed");
        }else{
            System.out.println("Something went wrong, the order could not be removed. Please try again");
        }
    }

    private void SearchOrder() {}

    private void SearchProduct() throws UnvalidPriceException, InvalidReferenceException {
        System.out.println("What do you want to search?,  \n1.A product \n2.an order ");
        int option=scan.nextInt();
        switch (option){
            case 1:System.out.println("Do you want to search A product by: \n1.Its Name \n2.Its Value \n3.Its Category \n4.Its purchased amount \n5.Its amount \n6.a range of price");
            int option2=scan.nextInt();
            if(option2==1){
                System.out.println("Enter the name of the product:");
                String name = scan.nextLine();
                System.out.println(storeManager.searchProductsbyName(name));

            }if(option2==2){
                System.out.println("Enter the product's value:");
                double value = scan.nextDouble();
                System.out.println(storeManager.searchProductsbyPrice(value));

            }if(option2==3){
                System.out.println("Enter the product's category:");
                String category = scan.nextLine();
                System.out.println(storeManager.searchProductsbyCategory(category));
            }if(option2==4){
                System.out.println("Enter the product's purchased amount:");
                int purchasedAmount = scan.nextInt();
                System.out.println(storeManager.searchProductsbyPurchasedAmount(purchasedAmount));
            }if(option2==5){
                System.out.println("Enter the product's amount:");
                int amount = scan.nextInt();
                System.out.println(storeManager.searchProductsbyAmount(amount));
            }if(option==6){
                System.out.println("Enter the product's min value:");
                double min = scan.nextDouble();
                System.out.println("Enter the product's max value:");
                double max = scan.nextDouble();
                System.out.println(storeManager.searchProductsbyRange(min,max));
            }else{
                System.out.println("Invalid option");
            }
            break;
            case 2:
            System.out.println("Do you want to search an order by: \n1.Its Name \n2.Its Date \n3.Its price");
            int option3=scan.nextInt();
            if(option3==1){
                System.out.println("Enter the name of the order:");
                String name2 = scan.nextLine();
                System.out.println(storeManager.searchOrderbyName(name2));
            }if(option3==2){
                System.out.println("Enter the order's date:");
                String date = scan.nextLine();
                System.out.println(storeManager.searchOrderbyDate(date));
            }if(option3==3){
                System.out.println("Enter the order's price:");
                double price = scan.nextDouble();
                System.out.println(storeManager.searchOrderbyPrice(price));
            }else{
            System.out.println("Invalid option");
            }
            break;
            default:System.out.println("invalid option");  
        } 
        
    }

    private void importData() {

        System.out.println("Please write the name of your file: \n(The file must be located in the data folder) \nc) Cancel") ;
            String fileName = scan.nextLine();
        if (storeManager.importData(fileName)){
            System.out.println("Data successfully uploaded");
        }
    }

    private void exportData() {
        System.out.println("Please write a name for your file:");
            String fileName = scan.nextLine();

        storeManager.exportData(fileName);
        System.out.println("JSON file exported successfully in data folder");
    }
}
