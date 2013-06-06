package db;

import model.Appoint;
import model.Emp;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class EmpMapper implements IMapper<Emp> {
    private final Connection conn;


    public EmpMapper(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Emp get(Integer objectId) {
        if (conn == null) {
            System.err.println("No connect!!");
            return null;
        }

        Emp emp = null;

        try {
            final String sql =
                    "SELECT " +
                            "id,lname,fname,mname,d_hire,d_fire " +
                    "FROM DDT_EMP" +
                    " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                emp = new Emp();
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

    @Override
    public void put(Emp object) {

        Emp emp = object;
        String sql;
        if (emp.getId() == null){

            emp.setId(DBSrv.getInstance().getNextId());
            sql = "INSERT INTO DDT_EMP(lname,fname,mname,d_hire,d_fire,id) " +
                    "VALUES(?,?,?,?,?,?)";
        }else{
            sql =
                    "UPDATE DDT_EMP SET " +
                            " lname = ?, " +
                            " fname = ?, " +
                            " mname = ?, " +
                            " d_hire = ?, " +
                            " d_fire = ?  " +
                            " WHERE id = ?";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, emp.getLastName());
            st.setString(2, emp.getFirstName());
            st.setString(3, emp.getMiddleName());
            st.setDate(4, DBSrv.UtilToSQL(emp.getHireDate()));
            st.setDate(5, DBSrv.UtilToSQL(emp.getFireDate()));
            st.setInt(6, emp.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Emp object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        id = object.getId();
        sql = "DELETE FROM DDT_EMP WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Emp> getList() {
        ArrayList<Emp> emps = new ArrayList<Emp>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return emps;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("" +
                    "SELECT e.id,e.lname,e.fname,e.mname,e.d_hire,e.d_fire " +
                    "FROM DDT_EMP AS e");
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

    @Override
    public ArrayList<Emp> getList(Object owner) {
        return new ArrayList<Emp>();
    }
}
