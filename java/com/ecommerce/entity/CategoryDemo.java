package com.ecommerce.entity;

public class CategoryDemo {
    private int id;
    private String name;
    private byte[] image;
    private String base64Image;
    private boolean isDeleted;

    public CategoryDemo() {}

    public CategoryDemo(int id, String name, byte[] image, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isDeleted = isDeleted;
    }

   
    public int getId() {
    	return id;
    	}
    public void setId(int id) { 
    	this.id = id;
    	}

    public String getName() {
    	return name; 
    	}
    public void setName(String name) { 
    	this.name = name; 
    	}

    public byte[] getImage() {
    	return image;
    	}
    public void setImage(byte[] image) {
    	this.image = image; 
    	}

    public String getBase64Image() { 
    	return base64Image; 
    	}
    public void setBase64Image(String base64Image) {
    	this.base64Image = base64Image; 
    	}

    public boolean isDeleted() {
    	return isDeleted;
    	}
    public void setDeleted(boolean deleted) { 
    	isDeleted = deleted; 
    	}
}
