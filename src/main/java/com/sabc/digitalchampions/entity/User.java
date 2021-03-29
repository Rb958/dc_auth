package com.sabc.digitalchampions.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sabc.digitalchampions.exceptions.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, updatable = false)
    private String matricule;
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String phone;
    @Column(unique = true, nullable = false)
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastConnected;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date enabledAt;
    private String nickname;
    private boolean enabled;
    private boolean emailChecked;
    private boolean phoneChecked;
    @Column(nullable = false)
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedAt;
    @Column(nullable = false)
    private String role;

    public User() {
        this.createdAt = new Date();
        this.lastUpdatedAt = new Date();
        this.enabled = false;
        this.emailChecked = false;
        this.phoneChecked = false;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getMatricule() {
        return matricule;
    }

    public User setMatricule(String ref) {
        this.matricule = ref;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstName) {
        this.firstname = firstName;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastName) {
        this.lastname = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getEnabledAt() {
        return enabledAt;
    }

    public User setEnabledAt(Date enabledAt) {
        this.enabledAt = enabledAt;
        return this;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public User setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    public Date getLastConnected() {
        return lastConnected;
    }

    public User setLastConnected(Date lastConnected) {
        this.lastConnected = lastConnected;
        return this;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public User setDeletedAt(Date lastDeleted) {
        this.deletedAt = lastDeleted;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isEmailChecked() {
        return emailChecked;
    }

    public User setEmailChecked(boolean emailChecked) {
        this.emailChecked = emailChecked;
        return this;
    }

    public boolean isPhoneChecked() {
        return phoneChecked;
    }

    public User setPhoneChecked(boolean phoneChecked) {
        this.phoneChecked = phoneChecked;
        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return matricule.equals(user.matricule) &&
                firstname.equals(user.firstname) &&
                Objects.equals(lastname, user.lastname) &&
                phone.equals(user.phone) &&
                email.equals(user.email) &&
//                username.equals(user.username) &&
                Objects.equals(createdAt, user.createdAt) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricule, firstname, lastname, phone, email, createdAt, role); // username
    }

//    @Override
//    public String toString() {
//        return firstName + " " + lastName;
//    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", ref='" + matricule + '\'' +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", role='" + role + '\'' +
                '}';
    }

    @JsonIgnore
    public void checkValidity() throws AbstractException{
        if (email == null || email.isEmpty()){
            throw new NullUsersEmailException();
        }
        if (matricule == null || matricule.isEmpty()){
            throw new NullUsersMatriculeException();
        }
        if (phone == null || phone.isEmpty()){
            throw new NullUsersPhoneException();
        }
        if (lastname == null || lastname.isEmpty()){
            throw new NullUsersLastNameException();
        }
    }
}
