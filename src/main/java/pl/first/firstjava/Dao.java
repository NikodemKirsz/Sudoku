package pl.first.firstjava;

public interface Dao<T> {
    T read();
    void write(T obj);
}
