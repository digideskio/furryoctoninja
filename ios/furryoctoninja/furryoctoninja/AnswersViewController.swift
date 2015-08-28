//
//  AnswersViewController.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 28/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import UIKit

class AnswersViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    var question = ServiceData.currentSurvey.questions![ServiceData.currentQuestionRow]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.hideUnusedRows()
        self.questionTitle.text = self.question.text
    }
    
    @IBOutlet weak var questionTitle: UILabel!
    
    @IBOutlet var tableView: UITableView!
    
    let textCellIdentifier = "AnswerCell"
    
    private func hideUnusedRows(){
        self.tableView.tableFooterView = UIView(frame: CGRect.zeroRect)
    }
    
    // Delegate
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.question.answers!.count
        
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier(textCellIdentifier, forIndexPath: indexPath) as! UITableViewCell
        let row = indexPath.row
        
        cell.textLabel?.text = self.question.answers![row].text
        if self.question.answers![row].checked == true{
            cell.backgroundColor = UIColor.lightGrayColor()
        }
        
        return cell
    }
    
    // Delegate
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let row = indexPath.row
        if (ServiceData.completed == false) {
            let answerId : Int = self.question.answers![row].id
            ServiceData.markAnswer(answerId)
        }else {
            tableView.deselectRowAtIndexPath(indexPath, animated: true)
        }
        
        
    }
    
    
    
    
}