package pe.edu.estubeca.estubeca.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="profiles")

public class Profile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column(name="name", length=20, nullable = false)
    private String name;
    @Column(name="lastName", length=20, nullable = false)
    private String lastName;
    @Column(name="phone", length=9, nullable = false)
    private String phone;
    /*@Lob*/

    @Basic(fetch = FetchType.LAZY)
    @Column(name= "picture")
    private byte[] picture;
    @Column(name="grade", length=100, nullable = false)
    private String grade;


    public Profile(){}

    public Profile(Long id, User user, String name, String lastName, String phone, byte[] picture, String grade) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.picture = picture;
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
