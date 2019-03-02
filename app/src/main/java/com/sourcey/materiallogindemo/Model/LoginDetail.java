package com.sourcey.materiallogindemo.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("last_name")
@Expose
private String lastName;
@SerializedName("email_id")
@Expose
private String emailId;
@SerializedName("mobile_no")
@Expose
private String mobileNo;
@SerializedName("image_url")
@Expose
private String imageUrl;
@SerializedName("image")
@Expose
private String image;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

}
