//
//  Common.swift nbgfddddddddssmm//
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
    
    struct Colors{
        static let lightGreen = UIColor(red: 0, green: 165/255, blue: 0, alpha: 0.07)
        static let lightBlue = UIColor(red: 0, green: 165/255, blue: 0, alpha: 0.07)
        static let superLightBlue = UIColor(red: 0, green: 165/255, blue: 1, alpha: 0.3)
        static let transparent = UIColor(red: 0, green: 0, blue: 0, alpha: 0)
        static let white = UIColor(red: 1, green: 1, blue: 1, alpha: 1)
    }
    
}



