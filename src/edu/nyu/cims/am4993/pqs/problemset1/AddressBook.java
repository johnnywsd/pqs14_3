package edu.nyu.cims.am4993.pqs.problemset1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This class represents an address book of a user. Each address book can hold
 * multiple entries.
 * 
 * @author ashishmanral
 * 
 */
public class AddressBook {

  // Unique identifier of the object
  private final UUID id = UUID.randomUUID();

  // Address book properties
  private ArrayList<Entry> entries;
  private String userName;

  /**
   * This method is used to create new instance of an address book. It takes a
   * user name as an argument which signifies the owner of the address book. It
   * cannot be a null string.
   * 
   * @param userName
   *          Owner(user) of the address book
   * @return A new AddressBook object
   * @throws InvalidUserNameException
   *           This exception is thrown when input userName is null
   */
  public static AddressBook newInstance(String userName)
          throws InvalidUserNameException {
    // Check if input user name is valid
    if (userName == null) {
      LoggerWrapper.LOGGER
              .warning("Trying to create an address book with null string user name");
      throw new InvalidUserNameException();
    }

    // Initialize instance variables
    AddressBook newAddressBook = new AddressBook();
    newAddressBook.entries = new ArrayList<Entry>();
    newAddressBook.userName = userName;
    LoggerWrapper.LOGGER.info("Successfully created an address book for user "
            + userName);
    return newAddressBook;
  }

  /**
   * Retrieves entries of the address book.
   * 
   * @return ArrayList of entries of the address book. If no entries are
   *         present, returns an empty array list.
   */
  public ArrayList<Entry> getEntries() {
    return entries;
  }

  /**
   * Retrieves user name of the address book.
   * 
   * @return User name of the address book as a string.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Retrieves unique id of the address book to avoid collisions with other
   * address books of users with same name.
   * 
   * @return Unique id of address book
   */
  public String getId() {
    return id.toString();
  }

  /**
   * This method is used to add an Entry to address book. The entry object
   * should have mandatory properties name and address populated.
   * 
   * @param entry
   *          Entry object that needs to be added to address book
   * @throws InvalidEntryException
   *           This exception is thrown if the input entry object, it's name
   *           property or address property is null
   */
  public void addEntry(Entry entry) throws InvalidEntryException {
    // Check the validity of input Entry object
    if (entry == null || entry.getName() == null || entry.getAddress() == null) {
      LoggerWrapper.LOGGER
              .warning("Entry is invalid : Cannot to added to address book");
      throw new InvalidEntryException();
    }

    // Add entry to arraylist of entries
    entries.add(entry);
    LoggerWrapper.LOGGER.info(entry.toString() + " successfully added");
  }

  /**
   * This method is used to remove an Entry from address book. Boolean
   * identifier is returned which tells if the remove was successful i.e. if the
   * object was found in the address book.
   * 
   * @param entry
   *          Entry object to be removed
   * @return Boolean identifier which tells if the removal was success. i.e.
   *         entry was present in address book
   * @throws InvalidEntryException
   *           This exception is thrown if the input entry object is null
   */
  public boolean removeEntry(Entry entry) throws InvalidEntryException {
    // Check validity of input entry object
    if (entry == null) {
      LoggerWrapper.LOGGER
              .warning("Null entry cannot be removed from address book");
      throw new InvalidEntryException();
    }
    return entries.remove(entry);
  }

  /**
   * This method is used to search for a property in all the entries stored in
   * address book. Partial search is possible which matches for the string
   * anywhere in the property.
   * 
   * @param property
   *          Property that needs to be searched
   * @param searchStr
   *          String that needs to be searched
   * @return Arraylist of all matching entries. If no matches are found, returns
   *         an empty arraylist
   * @throws InvalidSearchStringException
   *           This exception is thrown when the search string is null
   */
  public ArrayList<Entry> search(Properties property, String searchStr)
          throws InvalidSearchStringException {
    // Handle null search string
    if (searchStr == null) {
      LoggerWrapper.LOGGER.warning("Search string is null");
      throw new InvalidSearchStringException();
    }

    ArrayList<Entry> result = new ArrayList<Entry>();
    switch (property) {
    case NAME:
      for (Entry contact : entries) {
        if (contact.getName() != null && contact.getName().contains(searchStr)) {
          result.add(contact);
        }
      }
      break;
    case ADDRESS:
      for (Entry contact : entries) {
        if (contact.getAddress() != null
                && contact.getAddress().contains(searchStr)) {
          result.add(contact);
        }
      }
      break;
    case PHONENUM:
      for (Entry contact : entries) {
        if (contact.getPhoneNum() != null
                && contact.getPhoneNum().contains(searchStr)) {
          result.add(contact);
        }
      }
      break;
    case EMAIL:
      for (Entry contact : entries) {
        if (contact.getEmail() != null
                && contact.getEmail().startsWith(searchStr)) {
          result.add(contact);
        }
      }
      break;
    case NOTE:
      for (Entry contact : entries) {
        if (contact.getNote() != null
                && contact.getNote().indexOf(searchStr) > -1) {
          result.add(contact);
        }
      }
      break;
    }
    LoggerWrapper.LOGGER.info("Search results : " + result);
    return result;
  }

