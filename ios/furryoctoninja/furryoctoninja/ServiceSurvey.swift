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
    
    let headers = [
        "Authorization": "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==",
        "Content-Type": "application/x-www-form-urlencoded"
    ]
    
    func loadSurvey(callback: (result: Survey) -> ()){
        let (dictionary, error) = Locksmith.loadDataForUserAccount(AppSettings.currentUser)
        let token:String = (dictionary?["Token"] as? String)!
        let headers = [
            "Authorization": "Token " + AppSettings.clientId + ":" + token
        ]

        
        Alamofire.request(.GET, surveyURL, headers: headers)
            .responseJSON { _, _, JSON, error in
                debugPrint(JSON)
                if JSON != nil {
                    self.parseJson(JSON)
                    callback(result: Survey())
                }else{
                    self.errorDescription = error!.localizedDescription
                }
        }

    }
    
    func parseJson(data:AnyObject?){
        let json = JSON(data!)
        
        if let token = json["Token"].string {
            Locksmith.saveData(["Token": token], forUserAccount: AppSettings.currentUser)
            
        } else {
            print("serialization false !!!")
            //self.checkError(json)
        }
        
    }
    
    func checkError(json:JSON){
        if let error = json["OverallErrors"].array {
            self.errorDescription = error[0].string!
        }
    }
    
        
}

