package com.example.demo.dto;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
public class StringProcessor {

    public boolean isPalyndrome(String word){
        if(StringUtils.isEmpty(word) || word.length() == 1){
            return true;
        }
        else{
            int index = 0;
            while(word.length()-1-index > index){
                if(word.charAt(word.length()-1-index) != word.charAt(index)){
                    return false;
                }
                index++;
            }
            return true;
        }

    }
}
