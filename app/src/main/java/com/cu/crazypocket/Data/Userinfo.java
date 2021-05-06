package com.cu.crazypocket.Data;

public class Userinfo {
    String firstname;
    String lastname;
    String phoneno;
    String email;
    Long dob;
    String userpic;
    String gender;

    public String getRefcode () {
        return refcode;
    }

    public void setRefcode ( String refcode ) {
        this.refcode = refcode;
    }

    String refcode;

    public String getGender () {
        return gender;
    }

    public void setGender ( String gender ) {
        this.gender = gender;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
    }
}
