package com.project.cafe.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Bill.getAllBills",query = "select b from Bill b order by b.id DESC ")
@NamedQuery(name = "Bill.getBillByUserName",query = "select b from Bill b where b.createdBy=:username order by b.id desc ")
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Bill")
public class Bill implements Serializable {

    private static final Integer serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Uuid")
    private String uuid;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "Contactnumber")
    private String contactNumber;

    @Column(name = "Paymentmethod")
    private String paymentMethod;

    @Column(name = "Total")
    private Integer total;

    @Column(name = "Productdetails", columnDefinition = "json")
    private String productDetails;

    @Column(name = "Createdby")
    private String createdBy;


}
