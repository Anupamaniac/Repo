package com.rosettastone.succor.model {
import mx.collections.ArrayCollection;

/**
 * UI Model object for Statistic and errors
 */


[Bindable]
[RemoteClass(alias="com.rosettastone.succor.web.dto.StatisticDTO")]
public class Statistic {

    public var counters:ArrayCollection;
    public var errors:ArrayCollection;

}
}
