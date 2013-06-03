package db;

import model.Appoint;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class AppointMapper implements IMapper<Appoint> {
    private final Connection conn;

    public AppointMapper(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Appoint get(Integer objectId) {
        Appoint appoint = null;

        if (conn == null) {
            System.err.println("No connect!!");
            return appoint;
        }

        try {
            final String sql =
                    "SELECT id,name,socr FROM DDT_APPOINT" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                appoint = new Appoint();
                appoint.setId(rs.getInt("id"));
                appoint.setName(rs.getString("name"));
                appoint.setSocr(rs.getString("socr"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appoint;
    }

    @Override
    public void put(Appoint object) {

        Appoint appoint = object;

        String sql;

        if (appoint.getId() == null){

            appoint.setId(DBSrv.getInstance().getNextId());
            sql = "INSERT INTO DDT_APPOINT(NAME,SOCR ,ID) " +
                    "VALUES(?,?,?)";
        }else{
            sql =
                    "UPDATE DDT_APPOINT SET " +
                            " name = ?, " +
                            " socr = ? " +
                            " WHERE id = ?";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1,appoint.getName());
            st.setString(2,appoint.getSocr());
            st.setInt(3, appoint.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Appoint object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        id = object.getId();
        sql = "DELETE FROM DDT_APPOINT WHERE id = ?";

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

        ArrayList<Appoint> appoints = new ArrayList<Appoint>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return appoints;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id,name,socr FROM DDT_APPOINT");
            while (rs.next()) {
                Appoint appoint = new Appoint();
                appoint.setId(rs.getInt("id"));
                appoint.setId(rs.getInt("id"));
                appoint.setName(rs.getString("name"));
                appoint.setSocr(rs.getString("socr"));

                appoints.add(appoint);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appoints;
    }
}
