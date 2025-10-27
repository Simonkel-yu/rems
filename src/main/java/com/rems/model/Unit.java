package com.rems.model;

import com.rems.model.enums.UnitStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "units")
public class Unit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private int unitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mall_id", nullable = false)
    private Mall mall;

    @Column(name = "unit_number", nullable = false, length = 20)
    private String unitNumber;

    @Column(name = "size_sqft", nullable = false)
    private double sizeSqft;

    @Column(name = "base_rate", nullable = false)
    private double baseRate;

    @Column(nullable = false)
    private int tier;

    @Column(name = "usage_type", length = 100)
    private String usageType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitStatus status = UnitStatus.AVAILABLE;

    public Unit() {}

    public int getUnitId()               { return unitId; }
    public Mall getMall()                { return mall; }
    public void setMall(Mall m)          { this.mall = m; }
    public String getUnitNumber()        { return unitNumber; }
    public void setUnitNumber(String u)  { this.unitNumber = u; }
    public double getSizeSqft()          { return sizeSqft; }
    public void setSizeSqft(double s)    { this.sizeSqft = s; }
    public double getBaseRate()          { return baseRate; }
    public void setBaseRate(double r)    { this.baseRate = r; }
    public int getTier()                 { return tier; }
    public void setTier(int t)           { this.tier = t; }
    public String getUsageType()         { return usageType; }
    public void setUsageType(String u)   { this.usageType = u; }
    public UnitStatus getStatus()        { return status; }
    public void setStatus(UnitStatus s)  { this.status = s; }
}
