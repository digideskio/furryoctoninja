package pl.rspective.data.local.observer;

public interface Observable<T> {

    void onUpdate(T data);

}
