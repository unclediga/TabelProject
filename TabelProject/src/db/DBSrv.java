package db;

import model.*;

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

    private static DBSrv dbSrv;
    private Connection conn = null;
    private Map<Class,IMapper> mappers;
    // Параметр инициализации hashMap changes в модулях
    public static int INIT_CHANGES_COUNT;

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
        INIT_CHANGES_COUNT = Integer.parseInt(prop.getProperty("model.init_changes_count"));

        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(connString, connUser, connPassword);
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            System.err.println("Not found JDBC Driver");
        } catch (SQLException e) {
            System.err.println("SQL Exception. See log!");
        }

        // создаём служебные класс для каждого типа хранимых классов
        mappers = new HashMap<Class, IMapper>(10);
        mappers.put(Emp.class, new EmpMapper(conn));
        mappers.put(Leave.class, new LeaveMapper(conn));
        mappers.put(Ill.class, new IllMapper(conn));
        mappers.put(Schedule.class, new SchedulelMapper(conn));
        mappers.put(Appoint.class, new AppointMapper(conn));

    }

    public static java.sql.Date UtilToSQL(java.util.Date d) {
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

    public Emp getEmpById(Integer id) {
        return new EmpMapper(conn).get(id);
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

    public Object get(Integer objectId,Class objectClass){
        IMapper mapper = mappers.get(objectClass);
        return mapper.get(objectId);
    }

    public void put(Object object){
        IMapper mapper = mappers.get(object.getClass());
        mapper.put(object);
    }

    public void remove(Object object){
        IMapper mapper = mappers.get(object.getClass());
        mapper.remove(object);
    }

    public <T> ArrayList<T> getList(Class<T> cl){
        IMapper mapper = mappers.get(cl);
        return mapper.getList();
    }

    public static DBSrv getInstance() {
        if (dbSrv == null){
            dbSrv = new DBSrv();
        }
        return dbSrv;
    }

    public void putAll(Map<Object, String> changes) {

        for(Map.Entry e : changes.entrySet()){
            String status = (String) e.getValue();
            if(status.equals("D")){
                remove(e.getKey());
            }else {
                put(e.getKey());
            }
        }

    }

    public void clearBase(){
        if (conn == null) {
            System.err.println("No connect!!");
            return;
        }
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM DDT_ILL");
            st.executeUpdate("DELETE FROM DDT_LEAVE");
            st.executeUpdate("DELETE FROM DDT_SCHEDULE");
            st.executeUpdate("DELETE FROM DDT_TRANS");
            st.executeUpdate("DELETE FROM DDT_EMP");
            st.close();
            System.out.println("DELETE all employers");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void doCommit() throws Exception{
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("FAILED");
        }
    }
}
