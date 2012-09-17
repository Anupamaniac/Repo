package com.rosettastone.succor.flex
{
/**
 * Extension for TextArea UI component.
 * It sets border color to red if there is no content in TextField.
 */
	import mx.utils.StringUtil;
	
	import spark.components.TextArea;

	public class RuleName extends TextArea
	{
		private const POSITIVE_COLOR:uint = 0x000000; // Black
		private const NEGATIVE_COLOR:uint = 0xFF0000; // Red
		
		public function RuleName()
		{
			super();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			setStyle("borderColor", (StringUtil.trim(text) == "") ? NEGATIVE_COLOR : POSITIVE_COLOR);
		}	
	}
}