package com.rosettastone.succor.service {
import com.rosettastone.succor.model.Rule;

import spark.components.Label;

import spark.components.Application;

public class Helper {

    public static var rootApp:Application;
    public static var selectedRule:Rule;
	public static const SMS_TYPE:String = "SMS";
	public static const EMAIL_TYPE:String = "EMAIL";
	public static const SMS_TITLE:String = "SMS";
	public static const EMAIL_TITLE:String = "Email";
    public static var infoLabel:Label;

    public static function setInfo(text:String, color:String){
        infoLabel.text = text;
        infoLabel.setStyle("color", color);
    }
}
}
