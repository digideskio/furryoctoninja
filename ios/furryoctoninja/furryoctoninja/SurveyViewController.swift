//
//  SurveyViewController.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 27/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import UIKit

class SurveyViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    var survey:Survey = Survey()
    let serviceSurvey = ServiceSurvey()
    let textCellIdentifier = "TextCell"
    
    private func hideUnusedRows(){
        self.tableView.tableFooterView = UIView(frame: CGRect.zeroRect)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.titleUI.text = ""
        
        self.serviceSurvey.loadSurvey({
            (result:Survey, error_i:String) -> () in
                if (error_i == "") {
                    self.survey = result
                    ServiceData.currentSurvey = result
                    self.tableView.reloadData()
                    self.titleUI.text = self.survey.title
                    self.descriptionUI.text = self.survey.description
                }
                else {
                    self.titleUI.text = error_i
                }
        })
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.hideUnusedRows()
        self.sendSurveyButton.enabled = false
    }
    
    @IBAction func returned(segue: UIStoryboardSegue) {
        self.tableView.reloadData()
        if ServiceData.allChecked() == true {
           self.sendSurveyButton.enabled = true
        }
    }
    
    @IBOutlet weak var descriptionUI: UILabel!
    
    @IBOutlet weak var titleUI: UILabel!
    
    @IBOutlet var tableView: UITableView!
    
    @IBOutlet weak var sendSurveyButton: UIButton!
    
    @IBOutlet weak var loader: UIActivityIndicatorView!
    
    @IBAction func sendSurvey(sender: AnyObject) {
        self.loader.startAnimating()
        self.serviceSurvey.sendSurvey(ServiceData.userAnswers(), callback: {
            (result:Bool, error_i:String) -> () in
            if result {
                self.performSegueWithIdentifier("sendSurvey", sender: self)
            }
            else {
                let alert = UIAlertView()
                alert.title = "Error"
                alert.message = error_i
                alert.addButtonWithTitle("OK")
                alert.show()
            }
            self.loader.stopAnimating()
        })
    }
    
    // Delegate
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.survey.id == -1{
            debugPrintln("wiating for data")
            //TODO Loader needed
            return -1
        }else{
            return self.survey.questions!.count
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(textCellIdentifier, forIndexPath: indexPath) as! UITableViewCell
        let row = indexPath.row
        if row >= 0 {
            cell.textLabel?.text = self.survey.questions![row].text
            if ServiceData.questionAnswered(row) == true {
                cell.backgroundColor = UIColor.greenColor()
            }
        }
        return cell
    }
    
    // Delegate
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        //tableView.deselectRowAtIndexPath(indexPath, animated: true)
        var s = ServiceData.currentSurvey
        let row = indexPath.row
        ServiceData.currentQuestionRow = row
        performSegueWithIdentifier("showAnswers", sender: self)
    }
    
    
    

}