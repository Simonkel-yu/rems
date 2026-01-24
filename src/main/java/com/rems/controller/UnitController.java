package com.rems.controller;

import com.rems.model.Unit;
import com.rems.model.enums.UnitStatus;
import com.rems.service.UnitService;
import java.util.List;

public class UnitController {

    private final UnitService unitService = new UnitService();

    public Unit createUnit(int mallId, String unitNumber, double sizeSqft,
                           double baseRate, int tier, String usageType) {
        return unitService.createUnit(mallId, unitNumber, sizeSqft, baseRate, tier, usageType);
    }

    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }

    public List<Unit> getAvailableUnits() {
        return unitService.getAvailableUnits();
    }

    public List<Unit> searchUnits(Integer tier, Double minSize, Double maxSize, Double maxRate) {
        return unitService.searchUnits(tier, minSize, maxSize, maxRate);
    }

    public Unit updateUnit(int unitId, Double baseRate, Integer tier,
                           String usageType, UnitStatus status) {
        return unitService.updateUnit(unitId, baseRate, tier, usageType, status);
    }

    public void deleteUnit(int unitId) {
        unitService.deleteUnit(unitId);
    }
}
