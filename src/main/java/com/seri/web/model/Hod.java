package com.seri.web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by puneet on 24/05/16.
 */
@Entity
@Table(name = "HOD")
public class Hod implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "HOD_ID")
    private int hodId;
    @Column(name = "HOD_USER_ID")
    private long hodUserId;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "HOD_SCHOOL_ID")
    private int hodSchoolId;
    @Column(name = "DEPARTMENT_ID")
    private int hodDepartmentId;
    @Column(name = "F_NAME")
    private String fName;
    @Column(name = "M_NAME")
    private String mName;
    @Column(name = "L_NAME")
    private String lName;
    @Column(name = "DOB")
    private Date dob;
    @Column(name = "PHOTO")
    private String photo;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "ADDRESS")
    private String hodAddress;
    @Column(name = "CREATED_BY")
    private long createdBy;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "LAST_UPDATED_BY")
    private long lastUpdatedBy;
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    public int getHodId() {
        return hodId;
    }

    public void setHodId(int hodId) {
        this.hodId = hodId;
    }

    public long getHodUserId() {
        return hodUserId;
    }

    public void setHodUserId(long hodUserId) {
        this.hodUserId = hodUserId;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getHodSchoolId() {
        return hodSchoolId;
    }

    public void setHodSchoolId(int hodSchoolId) {
        this.hodSchoolId = hodSchoolId;
    }

    public int getHodDepartmentId() {
        return hodDepartmentId;
    }

    public void setHodDepartmentId(int hodDepartmentId) {
        this.hodDepartmentId = hodDepartmentId;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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

    public String getHodAddress() {
        return hodAddress;
    }

    public void setHodAddress(String hodAddress) {
        this.hodAddress = hodAddress;
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
