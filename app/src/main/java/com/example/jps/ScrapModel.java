package com.example.jps;

public class ScrapModel {
    private String CompanyName = "";
    private String JobPosition = "";
    private String Address = "";

    public ScrapModel() {
    }

    public ScrapModel(String CompanyName, String JobPosition, String Address) {
        this.CompanyName = CompanyName;
        this.JobPosition = JobPosition;
        this.Address = Address;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getJobPosition() {
        return JobPosition;
    }

    public void setJobPosition(String JobPosition) {
        this.JobPosition = JobPosition;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }


    @Override
    public String toString() {
        return "SCRAP{" +
                "CompanyName='" + CompanyName + '\'' +
                ", JobPosition='" + JobPosition + '\'' +
                ",Address=" + Address +
                '}';
    }
}