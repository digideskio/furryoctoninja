//
//  Common.swift
//  furryoctoninja
//
//  Created by Przemysław Barański on 14/09/2015.
//  Copyright (c) 2015 rspective. All rights reserved.
//

import Foundation
import UIKit
class Common{

    static func imageWithImage(image:UIImage, scaledToSize newSize:CGSize) -> UIImage{
        UIGraphicsBeginImageContextWithOptions(newSize, false, 0.0);
        image.drawInRect(CGRectMake(0, 0, newSize.width, newSize.height))
        let newImage:UIImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return newImage
    }

}