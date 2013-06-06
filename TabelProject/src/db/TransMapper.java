package db;

import model.Appoint;
import model.Emp;
import model.Leave;
import model.Trans;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class TransMapper implements IMapper<Trans> {
    private final Connection conn;

    public TransMapper(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Trans get(Integer objectId) {
        Trans trans = null;

        if (conn == null) {
            System.err.println("No connect!!");
            return trans;
        }

        try {
            final String sql =
                    "SELECT id,emp_id,d_from,appoint_id,wage_rate FROM DDT_TRANS" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                trans = new Trans();
                trans.setId(rs.getInt("id"));
                trans.setDateFrom(rs.getDate("d_from"));
                trans.setWageRate(rs.getDouble("wage_rate"));
                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                trans.setEmp(emp);
                Appoint appoint = (Appoint) DBSrv.getInstance().get(rs.getInt("appoint_id"), Appoint.class);
                trans.setAppoint(appoint);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trans;
    }

    @Override
    public void put(Trans object) {

        Trans trans = object;

        String sql;

        if (trans.getId() == null){

            trans.setId(DBSrv.getInstance().getNextId());
            sql = "INSERT INTO DDT_TRANS(EMP_ID, D_FROM, APPOINT_ID, WAGE_RATE ,ID) " +
                    "VALUES(?,?,?,?,?)";
        }else{
            sql =
                    "UPDATE DDT_TRANS SET " +
                            " emp_id = ?, " +
                            " d_from = ?, " +
                            " appoint_id = ?, " +
                            " wage_rate = ?  " +
                            " WHERE id = ?";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, trans.getEmp().getId());
            st.setDate(2, DBSrv.UtilToSQL(trans.getDateFrom()));
            st.setInt(3, trans.getAppoint().getId());
            st.setDouble(4, trans.getWageRate());
            st.setInt(5, trans.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Trans object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        id = object.getId();
        sql = "DELETE FROM DDT_TRANS WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList getList() {

        ArrayList<Trans> transes = new ArrayList<Trans>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return transes;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id,emp_id,d_from,appoint_id,wage_rate FROM DDT_TRANS");
            while (rs.next()) {
                Trans trans = new Trans();
                trans.setId(rs.getInt("id"));
                trans.setDateFrom(rs.getDate("d_from"));
                trans.setWageRate(rs.getDouble("wage_rate"));
                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                trans.setEmp(emp);
                Appoint appoint = (Appoint) DBSrv.getInstance().get(rs.getInt("appoint_id"), Appoint.class);
                trans.setAppoint(appoint);

                transes.add(trans);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transes;
    }

    @Override
    public ArrayList<Trans> getList(Object owner) {

        ArrayList<Trans> transes = new ArrayList<Trans>(100);

        Emp empOwner = (Emp) owner;

        if (owner == null){
            return transes;
        }

        if (conn == null) {
            System.err.println("No connect!!");
            return transes;
        }
        String sql = "" +
                "SELECT id,emp_id,d_from,appoint_id,wage_rate FROM DDT_TRANS\n" +
                "WHERE emp_id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, empOwner.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Trans trans = new Trans();
                trans.setId(rs.getInt("id"));
                trans.setDateFrom(rs.getDate("d_from"));
                trans.setWageRate(rs.getDouble("wage_rate"));
                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                trans.setEmp(emp);
                Appoint appoint = (Appoint) DBSrv.getInstance().get(rs.getInt("appoint_id"), Appoint.class);
                trans.setAppoint(appoint);

                transes.add(trans);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transes;
    }
}
