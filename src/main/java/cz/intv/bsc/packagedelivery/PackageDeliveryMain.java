package cz.intv.bsc.packagedelivery;

import cz.intv.bsc.packagedelivery.service.PackageDeliveryService;

import java.util.Scanner;

public class PackageDeliveryMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = true;

        final PackageDeliveryService packageDeliveryService = new PackageDeliveryService();

        while (quit) {
            System.out.println("---------------------");
            System.out.println("1 - Insert package: ");
            System.out.println("2 - Insert packages.txt by file: ");
            System.out.println("3 - Insert package fees: ");
            System.out.println("4 - Quit: ");
            System.out.println("---------------------");
            int choice = getChoice(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Insert package: ");
                    String[] s = scanner.nextLine().split(" ");
                    if (s.length > 2) {
                        System.err.println("To much parameters!");
                    } else if (s.length < 2) {
                        System.err.println("Missing one parameter");
                    } else {
                        packageDeliveryService.addPackage(s[0], s[1]);
                    }
                    break;
                case 2:
                    System.out.println("Insert package .txt path: ");
                    packageDeliveryService.addPackage(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Insert package fee .txt path: ");
                    packageDeliveryService.addPackageFee(scanner.nextLine());
                    break;
                case 4:
                    quit = false;
                    break;
                default:
                    break;
            }
        }
        packageDeliveryService.cancelScheduledJob();
    }

    private static int getChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Bad format of option: " + e.getMessage());
        }
        return -1;
    }
}
