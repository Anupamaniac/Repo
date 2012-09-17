package com.rosettastone.succor.validator
{
/**
 * Validator for single rule.
 */
	import com.rosettastone.succor.model.Event;
	import com.rosettastone.succor.model.Rule;
	
	import mx.utils.StringUtil;
	import mx.validators.ValidationResult;
	import mx.validators.Validator;
	
	public class RuleValidator extends Validator
	{
		
		public function RuleValidator()
		{
			super();
		}
		
		override protected function doValidation(value:Object):Array {
			var results:Array = super.doValidation(value);
			var rule:Rule = Rule(value);
            // We do not check anything if we need to remove rule.
			if (rule.remove) {
				return results;
			}
			
			if (StringUtil.trim(rule.name).length == 0) {
				results.push(new ValidationResult(true, "name", "", "Rule name is empty"));
			}
			
			if (rule.productName == null ||StringUtil.trim(rule.productName) == "") {
				results.push(new ValidationResult(true, "name", "", "Product name is not selected"));
			}
			
			if (rule.eventId <= 0) {
				results.push(new ValidationResult(true, "name", "", "Trigger is not selected"));
			}

            // Validate days for Discovery call trigger and Expiring Subscription trigger
			// FIXME use event names instead of ids
            if ((rule.eventId == 28 || rule.eventId == 33) && (rule.days <= 0)) {
                results.push(new ValidationResult(true, "days", "", "Days is not selected"));
            }
            
            if(isNaN(Number(rule.hoursPriorToSession))){
            results.push(new ValidationResult(true, "name", "", "Hour field is not a number"));
            }
            else{
            if(Number(rule.hoursPriorToSession) < 0 || Number(rule.hoursPriorToSession) > 48){
            results.push(new ValidationResult(true, "name", "", "Hour field needs to be in range [0-48]"));
            }
            }
//			if(rule.actions == null || rule.actions.length == 0) {
//				results.push(new ValidationResult(true, "name", "", "At least one action is required"));
//			}
			
			return results;
		}
	}
}