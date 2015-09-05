//
//  ServiceData.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 28/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//
import SwiftyJSON

class ServiceData{
    static var currentSurvey: Survey = Survey()
    static var currentQuestionRow: Int = 0
    
    static var completed = currentSurvey.completedByUser
    
    static func markAnswer(answerCheckId: Int) -> (){
        var answers = self.currentSurvey.questions![self.currentQuestionRow].answers!
        for row in 0..<(answers.count) {
            if answers[row].id == answerCheckId {
                self.currentSurvey.questions![self.currentQuestionRow].answers![row].checked = true
            }else{
                self.currentSurvey.questions![self.currentQuestionRow].answers![row].checked = false
            }
        }
    }
    
    static func questionAnswered(questionIndex: Int) -> Bool{
        var answers = self.currentSurvey.questions![questionIndex].answers!
        for row in 0..<(answers.count) {
            if answers[row].checked == true {
                return true
            }
        }
        return false
    }
    
    static func allChecked() -> Bool {
        for row in 0..<(self.currentSurvey.questions!.count) {
            if self.questionAnswered(row) == false{
                return false
            }
        }
        return true
    }
    
    static func userAnswers() -> [String: AnyObject]{
        let currentTimestamp = Int(NSDate().timeIntervalSince1970)
        let id = currentSurvey.id
        
        let answers: Array<[String:Int]> = {
            var foundAnswers: Array<[String:Int]> = []
            for question in self.currentSurvey.questions! {
                for answer in question.answers!{
                    if answer.checked == true {
                        foundAnswers.append(QuestionAnswer(question.id, answer.id))
                    }
                }
            }
            return foundAnswers
        }()
        
        return SurveyAnswerBody(currentTimestamp, id, answers)
    }
    
}
