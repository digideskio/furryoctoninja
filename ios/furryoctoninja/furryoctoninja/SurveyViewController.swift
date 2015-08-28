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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.titleUI.text = ""
        let serviceSurvey = ServiceSurvey()
        
        serviceSurvey.loadSurvey({
            (result:Survey, error_i:String) -> () in
                if (error_i == "") {
                    self.survey = result
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
    }
    
    @IBAction func returned(segue: UIStoryboardSegue) {
        
    }
    
    @IBOutlet weak var descriptionUI: UILabel!
    
    @IBOutlet weak var titleUI: UILabel!
    
    @IBOutlet var tableView: UITableView!
    
    let textCellIdentifier = "TextCell"
    
    private func hideUnusedRows(){
        self.tableView.tableFooterView = UIView(frame: CGRect.zeroRect)
    }
    
    // Delegate
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.survey.id == 0{
            debugPrintln("wiating for data")
            //Loader needed
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
        }
        return cell
    }
    
    // Delegate
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        //tableView.deselectRowAtIndexPath(indexPath, animated: true)
        
        let row = indexPath.row
        ServiceData.currentQuestion = self.survey.questions![row]
        performSegueWithIdentifier("showAnswers", sender: self)
    }
    
    
    

}