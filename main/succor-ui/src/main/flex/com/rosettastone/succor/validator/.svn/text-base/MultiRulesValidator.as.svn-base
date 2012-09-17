package com.rosettastone.succor.validator
{
/**
 * Global rules validator.
 * It checks that rule name is unique.
 */
	import com.rosettastone.succor.model.Rule;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	import mx.utils.StringUtil;
	import mx.validators.ValidationResult;
	import mx.validators.Validator;
	
	public class MultiRulesValidator extends Validator
	{
		public function MultiRulesValidator()
		{
			super();
		}
		
		override protected function doValidation(value:Object):Array {
			var results:Array = super.doValidation(value);
			var rules:ArrayCollection = ArrayCollection(value);
			
			for each (var rule:Rule in rules) {
				for each(var r:Rule in rules) {
					if (r.name != null && rule.name != null && rule != r && StringUtil.trim(rule.name.toLowerCase()) == StringUtil.trim(r.name.toLowerCase())) {
						results.push(new ValidationResult(true, "rules", "", "Rules names should be unique"));
						return results;
					}
				}
			}
			return results;
		}
	}
}