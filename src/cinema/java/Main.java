/*
Author: Matt McGee
History:
03/25/2021: Created
03/26/2021: Completed

Cinema Management Program:

This program allows the user to set dimensions of a cinema, display seats, make reservations, and print
sales statistics. The pricing and size values are scalable should they need to be altered.

 */

package cinema.java;
import java.util.Scanner;

public class Main {

    //creates a seat map based on user defined dimensions
    public static String[][] makeCinema(int rows, int cols) {

        //declare a 2D array based on entered dimensions
        String[][] cinema = new String[rows+1][cols+1];

        //fill 2D array with indices and available seats
        for (int i = 0; i < cinema.length; i++) {

            for (int j = 0; j < cinema[i].length; j++){

                if (i == 0 && j == 0) {
                    cinema[i][j] = ("  ");
                } else if (i == 0 && j != 0) {
                    cinema[i][j] = (j + " ");
                } else if (j == 0 && i != 0) {
                    cinema[i][j] = (i + " ");
                } else {
                    cinema[i][j] = ("S ");
                }
            }
        }
        //return initial cinema map
        return cinema;

    }

    //print the current seat map
    public static void showSeats(String[][] cinema, int rows, int cols) {

        System.out.println("Cinema:");
        for (int i = 0; i < cinema.length; i++) {

            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j]);
            }
            System.out.print("\n");
        }
    }

    //allows user to purchase and reserve an available seat
    public static String[][] buyTicket(String[][] cinema, int rows, int cols) {
        Scanner scanner = new Scanner(System.in);
        int totalSeats = rows * cols; //total number of seats in cinema
        int regularPrice = 10; //price of regular tickets
        int reducedPrice = 8; //reduced ticket price
        int ticketPrice; //actual price of selected seat
        boolean availableSelection = false; //boolean to continue loop until condition is met

        while(!availableSelection) {

            //get seat selection from user
            System.out.println("Enter a row number: ");
            int pickedRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row: ");
            int pickedCol = scanner.nextInt();

            //if there are less than or equal to 60 seats in the cinema all tickets will be full cost
            if (totalSeats <= 60) {
                ticketPrice = regularPrice;

            //Otherwise, seats in the back half of the cinema will be at reduced price
            } else {
                if (pickedRow <= rows / 2) {
                    ticketPrice = regularPrice;
                } else {
                    ticketPrice = reducedPrice;
                }

            }
            //input validation
            try {
                //if seat has been purchased, do not allow the user to buy the ticket and continue loop for input
                if (cinema[pickedRow][pickedCol].equals("B ")) {
                    System.out.println("That ticket has already been purchased!");

                //otherwise, reserve the seat and print the ticket price
                } else {
                    System.out.print("Ticket price: $" + ticketPrice + "\n");
                    cinema[pickedRow][pickedCol] = "B ";
                    availableSelection = true;
                }
            //prevent exceptions from producing errors
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        }
        //return the updated cinema map
        return cinema;
    }

    //print sales statistics
    public static void printStats(String[][] cinema, int rows, int cols) {
        int currentIncome = 0; //total income earned from ticket sales
        int totalIncome; //total income possible if all tickets are sold
        int totalSeats = rows * cols; //total number of seats in cinema
        int ticketsSold = 0; // total number of tickets sold
        int regularPrice = 10; //price for regular tickets
        int reducedPrice = 8; //price for reduced-price tickets
        int premiumSeats; //regular-priced seats
        int regSeats; //reduced-price seats

        //check cinema map for reserved seats
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                //get statistics from reserved seats
                if (cinema[i][j].equals("B ")) {
                    //increment ticket sales for each reserved seat
                    ticketsSold++;
                    //keep a running sum of sales income for cinemas with 60 or fewer seats
                    if (totalSeats <= 60) {
                        currentIncome += regularPrice;
                        //keep a running sum of sales income for cinemas with more than 60 seats
                    } else {
                        //determine if seats are regular or reduced price and add the income accordingly
                        if (i <= rows / 2) {
                            currentIncome += regularPrice;
                        } else {
                            currentIncome += reducedPrice;
                        }
                    }
                }
            }
        }

        //calculate total income for theater with 60 or fewer seats
        if (totalSeats <= 60) {
            totalIncome = totalSeats * regularPrice;
        //calculate total income for theater with mote than 60 seats
        } else {
            premiumSeats = (rows / 2 * cols);
            regSeats = (totalSeats - premiumSeats);
            totalIncome = (regSeats * reducedPrice) + (premiumSeats * regularPrice);

        }

        //calculate percentage of seats sold
        float percentage = ((float)ticketsSold / (float)totalSeats) * 100;

        //print sales statistics
        System.out.println("Number of purchased tickets: " + ticketsSold);
        System.out.printf("Percentage: %.2f%s", percentage, "%\n");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome + "\n");
    }

    public static void main(String[] args) {
        int selection = -1; //variable to store user selection for menu

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Cinema Management\nPlease enter the following information about your cinema.");

        //get dimensions of cinema from user
        System.out.println("Enter the number of rows: ");
        int r = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int c = scanner.nextInt();

        //create cinema map based on set dimensions
        String[][] cinema = makeCinema(r, c);

        //create a menu to call methods or exit
        while(selection != 0) {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");

            selection = scanner.nextInt();

            switch(selection) {
                case 1:
                    //show the seats
                    showSeats(cinema, r, c);
                    break;
                case 2:
                    //buy seats and update cinema map
                    cinema = buyTicket(cinema, r, c);
                    break;
                case 3:
                    //print sales statistics
                    printStats(cinema, r, c);
                    break;
                case 0:
                    break;
                //catch errors
                default:
                    System.out.println("Please enter a valid selection");
            }
        }
    }

}
