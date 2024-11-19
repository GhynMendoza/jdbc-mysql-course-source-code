import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class GetCountForDepartment {

    public static void main(String[] args) {
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            // Get a connection to the database
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "");

            String theDepartment = "Engineering";
            
            // Prepare the stored procedure call
            myStmt = myConn.prepareCall("{call get_count_for_department(?, ?)}");

            // Set the IN parameter
            myStmt.setString(1, theDepartment);
            
            // Register the OUT parameter (second parameter)
            myStmt.registerOutParameter(2, Types.INTEGER);

            // Call the stored procedure
            System.out.println("Calling stored procedure.  get_count_for_department('" + theDepartment + "', ?)");
            myStmt.execute();
            System.out.println("Finished calling stored procedure");

            // Get the value of the OUT parameter
            int theCount = myStmt.getInt(2);
            System.out.println("\nThe count = " + theCount);

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            close(myConn, myStmt);
        }
    }

    private static void close(Connection myConn, CallableStatement myStmt) {
        try {
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

