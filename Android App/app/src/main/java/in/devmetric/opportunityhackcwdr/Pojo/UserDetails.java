package in.devmetric.opportunityhackcwdr.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @silverFoxA on 13/11/16.
 */

public class UserDetails {

    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("tags")
    @Expose
    private List<String> tags = new ArrayList<String>();

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age The age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return The qualification
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * @param qualification The qualification
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The tags
     */
    public List<String> getTags() {
        return tags;
    }


    /**
     * @param tags The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
