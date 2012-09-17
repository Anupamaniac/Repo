package com.rosettastone.succor.model {
/**
 * UI Model object for ticket.
 */

[Bindable]
[RemoteClass(alias="com.rosettastone.succor.web.dto.TicketDTO")]
public class Ticket {
    public var type:String;
    public var summary:String;
}
}
