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
        self.errorMsg.text = ""
        let serviceSurvey = ServiceSurvey()
        
        serviceSurvey.loadSurvey({
            (result:Survey, error_i:String) -> () in
                if (error_i == "") {
                    self.survey = result
                    self.tableView.reloadData()
                }
                else {
                    self.errorMsg.text = error_i
                }
        })
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.hideUnusedRows()
    }
    
    
    @IBOutlet weak var errorMsg: UILabel!
    
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
    }
    
    
    

}