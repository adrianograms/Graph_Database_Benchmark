/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.peformance_test;

import java.util.Random;

/**
 *
 * @author adria
 */
public class No {
    public int id;
    public String name;
    public int number;
    public static Random r = new Random(1);
    
    public No(int id)
    {
        this.id = id;
        name = getAlphaNumericString();
        //Random r = new Random(1);
        number = r.nextInt(100000);
    }
    
    public String getAlphaNumericString()
    {
        
        //Random r = new Random(1);
        int low = 30;
        int high = 51;
        int n = r.nextInt(high-low) + low;
        
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        
        //Random generator = new Random(1);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * r.nextDouble());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
    
}


