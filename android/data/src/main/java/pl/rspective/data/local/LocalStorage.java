package pl.rspective.data.local;

public interface LocalStorage<T> {

    void save(T survey);

    T load();

}
