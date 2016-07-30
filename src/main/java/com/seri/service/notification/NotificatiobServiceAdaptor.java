package com.seri.service.notification;

import java.util.Date;

import com.seri.common.CommonTypes;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.LoggedUserUtil;


public class NotificatiobServiceAdaptor {

	private static NotificationService notificationService;
	
	public static void setNotificationService(NotificationService notificationServiceInstance){
		notificationService = notificationServiceInstance;
	}

	public static void createSingleNotification(CommonTypes notificationType, long entityId, Date dueDate, String description) {
		createSingleNotification(notificationType,entityId,dueDate, description, 0, null);

	}
	public static void createSingleNotification(CommonTypes notificationType, long entityId, int days, String description) {
		createSingleNotification(notificationType,entityId,days, description, 0, null); 
	}

	public static void createSingleNotification(CommonTypes notificationType, long entityId, Date dueDate, String description, long linkedEntity,
			RoleType linkedEntityRole) {
		Notification notification = new Notification();
		notification.setNotificationType(notificationType);
		notification.setEntityId(entityId);
		notification.setDueDate(dueDate);
		notification.setDescription(description);
		notification.setLinkedEntity(linkedEntity);
		notification.setLinkedEntityRole(linkedEntityRole);
		notification.setCreatedBy(LoggedUserUtil.getUserId());
		notification.setCreatedDate(CalendarUtil.getDate());
		createNotification(notification);
	}
	public static void createSingleNotification(CommonTypes notificationType, long entityId, int days,String description, long linkedEntity,
			RoleType linkedEntityRole) {
		createSingleNotification(notificationType,entityId,CalendarUtil.addDays(days), description, linkedEntity, linkedEntityRole);
	}

//	Group Notifications
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, Date dueDate) {
		createGroupNotification(notificationType, groupType, dueDate, 0);
	}
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, Date dueDate, long school) {
		createGroupNotification(notificationType, groupType, dueDate, school, 0);

	}
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, Date dueDate, long school, long standard) {
		createGroupNotification(notificationType, groupType, dueDate, school, 0, 0 , null);

	}
	
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, Date dueDate, long linkedEntity,
			RoleType linkedEntityRole) {
		createGroupNotification(notificationType, groupType, dueDate, 0, linkedEntity, linkedEntityRole);
	}
	
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, Date dueDate, long school, long linkedEntity,
			RoleType linkedEntityRole) {
		createGroupNotification(notificationType, groupType, dueDate, school, 0, linkedEntity, linkedEntityRole);

	}
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, int days) {
		createGroupNotification(notificationType, groupType, days, 0);
	}
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, int days, long school) {
		createGroupNotification(notificationType, groupType, days, school, 0);
	}
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, int days, long school, long standard) {
		createGroupNotification(notificationType, groupType, days, school, standard, 0, null);
	}
	
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, int days, long linkedEntity,
			RoleType linkedEntityRole) {
		createGroupNotification(notificationType, groupType, days, 0, linkedEntity, linkedEntityRole);
	}
	
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, int days, long school, long linkedEntity,
			RoleType linkedEntityRole) {
		createGroupNotification(notificationType, groupType, days, school, 0, linkedEntity, linkedEntityRole);
	}
	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, int days, long school, long standard, long linkedEntity,
			RoleType linkedEntityRole) {

	}

	public static void createGroupNotification(CommonTypes notificationType, RoleType groupType, Date dueDate, long school, long standard, long linkedEntity,
			RoleType linkedEntityRole) {
		Notification notification = new Notification();
		notification.setNotificationType(notificationType);
		notification.setGroupType(groupType);
		notification.setDueDate(dueDate);
		notification.setSchoolId(school);
		notification.setStandardId(standard);
		notification.setLinkedEntity(linkedEntity);
		notification.setLinkedEntityRole(linkedEntityRole);
		notification.setCreatedBy(LoggedUserUtil.getUserId());
		notification.setCreatedDate(CalendarUtil.getDate());
		createNotification(notification);
	}
	private static void createNotification(Notification notification) {
		notificationService.createNotification(notification);
	}

}
