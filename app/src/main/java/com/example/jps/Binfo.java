package com.example.jps;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Optional;


//사업체 정보 클래스
@Entity
public class Binfo {

//    mycsv에서 기본적으로 제공하는 사업체정보들
//        [ 재택근무 여부, 장애인 채용 구분 (중증,경증, 장애인만채용, 장애인도채용)] 는 추가예정

    private Optional<String> remote;

    private Optional<String> employmentType;

    @PrimaryKey(autoGenerate=true)
    private int serial_num=0; //primarykey


    private String id;
    private String job_application_date;
    private String recruitment_period;
    private String business_establishment_name;
    private String recruiting_occupation;
    private String employment_type;
    private String wage_form;
    private String wage;
    private String join_type;
    private String requested_experience;
    private String required_academic_background;
    private String major;
    private String required_certificate;
    private String business_address;
    private String enterprise_type;
    private String agency_in_charge;
    private String registration_date;
    private String Contact_information;


// --------<   getter&setter 구간>
    public Optional<String> getRemote() {
        return remote;
    }

    public void setRemote(Optional<String> remote) {
        this.remote = remote;
    }

    public Optional<String> getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Optional<String> employmentType) {
        this.employmentType = employmentType;
    }

    public int getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(int serial_num) {
        this.serial_num = serial_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = this.id;
    }

    public String getJob_application_date() {
        return job_application_date;
    }

    public void setJob_application_date(String job_application_date) {
        this.job_application_date = job_application_date;
    }

    public String getRecruitment_period() {
        return recruitment_period;
    }

    public void setRecruitment_period(String recruitment_period) {
        this.recruitment_period = recruitment_period;
    }

    public String getBusiness_establishment_name() {
        return business_establishment_name;
    }

    public void setBusiness_establishment_name(String business_establishment_name) {
        this.business_establishment_name = business_establishment_name;
    }

    public String getRecruiting_occupation() {
        return recruiting_occupation;
    }

    public void setRecruiting_occupation(String recruiting_occupation) {
        this.recruiting_occupation = recruiting_occupation;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getWage_form() {
        return wage_form;
    }

    public void setWage_form(String wage_form) {
        this.wage_form = wage_form;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getJoin_type() {
        return join_type;
    }

    public void setJoin_type(String join_type) {
        this.join_type = join_type;
    }

    public String getRequested_experience() {
        return requested_experience;
    }

    public void setRequested_experience(String requested_experience) {
        this.requested_experience = requested_experience;
    }

    public String getRequired_academic_background() {
        return required_academic_background;
    }

    public void setRequired_academic_background(String required_academic_background) {
        this.required_academic_background = required_academic_background;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getRequired_certificate() {
        return required_certificate;
    }

    public void setRequired_certificate(String required_certificate) {
        this.required_certificate = required_certificate;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getEnterprise_type() {
        return enterprise_type;
    }

    public void setEnterprise_type(String enterprise_type) {
        this.enterprise_type = enterprise_type;
    }

    public String getAgency_in_charge() {
        return agency_in_charge;
    }

    public void setAgency_in_charge(String agency_in_charge) {
        this.agency_in_charge = agency_in_charge;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public String getContact_information() {
        return Contact_information;
    }

    public void setContact_information(String contact_information) {
        Contact_information = contact_information;
    }

    @Override
    public String toString() {
        return "Binfo{" +
                "serial_num='" + id + '\'' +
                ", job_application_date='" + job_application_date + '\'' +
                ", recruitment_period='" + recruitment_period + '\'' +
                ", business_establishment_name='" + business_establishment_name + '\'' +
                ", recruiting_occupation='" + recruiting_occupation + '\'' +
                ", employment_type='" + employment_type + '\'' +
                ", wage_form='" + wage_form + '\'' +
                ", wage='" + wage + '\'' +
                ", join_type='" + join_type + '\'' +
                ", requested_experience='" + requested_experience + '\'' +
                ", required_academic_background='" + required_academic_background + '\'' +
                ", major='" + major + '\'' +
                ", required_certificate='" + required_certificate + '\'' +
                ", business_address='" + business_address + '\'' +
                ", enterprise_type='" + enterprise_type + '\'' +
                ", agency_in_charge='" + agency_in_charge + '\'' +
                ", registration_date='" + registration_date + '\'' +
                ", Contact_information='" + Contact_information + '\'' +
                '}';
    }
}