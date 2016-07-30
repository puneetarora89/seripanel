package com.seri.web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.seri.common.CommonTypes;
import com.seri.common.StatusEnum;

/**
 * Created by puneet on 29/05/16.
 */
@Entity
@Table(name = "SYLLABUS")
public class Syllabus implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private long taskId;
    @Column(name = "TASK_NAME")
    @Enumerated(EnumType.STRING)
    private CommonTypes taskName;
    @Column(name = "SUBJECT_ID")
    private int subjectId;
    @Column(name = "SCHOOL_ID")
    private int schoolId;
    @Column(name = "STANDARD_ID")
    private int standardId;
    @Column(name = "MODULE_ID")
    private int moduleId;
    @Column(name = "PID")
    private int pId;
    @Column(name = "STUDENT_ID")
    private long studentId;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "task_due_date")
    private Date taskDueDate;
    @Column(name = "CREATED_BY")
    private long createdBy;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "LAST_UPDATED_BY")
    private long lastUpdatedBy;
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusEnum status; 

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public CommonTypes getTaskName() {
        return taskName;
    }

    public void setTaskName(CommonTypes taskName) {
        this.taskName = taskName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
    
    
}
