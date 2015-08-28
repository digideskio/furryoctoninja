//
//  ServiceSurvey.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 27/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import Locksmith
import SwiftyJSON
import Alamofire

class ServiceSurvey{
    let surveyURL = AppSettings.apiURL + "/api/survey"
    var errorDescription = ""
    
    func loadSurvey(callback: (result: Survey, error_i: String) -> ()){
        let (dictionary, error) = Locksmith.loadDataForUserAccount(AppSettings.currentUser)
        let token:String = (dictionary?["Token"] as? String)!
        let headers = [
            "Authorization": "Token " + AppSettings.clientId + ":" + token
        ]
        Alamofire.request(.GET, surveyURL, headers: headers)
            .responseJSON { _, _, JSON, error in
                debugPrint(JSON)
                if JSON != nil {
                    var (survey, errorDescription)  = self.parseSurveyJson(JSON)
                    callback(result: survey, error_i: errorDescription)
                }else{
                    self.errorDescription = error!.localizedDescription
                    callback(result: Survey(), error_i: self.errorDescription)
                }
        }

    }
    
    private func parseSurveyJson(data:AnyObject?) -> (Survey, error:String){
        let json = JSON(data!)
        var survey = Survey()
        
        if let id = json["Id"].int,
            let title = json["Title"].string,
            let description = json["Description"].string,
            let createdDate = json["CreatedDate"].int,
            let completedByUser = json["CompletedByUser"].bool,
            let questions = json["Questions"].array
        {
                survey = Survey(id: id, title:title, description: description, createdDate: createdDate, questions: parseQuestionsJson(questions), completedByUser: completedByUser)
        }else{
            debugPrintln("JSON Parsing error - main parameters")
            debugPrintln(json)
            self.errorDescription = "Data cannot be loaded"
        }
        
       return (survey, self.errorDescription)
    }

    private func parseQuestionsJson(questionsJSON:[JSON]) -> [Question]{
        var questions: [Question] = []
        for questionJSON in questionsJSON{
            if let id = questionJSON["Id"].int,
                let text = questionJSON["Text"].string,
                let answers = questionJSON["Answers"].array
            {
                    questions.append(Question(id:id,text: text, answers:parseAnswersJson(answers)))
            } else {
                debugPrintln("JSON Parsing error - question")
                debugPrintln(questionJSON)
            }
            
        }
        return questions
    }

    private func parseAnswersJson(answersJSON:[JSON]) -> [Answer]{
        var answers: [Answer] = []
        for answerJSON in answersJSON {
            if let id = answerJSON["Id"].int,
                let text = answerJSON["Text"].string
            {
                answers.append(Answer(id:id, text:text))
            } else {
                debugPrintln("JSON Parsing error - answer")
                debugPrintln(answersJSON)
            }
        }
        return answers

    }
}
