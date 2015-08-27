//
//  SurveyViewController.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 27/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import UIKit

class SurveyViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        println("Loaded View")
        let serviceSurvey = ServiceSurvey()
        serviceSurvey.loadSurvey({
            (result:Survey, error:String) -> () in
            if (error == "") {
                //perform loading survey
            }
            else {
                //error_msg.text = error
            }
        })
        
    }
    

}