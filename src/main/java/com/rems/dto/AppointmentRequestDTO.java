package com.rems.dto;

import java.time.LocalDateTime;

public class AppointmentRequestDTO {
    private int unitId;
    private int tenantId;
    private int agentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AppointmentRequestDTO() {}
    public AppointmentRequestDTO(int unitId, int tenantId, int agentId,
                                  LocalDateTime startTime, LocalDateTime endTime) {
        this.unitId = unitId;
        this.tenantId = tenantId;
        this.agentId = agentId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getUnitId()                      { return unitId; }
    public void setUnitId(int u)                { this.unitId = u; }
    public int getTenantId()                    { return tenantId; }
    public void setTenantId(int t)              { this.tenantId = t; }
    public int getAgentId()                     { return agentId; }
    public void setAgentId(int a)               { this.agentId = a; }
    public LocalDateTime getStartTime()         { return startTime; }
    public void setStartTime(LocalDateTime s)   { this.startTime = s; }
    public LocalDateTime getEndTime()           { return endTime; }
    public void setEndTime(LocalDateTime e)     { this.endTime = e; }
}
