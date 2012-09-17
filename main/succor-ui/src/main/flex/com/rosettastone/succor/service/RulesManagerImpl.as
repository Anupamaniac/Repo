package com.rosettastone.succor.service {
import mx.collections.ArrayCollection;
import mx.rpc.remoting.RemoteObject;

public class RulesManagerImpl implements RulesManager{

    public var rules:ArrayCollection;

    public var flexRemoteService:RemoteObject;

    public function RulesManagerImpl() {

    }

    public function saveRules(lang:String):void {
        flexRemoteService.saveRules(rules, lang);
    }

    public function loadRules(lang:String):void {

    }

}
}
