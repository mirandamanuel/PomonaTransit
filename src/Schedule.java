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
}
