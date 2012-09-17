package com.rosettastone.succor.model
{
/**
 * UI Model object for template variable.
 */

	[Bindable]
    [RemoteClass(alias="com.rosettastone.succor.web.model.TemplateVariable")]
	public class TemplateVariable
	{
		public var id:int;
        public var name:String;
        public var displayName:String;
    }
}