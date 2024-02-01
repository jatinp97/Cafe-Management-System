package com.project.cafe.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@org.hibernate.annotations.NamedQuery(name = "Category.getAllCategory", query = "select c from Category c where c.id in (select p.category from Product p where p.status = 'true')")
@Data
@Entity
@Table(name = "category")
@DynamicInsert
@DynamicUpdate
public class Category implements Serializable{

    private static final Integer serialVersionID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
