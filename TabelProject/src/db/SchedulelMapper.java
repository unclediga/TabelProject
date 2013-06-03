package db;

import model.Emp;
import model.Ill;
import model.Leave;
import model.Schedule;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 */
public class SchedulelMapper implements IMapper<Schedule> {
    private final Connection conn;

    public SchedulelMapper(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Schedule get(Integer objectId){
        Schedule schedule = null;

        if (conn == null) {
            System.err.println("No connect!!");
            return schedule;
        }

        try {
            final String sql =
                    "SELECT id,emp_id,d_from,d_to FROM DDT_SCHEDULE" +
                            " WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, objectId.intValue());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                schedule = new Schedule();
                schedule.setId(rs.getInt("id"));
                schedule.setDateFrom(rs.getDate("d_from"));
                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
                schedule.setEmp(emp);
                for (int i = 1; i < 8; i++) {
                    schedule.setHours(i,rs.getInt("hours"+i));
                };
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;

    }

    @Override
    public void put(Schedule object) {
        final String sql;
        Schedule schedule = object;

        if (schedule.getId() == null) {

            schedule.setId(DBSrv.getInstance().getNextId());
            sql = "INSERT INTO DDT_SCHEDULE(" +
                    "  EMP_ID, " +
                    "  D_FROM, " +
                    "  hours1, " +
                    "  hours2, " +
                    "  hours3, " +
                    "  hours4, " +
                    "  hours5, " +
                    "  hours6, " +
                    "  hours7, " +
                    "  ID" +
                    ") " +
                    "VALUES(?,?,?, ?,?,?, ?,?,?, ?)";
        } else {
            sql =
                    "UPDATE DDT_SCHEDULE SET " +
                            "  EMP_ID = ?, " +
                            "  D_FROM = ?, " +
                            "  hours1 = ?, " +
                            "  hours2 = ?, " +
                            "  hours3 = ?, " +
                            "  hours4 = ?, " +
                            "  hours5 = ?, " +
                            "  hours6 = ?, " +
                            "  hours7 = ?  " +
                            " WHERE id = ?";
        }
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, schedule.getEmp().getId());
            st.setDate(2,DBSrv.UtilToSQL(schedule.getDateFrom()));
            for (int i = 1; i < 8; i++) {
                st.setInt(i + 2, schedule.getHours(i));
            }
            st.setInt(10, schedule.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Schedule object) {
        final int id;
        final String sql;

        if (conn == null) {
            System.err.println("No connect!!");
        }

            id = object.getId();
            sql = "DELETE FROM DDT_SCHEDULE WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Schedule> getList() {

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
                Emp emp = DBSrv.getInstance().getEmpById(new Integer(rs.getInt("emp_id")));
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
