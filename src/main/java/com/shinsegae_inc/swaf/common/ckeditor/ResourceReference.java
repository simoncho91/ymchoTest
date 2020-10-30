package com.shinsegae_inc.swaf.common.ckeditor;

public class ResourceReference {
	private final String type;
	
	private final String reference;

	public ResourceReference(String type, String reference) {
		this.type = type;
		this.reference = reference;
	}

    public String getType() {
        return type;
    }

    public String getReference() {
        return reference;
    }
}
