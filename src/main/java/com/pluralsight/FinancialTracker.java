package com.pluralsight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    /* ------------------------------------------------------------------
       Main menu
       ------------------------------------------------------------------ */
    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\n========== HOME ==========");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {

                case "D":
                    addDeposit(scanner);
                    break;

                case "P":
                    addPayment(scanner);
                    break;

                case "L":
                    ledgerMenu(scanner);
                    break;

                case "X":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

            // Loop repeats automatically and returns to Home Menu
        }

        scanner.close();
    }
    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */

    public static void loadTransactions(String fileName) {
        File file = new File(fileName);

        try {
            // If file does not exist, create an empty one
            if (!file.exists()) {
                file.createNewFile();
                return; // nothing to load yet
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            // Each line: date|time|description|vendor|amount
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0], DATE_FMT);
                    LocalTime time = LocalTime.parse(parts[1], TIME_FMT);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    transactions.add(
                            new Transaction(date, time, description, vendor, amount)
                    );
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }
    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */

    /**
     * Prompt for ONE date+time string in the format
     * "yyyy-MM-dd HH:mm:ss", plus description, vendor, amount.
     * Validate that the amount entered is positive.
     * Store the amount as-is (positive) and append to the file.
     */

    private static void addDeposit(Scanner scanner) {

        // prompt for date + time
        System.out.println("Enter date and time (yyyy-MM-dd HH:mm:ss)");
        String dateTime = scanner.nextLine();

        String[] parts = dateTime.split(" ");
        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time = LocalTime.parse(parts[1]);

        // Description
        System.out.println("Description: ");
        String description = scanner.nextLine();

        //Vendor
        System.out.println("Vendor: ");
        String vendor = scanner.nextLine();

        //Amount
        System.out.println("Amount (positive): ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (amount <= 0) {
            System.out.println("Amount must be positive. Deposit canceled.");
            return;
        }

        saveTransaction(
                date,
                time,
                description,
                vendor,
                amount
        );

        System.out.println("Deposit saved successfully.");
    }

    private static void saveTransaction(
            LocalDate date,
            LocalTime time,
            String description,
            String vendor,
            double amount) {

        Transaction transaction =
                new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            writer.write(
                    date.format(DATE_FMT) + "|" +
                            time.format(TIME_FMT) + "|" +
                            description + "|" +
                            vendor + "|" +
                            amount + "\n"
            );

        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    /**
     * Same prompts as addDeposit.
     * Amount must be entered as a positive number,
     * then converted to a negative amount before storing.
     */

    private static void addPayment(Scanner scanner) {

        // Prompt for date + time
        System.out.println("Enter date and time (yyyy-MM-dd HH:mm:ss)");
        String dateTime = scanner.nextLine();

        String[] parts = dateTime.split(" ");
        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time = LocalTime.parse(parts[1]);

        // Description
        System.out.println("Description:");
        String description = scanner.nextLine();

        // Vendor
        System.out.println("Vendor:");
        String vendor = scanner.nextLine();

        // Amount
        System.out.println("Amount (positive):");
        double amount = Double.parseDouble(scanner.nextLine());

        if (amount <= 0) {
            System.out.println("Amount must be positive. Payment canceled.");
            return;
        }

        // Store as negative for a debit
        amount = -amount;

        saveTransaction(
                date,
                time,
                description,
                vendor,
                amount
        );

        System.out.println("Payment saved successfully.");
    }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }  //

    private static void displayLedger() {

            sortNewestFirst();

            System.out.printf("%-12s %-10s %-20s %-20s %10s%n",
                    "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

            for (Transaction transaction : transactions) {
                System.out.printf("%-12s %-10s %-20s %-20s %10.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }

    private static void displayDeposits() {

        transactions.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );

        System.out.printf("%-12s %-10s %-20s %-20s %10s%n",
                "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

        for (Transaction transaction : transactions) {

            if (transaction.getAmount() > 0) {

                System.out.printf("%-12s %-10s %-20s %-20s %10.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

private static void displayPayments() {

    transactions.sort(
            Comparator.comparing(Transaction::getDate)
                    .thenComparing(Transaction::getTime)
                    .reversed()
    );

    System.out.printf("%-12s %-10s %-20s %-20s %10s%n",
            "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

    for (Transaction transaction : transactions) {

        if (transaction.getAmount() < 0) {

            System.out.printf("%-12s %-10s %-20s %-20s %10.2f%n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
        }
    }
}

private static void sortNewestFirst() {
    transactions.sort(
            Comparator.comparing(Transaction::getDate)
                    .thenComparing(Transaction::getTime)
                    .reversed()
    );
}

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {/* TODO – month-to-date report */ }
                case "2" -> {/* TODO – previous month report */ }
                case "3" -> {/* TODO – year-to-date report   */ }
                case "4" -> {/* TODO – previous year report  */ }
                case "5" -> {/* TODO – prompt for vendor then report */ }
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        // TODO – iterate transactions, print those within the range
    }

    private static void filterTransactionsByVendor(String vendor) {
        // TODO – iterate transactions, print those with matching vendor
    }

    private static void customSearch(Scanner scanner) {
        // TODO – prompt for any combination of date range, description,
        //        vendor, and exact amount, then display matches
    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s) {
        /* TODO – return LocalDate or null */
        return null;
    }

    private static Double parseDouble(String s) {
        /* TODO – return Double   or null */
        return null;
    }
}
