package com.mujugroup.core.objeck.bean;

import com.google.gson.JsonObject;

import java.io.Serializable;

public class DeviceBean implements Serializable {

    private String did;

    private String code;

    private String hospitalBed;

    private Integer agentId;

    private Integer hospitalId;

    private Integer departmentId;

    private HospitalBean hospital;

    private DepartmentBean department;

    private JsonObject info;



    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHospitalBed() {
        return hospitalBed;
    }

    public void setHospitalBed(String hospitalBed) {
        this.hospitalBed = hospitalBed;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public HospitalBean getHospital() {
        return hospital;
    }

    public void setHospital(HospitalBean hospital) {
        this.hospital = hospital;
    }

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        this.department = department;
    }

    public JsonObject getInfo() {
        return info;
    }

    public void setInfo(JsonObject info) {
        this.info = info;
    }
}