  /**
   * This method is used to write the object in a flat file. It stores address
   * book object with all the entries and user name. The saved file is saved in
   * current directory by the name of
   * "<userName of address book>_<unique id of address book>.ser".
   * 
   * @param addressBook
   *          Address book object to be saved
   * @throws InvalidAddressBookException
   *           This exception is thrown if the input addressBook is null
   */
  public static void save(AddressBook addressBook)
          throws InvalidAddressBookException {
    // Handles null input address book object
    if (addressBook == null) {
      LoggerWrapper.LOGGER.warning("Cannot serialize a null object");
      throw new InvalidAddressBookException();
    }

    // Declare output streams
    FileOutputStream fs = null;
    ObjectOutputStream os = null;
    try {
      // Name file as <username>_serialized.ser
      fs = new FileOutputStream(addressBook.getUserName() + "_"
              + addressBook.getId() + ".ser");
      os = new ObjectOutputStream(fs);
      os.writeObject(addressBook.toString());
      os.flush();
    } catch (FileNotFoundException ex) {
      LoggerWrapper.LOGGER.warning("File not found while saving");
    } catch (IOException ex) {
      LoggerWrapper.LOGGER.warning("IO Exception while trying to save");
    } finally {
      if (os != null) {
        try {
          os.close();
          LoggerWrapper.LOGGER.info("Saved successfully");
        } catch (IOException ex) {
          LoggerWrapper.LOGGER
                  .warning("IO Exception when trying to close file while saving");
        }
      }
    }
  }

  /**
   * This method is used to read the object from a flat file.
   * 
   * @param fileName
   *          File name where the object is saved
   * @return Reconstructed address book object
   * @throws InvalidFileNameException
   *           This exception is thrown when the input file name is null
   */
  public static AddressBook read(String fileName)
          throws InvalidFileNameException {
    // Handle null input file name
    if (fileName == null) {
      LoggerWrapper.LOGGER.warning("Cannot read from a null file");
      throw new InvalidFileNameException();
    }

    // Declare input streams
    FileInputStream fs = null;
    ObjectInputStream is = null;
    String savedObject = null;
    AddressBook addressBook = null;
    try {
      fs = new FileInputStream(fileName);
      is = new ObjectInputStream(fs);
      savedObject = (String) is.readObject();
      addressBook = constructObject(savedObject);
    } catch (FileNotFoundException ex) {
      LoggerWrapper.LOGGER.warning("File not found while reading from file");
      throw new InvalidFileNameException();
    } catch (IOException ex) {
      LoggerWrapper.LOGGER.warning("IO Exception while trying to read");
    } catch (ClassNotFoundException e) {
      LoggerWrapper.LOGGER
              .warning("Class Not Found Exception while trying to read from file");
    } finally {
      if (is != null && savedObject != null && addressBook != null) {
        try {
          is.close();
          LoggerWrapper.LOGGER.info("Object read successfully");
        } catch (IOException ex) {
          LoggerWrapper.LOGGER
                  .warning("IO Exception when trying to close file after reading");
        }
      }
    }
    return addressBook;
  }

  private static AddressBook constructObject(String constructString) {
    // Check if the input string is null
    if (constructString == null) {
      return null;
    }

    // Format of constructString is <username>~##~<first entry with ~#~
    // separated
    // properties>~##~<second entry>...and so on.
    String[] entriesStr = constructString.split("~##~");
    String userName = entriesStr[0];
    AddressBook addressBook = null;
    ;
    try {
      addressBook = AddressBook.newInstance(userName);
    } catch (InvalidUserNameException e) {
      LoggerWrapper.LOGGER
              .warning("Cannot reconstruct with invalid user name.");
      return null;
    }
    for (int i = 1; i < entriesStr.length; ++i) {
      String[] property = entriesStr[i].split("~#~");
      Entry entry = new Entry.Builder(property[0], property[1])
              .phoneNum(property[2].equals("null") ? null : property[2])
              .email(property[3].equals("null") ? null : property[3])
              .note(property[4].equals("null") ? null : property[4]).build();
      try {
        addressBook.addEntry(entry);
      } catch (InvalidEntryException iee) {
        LoggerWrapper.LOGGER
                .warning("Cannot reconstruct invalid entry. Skipping to next one.");
      }
    }
    return addressBook;
  }

  /**
   * Returns hash code of the AddressBook object.
   * 
   * @return Hash Code of the current object
   */
  @Override
  public int hashCode() {
    int hashCode = userName.hashCode();
    for (Entry contact : entries) {
      hashCode += contact.hashCode();
    }
    return hashCode;
  }

  /**
   * Checks if two similar objects are equal. If the object being being equated
   * is null or is not an instance of AddressBook class, then it returns false.
   * 
   * @return boolean value identifying if the objects are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof AddressBook)) {
      return false;
    }
    AddressBook thisObj = (AddressBook) obj;
    if (((thisObj.userName == null && userName == null) || (thisObj.userName
            .equals(userName)))
            && (thisObj.getEntries().size() == entries.size())) {
      for (Entry contact : thisObj.getEntries()) {
        if (!entries.contains(contact)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Prints the properties(delimited by ~#~ and ~##~) of AddressBook object.
   * ~##~ is used to separate multiple entries and the first string which is the
   * owner of the address book. Entries are in following sequence: 1) Name 2)
   * Address 3) Phone Number 4) Email 5) Note Prints null if property is not
   * set.
   * 
   * @return String representation of AddressBook object
   */
  @Override
  public String toString() {
    StringBuilder printString = new StringBuilder(userName == null ? "null"
            : userName);
    for (Entry contact : entries) {
      printString.append("~##~" + contact.toString());
    }
    return printString.toString();
  }

}
