package ui;

import java.util.Scanner;
import model.Controller;

public class Store {

    private static final Store mercaLibre = new Store();
    private static Controller controller;
    private static Scanner scan;

    public Store() {
        controller = new Controller();
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("<-----<| Welcome to MercadoLibre |>----->");
        mercaLibre.startMenu();
    }

    private void startMenu() {
        boolean exitCase = false;
        boolean configCase = false;

        while (!configCase) {
            System.out.println(
                    "\nBefore you start, Do you want to import data? \n1. Yes \n2. No"
            );

            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1 -> {
                    importData();
                    configCase = true;
                }
                case 2 -> configCase = true;
                default -> System.out.println("Invalid choice.");
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
                    exportData();
                    exitCase = true;
                }
            }
        }
    }

    private void importData() {
        System.out.println();
    }

    private void addProduct() {}

    private void removeProduct() {}

    private void addOrder() {}

    private void removeOrder() {}

    private void SearchOrder() {}

    private void SearchProduct() {}

    private void exportData() {
        System.out.println("Please write a name for your file:");
        String fileName = scan.nextLine();
        scan.nextLine();

        controller.exportData(fileName);
        System.out.println("JSON file exported successfully in data folder");
    }
}
