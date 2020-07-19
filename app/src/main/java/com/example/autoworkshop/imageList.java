package com.example.autoworkshop;

public class imageList {
    String image,image2;

    public imageList(){

    }

    public imageList(String image, String image2) {
        this.image = image;
        this.image2=image2;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
