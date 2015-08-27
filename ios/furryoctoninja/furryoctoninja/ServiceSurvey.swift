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
    var errorDescription = "Service unavailable"
    
    func loadSurvey(callback: (result: Survey, error: String) -> ()){
        let (dictionary, error) = Locksmith.loadDataForUserAccount(AppSettings.currentUser)
        let token:String = (dictionary?["Token"] as? String)!
        let headers = [
            "Authorization": "Token " + AppSettings.clientId + ":" + token
        ]
        Alamofire.request(.GET, surveyURL, headers: headers)
            .responseJSON { _, _, JSON, error in
                debugPrint(JSON)
                if JSON != nil {
                    var survey = self.parseSurveyJson(JSON)
                    callback(result: survey, error: "")
                }else{
                    self.errorDescription = error!.localizedDescription
                    callback(result: Survey(), error: self.errorDescription)
                }
        }

    }
    
    private func parseSurveyJson(data:AnyObject?) -> Survey{
        let json = JSON(data!)
        var survey = Survey()
        
        if let id = json["Id"].int,
            let title = json["Ttles"].string,
            let description = json["Description"].string,
            let createdDate = json["CreatedDate"].int,
            let completedByUser = json["CompletedByUser"].bool,
            let questions = json["Questions"].array{
                var questions_arr: [Question] = []
                for question in questions{
                    var question_it = Question()
                    if let q_id = question["Id"].int,
                        let q_text = question["Text"].string,
                        let answers = question["Answers"].array{
                            question_it = (Question(id:q_id,text: q_text, answers: []))
                            for answer in answers {
                                if let a_id = answer["Id"].int,
                                    let a_text = answer["Text"].string{
                                        question_it.answers?.append(Answer(id:a_id, text:a_text))
                                }
                            }
                        questions_arr.append(question_it)
                    }
                }
                survey = Survey(id: id, title:title, description: description, createdDate: createdDate, questions: questions_arr, completedByUser: completedByUser)
        }else{
            debugPrint("JSON Parsing error")
            self.errorDescription = "Data cannot be loaded"
        }
        
       return survey
    }
    
    
        
}

