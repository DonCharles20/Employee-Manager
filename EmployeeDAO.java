//package finalproject;
import java.util.logging.Logger;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class EmployeeDAO {
    
    private static Logger log = Logger.getLogger(EmployeeDAO.class.getName());

    public EmployeeDAO() throws Exception {

        //String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\EmployeeManager\\employees.mdb;";
        String url = "jdbc:mysql://localhost:3306/employees";


        String username = "anonymous";
        String password = "guest";
        // Load the driver to allow connection to the database
        try {
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Class.forName("com.mysql.cj.jdbc.Driver");//Note:Doneddy's code has com.mysql.jdbc.Driver for mysql
            Connection connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Failed to load JDBC/ODBC driver.Jack Wrote This!!!");
            cnfex.printStackTrace();
            System.exit(1); // terminate program
        } catch (SQLException sqlex) {
            System.err.println("Unable to connect");
            sqlex.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees() throws Exception {
        log.fine("getAllEmployees called");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM EMPLOYEES ORDER BY name");

            return resultSetToEmployees(resultSet);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public void addEmployee(Employee employee) throws Exception {
        log.fine("addEmployee called");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO EMPLOYEES (name, address, hours, rate, sex, age, active, ssn, phone, "+
                    "city, gpa, gender, dependents, graduated) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");// STEP 3

            int i = 1;
            statement.setString(i++, employee.getName());
            statement.setString(i++, employee.getAddress());
            statement.setDouble(i++, employee.getHours());
            statement.setDouble(i++, employee.getRate());
            statement.setString(i++, String.valueOf(employee.getSex()));
            statement.setInt(i++, employee.getAge());
            statement.setBoolean(i++, employee.isActive());
            statement.setString(i++, employee.getSSN());
            statement.setString(i++, employee.getPhone()); // Step 3
            statement.setString(i++, employee.getCity()); // Step 3
            statement.setDouble(i++, employee.getGPA());
            statement.setString(i++, String.valueOf(employee.getGender()));
            statement.setInt(i++, employee.getDependents());
            statement.setBoolean(i++, employee.isGraduated());

            statement.executeUpdate();// DO IT!!!!!
        } finally {
            close(null, statement, connection);
        }
    }

    public void updateEmployee(Employee employee) throws Exception {
        log.fine("updateEmployee called");

        Connection connection = null;
        PreparedStatement statement = null; // no dangling pointers please!!!!

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                    "UPDATE EMPLOYEES SET name = ?, address = ?, hours = ?, rate = ?, sex = ?, age = ?, active = ?, ssn = ?, " + 
                    "phone = ?, city =?, gpa = ?, gender = ?, dependents = ?, graduated = ?,  WHERE id = ?");// STEP 4

            int i = 1;
            statement.setString(i++, employee.getName());
            statement.setString(i++, employee.getAddress());
            statement.setDouble(i++, employee.getHours());
            statement.setDouble(i++, employee.getRate());
            statement.setString(i++, String.valueOf(employee.getSex()));
            statement.setInt(i++, employee.getAge());
            statement.setBoolean(i++, employee.isActive());
            statement.setString(i++, employee.getSSN());
            statement.setString(i++, employee.getPhone()); // STEP 4
            statement.setString(i++, employee.getCity()); // STEP 4
            statement.setDouble(i++, employee.getGPA());
            statement.setString(i++, String.valueOf(employee.getGender()));
            statement.setInt(i++, employee.getDependents());

            statement.setInt(i++, employee.getId()); // get the ID here! Remember, you can't set the ID as the DBMS
                                                     // makes it unique!!!

            statement.executeUpdate();
        } finally {
            close(null, statement, connection);
        }
    }

    public void deleteEmployee(Employee employee) throws Exception {
        log.fine("deleteEmployee called");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM EMPLOYEES WHERE id = ?");

            statement.setInt(1, employee.getId());

            statement.executeUpdate();
        } finally {
            close(null, statement, connection);
        }
    }

    private List<Employee> resultSetToEmployees(ResultSet resultSet) throws Exception {
        log.fine("resultSetToEmployees called");

        ArrayList<Employee> employees = new ArrayList<Employee>();

        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("name"));
            employee.setAddress(resultSet.getString("address"));
            employee.setHours(resultSet.getDouble("hours"));
            employee.setRate(resultSet.getDouble("rate"));

            String sex = resultSet.getString("sex");
            if (sex != null && sex.length() > 0)
                employee.setSex(sex.charAt(0));

            employee.setAge(resultSet.getInt("age"));
            employee.setActive(resultSet.getBoolean("active"));
            employee.setSSN(resultSet.getString("ssn"));
            employee.setPhone(resultSet.getString("phone")); // STEP 5
            employee.setCity(resultSet.getString("city")); // STEP 5
            employee.setGPA(resultSet.getDouble("gpa"));
            employee.setGraduated(resultSet.getBoolean("graduated"));

            String gender = resultSet.getString("gender");
            if (gender != null && gender.length() > 0)
                employee.setGender(gender.charAt(0));

            employee.setDependents(resultSet.getInt("dependents"));

            employees.add(employee);
        } // while

        return employees; // &employees[0]
    }

    private void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() throws Exception {
        log.fine("getConnection called");

       // return DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\EmployeeManager\\employees.mdb");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "anonymous", "guest");
    }
}
