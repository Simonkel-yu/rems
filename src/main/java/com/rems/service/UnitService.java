package com.rems.service;

import com.rems.model.Mall;
import com.rems.model.Unit;
import com.rems.model.enums.UnitStatus;
import com.rems.repository.MallRepository;
import com.rems.repository.UnitRepository;
import java.util.List;

public class UnitService {

    private final UnitRepository unitRepository = new UnitRepository();
    private final MallRepository mallRepository = new MallRepository();

    public Unit createUnit(int mallId, String unitNumber, double sizeSqft,
                           double baseRate, int tier, String usageType) {
        Mall mall = mallRepository.findById(mallId);
        if (mall == null) throw new RuntimeException("Mall not found with ID: " + mallId);

        Unit unit = new Unit();
        unit.setMall(mall);
        unit.setUnitNumber(unitNumber);
        unit.setSizeSqft(sizeSqft);
        unit.setBaseRate(baseRate);
        unit.setTier(tier);
        unit.setUsageType(usageType);
        unit.setStatus(UnitStatus.AVAILABLE);
        unitRepository.save(unit);
        return unit;
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public List<Unit> getAvailableUnits() {
        return unitRepository.findAvailable();
    }

    public List<Unit> searchUnits(Integer tier, Double minSize, Double maxSize, Double maxRate) {
        return unitRepository.findByFilters(tier, minSize, maxSize, maxRate);
    }

    public Unit updateUnit(int unitId, Double baseRate, Integer tier, String usageType, UnitStatus status) {
        Unit unit = unitRepository.findById(unitId);
        if (unit == null) throw new RuntimeException("Unit not found with ID: " + unitId);

        if (baseRate != null)   unit.setBaseRate(baseRate);
        if (tier != null)       unit.setTier(tier);
        if (usageType != null)  unit.setUsageType(usageType);
        if (status != null)     unit.setStatus(status);

        unitRepository.update(unit);
        return unit;
    }

    public void deleteUnit(int unitId) {
        unitRepository.delete(unitId);
    }

    public Unit getUnitById(int unitId) {
        Unit unit = unitRepository.findById(unitId);
        if (unit == null) throw new RuntimeException("Unit not found with ID: " + unitId);
        return unit;
    }
}
