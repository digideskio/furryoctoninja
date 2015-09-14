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
//        self.navigationItem.backBarButtonItem?.title = ""
//        self.navigationItem.setHidesBackButton(true, animated: false)
        
        self.navigationController?.navigationBar.barTintColor = UIColor.whiteColor()

        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 0, height: 30))
        imageView.contentMode = .ScaleAspectFit
        imageView.image =  UIImage(named: "logo")
        self.navigationItem.titleView = imageView
    }
    
    override func viewDidAppear(animated: Bool){
        super.viewDidAppear(true)
        //self.navigationItem.setLeftBarButtonItem(nil, animated: false)
        //self.navigationItem.backBarButtonItem?.title = ""
        self.navigationItem.setHidesBackButton(true, animated: false)

        
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
        self.username.text = "h"
        self.password.text = "h"
    }    
    // Tests END
}

