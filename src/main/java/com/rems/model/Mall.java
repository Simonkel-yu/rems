package com.rems.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "malls")
public class Mall {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mall_id")
    private int mallId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @OneToMany(mappedBy = "mall", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Unit> units;

    public Mall() {}
    public Mall(String name, String address, String city) {
        this.name = name; this.address = address; this.city = city;
    }

    public int getMallId()           { return mallId; }
    public String getName()          { return name; }
    public void setName(String n)    { this.name = n; }
    public String getAddress()       { return address; }
    public void setAddress(String a) { this.address = a; }
    public String getCity()          { return city; }
    public void setCity(String c)    { this.city = c; }
    public List<Unit> getUnits()     { return units; }
}
