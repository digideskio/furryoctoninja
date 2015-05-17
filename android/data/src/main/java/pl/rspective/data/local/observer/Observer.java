package pl.rspective.data.local.observer;

public interface Observer<T> {

    void register(Observable<T> observable);

    void clear();

}
