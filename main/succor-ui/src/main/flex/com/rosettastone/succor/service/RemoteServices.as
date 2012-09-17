package com.rosettastone.succor.service {
import flash.net.URLRequest;
import flash.net.navigateToURL;

import mx.controls.Alert;
import mx.core.FlexGlobals;
import mx.events.CloseEvent;
import mx.messaging.ChannelSet;
import mx.messaging.channels.AMFChannel;
import mx.messaging.events.ChannelEvent;
import mx.messaging.events.ChannelFaultEvent;
import mx.rpc.events.FaultEvent;
import mx.rpc.remoting.RemoteObject;

import mx.messaging.messages.ErrorMessage;

import spark.components.Application;

/**
 * This service init and store remote services.
 */
public class RemoteServices {

    private static var _flexService:RemoteObject;
    private static var _statisticService:RemoteObject;
    private static var _channelSet:ChannelSet;

    /**
     * Init and return remote object for flex service.
     */
    public static function get flexService():RemoteObject {
        if (_flexService == null) {
            init();
        }
        return _flexService;
    }

    /**
     * Init and return remote object for statistic service.
     */

    public static function statisticService():RemoteObject {
        if (_statisticService == null) {
            init();
        }
        return _statisticService;
    }

    /**
     * Initialize remote services
     */
    private static function init():void {
        //trace("Init flex service");
        _flexService = new RemoteObject("flexService");
        _statisticService = new RemoteObject("statisticService");
        _channelSet = new ChannelSet();
        var channel:AMFChannel = new AMFChannel("amf", getBaseUrl() + "/messagebroker/amf")
        // add some error listeners
        channel.addEventListener(ChannelFaultEvent.FAULT, channelFaultHandler);
        channel.addEventListener(ChannelEvent.DISCONNECT, channelDisconnectHandler);
        _channelSet.addChannel(channel);
        flexService.channelSet = _channelSet;
        _statisticService.channelSet = _channelSet;
    }

    // redirect or not to login page
    private static var needRedirect:Boolean = true;

    /**
     * Handler that worked when channel was disconnected from server.
     * @param event
     */
    private static function channelDisconnectHandler(event:ChannelEvent):void {
        if (needRedirect) {
            reconnect();
        }
    }

    private static function channelFaultHandler(event:ChannelFaultEvent):void {
        if (needRedirect) {
            reconnect();
        }
    }

    /**
     * Redirect to login page and redirect
     */
    private static function reconnect():void {
        needRedirect = false;
        Helper.setInfo("Error while connect.", "red");
        openBaseurl();
    }


    private static function openBaseurl():void {
        Alert.show("Session expired. Need to reconnect.", "Warning", Alert.OK, null, handleSessionExpired);
    }

    private static function handleSessionExpired(event:CloseEvent):void {
        var urlRequest:URLRequest = new URLRequest(getBaseUrl.call());
        navigateToURL(urlRequest, '_top');
    }

    private static function handle():void {
    }

    /**
     * Get base application URL.
     * @return
     */
    public static function getBaseUrl():String {
        var params:Object = FlexGlobals.topLevelApplication.parameters;
        //trace("Base url = " + params.baseUrl);
        return params.baseUrl;
    }

    /**
     * Get current user.
     * @return
     */
    public static function getUser():String {
        var params:Object = FlexGlobals.topLevelApplication.parameters;
        //trace("Base user = " + params.user);
        return params.user;
    }
	
	/**
	 * Updated error handler for InvalidUpdateException
	 * @param FaultEvent
	 */
	
	public static function errorHandler(event:FaultEvent):void {
		var faultString:String = event.fault.faultString;//Refresh Invalid Update State
		if(faultString.indexOf("InvalidUpdateException") != -1){//Refresh Invalid Update State
			Alert.show("Invalid update. Your current state is out of sync with the state of the server as other user has updated it. Please repeat your changes. Click OK to refresh your state.", "Warning", Alert.OK, null, handleSessionExpired);//Refresh Invalid Update State
		}//Refresh Invalid Update State
		else{//Refresh Invalid Update State
			Alert.show('Error ' + faultString);
		}//Refresh Invalid Update State
	}
    /**
     * Redirect to specified URL
     * @param url
     */
    public static function openLink(url:String):void {
        var urlRequest:URLRequest = new URLRequest(url);
        navigateToURL(urlRequest, '_top');
    }

}
}
