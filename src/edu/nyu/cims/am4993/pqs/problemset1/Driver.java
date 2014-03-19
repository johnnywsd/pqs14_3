package edu.nyu.cims.am4993.pqs.problemset1;

import java.util.ArrayList;

/**
 * This class test the working of AddressBook library.
 * 
 * @author ashishmanral
 * 
 */
public class Driver {

  // Non-instantiable class
  private Driver() {
  }

  /**
   * Main class to test AddressBook library
   * 
   * @param args
   */
  public static void main(String args[]) {
    // Empty Address Book
    AddressBook newAddressBook = null;
    try {
      newAddressBook = AddressBook.newInstance("Ashish");
    } catch (InvalidUserNameException e) {
      LoggerWrapper.LOGGER
              .warning("Invalid User Name while creating new address book");
    }

    // Create an address book entry
    Entry one = new Entry.Builder("Pratik", "Indore").note("Good Boy").build();
    Entry two = new Entry.Builder("Anshul", "Seattle").email("jaina@nsit.com")
            .note("Cook").build();
    Entry three = new Entry.Builder("Monish", "Connecticut").phoneNum(
            "1234567890").build();

    // Add entry to address book
    try {
      newAddressBook.addEntry(one);
      newAddressBook.addEntry(two);
      newAddressBook.addEntry(three);
    } catch (InvalidEntryException iee) {
      LoggerWrapper.LOGGER
              .warning("Invalid Entry while adding to address book of "
                      + newAddressBook.getUserName());
    } catch (Exception e) {
      LoggerWrapper.LOGGER.warning(e.getMessage());
    }

    // Remove entry from address book
    try {
      newAddressBook.removeEntry(two);
    } catch (InvalidEntryException iee) {
      LoggerWrapper.LOGGER
              .warning("Invalid Entry while removing from address book of "
                      + newAddressBook.getUserName());
    } catch (Exception e) {
      LoggerWrapper.LOGGER.warning(e.getMessage());
    }

    // Search
    try {
      ArrayList<Entry> searchEntry = newAddressBook.search(Properties.PHONENUM,
              "567");
      LoggerWrapper.LOGGER.info("Search Result");
      for (Entry e : searchEntry) {
        LoggerWrapper.LOGGER.info(e.toString());
      }
    } catch (InvalidSearchStringException isse) {
      LoggerWrapper.LOGGER.warning("Invalid search string");
    } catch (Exception e) {
      LoggerWrapper.LOGGER.warning(e.getMessage());
    }

    // Edit
    try {
      one.editField(Properties.NAME, "Ashwini");
    } catch (InvalidUpdateStringException iuse) {
      LoggerWrapper.LOGGER.warning("Invalid update string");
    } catch (Exception e) {
      LoggerWrapper.LOGGER.warning(e.getMessage());
    }

    // Save
    try {
      AddressBook.save(newAddressBook);
    } catch (InvalidAddressBookException iabe) {
      LoggerWrapper.LOGGER
              .warning("Invalid Address Book object being serialized");
    } catch (Exception e) {
      LoggerWrapper.LOGGER.warning(e.getMessage());
    }

    // Edit : This edit happens after saving object to file. Hence, when
    // reading, this update should get lost/overwritten.
    try {
      three.editField(Properties.NAME, "Varun");
    } catch (InvalidUpdateStringException iuse) {
      LoggerWrapper.LOGGER.warning("Invalid update string");
    } catch (Exception e) {
      LoggerWrapper.LOGGER.warning(e.getMessage());
    }

    AddressBook readAddBook = null;

    // Read
    try {
      readAddBook = AddressBook.read(newAddressBook.getUserName() + "_"
              + newAddressBook.getId() + ".ser");
    } catch (InvalidFileNameException ifne) {
      LoggerWrapper.LOGGER.warning("Invalid File Name being read");
    } catch (Exception e) {
      LoggerWrapper.LOGGER.warning(e.getMessage());
    }
    LoggerWrapper.LOGGER.info("Read Address Book : " + readAddBook);
    LoggerWrapper.LOGGER.info("Original Address Book : " + newAddressBook);

  }

}
