package com.example.demo;

import com.example.demo.dto.StringProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringProcessorTest {

    StringProcessor stringProcessor = new StringProcessor();
    Logger logger = Logger.getLogger(StringProcessorTest.class.getName());

    @Test
    public void testIsPalyndromeSuccessWithNull(){
        logger.info("Running...");
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
