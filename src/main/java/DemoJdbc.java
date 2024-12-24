import java.sql.*;

public class DemoJdbc {
    public static void main(String[] args) {
        String url ="jdbc:postgresql://localhost:5432/demo";
        String username = "postgres";
        String password = "1004";
        try {
            // load driver
            Class.forName("org.postgresql.Driver");
            Connection con =DriverManager.getConnection(url,username,password);
            System.out.println("Connected to PostgreSQL database");

            Statement stmt = con.createStatement();

            // select data from db
            String sql = "SELECT sname FROM public.student WHERE sid = 1";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println(rs.next()); // this will move the db curesor to the first row

            String sname = rs.getString("sname");
            System.out.println("sname is " + sname);

            // select all from db
            String selectAllQuery = "SELECT * FROM public.student";
            ResultSet rsAll = stmt.executeQuery(selectAllQuery);
            int rowCount = 1;
            while (rsAll.next()) {
                rowCount++;
                System.out.print(rsAll.getInt(1) + " - ");
                System.out.print(rsAll.getString(2) + " - ");
                System.out.println(rsAll.getInt(3));
            }


            // insert into db
            //String createStmt = "Insert into student values('"+(rowCount) +"','Student"+rowCount+"','"+(rowCount * 10)+"')";
            //stmt.execute(createStmt);

            // to avoid sql injections and many other problems lets use preparedStatement
            String createStmt = "Insert into student values(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(createStmt);

            pstmt.setInt(1, rowCount);
            pstmt.setString(2, "student"+rowCount);
            pstmt.setInt(3, rowCount*10 );

            //update into db
            String updateQuery = "update student set sname = 'John' where sid = 1";
            stmt.execute(updateQuery);

            con.close();
            System.out.println("Closed connection");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}