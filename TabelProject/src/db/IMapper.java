package db;

import java.util.ArrayList;

/**
 *
 */
public interface IMapper<T> {

    public T get(Integer objectId);
    public void put(T object);
    public void remove(T object);
    public ArrayList<T> getList();

}
