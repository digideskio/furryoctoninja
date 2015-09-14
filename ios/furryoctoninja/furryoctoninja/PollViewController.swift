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

class PollViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, UICollectionViewDataSource, UICollectionViewDelegate {
    var surveyWithAnswers:Survey = Survey()
    let serviceSurvey = ServiceSurvey()
    var question = Question()
    let textCellIdentifier = "AnswerCell"
    let collectionCellIdentifier = "QuestionCell"
    
    private func hideUnusedRows(){
        self.tableView.tableFooterView = UIView(frame: CGRect.zeroRect)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        var nav = self.navigationController!
        prepareNav(navigationItem, navController: nav)
        self.loader.startAnimating()
        ServiceData.currentQuestionRow = 0
        if(AppSettings.tokenNotAvailable()){
            self.goToLogginView()
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
                    self.collectionView.reloadData()
                    self.loader.stopAnimating()
                }
                else {
                    self.questionTitle.text = error_i
                    self.loader.stopAnimating()
                }
            })
            self.tableView.delegate = self
            self.tableView.dataSource = self
            self.collectionView.delegate = self
            self.collectionView.dataSource = self
            self.hideUnusedRows()

        }
    }
    
    func prepareNav(navItem: UINavigationItem, navController: UINavigationController){
        let refreashButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Refresh, target: self, action: "refreash")
        navItem.rightBarButtonItem = refreashButton
        
        let imageLogout = UIImage(named: "logout")
        let logout = Common.imageWithImage(imageLogout!, scaledToSize: CGSizeMake(40,40))
        
        let logoutButton = UIBarButtonItem(image: logout, style: .Plain, target: self, action: "logout")
        navItem.leftBarButtonItem = logoutButton
        
        navController.setNavigationBarHidden(false, animated: true)
        navController.navigationBar.barTintColor = UIColor.whiteColor()
        
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 0, height: 30))
        imageView.contentMode = .ScaleAspectFit
        imageView.image =  UIImage(named: "logo")
        navItem.titleView = imageView

    }
    
    func logout(){
        AppSettings.logout()
        self.goToLogginView()
    }
    
    func refreash(){
        self.surveyWithAnswers.id = -1
        self.viewDidAppear(true)
    }
    
    func goToLogginView(){
        dispatch_async(dispatch_get_main_queue()) {
//            self.navigationItem.backBarButtonItem?.title = ""
//            self.navigationItem.setHidesBackButton(true, animated: false)
            self.performSegueWithIdentifier("goToLogin", sender: self)
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
    
    @IBOutlet weak var collectionView: UICollectionView!
    
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
        
        let msg : String = "(" + String(self.question.answers![row].count) + ") "
        
        cell.textLabel?.text = msg + self.question.answers![row].text
        var blue = UIColor(red: 0, green: 165/255, blue: 0, alpha: 0.07)
        var trans = UIColor(red: 0, green: 165/255, blue: 1, alpha: 0)
        var white = UIColor(red: 1, green: 1, blue: 1, alpha: 1)
        cell.contentView.layer.cornerRadius = 2
        cell.contentView.layer.masksToBounds = true
        if self.question.answers![row].isUserChoice == true{
            cell.contentView.layer.backgroundColor = blue.CGColor
            cell.textLabel?.backgroundColor = trans
        }else{
            cell.contentView.layer.backgroundColor = white.CGColor
            cell.textLabel?.backgroundColor = white
        }
        
        cell.userInteractionEnabled = false
        
        return cell
    }
    
    func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
        return 1
    }
    
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if self.surveyWithAnswers.id == -1{
            return -1
        }else{
            return self.surveyWithAnswers.questions!.count
        }

    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(self.collectionCellIdentifier, forIndexPath: indexPath) as! MyCollectionViewCell
        let row = indexPath.row
        var blueChosen = UIColor(red: 0, green: 165/255, blue: 1, alpha: 0.3)
        var blue = UIColor(red: 0, green: 165/255, blue: 1, alpha: 0.07)
        cell.contentView.layer.cornerRadius = 7
        cell.contentView.layer.masksToBounds = true
        if row == ServiceData.currentQuestionRow {
             cell.contentView.layer.backgroundColor = blueChosen.CGColor
        }else{
             cell.contentView.layer.backgroundColor = blue.CGColor
        }

//        cell.layer.cornerRadius = 20
//        cell.layer.masksToBounds = true
       
 
        cell.myLabel.text = String(row+1)
        return cell
    }
    
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        let row = indexPath.row
        self.question = ServiceData.questionAtRow(row)
        self.tableView.reloadData()
        self.collectionView.reloadData()
        self.collectionView.flashScrollIndicators()
    }
    
}

import UIKit
class MyCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var myLabel: UILabel!
}