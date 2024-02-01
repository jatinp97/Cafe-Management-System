package com.project.cafe.pojo;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId",query = "select u from User u where u.email=:email")

@NamedQuery(name = "User.getAllUser",query = "select new com.project.cafe.wrapper.UserWrapper(u.id, u.name, u.email, u.status, u.contactNumber) from User u where u.role = 'user'")

@NamedQuery(name = "User.updateStatus", query = "update User u set u.status=:status where u.id=:id")

@NamedQuery(name = "User.getAllAdmin",query = "select u.email from User u where u.role = 'admin'")

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
@Data //Getters and Setters and Default Constructor
public class User implements Serializable {

    private static final int serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "Status")
    private String status;

    @Column(name = "Role")
    private String role;



}
