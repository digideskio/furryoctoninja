//
//  Survey.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 27/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation

class Survey {
    var id: Int = 0
    var title: String = ""
    var description: String = ""
    var createdDate: Int = 0
    var questions: [Question]?
    var completedByUser: Bool = false
    
    required init(){
    }

}

class Question {
    var id: Int = 0
    var text: String = ""
    var answers: [Answer]?
}

class Answer {
    var id: Int = 0
    var text: String = ""
}

