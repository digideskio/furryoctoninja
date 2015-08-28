//
//  Survey.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 27/08/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation

struct Survey {
    var id: Int = 0
    var title: String = ""
    var description: String = ""
    var createdDate: Int = 0
    var questions: [Question]?
    var completedByUser: Bool = false

}

struct Question {
    var id: Int = 0
    var text: String = ""
    var answers: [Answer]?
}

struct Answer {
    var id: Int = 0
    var text: String = ""
    var checked: Bool? = false
    
    init(id: Int, text: String){
        self.id = id
        self.text = text
    }
}