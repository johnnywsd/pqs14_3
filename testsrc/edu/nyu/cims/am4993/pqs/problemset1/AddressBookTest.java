package edu.nyu.cims.am4993.pqs.problemset1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;

import edu.nyu.cims.am4993.pqs.problemset1.Entry.Builder;

public class AddressBookTest {

  /**
   * Failed! AddressBook Instance can be created by default constructor
   */
  @Test(expected = Exception.class)
  public void constructorDefault() {
    AddressBook ab = new AddressBook();
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
  
  
  /**
   * Test whether empty name and address entry. Pass, if empty name and address
   * is valid.
   * @throws InvalidEntryException
   * @throws InvalidUserNameException
   */
  @Test
  public void createEntryEmptyNameAddress() 
      throws InvalidEntryException, InvalidUserNameException {
    String name = "";
    String address = "";
    AddressBook ab = AddressBook.newInstance(name);
    Builder builder = new Builder(name, address);
    Entry entry = builder.build();
    ab.addEntry(entry);
  }
  
  /**
   * Test whether a new AddressBook instance can return an empty list of entry.
   * @throws InvalidEntryException
   * @throws InvalidUserNameException
   */
  @Test
  public void getEntryNoEntry() 
      throws InvalidEntryException, InvalidUserNameException {
    String name = "";
    AddressBook ab = AddressBook.newInstance(name);
    ArrayList<Entry> entryList = ab.getEntries();
    assertNotEquals(entryList, null);
    assertEquals(entryList.isEmpty(), true);
  }
  
  /**
   * Test getEntry method.
   * @throws InvalidEntryException
   * @throws InvalidUserNameException
   */
  @Test
  public void getEntryNormally() 
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
  
  
  /**
   * Test whether it throws InvalidEntryException when the parameter
   * of removeEntry() is null.
   * @throws InvalidUserNameException
   * @throws InvalidEntryException
   */
  @Test(expected = InvalidEntryException.class)
  public void removeEntryNull() 
      throws InvalidUserNameException, InvalidEntryException{
    String name = "";
    AddressBook ab = AddressBook.newInstance(name);
    ab.removeEntry(null);
  }
  
  /**
   * Test removeEntry() method
   * @throws InvalidUserNameException
   * @throws InvalidEntryException
   */
  @Test
  public void removeEntryNormally() 
      throws InvalidUserNameException, InvalidEntryException{
    int NUM = 20;
    String name = "";
    String address = "";
    AddressBook ab = AddressBook.newInstance(name);
    ArrayList<Entry> entriesAdded = new ArrayList<Entry>();
    for(int i=0; i<NUM; i++){
      Builder builder = new Builder(name, address);
      Entry entry = builder.build();
      entriesAdded.add(entry);
    }
    for (Entry item : entriesAdded){
      ab.addEntry(item);
    }
    assertEquals(ab.getEntries().size(), NUM);
    for (Entry item : entriesAdded){
      ab.removeEntry(item);
    }
    assertEquals(ab.getEntries().size(), 0);
  }
  
  /**
   * Test, remove a Entry that does not exist in the Addressbook instance
   * @throws InvalidUserNameException
   * @throws InvalidEntryException
   */
  @Test
  public void removeEntryRemoveEntryNotExist() 
      throws InvalidUserNameException, InvalidEntryException{
    String name = "";
    String address = "";
    AddressBook ab = AddressBook.newInstance(name);
    Builder builder = new Builder(name, address);
    Entry entry = builder.build();
    ab.addEntry(entry);
    boolean rtn = false;
    rtn = ab.removeEntry(entry);
    assertEquals(rtn, true);
    rtn = ab.removeEntry(entry);
    assertEquals(rtn, false);
  }
  
  @Test(expected = InvalidSearchStringException.class)
  public void searchNull() 
      throws InvalidUserNameException, InvalidSearchStringException{
    String name = "";
    AddressBook ab = AddressBook.newInstance(name);
    ab.search(Properties.ADDRESS, null);
  }
  
  @Test()
  public void searchEmptyEntry() 
      throws InvalidUserNameException, InvalidSearchStringException{
    String name = "";
    AddressBook ab = AddressBook.newInstance(name);
    ArrayList<Entry> rtn = ab.search(Properties.ADDRESS, "");
    assertEquals(rtn.isEmpty(), true);
  }
  
  @Test()
  public void searchCommonEntry() 
      throws InvalidUserNameException, 
      InvalidSearchStringException, InvalidEntryException{
    int NUM1 = 10;
    int NUM2 = 20;
    int NUM3 = 30;
    String name = "AddressBook";
    String name1 = "Yes Man";
    String address1 = "Yes Man Address";
    String phoneNum1 = "1234567890";
    String email1 = "yesman@gmail.com";
    String note1 = "I am Yes Man";
    
    String name2 = "No Man";
    String address2 = "No Man Address";
    String phoneNum2 = "0987654321";
    String email2 = "noman@gmail.com";
    String note2 = "I am No Man";
    
    String name3 = "OK Man";
    String address3 = "No Man Address";
    
    AddressBook ab = AddressBook.newInstance(name);
    ArrayList<Entry> rtn = null;
    ArrayList<Entry> entriesAdded1 = new ArrayList<Entry>();
    ArrayList<Entry> entriesAdded2 = new ArrayList<Entry>();
    ArrayList<Entry> entriesAdded3 = new ArrayList<Entry>();
    for(int i=0; i<NUM1; i++){
      Builder builder = new Builder(name1, address1);
      builder.phoneNum(phoneNum1).email(email1).note(note1);
      Entry entry = builder.build();
      entriesAdded1.add(entry);
    }
    for(int i=0; i<NUM2; i++){
      Builder builder = new Builder(name2, address2);
      builder.phoneNum(phoneNum2).email(email2).note(note2);
      Entry entry = builder.build();
      entriesAdded2.add(entry);
    }
    for(int i=0; i<NUM3; i++){
      Builder builder = new Builder(name3, address3);
      Entry entry = builder.build();
      entriesAdded3.add(entry);
    }
    for (Entry item : entriesAdded1){
      ab.addEntry(item);
    }
    for (Entry item : entriesAdded2){
      ab.addEntry(item);
    }
    
    rtn = ab.search(Properties.NAME, "Yes");
    assertEquals(rtn.size(), NUM1);
    rtn = ab.search(Properties.NAME, "No");
    assertEquals(rtn.size(), NUM2);
    
    rtn = ab.search(Properties.ADDRESS, "Yes");
    assertEquals(rtn.size(), NUM1);
    rtn = ab.search(Properties.ADDRESS, "No");
    assertEquals(rtn.size(), NUM2);
    
    rtn = ab.search(Properties.PHONENUM, "1234");
    assertEquals(rtn.size(), NUM1);
    rtn = ab.search(Properties.PHONENUM, "0987");
    assertEquals(rtn.size(), NUM2);
    
    rtn = ab.search(Properties.EMAIL, "yes");
    assertEquals(rtn.size(), NUM1);
    rtn = ab.search(Properties.EMAIL, "no");
    assertEquals(rtn.size(), NUM2);
    
    rtn = ab.search(Properties.NOTE, "yes");
    assertEquals(rtn.size(), NUM1);
    rtn = ab.search(Properties.NOTE, "no");
    assertEquals(rtn.size(), NUM2);
    rtn = ab.search(Properties.NOTE, "am");
    assertEquals(rtn.size(), NUM1 + NUM2);
    
    //Fail, Email search use startswith()
    rtn = ab.search(Properties.EMAIL, "man");
    assertEquals(rtn.size(), NUM1 + NUM2);
    
  }
  
  @Test(expected = InvalidAddressBookException.class)
  public void saveNullAddressBook() throws InvalidAddressBookException{
    AddressBook ab = null;
    AddressBook.save(ab);
  }
  
  @Test
  public void saveNormally() 
      throws InvalidUserNameException,
      InvalidEntryException, InvalidAddressBookException{
    int NUM1 = 10;
    int NUM2 = 20;
    int NUM3 = 30;
    String name = "AddressBook";
    String name1 = "Yes Man";
    String address1 = "Yes Man Address";
    String phoneNum1 = "1234567890";
    String email1 = "yesman@gmail.com";
    String note1 = "I am Yes Man";
    
    String name2 = "No Man";
    String address2 = "No Man Address";
    String phoneNum2 = "0987654321";
    String email2 = "noman@gmail.com";
    String note2 = "I am No Man";
    
    String name3 = "OK Man";
    String address3 = "No Man Address";
    
    AddressBook ab = AddressBook.newInstance(name);
    ArrayList<Entry> rtn = null;
    ArrayList<Entry> entriesAdded1 = new ArrayList<Entry>();
    ArrayList<Entry> entriesAdded2 = new ArrayList<Entry>();
    ArrayList<Entry> entriesAdded3 = new ArrayList<Entry>();
    for(int i=0; i<NUM1; i++){
      Builder builder = new Builder(name1, address1);
      builder.phoneNum(phoneNum1).email(email1).note(note1);
      Entry entry = builder.build();
      entriesAdded1.add(entry);
    }
    for(int i=0; i<NUM2; i++){
      Builder builder = new Builder(name2, address2);
      builder.phoneNum(phoneNum2).email(email2).note(note2);
      Entry entry = builder.build();
      entriesAdded2.add(entry);
    }
    for(int i=0; i<NUM3; i++){
      Builder builder = new Builder(name3, address3);
      Entry entry = builder.build();
      entriesAdded3.add(entry);
    }
    for (Entry item : entriesAdded1){
      ab.addEntry(item);
    }
    for (Entry item : entriesAdded2){
      ab.addEntry(item);
    }
    for (Entry item : entriesAdded3){
      ab.addEntry(item);
    }
    AddressBook.save(ab);
  }
  

}
