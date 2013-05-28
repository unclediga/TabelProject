package db;

import model.Emp;
import model.Ill;
import model.Leave;
import model.Schedule;

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
            prop.load(new FileInputStream("tabel.properties"));
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
            ResultSet rs = st.executeQuery("SELECT id,lname,fname,mname,appoint,d_hire,d_fire FROM DDT_EMP");
            while (rs.next()) {
                Emp emp = new Emp();
                emp.setId(rs.getInt("id"));

                emp.setLastName(rs.getString("lname"));
                emp.setFirstName(rs.getString("fname"));
                emp.setMiddleName(rs.getString("mname"));
                emp.setAppoint(rs.getString("appoint"));
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
        Random rnd = new Random();

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("emplist.txt")));
            String str;
            int i = emps.size();
            while ((str = r.readLine()) != null) {
                StringTokenizer t = new StringTokenizer(str, ":");

                while (t.hasMoreTokens()) {

                    Calendar c = new GregorianCalendar(2000, 1, 1);
                    c.set(2000 + rnd.nextInt(13), 1 + rnd.nextInt(12), 1 + rnd.nextInt(29));
                    emps.add(new Emp(i, t.nextToken(), t.nextToken(), t.nextToken(),"космонавт" ,c.getTime(), new java.util.Date()));
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emps;
    }

    public Emp getEmpById(Integer id) {
        if (conn == null) {
            System.err.println("No connect!!");
            return null;
        }

        Emp emp = null;

        try {
            final String sql =
                    "SELECT id,lname,fname,mname,appoint,d_hire,d_fire FROM DDT_EMP" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                emp = new Emp();
                emp.setId(rs.getInt("id"));
                emp.setLastName(rs.getString("lname"));
                emp.setFirstName(rs.getString("fname"));
                emp.setMiddleName(rs.getString("mname"));
                emp.setAppoint(rs.getString("appoint"));
                emp.setHireDate(rs.getDate("d_hire"));
                emp.setFireDate(rs.getDate("d_fire"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return emp;

    }

    public void save(Object obj) {

        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        if (obj == null) {
            System.err.println("null object for save() ");
        }

        if (obj instanceof Emp) {

            Emp emp = (Emp) obj;
            if (emp.getId() == null){

                emp.setId(getNextId());
                sql = "INSERT INTO DDT_EMP(lname,fname,mname,appoint,d_hire,d_fire,id) " +
                        "VALUES(?,?,?,?,?,?,?)";
            }else{
                sql =
                        "UPDATE DDT_EMP SET " +
                                " lname = ?, " +
                                " fname = ?, " +
                                " mname = ?, " +
                                " appoint = ?, " +
                                " d_hire = ?, " +
                                " d_fire = ? " +
                                " WHERE id = ?";
            }
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, emp.getLastName());
                st.setString(2, emp.getFirstName());
                st.setString(3, emp.getMiddleName());
                st.setString(4, emp.getAppoint());
                st.setDate(5, UtilToSQL(emp.getHireDate()));
                st.setDate(6, UtilToSQL(emp.getFireDate()));
                st.setInt(7, emp.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (obj instanceof Leave) {

            Leave leave = (Leave) obj;

            if (leave.getId() == null){

                leave.setId(getNextId());
                sql = "INSERT INTO DDT_LEAVE(EMP_ID, TLEAVE_ID, D_FROM, D_TO ,ID) " +
                        "VALUES(?,?,?,?,?)";
            }else{
                sql =
                        "UPDATE DDT_LEAVE SET " +
                                " emp_id = ?, " +
                                " tleave_id = ?, " +
                                " d_from = ?, " +
                                " d_to = ?  " +
                                " WHERE id = ?";
            }
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, leave.getEmp().getId());
                st.setString(2, leave.getTleave_id());
                st.setDate(3, UtilToSQL(leave.getDateFrom()));
                st.setDate(4, UtilToSQL(leave.getDateTo()));
                st.setInt(5, leave.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (obj instanceof Ill) {

            Ill ill = (Ill) obj;

            if (ill.getId() == null){

                ill.setId(getNextId());
                sql = "INSERT INTO DDT_ILL(EMP_ID, TILL_ID, D_FROM, D_TO ,ID) " +
                        "VALUES(?,?,?,?,?)";
            }else{
                sql =
                        "UPDATE DDT_ILL SET " +
                                " emp_id = ?, " +
                                " till_id = ?, " +
                                " d_from = ?, " +
                                " d_to = ?  " +
                                " WHERE id = ?";
            }
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, ill.getEmp().getId());
                st.setString(2, ill.getTill_id());
                st.setDate(3, UtilToSQL(ill.getDateFrom()));
                st.setDate(4, UtilToSQL(ill.getDateTo()));
                st.setInt(5, ill.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (obj instanceof Schedule) {

            Schedule schedule = (Schedule) obj;

            if (schedule.getId() == null){

                schedule.setId(getNextId());
                sql = "INSERT INTO DDT_SCHEDULE(" +
                      "  EMP_ID, " +
                      "  D_FROM, " +
                      "  hours1, "+
                      "  hours2, "+
                      "  hours3, "+
                      "  hours4, "+
                      "  hours5, "+
                      "  hours6, "+
                      "  hours7, "+
                      "  ID" +
                      ") " +
                        "VALUES(?,?,?, ?,?,?, ?,?,?, ?)";
            }else{
                sql =
                        "UPDATE DDT_SCHEDULE SET " +
                                "  EMP_ID = ?, " +
                                "  D_FROM = ?, " +
                                "  hours1 = ?, "+
                                "  hours2 = ?, "+
                                "  hours3 = ?, "+
                                "  hours4 = ?, "+
                                "  hours5 = ?, "+
                                "  hours6 = ?, "+
                                "  hours7 = ?  "+
                                " WHERE id = ?";
            }
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setInt(1, schedule.getEmp().getId());
                st.setDate(2, UtilToSQL(schedule.getDateFrom()));
                for (int i = 1; i < 8; i++) {
                    st.setInt(i+2,schedule.getHours(i));
                }
                st.setInt(10, schedule.getId());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при фиксации изменений!");
            System.out.println(e.getMessage());
        }
    }


    public void delete(Object obj){

        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        if(obj instanceof Emp){
            id = ((Emp) obj).getId();
            sql = "DELETE FROM DDT_EMP WHERE id = ?";
        }else if(obj instanceof Leave) {
            id = ((Leave) obj).getId();
            sql = "DELETE FROM DDT_LEAVE WHERE id = ?";
        }else if(obj instanceof Ill){
            id = ((Ill) obj).getId();
            sql = "DELETE FROM DDT_ILL WHERE id = ?";
        }else if(obj instanceof Schedule){
            id = ((Schedule) obj).getId();
            sql = "DELETE FROM DDT_SCHEDULE WHERE id = ?";
        }else
            return;

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int getNextId() {
        int id = 0;
        if (conn == null) {
            System.err.println("No connect!!");
            return 0;
        }
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT DDT_ID_SEQ.NEXTVAL");
            while (rs.next()) {
                id = rs.getInt(1);
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
            ResultSet rs = st.executeQuery("SELECT id,emp_id,tleave_id,d_from,d_to FROM DDT_LEAVE");
            while (rs.next()) {
                Leave leave = new Leave();
                leave.setId(rs.getInt("id"));
                leave.setTleave_id(rs.getString("tleave_id"));
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


    public ArrayList<Ill> getIlls() {
        ArrayList<Ill> ills = new ArrayList<Ill>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return ills;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id,emp_id,till_id, d_from,d_to FROM DDT_ILL");
            while (rs.next()) {
                Ill ill = new Ill();
                ill.setId(rs.getInt("id"));
                ill.setTill_id(rs.getString("till_id"));
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


        /**
     * Оработать emplist.txt и вставить из
     * него работников в таблицу DDT_EMP
     * Таблицу очищаем перед этим
     */
    public void srvInsertEmpList() {
        if (conn == null) {
            System.err.println("No connect!!");
            return;
        }
        ArrayList<Emp> emps = getEmpsFromTxt();
        if (emps.size() == 0) {
            System.err.println("No employers for INSERT!!");
            return;
        }

        System.out.println("we will insert " + emps.size() + " employers");


        ;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM DDT_ILL");
            st.executeUpdate("DELETE FROM DDT_LEAVE");
            st.executeUpdate("DELETE FROM DDT_EMP");
            st.close();
            System.out.println("DELETE all employers");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Emp emp : emps) {
            System.out.println("Prepare for inserting " + emp);
            save(emp);
        }
    }

    public ArrayList<Schedule> getSchedules() {

        ArrayList<Schedule> schedules = new ArrayList<Schedule>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return schedules;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT " +
                            "id,emp_id,d_from,week_day," +
                            "hours1," +
                            "hours2," +
                            "hours3," +
                            "hours4," +
                            "hours5," +
                            "hours6," +
                            "hours7 " +
                            "FROM DDT_SCHEDULE");
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(rs.getInt("id"));
                schedule.setDateFrom(rs.getDate("d_from"));
                Emp emp = getEmpById(new Integer(rs.getInt("emp_id")));
                schedule.setEmp(emp);
                for (int i = 1; i < 8; i++) {
                    schedule.setHours(i,rs.getInt("hours"+i));
                };
                schedules.add(schedule);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return schedules;
    }
}
