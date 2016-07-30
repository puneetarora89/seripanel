package com.seri.service.notification;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.seri.common.dao.AbstractDao;
import com.seri.web.model.User;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.DbCon;
import com.seri.web.utils.GlobalFunUtils;

@Repository("notificationDao")
public class NotificationDaoImpl extends AbstractDao<Notification> implements NotificationDao{
	
	private static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class.getName());
	
	public NotificationDaoImpl() {
		this.entityManager=DbCon.getEntityManager();
	}


	@Override
	public List<Notification> getNotificationsForUser(User user) {
		Query query = entityManager.createQuery("select n from Notification n where entityId=:entityId or (groupType=:groupType AND dueDate >= :dueDate AND (schoolId = :schoolId OR schoolId = 0) AND (standardId IN :standardId OR standardId = 0)) ORDER BY createdDate desc");
		query.setParameter("entityId", user.getId());
		query.setParameter("groupType", user.getDefaultRole().getRoleName());
		query.setParameter("dueDate", CalendarUtil.getDate());
		query.setParameter("schoolId", (long)user.getSchool());
		query.setParameter("standardId", GlobalFunUtils.convertInLongList(StringUtils.split(user.getStandard(),",")));
		List<Notification> notificationList = query.getResultList();
		logger.info("Notification count is {} for user {}", notificationList != null ? notificationList.size() : 0,user.getUsername());
		return notificationList;
	}

}
