package com.rosettastone.succor.spring;

public class ScopeTestBean implements ScopeTestInterface {

    private int counter = 0;

    @Override
    public int getCounter() {
        return counter++;
    }

}
