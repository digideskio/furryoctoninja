package pl.rspective.mckinsey.domain.interactor;

import rx.Subscriber;

public interface UseCase<T> {

    void execute(Subscriber<T> subscriber);

}
