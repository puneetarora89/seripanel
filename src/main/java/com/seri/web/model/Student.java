package com.seri.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by arorapu1 on 24/11/2015.
 */
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private int studentId;
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PARENT")
    private long parentId;
    @Column(name = "F_NAME")
    private String fName;
    @Column(name = "M_NAME")
    private String mName;
    @Column(name = "L_NAME")
    private String lName;
    @Column(name = "DOB")
    private String dob;
    @Column(name = "PHOTO")
    private String photo;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "SCHOOL_ID")
    private int stuSchoolId;
    @Column(name = "STANDARD_ID")
    private int stuStandardId;

    @Column(name = "BIRTH_PLACE")
    private String birthPlace;
    @Column(name = "hobbies")
    private String hobbies;
    @Column(name = "BLOOD_GROUP")
    private String bloodGroup;
    @Column(name = "ADDMISSION_DATE")
    private Date admissionDate;

    @Column(name = "APPLICATION_NO")
    private String applicationNo;
    @Column(name = "stream")
    private String stream;
    @Column(name = "SESSION")
    private String session;
    @Column(name = "SECTION")
    private String section;

    @Column(name = "NATIONALITY")
    private String nationality;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "RELIGION")
    private String religion;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "STATE")
    private String state;
    @Column(name = "CITY")
    private String city;
    @Column(name = "PINCODE")
    private String pincode;
    @Column(name = "mob_no")
    private String mobNo;

    @Column(name = "CREATED_BY")
    private long createdBy;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "LAST_UPDATED_BY")
    private long lastUpdatedBy;
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public int getStuSchoolId() {
        return stuSchoolId;
    }

    public void setStuSchoolId(int stuSchoolId) {
        this.stuSchoolId = stuSchoolId;
    }

    public int getStuStandardId() {
        return stuStandardId;
    }

    public void setStuStandardId(int stuStandardId) {
        this.stuStandardId = stuStandardId;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
