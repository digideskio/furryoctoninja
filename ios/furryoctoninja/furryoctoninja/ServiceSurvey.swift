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
    let surveyWithAnswersURL = AppSettings.apiURL + "/api/survey/results"
    var errorDescription = ""
    var surveyFilled = false;
    
    func loadSurvey(surveyFilled:Bool = false, callback: (result: Survey, error_i: String) -> ()){
        self.surveyFilled = surveyFilled
        let url =  surveyFilled ? self.surveyWithAnswersURL : self.surveyURL
        Alamofire.request(.GET, url, headers: AppSettings.token_header())
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
    
    func sendSurvey(answers: [String: AnyObject], callback: (result: Bool, error_i: String) -> ()){
        Alamofire.request(.POST, self.surveyURL, headers: AppSettings.token_header(), parameters: answers, encoding: .JSON)
            .responseJSON{ request, response, JSON, error in
                debugPrintln("Req")
                debugPrintln(NSString(data: request.HTTPBody!, encoding:NSUTF8StringEncoding)!)
                debugPrintln("Res")
                debugPrintln(response)
                debugPrintln("Req")
                debugPrintln(JSON)
                if response != nil {
                    let success:Bool = ( response?.statusCode == 200 )
                    let err = (error != nil) ? (error?.description)! : ""
                    callback(result: success, error_i: err)
                }else{
                    self.errorDescription = error!.localizedDescription
                    callback(result: false, error_i: self.errorDescription)
                }
        }
        
    }
    
    private func parseSurveyJson(data:AnyObject?) -> (Survey, error:String){
        let json = JSON(data!)
        var survey = Survey()
        var surveyId = 0
        var createdDate = 0
        //Those are the differences between survey to fill and filled
        if surveyFilled {
            if let id = json["SurveyId"].int{
                    surveyId = id
            }else{
                debugPrintln("JSON Parsing error - SurveyId")
            }

        }else{
            if let id = json["Id"].int,
                let date = json["CreatedDate"].int{
                    surveyId = id
                    createdDate = date
            }else{
                debugPrintln("JSON Parsing error - Id or CreationDate")
            }
        }
        //difference end
        if
            let title = json["Title"].string,
            let description = json["Description"].string,
            let completedByUser = json["CompletedByUser"].bool,
            let questions = json["Questions"].array
        {
                survey = Survey(id: surveyId, title:title, description: description, createdDate: createdDate, questions: parseQuestionsJson(questions), completedByUser: completedByUser)
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
                 //Those are the differences between survey to fill and filled
                if self.surveyFilled{
                    if let isUserChoice = answerJSON["IsUserChoice"].bool,
                        let count = answerJSON["Count"].int
                    {
                        answers.append(Answer(id:id, text:text, isUserChoice:isUserChoice, count:count))
                    }else {
                        debugPrintln("JSON Parsing error - answer for filled survey")
                        debugPrintln(answersJSON)
                    }
                }else {
                    answers.append(Answer(id:id, text:text))
                }
                //differencess end
            } else {
                debugPrintln("JSON Parsing error - answer")
                debugPrintln(answersJSON)
            }
            
            
        }
        return answers
    }
    
}
