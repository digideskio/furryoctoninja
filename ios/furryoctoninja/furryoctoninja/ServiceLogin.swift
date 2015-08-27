//
//  serviceLogin.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 21/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Alamofire
import SwiftyJSON
import Locksmith

class ServiceLogin{
    let authURL = AppSettings.apiURL + "/api/auth/login"
    var logged = false
    var token = ""
    var expiration = ""
    var errorDescription = "Service unavailable"
    
    
    func loginBody(username:String, password:String) -> Dictionary<String, String> {
        return  [
            "login" : username,
            "password" : password,
            "clientId" : AppSettings.clientId
        ]
    }
    
    func login(username:String, password:String, callback: (result:Bool) -> ()){
            AppSettings.currentUser = username
            Alamofire.request(.POST, authURL, parameters: loginBody(username, password:password))
            .responseJSON { _, _, JSON, error in
                debugPrint(JSON)
                if JSON != nil {
                    self.parseJson(JSON)
                    callback(result: self.logged)
                }else{
                    self.errorDescription = error!.localizedDescription
                }
            }
    }
    
    func parseJson(data:AnyObject?){
        let json = JSON(data!)
        
        if let token = json["Token"].string {
            Locksmith.saveData(["Token": token], forUserAccount: AppSettings.currentUser)
            
            self.token = token
            self.logged = true
        } else {
            self.checkError(json)
        }
        
        if let expiration = json["Expiration"].string {
            self.expiration = expiration
            Locksmith.saveData(["Expiration": expiration], forUserAccount: AppSettings.currentUser)
        }
        

    }
    
    func checkError(json:JSON){
        if let error = json["OverallErrors"].array {
            self.errorDescription = error[0].string!
        }
    }
    

    
}

