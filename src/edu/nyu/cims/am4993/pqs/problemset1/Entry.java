package edu.nyu.cims.am4993.pqs.problemset1;

/**
 * This class represents an Entry of an Address Book. An entry consists of : 1)
 * Name 2) Address 3) Phone Number 4) Email 5) Note It is not mandatory that all
 * the above properties are present for an Entry. Only, name and address are
 * mandatory fields. Rest are all optional.
 * 
 * @author ashishmanral
 * 
 */
public class Entry {

  // Entry properties
  private String name;
  private String address;
  private String phoneNum;
  private String email;
  private String note;

  // Save HashCode for optimization
  private int hashCode;

  /**
   * This class builds an Entry object using mandatory name and address argument
   * and optional phone number, email and note arguments.
   * 
   * @author ashishmanral
   * 
   */
  public static class Builder {

    // Required parameters
    private String name;
    private String address;

    // Optional parameters - initialized to default values
    private String phoneNum;
    private String email;
    private String note;

    /**
     * Constructor that takes name and address of an entry as arguments.
     * 
     * @param name
     *          Name of the entry
     * @param address
     *          Address of the entry
     */
    public Builder(String name, String address) {
      this.name = name;
      this.address = address;
      LoggerWrapper.LOGGER.info("Entry name : " + name);
      LoggerWrapper.LOGGER.info("Entry address : " + address);
    }

    /**
     * This method builds the phone number of the entry.
     * 
     * @param phoneNum
     *          Phone number of the entry
     * @return Builder object with populated phone number
     */
    public Builder phoneNum(String phoneNum) {
      this.phoneNum = phoneNum;
      LoggerWrapper.LOGGER.info("Entry phone num : " + phoneNum);
      return this;
    }

    /**
     * This method builds the email of the entry.
     * 
     * @param email
     *          Email of the entry
     * @return Builder object with populated email
     */
    public Builder email(String email) {
      this.email = email;
      LoggerWrapper.LOGGER.info("Entry email : " + email);
      return this;
    }

    /**
     * This method builds the note of the entry.
     * 
     * @param note
     *          Note of the entry
     * @return Builder object with populated note
     */
    public Builder note(String note) {
      this.note = note;
      LoggerWrapper.LOGGER.info("Entry note : " + note);
      return this;
    }

    /**
     * This method builds the entry object after the properties of the entry are
     * populated.
     * 
     * @return Entry object with all the parameters set
     */
    public Entry build() {
      return new Entry(this);
    }

  }

  private Entry(Builder builder) {
    name = builder.name;
    address = builder.address;
    phoneNum = builder.phoneNum;
    email = builder.email;
    note = builder.note;
  }

  /**
   * Retrieve name of the entry. Name can be null.
   * 
   * @return Name of the entry
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieve address of the entry. Address can be null.
   * 
   * @return Address of the entry
   */
  public String getAddress() {
    return address;
  }

  /**
   * Retrieve phone number of the entry. Phone number can be null.
   * 
   * @return Phone number of the entry
   */
  public String getPhoneNum() {
    return phoneNum;
  }

  /**
   * Retrieve email of the entry. Email can be null.
   * 
   * @return Email of the entry
   */
  public String getEmail() {
    return email;
  }

  /**
   * Retrieve note of the entry. Note can be null.
   * 
   * @return Note of the entry
   */
  public String getNote() {
    return note;
  }

  /**
   * Edit a particular property. Does not set mandatory properties like name and
   * address to null.
   * 
   * @param property
   *          The property that needs to be edited
   * @param value
   *          New value. Cannot be null for name or address
   * @throws InvalidUpdateStringException
   *           This exception is thrown when name or address are being edited to
   *           null value. They are mandatory fields and cannot be null.
   */
  public void editField(Properties property, String value)
          throws InvalidUpdateStringException {
    // Handle null search string
    if ((property == Properties.NAME || property == Properties.ADDRESS)
            && value == null) {
      LoggerWrapper.LOGGER
              .warning("Trying to update mandatory field with null value");
      throw new InvalidUpdateStringException();
    }

    switch (property) {
    case NAME:
      name = value;
      break;
    case ADDRESS:
      address = value;
      break;
    case PHONENUM:
      phoneNum = value;
      break;
    case EMAIL:
      email = value;
      break;
    case NOTE:
      note = value;
      break;
    }
    LoggerWrapper.LOGGER.info("Successfully edited.");
  }

  /**
   * Returns hash code of the Entry object. Returns 0 if all the fields are
   * null.
   * 
   * @return Hash Code of the object
   */
  @Override
  public int hashCode() {
    if (hashCode == 0) {
      if (name != null) {
        hashCode += name.hashCode();
      }
      if (address != null) {
        hashCode += address.hashCode();
      }
      if (phoneNum != null) {
        hashCode += phoneNum.hashCode();
      }
      if (email != null) {
        hashCode += email.hashCode();
      }
      if (note != null) {
        hashCode += note.hashCode();
      }
    }
    return hashCode;
  }

  /**
   * Checks if two similar objects are equal. If the object being being equated
   * is null or is not an instance of Entry class, then it returns false.
   * 
   * @return boolean value identifying if the objects are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Entry)) {
      return false;
    }
    Entry thisObj = (Entry) obj;
    if (((thisObj.name != null && thisObj.name.equals(name)) || (thisObj.name == null && name == null))
            && ((thisObj.address != null && thisObj.address.equals(address)) || (thisObj.address == null && address == null))
            && ((thisObj.phoneNum != null && thisObj.phoneNum.equals(phoneNum)) || (thisObj.phoneNum == null && phoneNum == null))
            && ((thisObj.email != null && thisObj.email.equals(email)) || (thisObj.email == null && email == null))
            && ((thisObj.note != null && thisObj.note.equals(note)) || (thisObj.note == null && note == null))) {
      return true;
    }
    return false;
  }

  /**
   * Prints the properties(delimited by ~#~) of Entry object in following
   * sequence: 1) Name 2) Address 3) Phone Number 4) Email 5) Note Prints null
   * if property is not set.
   * 
   * @return String representation of Entry object
   */
  @Override
  public String toString() {
    return (name == null ? "null" : name) + "~#~"
            + (address == null ? "null" : address) + "~#~"
            + (phoneNum == null ? "null" : phoneNum) + "~#~"
            + (email == null ? "null" : email) + "~#~"
            + (note == null ? "null" : note);
  }

}
