package test;

import db.DBSrv;
import model.Emp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 */
public class TestUtils {

    /**
     * Читаем работников из текстового файла
     * @return
     */
    private static ArrayList<Emp> getEmpsFromTxt() {

        ArrayList<Emp> emps = new ArrayList<Emp>(100);
        Random rnd = new Random();

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("emplist.txt")));
            String str;
            int i = emps.size();
            while ((str = r.readLine()) != null) {
                StringTokenizer t = new StringTokenizer(str, ":");

                while (t.hasMoreTokens()) {

                    Calendar c = new GregorianCalendar(2000, 1, 1);
                    c.set(2000 + rnd.nextInt(13), 1 + rnd.nextInt(12), 1 + rnd.nextInt(29));
                    emps.add(new Emp(i, t.nextToken(), t.nextToken(), t.nextToken(), c.getTime(), new java.util.Date()));
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emps;
    }

    /**
     * Обработать emplist.txt и вставить из
     * него работников в таблицу DDT_EMP
     * Таблицу очищаем перед этим
     */
    public static void insertEmpList() {

        ArrayList<Emp> emps = getEmpsFromTxt();
        if (emps.size() == 0) {
            System.err.println("No employers for INSERT!!");
            return;
        }

        System.out.println("we will insert " + emps.size() + " employers");


        DBSrv.getInstance().clearBase();

        for (Emp emp : emps) {
            System.out.println("Prepare for inserting " + emp);
            DBSrv.getInstance().put(emp);
        }
    }

}
