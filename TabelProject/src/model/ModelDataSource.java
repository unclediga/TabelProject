package model;

import java.util.ArrayList;
import java.util.Map;

/**
 *  Источник данных для Модели таблицы
 */
public interface ModelDataSource {
    public ArrayList <Object>getData(boolean refresh);
    public String[] getColumnNames();
    public Class[] getColumnClasses();
    public void saveChanges(Map<Object,String> empMap);

}
