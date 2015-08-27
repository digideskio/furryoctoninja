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
            (result:Survey) -> () in
            if (result.description == "") {
                //perform loading survey
            }
            else {
                //error
            }
        })
        
    }
    override func viewDidAppear(animated: Bool) {
        println("Loaded View")
    }
    
    //@IBOutlet weak var loader: UIActivityIndicatorView!
    
    
    // FOR TESTS ONLY TB REMOVED
    
    
    // Tests END

}