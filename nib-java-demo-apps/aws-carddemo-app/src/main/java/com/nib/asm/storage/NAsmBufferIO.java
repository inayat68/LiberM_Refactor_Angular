package com.nib.asm.storage;

/**
 * Default class for keeping previous and next data records<br>
 * 
 * @author nib-labs.io
 */
public class NAsmBufferIO {

	private byte[] data;
	private boolean empty = true;

	/**
	 * @param data
	 */
	public NAsmBufferIO(int bufferLength) {
		this.data = new byte[bufferLength];
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
		this.empty = false;
	}

	/**
	 * @return the empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * @param empty the empty to set
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}
