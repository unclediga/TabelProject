package db;

import model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class TabelDayMapper implements IMapper<TabelDay> {

    private final Connection conn;
    private final String[] fields = new String[]{
            "id","tabel_id","day_num","day_type","day_hours"
    };


    private String INSERT() {
        StringBuffer result = new StringBuffer("INSERT INTO DDT_TABEL_DAY(");
        for (int i = 1; i < fields.length; i++) {
            result.append( fields[i] );
            result.append(", ");
        }

        result.append("ID) VALUES(");
        for (int i = 1; i < fields.length; i++) {
            result.append("?,");
        }
        result.append("?)");
        return result.toString();
    }

    private String UPDATE() {
        StringBuffer result = new StringBuffer("UPDATE DDT_TABEL_DAY SET\n");
        for (int i = 1; i < fields.length; i++) {
            result.append( fields[i] );
            result.append(" = ?,\n");
        }
        result.append(" WHERE id = ?");
        return result.toString();
    }

    private String SELECT() {
        StringBuffer result = new StringBuffer("SELECT ");
        for (int i = 1; i < fields.length; i++) {
            result.append( fields[i] );
            result.append(", ");
        }
        result.append("ID FROM DDT_TABEL_DAY");
        return result.toString();
    }

    public TabelDayMapper(Connection conn) {
        this.conn = conn;
    }

    private void fillObject(ResultSet rs,TabelDay tabelDay) throws SQLException {

        tabelDay.setId(rs.getInt("id"));
        Tabel tabel = DBSrv.getInstance().getTabelById(new Integer(rs.getInt("tabel_id")));
        tabelDay.setTabel(tabel);
        tabelDay.setDayNum(rs.getInt("day_num"));
        tabelDay.setDayType(rs.getString("day_type"));
        tabelDay.setDayHours(rs.getDouble("day_hours"));
    }

    @Override
    public TabelDay get(Integer objectId) {

        TabelDay tabelDay = null;
        if (conn == null) {
            System.err.println("No connect!!");
            return tabelDay;
        }

        try {
            PreparedStatement st = conn.prepareStatement(SELECT() + " WHERE id = ?");
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                tabelDay = new TabelDay();
                fillObject(rs,tabelDay);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabelDay;



    }

    @Override
    public void put(TabelDay object) {
        TabelDay tabelDay = object;

        String sql;

        if (tabelDay.getId() == null){
                        tabelDay.setId(DBSrv.getInstance().getNextDayId());
            sql = INSERT();
        }else{
            sql = UPDATE();
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, tabelDay.getTabel().getId());
            st.setInt(2, tabelDay.getDayNum());
            st.setString(3, tabelDay.getDayType());
            st.setDouble(4, tabelDay.getDayHours());
            st.setInt(5, tabelDay.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(TabelDay object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        id = object.getId();
        sql = "DELETE FROM DDT_TABEL_DAY WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<TabelDay> getList() {

        ArrayList<TabelDay> tabelDays = new ArrayList<TabelDay>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return tabelDays;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT());
            while (rs.next()) {
                TabelDay  tabelDay = new TabelDay();
                fillObject(rs,tabelDay);
                tabelDays.add(tabelDay);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabelDays;
    }

    @Override
    public ArrayList<TabelDay> getList(Object owner) {
        return getList();
    }

    public static void main(String[] args) {
        TabelDayMapper tm = new TabelDayMapper(null);
        System.out.println(tm.SELECT());
        System.out.println(tm.INSERT());
        System.out.println(tm.UPDATE());
    }
}
