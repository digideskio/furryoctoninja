package pl.rspective.data.local;

import pl.rspective.data.local.model.StorageType;

public interface SurveyLocalStorage<T> {

    void save(StorageType type, T data);

    void clear(StorageType type);

    T load(StorageType type);

}
