package db;

import model.Emp;
import model.ModelDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class EmpDataSource implements ModelDataSource {

    private ArrayList<Emp> al;
    private final DBSrv db;


    public EmpDataSource(DBSrv dbsrv) {

        db = dbsrv;
        al = new ArrayList<Emp>(100);

//        al.add(new Emp(1, "Иванов", "Иван", "Иванович", new GregorianCalendar(2000, 01, 20).getTime(), null));
//        al.add(new Emp(2, "Петров", "Петр", "Петрович", new GregorianCalendar(2002, 02, 20).getTime(), null));
//        al.add(new Emp(3, "Сидоров", "Сидор", "Сидорович", new GregorianCalendar(2004, 03, 20).getTime(), null));
//        al.add(new Emp(4, "Васильев", "Василий", "Васильевич", new GregorianCalendar(2008, 10, 20).getTime(), null));

    }


    @Override
    public ArrayList getData(boolean refresh) {
        if(refresh){
            al = db.getEmps();
        }
        return al;
    }

    @Override
    public String[] getColumnNames() {
        return         new String[]{
                "ID", "Фамилия", "Имя", "Отчество", "ДатаПриема","ДатаУвольнения"
        };

    }

    @Override
    public Class[] getColumnClasses() {
        return
                new Class[]{
                        Integer.class, String.class, String.class, String.class, Date.class, Date.class};
    }

    @Override
    public void saveChanges(Map<Object, String> empMap) {

        for(Map.Entry e : empMap.entrySet()){
            String status = (String) e.getValue();
            if(status.equals("I")){
                db.save(e.getKey());
            }else if(status.equals("U")){
                db.save(e.getKey());
            }else if(status.equals("D")){
                db.save(e.getKey());
            }
        }
    }
}

