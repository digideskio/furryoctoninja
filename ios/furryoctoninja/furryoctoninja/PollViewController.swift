//
//  AnswersViewController.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 28/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import UIKit

class PollViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    var surveyWithAnswers:Survey = Survey()
    let serviceSurvey = ServiceSurvey()
    var question = Question()
    let textCellIdentifier = "AnswerCell"
    
    private func hideUnusedRows(){
        self.tableView.tableFooterView = UIView(frame: CGRect.zeroRect)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        ServiceData.currentQuestionRow = 1
        
        if(AppSettings.tokenNotAvailable()){
            dispatch_async(dispatch_get_main_queue()) {
                self.performSegueWithIdentifier("goToLogin", sender: self)
                self.view.hidden = true
            }
        }
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(true)
        
        if self.surveyWithAnswers.id == -1 && AppSettings.tokenNotAvailable() == false{
            serviceSurvey.loadSurvey({
                (result:Survey, error_i:String) -> () in
                if (error_i == "") {
                    if (result.completedByUser == false){
                        dispatch_async(dispatch_get_main_queue()) {
                            self.view.hidden = true
                            self.performSegueWithIdentifier("surveyToAnswer", sender: self)
                        }
                    }
                    self.surveyWithAnswers = result
                    ServiceData.currentSurvey = result
                    self.question = ServiceData.currentSurvey.questions![0]
                    self.tableView.reloadData()
                }
                else {
                    self.questionTitle.text = error_i
                }
            }, surveyFilled:true)
            
            self.tableView.delegate = self
            self.tableView.dataSource = self
            self.hideUnusedRows()

        }
        
    }
    
    @IBOutlet weak var questionTitle: UILabel!
    
    @IBOutlet var tableView: UITableView!
    
    @IBAction func nextQuestion(sender: AnyObject) {
        self.question = ServiceData.nextQuestion()
        self.tableView.reloadData()
    }
    
    @IBAction func previousQuestion(sender: AnyObject) {
        self.question = ServiceData.previousQuestion()
        self.tableView.reloadData()
    }
    // Delegate
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.surveyWithAnswers.id == -1{
            debugPrintln("wiating for data")
            //TODO Loader needed
            return -1
        }else{
            self.questionTitle.text = self.question.text
            return self.question.answers!.count
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(textCellIdentifier, forIndexPath: indexPath) as! UITableViewCell
        let row = indexPath.row
        
        cell.textLabel?.text = self.question.answers![row].text
        if self.question.answers![row].isUserChoice == true{
            cell.backgroundColor = UIColor.lightGrayColor()
        }else{
            cell.backgroundColor = UIColor.whiteColor()
        }
        
        cell.userInteractionEnabled = false
        
        return cell
    }
    
    // Delegate
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
    }
    
    
    
    
}