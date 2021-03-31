package com.sabc.digitalchampions.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String matricule;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    public Otp() {
        this.createdAt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(createdAt);
        c.add(Calendar.HOUR, 6);
        this.dueDate = c.getTime();
    }

    public int getId() {
        return id;
    }

    public Otp setId(int id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Otp setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMatricule() {
        return matricule;
    }

    public Otp setMatricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Otp setCode(String code) {
        this.code = code;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Otp setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Otp setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Otp otp = (Otp) o;
        return id == otp.id &&
                email.equals(otp.email) &&
                matricule.equals(otp.matricule) &&
                code.equals(otp.code) &&
                createdAt.equals(otp.createdAt) &&
                dueDate.equals(otp.dueDate);
    }

    public boolean verify(){
        return !(code == null || code.isEmpty() ||
                email == null || email.isEmpty() ||
                matricule == null || matricule.isEmpty() ||
                createdAt.after(dueDate)
        );
    }

    public boolean isExpired(){
        return this.dueDate.before(new Date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, matricule, code, createdAt, dueDate);
    }

    @Override
    public String toString() {
        return "Otp{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", matricule='" + matricule + '\'' +
                ", code='" + code + '\'' +
                ", createdAt=" + createdAt +
                ", dueDate=" + dueDate +
                '}';
    }
}
