package db;

import model.Emp;
import model.Ill;
import model.Leave;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class LeaveMapper implements IMapper<Leave> {
    private final Connection conn;

    public LeaveMapper(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Leave get(Integer objectId) {
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
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                leave = new Leave();
                leave.setId(rs.getInt("id"));
                leave.setDateFrom(rs.getDate("d_from"));
                leave.setDateTo(rs.getDate("d_to"));
                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                leave.setEmp(emp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leave;
    }

    @Override
    public void put(Leave object) {

        Leave leave = object;

        String sql;

        if (leave.getId() == null){

            leave.setId(DBSrv.getInstance().getNextId());
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
            st.setDate(3, DBSrv.UtilToSQL(leave.getDateFrom()));
            st.setDate(4, DBSrv.UtilToSQL(leave.getDateTo()));
            st.setInt(5, leave.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Leave object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        id = object.getId();
        sql = "DELETE FROM DDT_LEAVE WHERE id = ?";

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

                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                leave.setEmp(emp);

                leaves.add(leave);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

    @Override
    public ArrayList<Leave> getList(Object owner) {
        return new ArrayList<Leave>();
    }
}
