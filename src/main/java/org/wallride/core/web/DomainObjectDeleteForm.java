package org.wallride.core.web;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DomainObjectDeleteForm implements Serializable {
	
	private boolean confirmed;
	
	public boolean isConfirmed() {
		return confirmed;
	}
	
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
}