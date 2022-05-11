import java.math.BigInteger;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pomona_transit",
                    "postgres", "Chemist0-Exile-Trailside");

            Schedule schedule = new Schedule(con);

            System.out.println("Pomona Transit System Manager");

            boolean terminate = false;
            while(!terminate){
                System.out.println("\nPlease select one of the following options:");
                System.out.println("""
                        [1] Find the schedule of trips from Start to Destination on a date
                        [2] Edit the trip offerings table
                        [3] Display the stops of a given trip
                        [4] Display the weekly schedule of a given driver and date
                        [5] Add a driver
                        [6] Add a bus
                        [7] Delete a bus
                        [8] Record actual data for a trip offering
                        [0] Exit program""");
                Scanner scanner = new Scanner(System.in);
                int selection = scanner.nextInt();
                switch (selection){
                    case 1:
                        System.out.println("---Find the schedule of trips from Start to Destination on a date---");
                        System.out.println("Enter Start Location Name:");
                        String start = getLine();
                        System.out.println("Enter Destination Name:");
                        String dest = getLine();
                        System.out.println("Enter Date (yyyy-mm-dd):");
                        String date = getLine();
                        schedule.displayTripOfferings(start, dest, date);
                        break;
                    case 2:
                        boolean terminateIn = false;
                        while(!terminateIn){
                            System.out.println("""
                                Please select one of the following options:
                                [1] Delete a trip offering
                                [2] Add trip offerings
                                [3] Change the driver for a given trip offering
                                [4] Change the bus for a given trip offering
                                [5] Return to main menu""");
                            int selectionIn = scanner.nextInt();
                            switch (selectionIn){
                                case 1:
                                    System.out.println("---Delete a trip offering---");
                                    System.out.println("Enter Trip Number:");
                                    int deleteTripNum = scanner.nextInt();
                                    System.out.println("Enter Date (yyyy-mm-dd):");
                                    String deleteDate = getLine();
                                    System.out.println("Enter scheduled start time (hh:mm:ss):");
                                    String deleteStart = getLine();
                                    schedule.deleteTripOffering(deleteTripNum, deleteDate, deleteStart);
                                    break;
                                case 2:
                                    boolean addAnotherBool = false;
                                    do{
                                        System.out.println("---Add a trip offering---");
                                        System.out.println("Enter Trip Number:");
                                        int newTripNum = scanner.nextInt();
                                        System.out.println("Enter Date (yyyy-mm-dd):");
                                        String newDate = getLine();
                                        System.out.println("Enter scheduled start time (hh:mm:ss):");
                                        String newStart = getLine();
                                        System.out.println("Enter scheduled arrival time (hh:mm:ss):");
                                        String newArrival = getLine();
                                        System.out.println("Enter driver name:");
                                        String newName = getLine();
                                        System.out.println("Enter bus ID:");
                                        int newBus = scanner.nextInt();
                                        schedule.addTripOffering(newTripNum, newDate, newStart, newArrival, newName,
                                                newBus);
                                        System.out.println("Would you like to add another trip offering? [Y/N]: ");
                                        String addAnother = scanner.next();
                                        if(Objects.equals(addAnother, "Y"))
                                            addAnotherBool = true;
                                        else if(Objects.equals(addAnother, "N"))
                                            addAnotherBool = false;
                                    }while(addAnotherBool);
                                    break;
                                case 3:
                                    System.out.println("---Change a driver---");
                                    System.out.println("Enter trip number:");
                                    int newDriverTripNum = scanner.nextInt();;
                                    System.out.println("Enter Date (yyyy-mm-dd):");
                                    String newDriverDate = getLine();
                                    System.out.println("Enter scheduled start time (hh:mm:ss):");
                                    String newStartTime = getLine();
                                    System.out.println("Enter new driver's name:");
                                    String newDriverName = getLine();
                                    schedule.updateDriver(newDriverName, newDriverTripNum, newDriverDate, newStartTime);
                                    break;
                                case 4:
                                    System.out.println("---Change a bus on a trip---");
                                    System.out.println("Enter trip number:");
                                    int newBusTripNum = scanner.nextInt();;
                                    System.out.println("Enter Date (yyyy-mm-dd):");
                                    String newBusDate = getLine();
                                    System.out.println("Enter scheduled start time (hh:mm:ss):");
                                    String newBusStart = getLine();
                                    System.out.println("Enter new bus ID:");
                                    int newBusID = scanner.nextInt();
                                    schedule.updateBus(newBusID, newBusTripNum, newBusDate, newBusStart);
                                    break;
                                case 5:
                                    terminateIn = true;
                                    break;
                            }
                        }
                        break;
                    case 3:
                        System.out.println("---Display the stops of a given trip---");
                        System.out.println("Enter trip number:");
                        int stopsTripNum = scanner.nextInt();
                        schedule.displayTripStops(stopsTripNum);
                        break;
                    case 4:
                        System.out.println("---Display the weekly schedule of a given driver---");
                        System.out.println("Enter driver name:");
                        String weeklyDriver = getLine();
                        System.out.println("Enter Date (yyyy-mm-dd):");
                        String weeklyDate = getLine();
                        schedule.displayWeeklySchedule(weeklyDriver, weeklyDate);
                        break;
                    case 5:
                        System.out.println("---Add a driver---");
                        System.out.println("Enter new driver's name:");
                        String newDriverName = getLine();
                        System.out.println("Enter new driver's phone number (xxxxxxxxxx):");
                        BigInteger newDriverPhone = scanner.nextBigInteger();
                        schedule.addDriver(newDriverName, newDriverPhone);
                        break;
                    case 6:
                        System.out.println("---Add a bus---");
                        System.out.println("Enter new bus' ID:");
                        int newBusID = scanner.nextInt();
                        schedule.addBus(newBusID);
                        break;
                    case 7:
                        System.out.println("---Delete a bus---");
                        System.out.println("Enter Bus ID to delete:");
                        int delBusID = scanner.nextInt();
                        schedule.deleteBus(delBusID);
                        break;
                    case 8:
                        System.out.println("---Add live data---");
                        System.out.println("Enter trip number:");
                        int liveTripNum = scanner.nextInt();
                        System.out.println("Enter Date (yyyy-mm-dd):");
                        String liveDate = getLine();
                        System.out.println("Enter scheduled start time (hh:mm:ss):");
                        String liveSchStart = getLine();
                        System.out.println("Enter stop number: ");
                        int liveStopNum = scanner.nextInt();
                        System.out.println("Enter scheduled arrival time (hh:mm:ss):");
                        String liveSchArrival = getLine();
                        System.out.println("Enter actual start time (hh:mm:ss):");
                        String liveActStart = getLine();
                        System.out.println("Enter actual arrival time (hh:mm:ss):");
                        String liveActArrival = getLine();
                        System.out.println("Enter number of passengers in:");
                        int livePassIn = scanner.nextInt();
                        System.out.println("Enter number of passengers out:");
                        int livePassOut = scanner.nextInt();
                        schedule.inputLiveData(liveTripNum, liveDate, liveSchStart, liveStopNum, liveSchArrival,
                                liveActStart, liveActArrival, livePassIn, livePassOut);
                        break;
                    case 0:
                        System.out.println("Thank you for using Pomona Transit System Manager");
                        terminate = true;
                        break;
                }
            }
            con.close();
        }
    }
    private static String getLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
