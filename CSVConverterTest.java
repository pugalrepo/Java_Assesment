package com.coding.java;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVConverterTest {

	    
    @Test
    public void testTextProcessing() {
                 
         String line = "";  
         String splitBy = ",";  
         try   
         {  
         //parsing a CSV file into BufferedReader class constructor  
         BufferedReader br = new BufferedReader(new FileReader("C://output/output.csv"));  
         while ((line = br.readLine()) != null)   //returns a Boolean value  
         {  
        	
          String[] sentence = line.split(splitBy);    // use comma as separator 
          //Check Whether data available in output file
          assertNotNull(sentence[1]);
          }  
               
         }   
         catch (IOException e)   
         {  
           e.printStackTrace();  
          }  
         }

}