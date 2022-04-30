import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException {
        {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pomona_transit",
                    "postgres", "Chemist0-Exile-Trailside");

            Schedule schedule = new Schedule(con);

            schedule.displayTripOfferings();
            //schedule.displayTripOfferings("A", "B", "2022-06-01");
            schedule.addTripOffering(300, "2022-06-01", "08:15:00", "08:30:00", "Eva Evante", 5);
            schedule.displayTripOfferings();
            schedule.updateDriver("Adama Alonso", 300, "2022-06-01", "08:15:00");
            schedule.displayTripOfferings();
            schedule.updateBus(8, 300, "2022-06-01", "08:15:00");
            schedule.displayTripOfferings();
            schedule.deleteTripOffering(300, "2022-06-01", "08:15:00");
            schedule.displayTripOfferings();


            con.close();

        }
    }

}
