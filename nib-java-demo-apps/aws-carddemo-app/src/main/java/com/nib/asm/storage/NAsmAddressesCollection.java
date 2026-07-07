package com.nib.asm.storage;

import java.util.HashMap;

import com.nib.asm.exceptions.NAsmException;

public class NAsmAddressesCollection implements INAsmClonable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private HashMap<Integer, NAsmAddress> addressesList = new HashMap<>(10);

  /**
   * @return the addressesList
   */
  public HashMap<Integer, NAsmAddress> getAddressesList() {
    return addressesList;
  }

  /**
   * 
   * @param address to add
   */
  public void addAddress(NAsmAddress address) {
    if (this.addressesList.size() > 100) { //DEBUG
      Integer memoryId = this.addressesList.keySet().iterator().next(); //DEBUG
      if (memoryId != null && memoryId > -1 && this.addressesList.get(memoryId).getAddressedField().getId() == 508) { //DEBUG
        System.out.print(""); //DEBUG
      } //DEBUG
    } //DEBUG
    this.addressesList.put(address.getMemoryId(), address);
  }

  /**
   * 
   * @param memoryId
   * @throws NAsmException
   */
  public void removeAddress(Integer memoryId) throws NAsmException {
    NAsmAddress addr = addressesList.get(memoryId);
    addressesList.remove(memoryId);
    addr.freeMemoryId();
  }

  /**
   * 
   * @param memoryId
   * @throws NAsmException
   */
  public NAsmAddress getAddress(Integer memoryId) throws NAsmException {
    return addressesList.get(memoryId);
  }
  
  /**
   * 
   * @param address
   * @return
   */
  public NAsmAddress getMappedField(NAsmAddress address) {
    for (int idx = 0; idx < addressesList.size(); idx++) {
      if (addressesList.get(idx).getMemoryId() == address.getMemoryId()) {
        return addressesList.get(idx);
      }
    }
    return null;
  }
}
