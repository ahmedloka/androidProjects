package com.gamecodeschool.dualfragment;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class AddressBook {
    private static final AddressBook ourInstance = new AddressBook();

    private ArrayList<NameAndAddress> mNamesAndAddresses ;

    public static AddressBook getInstance() {
        return ourInstance;
    }

    private AddressBook() {
        mNamesAndAddresses = new ArrayList<NameAndAddress>();
        // Some hardcoded dummy data
        // Create a new entry
        NameAndAddress tempEntry = new NameAndAddress("B Obama", "The White House", "Washington", "DC1");
       // Add it to the ArrayList
        mNamesAndAddresses.add(tempEntry);
       // Create a new entry
        tempEntry = new NameAndAddress("E Windsor", "Buckingham Palace", "London", "SW1A 1AA");
       // Add it to the ArrayList
        mNamesAndAddresses.add(tempEntry);
       // Create a new entry
        tempEntry = new NameAndAddress("V Putin", "The Kremlin", "Moscow", "MS1");
       // Add it to the ArrayList
        mNamesAndAddresses.add(tempEntry);

    }
    public ArrayList<NameAndAddress>getBook(){
        return mNamesAndAddresses ;
    }
}
