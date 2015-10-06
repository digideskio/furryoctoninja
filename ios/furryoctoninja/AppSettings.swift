//
//  globalData.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 21/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//
import Locksmith

struct AppSettings {
    static var apiURL = "http://furryoctoninja.apphb.com"
    static var clientId = "p35iw0R6RO1730BSK432qswrZldwY0jR"
    static var currentUser = ""
    
    static func token_header() -> [String: String]{
        let (dictionary, error) = Locksmith.loadDataForUserAccount(AppSettings.currentUser)
        let token:String = (dictionary?["Token"] as? String)!
        return [ "Authorization": "Token " + AppSettings.clientId + ":" + token ]        
    }
    
    static func tokenNotAvailable() -> Bool{
        let (dictionary, error) = Locksmith.loadDataForUserAccount(AppSettings.currentUser)
        return error != nil
    }
    
    static func logout() {
        Locksmith.deleteDataForUserAccount(AppSettings.currentUser)
    }
    
    static func logo() -> UIImageView {
        let imageView = UIImageView(frame: CGRectMake(0, 0, 0, 40))
        imageView.image = Common.imageWithImage(UIImage(named: "logo_v3")!, scaledToSize: CGSizeMake(120,40))
        return imageView
    }
}

