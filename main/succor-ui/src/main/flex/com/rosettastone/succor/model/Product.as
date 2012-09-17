package com.rosettastone.succor.model
{
/**
 * Model object for Product
 */

	[Bindable]
    [RemoteClass(alias="com.rosettastone.succor.web.model.Product")]
	public class Product
	{
		public var name:String;
		public var longName:String;
		
	}
}