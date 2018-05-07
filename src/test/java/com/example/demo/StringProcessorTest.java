package com.example.demo;

import com.example.demo.dto.StringProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringProcessorTest {

    StringProcessor stringProcessor = new StringProcessor();

    @Test
    public void testIsPalyndromeSuccessWithNull(){
        Assert.assertEquals(true,stringProcessor.isPalyndrome(null));
    }

    @Test
    public void testIsPalyndromeSuccessWithSingleLetter(){
        Assert.assertEquals(true,stringProcessor.isPalyndrome("a"));
    }

    @Test
    public void testIsPalyndromeSuccess(){
        Assert.assertEquals(true,stringProcessor.isPalyndrome("abba"));
    }

    @Test
    public void testIsPalyndromeReturnsFalse(){
        Assert.assertEquals(false,stringProcessor.isPalyndrome("abbae"));
    }
}
