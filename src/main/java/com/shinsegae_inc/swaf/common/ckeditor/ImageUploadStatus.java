package com.shinsegae_inc.swaf.common.ckeditor;

public class ImageUploadStatus {
	private final int uploaded;
	private final String filename;
	private final String url;
	private final ResourceReference resourceReference;
	
	public ImageUploadStatus(int uploaded, String filename, String url) {
		super();
		this.uploaded = uploaded;
		this.filename = filename;
		this.url = url;
		this.resourceReference = new ResourceReference("attach", filename);
	}

	public int getUploaded() {
		return uploaded;
	}

	public String getFilename() {
		return filename;
	}

	public String getUrl() {
		return url;
	}
	
	public ResourceReference getResourceReference() {
		return resourceReference;
	}
}

