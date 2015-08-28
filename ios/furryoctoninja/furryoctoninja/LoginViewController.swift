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
    
    @IBOutlet weak var loader: UIActivityIndicatorView!
    
    
    @IBOutlet weak var submitButton: UIButton!
    
    @IBAction func submit(sender: UIButton) {
        self.errorMsg.text = ""
        self.loader.startAnimating()
        self.submitButton.enabled = false;
        let serviceLogin = ServiceLogin()
        serviceLogin.login(self.username.text, password: self.password.text, callback: {
            (result: Bool) -> () in
            
                if result {
                    self.performSegueWithIdentifier("surveyView", sender: self)
                }
                else {
                    self.errorMsg.text = serviceLogin.errorDescription
                    self.loader.stopAnimating()
                    self.submitButton.enabled = true;
                }
            })
        
    }
    
    
    // FOR TESTS ONLY TB REMOVED

    @IBAction func Cred(sender: UIButton) {
            self.username.text = "shem"
            self.password.text = "furryninja2014"
        
    }
    
    @IBAction func CredNotSubmitted(sender: UIButton) {
        self.username.text = "wi"
        self.password.text = "furryninja2014"
        
    }
    
    // Tests END
}

