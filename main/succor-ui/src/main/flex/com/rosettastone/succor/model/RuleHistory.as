package com.rosettastone.succor.model {

/**
 * Rule history UI model object that mapped to corresponding Java object.
 */

[Bindable]
[RemoteClass(alias="com.rosettastone.succor.web.model.RuleHistory")]
public class RuleHistory {

    public var id:int;

    public var username:String;

    public var ruleName:String;

    public var ruleId:int;

    public var created:Date;

    public var action:String;

    public var language:String;

}
}
