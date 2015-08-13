//
//  ViewController.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 13/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBOutlet weak var username: UITextField!

    @IBOutlet weak var password: UITextField!
    

    @IBOutlet weak var errorMsg: UILabel!
    
    @IBAction func submit(sender: UIButton) {
        performSegueWithIdentifier("surveyView", sender: self)
    }
    
    
    func userTokenActive() -> Bool {
        return true;
    }
    
    
    

}

