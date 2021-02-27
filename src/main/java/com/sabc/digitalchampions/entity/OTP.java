package com.sabc.digitalchampions.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table
@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phone;
    private String userCode;
    private Date createAt;
    private String email;

    public OTP(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OTP otp = (OTP) o;
        return id == otp.id &&
                phone.equals(otp.phone) &&
                userCode.equals(otp.userCode) &&
                createAt.equals(otp.createAt) &&
                email.equals(otp.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, userCode, createAt, email);
    }
}
