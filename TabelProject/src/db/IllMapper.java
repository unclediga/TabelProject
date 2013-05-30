package db;

import model.Emp;
import model.Ill;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class IllMapper implements IMapper<Ill> {
    private final Connection conn;

    public IllMapper(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Ill get(Integer objectId){

        Ill ill = null;
        assert (conn == null);

        try {
            final String sql =
                    "SELECT id,emp_id,d_from,d_to FROM DDT_ILL" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ill = new Ill();
                ill.setId(rs.getInt("id"));
                ill.setDateFrom(rs.getDate("d_from"));
                ill.setDateTo(rs.getDate("d_to"));
                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                ill.setEmp(emp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ill;
    }

    @Override
    public void put(Ill object) {
        final String sql;
        Ill ill = (Ill) object;
        if (ill.getId() == null){

            ill.setId(DBSrv.getInstance().getNextId());
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
            st.setDate(3, DBSrv.UtilToSQL(ill.getDateFrom()));
            st.setDate(4, DBSrv.UtilToSQL(ill.getDateTo()));
            st.setInt(5, ill.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Ill object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

            id = object.getId();
            sql = "DELETE FROM DDT_ILL WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Ill> getList() {
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

                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                ill.setEmp(emp);

                ills.add(ill);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return ills;
    }
}
