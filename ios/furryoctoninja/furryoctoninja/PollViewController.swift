//
//  AnswersViewController.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 28/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import UIKit
import Charts

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
        self.loader.startAnimating()
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
            serviceSurvey.loadSurvey(surveyFilled:true, callback: {
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
                    self.loader.stopAnimating()
                }
                else {
                    self.questionTitle.text = error_i
                    self.loader.stopAnimating()
                }
            })
            self.tableView.delegate = self
            self.tableView.dataSource = self
            self.hideUnusedRows()
        }
    }
    
    func setChart(){
        var answersToDisplay = self.question.answers!.filter{$0.count > 0}
        var dataPoints: [String] = answersToDisplay.map({return $0.text})
        var values: [Double] = answersToDisplay.map({return Double($0.count)})
        var dataEntries: [ChartDataEntry] = []
        
        for i in 0..<dataPoints.count {
            let dataEntry = ChartDataEntry(value: values[i], xIndex: i)
            dataEntries.append(dataEntry)
        }
        
        let pieChartDataSet = PieChartDataSet(yVals: dataEntries, label: "")
        pieChartDataSet.colors = ChartColorTemplates.vordiplom()
        pieChartDataSet.valueTextColor = UIColor.darkGrayColor()
        
        let pieChartData = PieChartData(xVals: dataPoints, dataSet: pieChartDataSet)

        pieChartView.data = pieChartData
        pieChartView.descriptionText = ""
        pieChartView.animate(xAxisDuration: 2.0, yAxisDuration: 2.0)
    }
 
    
    @IBOutlet weak var loader: UIActivityIndicatorView!
    
    @IBOutlet weak var pieChartView: PieChartView!
    
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
            self.setChart()
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