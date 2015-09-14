//
//  AnswersViewController.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 28/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import UIKit

class AnswersViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, UICollectionViewDataSource, UICollectionViewDelegate  {
    var question = ServiceData.currentSurvey.questions![ServiceData.currentQuestionRow]
    let textCellIdentifier = "AnswerCell"
    let collectionCellIdentifier = "QuestionCell"
    
    private func hideUnusedRows(){
        self.tableView.tableFooterView = UIView(frame: CGRect.zeroRect)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.barTintColor = UIColor.whiteColor()
        
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 100, height: 30))
        imageView.contentMode = .ScaleAspectFit
        imageView.image =  UIImage(named: "logo")
        self.navigationItem.titleView = imageView

        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.hideUnusedRows()
        self.questionTitle.text = self.question.text
    }
    
    override func viewDidAppear(animated: Bool) {
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
    }
    
    @IBOutlet weak var questionTitle: UILabel!
    
    @IBOutlet var tableView: UITableView!
    
    @IBOutlet weak var collectionView: UICollectionView!
    
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
        var green = UIColor(red: 0, green: 165/255, blue: 0, alpha: 0.07)
        var trans = UIColor(red: 0, green: 165/255, blue: 1, alpha: 0)
        var white = UIColor(red: 1, green: 1, blue: 1, alpha: 1)
        cell.contentView.layer.cornerRadius = 4
        cell.contentView.layer.masksToBounds = true
        if self.question.answers![row].isUserChoice == true{
            cell.contentView.layer.backgroundColor = green.CGColor
            cell.textLabel?.backgroundColor = trans
        }else{
            cell.contentView.layer.backgroundColor = white.CGColor
            cell.textLabel?.backgroundColor = white
        }
        let bgColor = UIView()
        bgColor.backgroundColor = green
        cell.selectedBackgroundView = bgColor
        
        return cell
    }
    
    // Delegate
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let row = indexPath.row
        if (ServiceData.completed == false) {
            let answerId : Int = self.question.answers![row].id
            ServiceData.markAnswer(answerId)
            self.question = ServiceData.currentSurvey.questions![ServiceData.currentQuestionRow]
            tableView.reloadData()
            //self.performSegueWithIdentifier("toQuestions", sender:self)
            //self.navigationController!.popViewControllerAnimated(true)
        }else {
            tableView.deselectRowAtIndexPath(indexPath, animated: true)
        }
    }
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if ServiceData.currentSurvey.id == -1{
            return -1
        }else{
            return ServiceData.currentSurvey.questions!.count
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
