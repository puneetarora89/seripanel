package com.seri.service.notification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.seri.common.BaseEntity;
import com.seri.common.CommonTypes;

@Entity
@Table(name="NOTIFICATION")
public class Notification extends BaseEntity implements Serializable,Comparable<Notification> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="ENTITY_ID")
	private long entityId;
	@Column(name="GROUP_TYPE")
	@Enumerated(EnumType.STRING)
	private RoleType groupType;
	@Column(name="STANDARD")
	private long standardId;
	@Column(name="SCHOOL")
	private long schoolId;
	@Column(name="NOTI_TYPE")
	@Enumerated(EnumType.STRING)
	private CommonTypes notificationType;
	@Column(name="LINKED_ENTITY")
	private long linkedEntity;
	@Column(name="LINKED_ENTITY_ROLE")
	@Enumerated(EnumType.STRING)
	private RoleType linkedEntityRole;
	@Column(name="DUE_DATE")
	private Date dueDate;
	@Column(name="DESCRIPTION")
	private String description;


	public CommonTypes getNotificationType() {
		return notificationType;
	}

	public long getLinkedEntity() {
		return linkedEntity;
	}

	public void setNotificationType(CommonTypes notificationType) {
		this.notificationType = notificationType;
	}


	public void setLinkedEntity(long linkedEntity) {
		this.linkedEntity = linkedEntity;
	}

	public RoleType getLinkedEntityRole() {
		return linkedEntityRole;
	}

	public void setLinkedEntityRole(RoleType linkedEntityRole) {
		this.linkedEntityRole = linkedEntityRole;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public long getEntityId() {
		return entityId;
	}

	public RoleType getGroupType() {
		return groupType;
	}

	public long getStandardId() {
		return standardId;
	}

	public long getSchoolId() {
		return schoolId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	public void setGroupType(RoleType groupType) {
		this.groupType = groupType;
	}

	public void setStandardId(long standardId) {
		this.standardId = standardId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Notification [entityId=" + entityId + ", groupType=" + groupType + ", standardId=" + standardId
				+ ", schoolId=" + schoolId + ", notificationType=" + notificationType + ", linkedEntity=" + linkedEntity
				+ ", linkedEntityRole=" + linkedEntityRole + ", dueDate=" + dueDate + "]";
	}

	@Override
	public int compareTo(Notification o) {
		return this.createdDate.compareTo(o.createdDate);
	}

}
