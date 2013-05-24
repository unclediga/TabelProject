package db;

import model.Emp;
import model.Ill;
import model.Leave;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 *
 */
public class DBSrv {


    private Connection conn = null;

    public DBSrv() {

        String connString;
        String connUser;
        String connPassword;
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("tabel.property"));
        } catch (IOException e) {
            System.err.print("Can't read property file");
            return;
        }

        connString = prop.getProperty("db.connect.string");
        connUser = prop.getProperty("db.connect.user");
        connPassword = prop.getProperty("db.connect.password");


        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(connString, connUser, connPassword);
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            System.err.println("Not found JDBC Driver");
        } catch (SQLException e) {
            System.err.println("SQL Exception. See log!");
        }
    }

    private java.sql.Date UtilToSQL(java.util.Date d) {
        if (d != null)
            return new Date(d.getTime());
        else
            return null;
    }

    public Connection getConnection() throws Exception {
        if (conn != null) {
            return conn;
        } else
            throw new Exception("Not found opened connection");
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Emp> getEmps() {

        ArrayList<Emp> emps = new ArrayList<Emp>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return emps;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id,lname,fname,mname,d_hire,d_fire FROM DDT_EMP");
            while (rs.next()) {
                Emp emp = new Emp();
                emp.setId(rs.getInt("id"));

                emp.setLastName(rs.getString("lname"));
                emp.setFirstName(rs.getString("fname"));
                emp.setMiddleName(rs.getString("mname"));
                emp.setHireDate(rs.getDate("d_hire"));
                emp.setFireDate(rs.getDate("d_fire"));
                emps.add(emp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return emps;
    }

    public ArrayList<Emp> getEmpsFromTxt() {

        ArrayList<Emp> emps = new ArrayList<Emp>(100);

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("emplist.txt")));
            String str;
            int i = emps.size();
            while((str = r.readLine()) != null){
                StringTokenizer t = new StringTokenizer(str,":");

                while (t.hasMoreTokens()){
                    emps.add(new Emp(i, t.nextToken(), t.nextToken(),t.nextToken(),new java.util.Date(),new java.util.Date()));
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emps;
    }
    public Emp getEmpById(Integer id){
        if (conn == null) {
            System.err.println("No connect!!");
            return null;
        }

        Emp emp = null;

        try {
            final String sql =
                    "SELECT id,lname,fname,mname,d_hire,d_fire FROM DDT_EMP" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,id.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                emp =  new Emp();
                emp.setId(rs.getInt("id"));
                emp.setLastName(rs.getString("lname"));
                emp.setFirstName(rs.getString("fname"));
                emp.setMiddleName(rs.getString("mname"));
                emp.setHireDate(rs.getDate("d_hire"));
                emp.setFireDate(rs.getDate("d_fire"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return emp;

    }
    public void insertEmp(Emp emp) {
        if (conn == null) {
            System.err.println("No connect!!");
        }
        final String sql =
                "INSERT INTO DDT_EMP(id,lname,fname,mname,d_hire,d_fire) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, emp.getId());
            st.setString(2, emp.getLastName());
            st.setString(3, emp.getFirstName());
            st.setString(4, emp.getMiddleName());
            st.setDate(5, UtilToSQL(emp.getHireDate()));
            st.setDate(6, UtilToSQL(emp.getFireDate()));
            st.executeUpdate();
            conn.commit();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmp(Emp emp) {
        if (conn == null) {
            System.err.println("No connect!!");
        }
        final String sql =
                "UPDATE DDT_EMP SET " +
                        " lname = ?, " +
                        " fname = ?, " +
                        " mname = ?, " +
                        " d_hire = ?, " +
                        " d_fire = ? " +
                        " WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, emp.getLastName());
            st.setString(2, emp.getFirstName());
            st.setString(3, emp.getMiddleName());
            st.setDate(4, UtilToSQL(emp.getHireDate()));
            st.setDate(5, UtilToSQL(emp.getFireDate()));
            st.setInt(6, emp.getId());
            st.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteEmp(Emp emp) {
        if (conn == null) {
            System.err.println("No connect!!");
        }
        try {
            final String sql =
                    "DELETE FROM DDT_EMP " +
                            "WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, emp.getId());
            st.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getNextEmpId() {
        int id = 0;
        if (conn == null) {
            System.err.println("No connect!!");
            return 0;
        }
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ISNULL(MAX(id),0) FROM DDT_EMP");
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Leave> getLeaves() {
        ArrayList<Leave> leaves = new ArrayList<Leave>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return leaves;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id,emp_id,d_from,d_to FROM DDT_LEAVE");
            while (rs.next()) {
                Leave leave = new Leave();
                leave.setId(rs.getInt("id"));
                leave.setDateFrom(rs.getDate("d_from"));
                leave.setDateTo(rs.getDate("d_to"));

                Emp emp = getEmpById(new Integer(rs.getInt("emp_id")));
                leave.setEmp(emp);

                leaves.add(leave);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return leaves;
    }

    public Leave getLeaveById(Integer id) {
        Leave leave = null;

        if (conn == null) {
            System.err.println("No connect!!");
            return leave;
        }

        try {
            final String sql =
                    "SELECT id,emp_id,d_from,d_to FROM DDT_LEAVE" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                leave = new Leave();
                leave.setId(rs.getInt("id"));
                leave.setDateFrom(rs.getDate("d_from"));
                leave.setDateTo(rs.getDate("d_to"));

                Emp emp = getEmpById(new Integer(rs.getInt("emp_id")));
                leave.setEmp(emp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leave;
    }
    public void insertLeave(Leave key) {
    }

    public void updateLeave(Leave key) {
    }

    public void deleteLeave(Leave key) {

    }

    public int getNextLeaveId() {
        int id = 0;
        if (conn == null) {
            System.err.println("No connect!!");
            return 0;
        }
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ISNULL(MAX(id),0) FROM DDT_LEAVE");
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public ArrayList<Ill> getIlls() {
        ArrayList<Ill> ills = new ArrayList<Ill>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return ills;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id,emp_id,d_from,d_to FROM DDT_ILL");
            while (rs.next()) {
                Ill ill = new Ill();
                ill.setId(rs.getInt("id"));
                ill.setDateFrom(rs.getDate("d_from"));
                ill.setDateTo(rs.getDate("d_to"));

                Emp emp = getEmpById(new Integer(rs.getInt("emp_id")));
                ill.setEmp(emp);

                ills.add(ill);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return ills;
    }

    public Ill getIllById(Integer id) {
        Ill ill = null;

        if (conn == null) {
            System.err.println("No connect!!");
            return ill;
        }

        try {
            final String sql =
                    "SELECT id,emp_id,d_from,d_to FROM DDT_ILL" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ill = new Ill();
                ill.setId(rs.getInt("id"));
                ill.setDateFrom(rs.getDate("d_from"));
                ill.setDateTo(rs.getDate("d_to"));

                Emp emp = getEmpById(new Integer(rs.getInt("emp_id")));
                ill.setEmp(emp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ill;
    }
    public void insertIll(Ill key) {
    }

    public void updateIll(Ill key) {
    }

    public void deleteIll(Ill key) {

    }

    public int getNextIllId() {
        int id = 0;
        if (conn == null) {
            System.err.println("No connect!!");
            return 0;
        }
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ISNULL(MAX(id),0) FROM DDT_ILL");
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
