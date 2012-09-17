package com.rosettastone.succor.web.model;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Action} represents enum of rule actions.
 */
public enum Action {
    PHONE, EMAIL, POSTAL, INSTANT_ACTION_TICKET, PHONE_OR_EMAIL, SMS;

    /**
     * Provides string presentation for actions.
     *
     * @param actions
     * @return set
     */
    public static Set<String> toStringSet(Set<Action> actions) {
        Set<String> set = new HashSet<String>();
        if (actions != null) {
            for (Action action : actions) {
                set.add(action.name());
            }
        }
        return set;
    }

    /**
     * @param actions
     * @return set
     */
    public static Set<Action> toEnumSet(Set<String> actions) {
        Set<Action> set = EnumSet.noneOf(Action.class);
        if (actions != null) {
            for (String str : actions) {
                set.add(Action.valueOf(str));
            }
        }
        return set;
    }
}
