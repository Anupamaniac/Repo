package com.rosettastone.succor.model {
import mx.collections.ArrayCollection;

/**
 * DTo object for getting static objects from java side.
 */
[Bindable]
[RemoteClass(alias="com.rosettastone.succor.web.dto.StaticObjectsDTO")]
public class StaticObjects {

    public var actionTypes:ArrayCollection;

    public var productTypes:ArrayCollection;

    public var eventTypes:ArrayCollection;

    public var ticketTypes:ArrayCollection;

    public var languages:ArrayCollection;

    public var templateVariables:ArrayCollection;

}
}
