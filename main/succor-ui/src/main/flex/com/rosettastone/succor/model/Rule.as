package com.rosettastone.succor.model
{
	import mx.collections.ArrayCollection;

    /**
     * Rule UI model object that mapped to corresponding Java object.
     */

	[Bindable]
    [RemoteClass(alias="com.rosettastone.succor.web.dto.RuleDTO")]
	public class Rule
	{
		public var id:int;
        public var name:String;
        public var supportLang:String;
        public var productName:String;
		public var eventId:int;
		public var active:Boolean;
		public var updateSuperTicket:Boolean;
        public var ignoreDoNotContact:Boolean;
		public var grandfathered:Boolean;
		public var solo:Boolean;
        public var days:int;
		public var hoursPriorToSession:String;
		public var actions:ArrayCollection;
        public var tickets:ArrayCollection;
        public var selectedTicketType:String;
		public var version:int;//versioning
		public var addNew:Boolean;//versioning

        public var templates:ArrayCollection;

        // for marking removed rule
        public var remove:Boolean;


        public function Rule() {
            this.actions = new ArrayCollection();
            this.tickets = new ArrayCollection();
            this.templates = new ArrayCollection();
        }
    }
}