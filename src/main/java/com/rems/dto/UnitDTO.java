package com.rems.dto;

import com.rems.model.enums.UnitStatus;

public class UnitDTO {
    private int unitId;
    private String mallName;
    private String unitNumber;
    private double sizeSqft;
    private double baseRate;
    private int tier;
    private String usageType;
    private UnitStatus status;

    public UnitDTO() {}
    public UnitDTO(int unitId, String mallName, String unitNumber, double sizeSqft,
                   double baseRate, int tier, String usageType, UnitStatus status) {
        this.unitId = unitId;
        this.mallName = mallName;
        this.unitNumber = unitNumber;
        this.sizeSqft = sizeSqft;
        this.baseRate = baseRate;
        this.tier = tier;
        this.usageType = usageType;
        this.status = status;
    }

    public int getUnitId()              { return unitId; }
    public String getMallName()         { return mallName; }
    public String getUnitNumber()       { return unitNumber; }
    public double getSizeSqft()         { return sizeSqft; }
    public double getBaseRate()         { return baseRate; }
    public int getTier()                { return tier; }
    public String getUsageType()        { return usageType; }
    public UnitStatus getStatus()       { return status; }
}
