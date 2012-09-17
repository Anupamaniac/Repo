package com.rosettastone.succor.controls

{
	import flash.events.FocusEvent;
	
	import mx.controls.TextInput;
	import mx.events.FlexEvent;
	
	/**
	 * TextInputHint extends the TextInput control and displays a hint in 
	 * the provided color in the TextInput box whilst the text property is empty.
	 */
	public class TextInputHint extends TextInput
	{
		private var _hint:String = ""
		private var _hintColor:uint = 0xC0C0C0;
		
		private var _backColorOrg:int;
		private var _editableChanged:Boolean;
		
		private var _hasFocus:Boolean = false;
		
		public function TextInputHint()
		{
			super();
		}
		
		/*
		* Store the original backgroud color.
		*/
		override public function initialize() :void
		{
			super.initialize();
			_backColorOrg = this.getStyle("backgroundColor");
		}
		
		public override function set editable(value:Boolean):void
		{
			if (value != super.editable)
			{
				_editableChanged = true;
				super.editable = value;
			}
		}
		
		/*
		* Upon receiving focus clear all text.
		*/
		override protected function focusInHandler(event:FocusEvent):void
		{
			super.focusInHandler(event);
			_hasFocus = true;
			
			if(text == "" && enabled && editable)
			{
				text = "";
				textField.text = "";
				textField.textColor = getStyle("color");
			}
		}
		
		/*
		* Upon losing focus, check if the text property is empty.
		* If so then display the hint.
		*/
		override protected function focusOutHandler(event:FocusEvent):void
		{
			super.focusOutHandler(event);
			_hasFocus = false;
			
			if(text == "" && enabled && editable)
			{
				setHint();
			}
		}
		
		/*
		* Set the hint text and color.
		*/
		private function setHint():void
		{
			if (hint.length == 0) return;
			if (!textField) return;
			if (!enabled) return;
			
			text = "";
			textField.text = hint;
			textField.textColor = hintColor;
		}
		
		/*
		* Check commit so we can get the background color
		* if it changes.
		*/
		protected override function commitProperties():void
		{
			super.commitProperties();
			
			if (_editableChanged)
			{
				_editableChanged = false;
				if (editable)
				{
					this.setStyle("backgroundColor", _backColorOrg);
				}
				else
				{
					_backColorOrg = this.getStyle("backgroundColor");
					this.setStyle("backgroundColor", 0xE5E5E5);
					textField.text = text;
				}
			}
			
			if(text == "" && enabled && editable)
				callLater(setHint);
			else
				this.textField.textColor = this.getStyle("color");
		}
		
		public function get hint():String
		{
			return _hint;
		}
		
		public function set hint(value:String):void
		{
			_hint = value;
			
			invalidateProperties();
			invalidateSize();
			invalidateDisplayList();
			
			dispatchEvent(new FlexEvent(FlexEvent.VALUE_COMMIT));
		}
		
		public function get hintColor():uint
		{
			return _hintColor;
		}
		
		public function set hintColor(value:uint):void
		{
			_hintColor = value;
		}
	}
}