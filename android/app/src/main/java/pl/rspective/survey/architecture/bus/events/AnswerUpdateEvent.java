package pl.rspective.survey.architecture.bus.events;

import pl.rspective.data.entity.Question;

public class AnswerUpdateEvent {

    private Question question;

    public AnswerUpdateEvent(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
