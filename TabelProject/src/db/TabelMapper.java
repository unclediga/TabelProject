package db;

import model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class TabelMapper implements IMapper<Tabel> {

    private final Connection conn;
    private final String[] fields = new String[]{
            "id","tyear","tmonth","row_num","emp_id","trans_id"
    };


    private String INSERT() {
        StringBuffer result = new StringBuffer("INSERT INTO DDT_TABEL(");
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
        StringBuffer result = new StringBuffer("UPDATE DDT_TABEL SET\n");
        for (int i = 1; i < fields.length-1; i++) {
            result.append( fields[i] );
            result.append(" = ?,\n");
        }
        result.append( fields[fields.length-1] );
        result.append(" = ?\n");
        result.append(" WHERE id = ?");
        return result.toString();
    }

    private String SELECT() {
        StringBuffer result = new StringBuffer("SELECT ");
        for (int i = 1; i < fields.length; i++) {
            result.append( fields[i] );
            result.append(", ");
        }
        result.append("ID FROM DDT_TABEL");
        return result.toString();
    }

    public TabelMapper(Connection conn) {
        this.conn = conn;
    }

    private void fillObject(ResultSet rs,Tabel tabel) throws SQLException {

        tabel.setId(rs.getInt("id"));
        tabel.setTyear(rs.getInt("tyear"));
        tabel.setTmonth(rs.getInt("tmonth"));
        tabel.setRowNum(rs.getInt("row_num"));
        Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
        tabel.setEmp(emp);
        Trans trans =  DBSrv.getInstance().getTransById(rs.getInt("trans_id"));
        tabel.setTrans(trans);
    }

    @Override
    public Tabel get(Integer objectId) {

        Tabel tabel = null;
        if (conn == null) {
            System.err.println("No connect!!");
            return tabel;
        }

        try {
            PreparedStatement st = conn.prepareStatement(SELECT() + " WHERE id = ?");
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                tabel = new Tabel();
                fillObject(rs,tabel);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabel;



    }

    @Override
    public void put(Tabel object) {
        Tabel tabel = object;

        String sql;

        if (tabel.getId() == null){
                        tabel.setId(DBSrv.getInstance().getNextId());
            sql = INSERT();
        }else{
            sql = UPDATE();
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,tabel.getTyear());
            st.setInt(2, tabel.getTmonth());
            st.setInt(3, tabel.getRowNum());
            st.setInt(4, tabel.getEmp().getId());
            st.setInt(5, tabel.getTrans().getId());
            st.setInt(6, tabel.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Tabel object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

        id = object.getId();
        sql = "DELETE FROM DDT_TABEL WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Tabel> getList() {

        ArrayList<Tabel> tabels = new ArrayList<Tabel>(100);

        if (conn == null) {
            System.err.println("No connect!!");
            return tabels;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT());
            while (rs.next()) {
                Tabel tabel = new Tabel();
                fillObject(rs,tabel);
                tabels.add(tabel);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabels;
    }

    @Override
    public ArrayList<Tabel> getList(Object owner) {
        return getList();
    }

//    public static void main(String[] args) {
//        TabelMapper tm = new TabelMapper(null);
//        System.out.println(tm.SELECT());
//        System.out.println(tm.INSERT());
//        System.out.println(tm.UPDATE());
//    }
}
