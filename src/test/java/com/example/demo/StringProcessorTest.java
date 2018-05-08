package com.example.demo;

import com.example.demo.dto.StringProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
public class StringProcessorTest {

    StringProcessor stringProcessor = new StringProcessor();
    Logger logger = Logger.getLogger(StringProcessorTest.class.getName());

    @Test
    public void testIsPalyndromeSuccessWithNull(){
        logger.info("Running.......................................................1");
        Assert.assertEquals(true,stringProcessor.isPalyndrome(null));
    }

    @Test
    public void testIsPalyndromeSuccessWithSingleLetter(){
        logger.info("Running.......................................................2");
        Assert.assertEquals(true,stringProcessor.isPalyndrome("a"));
    }

    @Test
    public void testIsPalyndromeSuccess(){
        logger.info("Running.......................................................3");
        Assert.assertEquals(true,stringProcessor.isPalyndrome("abba"));
    }

    @Test
    public void testIsPalyndromeReturnsFalse(){
        logger.info("Running.......................................................4");
        Assert.assertEquals(false,stringProcessor.isPalyndrome("abbae"));
    }
}
