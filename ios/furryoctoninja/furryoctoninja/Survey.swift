//
//  Survey.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 27/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation

struct Survey {
    var id: Int = -1
    var title: String = ""
    var description: String = ""
    var createdDate: Int = 0
    var questions: [Question]?
    var completedByUser: Bool = false

}

struct Question {
    var id: Int = 0
    var text: String = ""
    var answers: [Answer]?
}

struct Answer {
    var id: Int = 0
    var text: String = ""
    var isUserChoice: Bool? = false
    var count: Int = 0
    
    init(id: Int, text: String){
        self.id = id
        self.text = text
    }
    
    init(id: Int, text: String, isUserChoice: Bool, count: Int){
        self.id = id
        self.text = text
        self.isUserChoice = isUserChoice
        self.count = count
    }
}

var QuestionAnswer = {
    (questionId:Int, answerId:Int) in
        [
            "QuestionId": questionId,
            "AnswerId": answerId
        ]
}

var SurveyAnswerBody =  {
    (currentTimestamp:Int, id:Int, answers: Array<[String:Int]>) -> [String: AnyObject] in
        [
            "Modified": currentTimestamp,
            "Id": id,
            "Answers": answers
        ]
}