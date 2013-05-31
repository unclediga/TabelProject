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
                            "id,lname,fname,mname,d_hire,d_fire,appoint_id " +
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
                Appoint appoint = (Appoint) DBSrv.getInstance().get(rs.getInt("appoint_id"), Appoint.class);
                emp.setAppoint(appoint);
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
            sql = "INSERT INTO DDT_EMP(lname,fname,mname,d_hire,d_fire,id,appoint_id) " +
                    "VALUES(?,?,?,?,?,?,?)";
        }else{
            sql =
                    "UPDATE DDT_EMP SET " +
                            " lname = ?, " +
                            " fname = ?, " +
                            " mname = ?, " +
                            " d_hire = ?, " +
                            " d_fire = ?, " +
                            " appoint_id = ? " +
                            " WHERE id = ?";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, emp.getLastName());
            st.setString(2, emp.getFirstName());
            st.setString(3, emp.getMiddleName());
            st.setDate(4, DBSrv.UtilToSQL(emp.getHireDate()));
            st.setDate(5, DBSrv.UtilToSQL(emp.getFireDate()));
            Appoint appoint = emp.getAppoint();
            st.setInt(6, appoint == null ? null : emp.getAppoint().getId());
            st.setInt(7, emp.getId());
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
            ResultSet rs = st.executeQuery("SELECT id,lname,fname,mname,d_hire,d_fire,appoint_id FROM DDT_EMP");
            while (rs.next()) {
                Emp emp = new Emp();
                emp.setId(rs.getInt("id"));

                emp.setLastName(rs.getString("lname"));
                emp.setFirstName(rs.getString("fname"));
                emp.setMiddleName(rs.getString("mname"));
                emp.setHireDate(rs.getDate("d_hire"));
                emp.setFireDate(rs.getDate("d_fire"));
                Appoint appoint = (Appoint) DBSrv.getInstance().get(rs.getInt("appoint_id"), Appoint.class);
                emp.setAppoint(appoint);
                emps.add(emp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return emps;

    }
}
