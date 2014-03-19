package edu.nyu.cims.am4993.pqs.problemset1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;

import edu.nyu.cims.am4993.pqs.problemset1.Entry.Builder;

public class AddressBookTest {

  @Test
  /**
   * Failed. AddressBook Instance can be created by default constructor
   */
  public void constructorDefault() {
    AddressBook ab = new AddressBook();
    String msg = "AddressBook Instance can be created by default constructor";
    assertEquals(ab, null);
  }
  
  @Test(expected = InvalidUserNameException.class)
  public void newInstanceNull() throws InvalidUserNameException {
    AddressBook ab = AddressBook.newInstance(null);
  }
  
  @Test
  public void newInstanceWithName() throws InvalidUserNameException {
    String name = "Shouda Wang";
    AddressBook ab = AddressBook.newInstance(name);
    String errMsg = "New AddressBook instance created is null";
    assertNotEquals(errMsg, ab, null);
    errMsg = "AddressBook instance userName chaged";
    assertEquals(errMsg, ab.getUserName(), name);
  }
  
  @Test
  public void newInstanceWithKindsOfNames() throws InvalidUserNameException {
    String[] names = new String[]{"", " ", "\n", "\t", "!@#$%^&*()", "1"};
    for (String name : names){
      AddressBook ab = AddressBook.newInstance(name);
      String errMsg = "New AddressBook instance created is null";
      assertNotEquals(errMsg, ab, null);
      errMsg = "AddressBook instance userName chaged";
      assertEquals(errMsg, ab.getUserName(), name);
    }
  }
  
  @Test
  public void newInstanceUUID() throws InvalidUserNameException {
    int NUM = 1000;
    HashSet<String> uuidSet = new HashSet<String>();
    String name = "Shouda Wang";
    for (int i=0; i<NUM; i++){
      AddressBook ab = AddressBook.newInstance(name);
      uuidSet.add(ab.getId());
    }
    assertEquals(uuidSet.size(), NUM);
  }

  @Test(expected = InvalidEntryException.class)
  public void createEntryNull() 
      throws InvalidEntryException, InvalidUserNameException {
    String name = "Shouda Wang";
    AddressBook ab = AddressBook.newInstance(name);
    ab.addEntry(null);
    
  }
  
  @Test(expected = InvalidEntryException.class)
  public void createEntryNullName() 
      throws InvalidEntryException, InvalidUserNameException {
    String name = "Shouda Wang";
    AddressBook ab = AddressBook.newInstance(name);
    Builder builder = new Builder(null, "");
    Entry entry = builder.build();
    ab.addEntry(entry);
    
  }
  
  @Test(expected = InvalidEntryException.class)
  public void createEntryNullAddress() 
      throws InvalidEntryException, InvalidUserNameException {
    String name = "Shouda Wang";
    AddressBook ab = AddressBook.newInstance(name);
    Builder builder = new Builder(name, null);
    Entry entry = builder.build();
    ab.addEntry(entry);

  }
  
  @Test
  /**
   * Test whether empty name and address entry. Pass, if empty name and address
   * is valid.
   * @throws InvalidEntryException
   * @throws InvalidUserNameException
   */
  public void createEntryEmptyNameAddress() 
      throws InvalidEntryException, InvalidUserNameException {
    String name = "";
    String address = "";
    AddressBook ab = AddressBook.newInstance(name);
    Builder builder = new Builder(name, address);
    Entry entry = builder.build();
    ab.addEntry(entry);
  }
  
  @Test
  /**
   * Test whether a new AddressBook instance can return an empty list of entry.
   * @throws InvalidEntryException
   * @throws InvalidUserNameException
   */
  public void getEntryNoEntry() 
      throws InvalidEntryException, InvalidUserNameException {
    String name = "";
    AddressBook ab = AddressBook.newInstance(name);
    ArrayList<Entry> entryList = ab.getEntries();
    assertNotEquals(entryList, null);
    assertEquals(entryList.isEmpty(), true);
  }
  
  @Test
  /**
   * Test getEntry method.
   * @throws InvalidEntryException
   * @throws InvalidUserNameException
   */
  public void getEntryTest() 
      throws InvalidEntryException, InvalidUserNameException {
    int NUM = 20;
    String name = "";
    String address = "";
    AddressBook ab = AddressBook.newInstance(name);
    for(int i=0; i<NUM; i++){
      Builder builder = new Builder(name, address);
      Entry entry = builder.build();
      ab.addEntry(entry);
    }
    ArrayList<Entry> entryList = ab.getEntries();
    assertEquals(entryList.size(), NUM);
  }
  
  @Test(expected = InvalidEntryException.class)
  public void removeEntryNull() 
      throws InvalidUserNameException, InvalidEntryException{
    int NUM = 20;
    String name = "";
    AddressBook ab = AddressBook.newInstance(name);
    ab.removeEntry(null);
  }
  

}
