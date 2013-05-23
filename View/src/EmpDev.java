/**
 *
 */
public class EmpDev {
    int id;
    String lname;
    String fname;
    String mname;

    public EmpDev(int id, String lname, String fname, String mname) {
        this.id = id;
        this.lname = lname;
        this.fname = fname;
        this.mname = mname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    @Override
    public String toString() {
        return lname+" "+fname+" "+mname;
    }
}
