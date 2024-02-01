package com.project.cafe.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getAllProducts", query = "select new com.project.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id, p.category.name) from Product p")
@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=: id")
@NamedQuery(name = "Product.getProductByCategory", query = "select new com.project.cafe.wrapper.ProductWrapper(p.id, p.name) from Product p where p.category.id=:id and p.status='true'")
@NamedQuery(name = "Product.getProductById", query = "select new com.project.cafe.wrapper.ProductWrapper(p.id, p.name,p.description, p.price) from Product p where p.id=:id")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
@Data
public class Product implements Serializable {

    public static final Integer serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name")
    private String name;

   @ManyToOne(fetch = FetchType.LAZY) //
   @JoinColumn(name = "category_fk", nullable = false)
    private Category category;


   @Column(name = "Description")
   private String description;

   @Column(name = "Price")
    private Integer price;

   @Column(name = "Status")
    private String status;



}
