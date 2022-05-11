import java.math.BigInteger;
import java.sql.*;
public class Schedule {
    Connection con;


    public Schedule(Connection con) throws SQLException {
        this.con = con;

    }

    public void displayTripOfferings() throws SQLException {
        System.out.println("---Trip Offerings---");
        Statement st  = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM trip_offering");
        while(rs.next()){
            String tripNumber = rs.getString("trip_number");
            String date = rs.getString("date");
            String scheduledStart = rs.getString("scheduled_start_time");
            String scheduledArrival = rs.getString("scheduled_arrival_time");
            String driverName = rs.getString("driver_name");
            String busID = rs.getString("bus_id");
            System.out.println("Trip Number: "+ tripNumber + ", Date: " + date + ", Scheduled Start Time: " +
                    scheduledStart + ", Scheduled Arrival Time: " + scheduledArrival +
                    ", Driver Name: " + driverName + ", Bus ID: " + busID);
        }
        System.out.println("---End of results---");
        st.close();
    }

    public void displayTripOfferings(String startLocation, String destination, String date) throws SQLException {
        System.out.println("---Trip Offerings that match input---");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(String.format("SELECT * FROM trip_offering INNER JOIN trip ON trip_offering.trip_number = trip.trip_number WHERE trip.start_location_name = '%s' AND trip.destination_name = '%s' AND date = '%s'", startLocation, destination, date));
        while(rs.next()){
            String startLoc = rs.getString("start_location_name");
            String destLoc = rs.getString("destination_name");
            String dateResult = rs.getString("date");
            String scheduledStart = rs.getString("scheduled_start_time");
            String scheduledArrival = rs.getString("scheduled_arrival_time");
            String driverName = rs.getString("driver_name");
            String busID = rs.getString("bus_id");
            System.out.println("Start Location: "+ startLoc + ", Destination: " + destLoc + ", Date: " + dateResult + ", Scheduled Start Time: " +
                    scheduledStart + ", Scheduled Arrival Time: " + scheduledArrival +
                    ", Driver Name: " + driverName + ", Bus ID: " + busID);
        }
        System.out.println("---End of results---");
        st.close();
    }

    public void deleteTripOffering(int tripNum, String date, String scheduledStartTime) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate(String.format("DELETE FROM trip_offering WHERE trip_number = %d AND date = '%s' AND scheduled_start_time = '%s'", tripNum, date, scheduledStartTime));
        System.out.println("---Trip Offering Deleted---");
    }

    public void addTripOffering(int tripNum, String date, String scheduledStart, String scheduledArrival, String driverName, int busID) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate(String.format("INSERT INTO trip_offering VALUES (%d, '%s', '%s', '%s', '%s', %d)", tripNum, date, scheduledStart, scheduledArrival, driverName, busID));
        System.out.println("---Trip Offering Added---");
    }

    public void updateDriver(String newDriverName, int tripNum, String date, String scheduledStart) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate(String.format("UPDATE trip_offering SET driver_name = '%s' WHERE trip_number = %d AND date = '%s' AND scheduled_start_time = '%s'", newDriverName, tripNum, date, scheduledStart));
        System.out.println("---Driver updated---");
    }

    public void updateBus(int newBus, int tripNum, String date, String scheduledStart) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate(String.format("UPDATE trip_offering SET bus_id = %d WHERE trip_number = %d AND date = '%s' AND scheduled_start_time = '%s'", newBus, tripNum, date, scheduledStart));
        System.out.println("---Bus updated---");
    }

    public void displayTripStops(int tripNum) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(String.format("SELECT * FROM trip_stop_info WHERE trip_number = %d ORDER BY sequence_number ASC", tripNum));
        while(rs.next()){
            String tableTripNumber = rs.getString("trip_number");
            String stopNum = rs.getString("stop_number");
            String seqNum = rs.getString("sequence_number");
            String drivingTime = rs.getString("driving_time");
            System.out.println("Trip Number: "+ tableTripNumber + ", Stop Number: " + stopNum + ", Sequence Number: " + seqNum + ", Driving Time: " + drivingTime);
        }
    }

    public void displayWeeklySchedule(String driver, String date) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(String.format("SELECT * FROM trip_offering WHERE driver_name = '%s' AND trip_offering.date BETWEEN '%s' AND '%s'::DATE + INTERVAL '7 day' ORDER BY trip_offering.date ASC", driver, date, date));
        System.out.printf("---Weekly Schedule for %s starting on %s---\n", driver, date);
        while(rs.next()){
            String tripNumber = rs.getString("trip_number");
            String tableDate = rs.getString("date");
            String startTime = rs.getString("scheduled_start_time");
            String arrivalTime = rs.getString("scheduled_arrival_time");
            String busID = rs.getString("bus_id");
            System.out.println("Trip Number: "+ tripNumber + ", Date: " + tableDate + ", Start Time: " + startTime + ", Arrival Time: " + arrivalTime + ", Bis ID: " + busID);
        }
        System.out.println("---End of results---");
    }

    public void addDriver(String driver, BigInteger phoneNum) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate(String.format("INSERT INTO driver (driver_name, driver_telephone_number) VALUES ('%s', %d)", driver, phoneNum));
        System.out.printf("---Added %s to drivers---", driver);
    }

    public void addBus(int busID) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate(String.format("INSERT INTO bus (bus_id) VALUES (%d)", busID));
        System.out.printf("---Added %d to bus---", busID);
    }

    public void deleteBus(int busID) throws SQLException{
        Statement st = con.createStatement();
        st.executeUpdate(String.format("DELETE FROM bus WHERE bus_id = %d",busID));
        System.out.printf("---Bus %d Deleted---", busID);
    }

    public void inputLiveData(int tripNum, String date, String schStart, int stopNum, String schArrival, String actStart, String actArrival, int numPassIn, int numPassOut) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate(String.format("INSERT INTO actual_trip_stop_info (trip_number, date, scheduled_start_time, " +
                "stop_number, scheduled_arrival_time, actual_start_time, actual_arrive_time, number_of_passengers_in, " +
                "number_of_passengers_out) VALUES (%d, '%s', '%s', %d, '%s', '%s', '%s', %d, %d)",
                tripNum, date, schStart, stopNum, schArrival, actStart, actArrival, numPassIn, numPassOut));
        System.out.printf("---Live data for trip %d added---", tripNum);
    }


}
