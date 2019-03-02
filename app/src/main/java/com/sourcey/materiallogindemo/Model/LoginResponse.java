package com.sourcey.materiallogindemo.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("login details")
@Expose
private List<LoginDetail> loginDetails = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<LoginDetail> getLoginDetails() {
return loginDetails;
}

public void setLoginDetails(List<LoginDetail> loginDetails) {
this.loginDetails = loginDetails;
}

}