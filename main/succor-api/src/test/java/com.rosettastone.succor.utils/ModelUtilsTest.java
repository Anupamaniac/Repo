package com.rosettastone.succor.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

@Test
public class ModelUtilsTest {

    @Test
    void testSimpleEqual() {
        Assert.assertEquals(ModelUtils.equals(null, null), true);
        Assert.assertEquals(ModelUtils.equals(new Integer(1), new Integer(1)), true);
        Assert.assertEquals(ModelUtils.equals(new Integer(1), new Integer(0)), false);
        Assert.assertEquals(ModelUtils.equals(null, new Integer(0)), false);
        Assert.assertEquals(ModelUtils.equals(new Integer(0), null), false);
    }

    @Test
    void testSetEqual() {
        Set set1 = new HashSet();
        set1.add(new Integer(1));
        Assert.assertEquals(ModelUtils.equals(null, set1), false);
        Assert.assertEquals(ModelUtils.equals(set1, null), false);
        Assert.assertEquals(ModelUtils.equals(set1, set1), true);

        Set set2 = new HashSet();
        set2.add(new Integer(1));

        Assert.assertEquals(ModelUtils.equals(set1, set2), true);
        set2.add(new Integer(2));
        Assert.assertEquals(ModelUtils.equals(set1, set2), false);
    }

}
