package com.seri.web.dao.daoImpl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.seri.common.CommonTypes;
import com.seri.web.dao.SyllabusDao;
import com.seri.web.dto.RatingTask;
import com.seri.web.model.Syllabus;
import com.seri.web.utils.CalendarUtil;
import com.seri.web.utils.DbCon;
import com.seri.web.utils.GlobalFunUtils;

/**
 * Created by puneet on 29/05/16.
 */
@Service("syllabusDao")
public class SyllabusDaoImpl implements SyllabusDao {

    private GlobalFunUtils globalFunUtils = new GlobalFunUtils();

    @Override
    public Boolean create(Syllabus syllabus) {
        try{
            EntityManager em = DbCon.getEntityManager();
            globalFunUtils.inActiveAllTransaction(em);
            em.getTransaction().begin();
            em.persist(syllabus);
            em.getTransaction().commit();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public Boolean update(Syllabus syllabus) {
        try {
            EntityManager em = DbCon.getEntityManager();
            globalFunUtils.inActiveAllTransaction(em);
            em.getTransaction().begin();
            Syllabus syllabus1 = em.find(Syllabus.class, syllabus.getTaskId());

            syllabus1.setSchoolId(syllabus.getSchoolId());
            syllabus1.setSubjectId(syllabus.getSubjectId());
            syllabus1.setStandardId(syllabus.getStandardId());
            syllabus1.setStudentId(syllabus.getStudentId());
            syllabus1.setpId(syllabus.getpId());
            syllabus1.setModuleId(syllabus.getModuleId());
            syllabus1.setContent(syllabus.getContent());
            syllabus1.setTaskDueDate(syllabus.getTaskDueDate());
            syllabus1.setTaskName(syllabus.getTaskName());

            syllabus1.setLastUpdatedBy(syllabus.getLastUpdatedBy());
            syllabus1.setLastUpdatedDate(syllabus.getLastUpdatedDate());


            em.getTransaction().commit();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Syllabus getSyllabusBySyllabusId(long id) {
        try {
            EntityManager em = DbCon.getEntityManager();
            Query ui = em.createQuery("select c from Syllabus c where c.taskId="+id);
            em.clear();
            if (ui.getResultList().size() > 0)
                return (Syllabus) ui.getResultList().get(0);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Syllabus> getSyllabusBySyllabusSchoolId(int id) {
        try {
            EntityManager em = DbCon.getEntityManager();
            Query ui = em.createQuery("select c from Syllabus c where c.schoolId="+id);
            em.clear();
            if (ui.getResultList().size() > 0)
                return ui.getResultList();
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Syllabus> getAllSyllabusBySyllabusStandardId(int id) {
        try {
            EntityManager em = DbCon.getEntityManager();
            Query ui = em.createQuery("select c from Syllabus c where c.standardId="+id);
            em.clear();
            if (ui.getResultList().size() > 0)
                return ui.getResultList();
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Syllabus getRecentSyllabusBySyllabusStandardId(int id) {
        try {
            EntityManager em = DbCon.getEntityManager();
            Query ui = em.createQuery("select c from Syllabus c where c.standardId="+id+" order by createdDate desc");
            em.clear();
            if (ui.getResultList().size() > 0)
                return (Syllabus) ui.getResultList().get(0);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Syllabus> getAllSyllabusBySyllabusStudentId(int id) {
        try {
            EntityManager em = DbCon.getEntityManager();
            Query ui = em.createQuery("select c from Syllabus c where c.studentId="+id);
            em.clear();
            if (ui.getResultList().size() > 0)
                return ui.getResultList();
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Syllabus getRecentSyllabusBySyllabusStudentId(int id) {
        try {
            EntityManager em = DbCon.getEntityManager();
            Query ui = em.createQuery("select c from Syllabus c where c.studentId="+id+" order by createdDate desc");
            em.clear();
            if (ui.getResultList().size() > 0)
                return (Syllabus) ui.getResultList().get(0);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Syllabus getSyllabusBySyllabusFilters(Map<String, String> params){
        EntityManager em = DbCon.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Syllabus> criteriaQuery = criteriaBuilder.createQuery(Syllabus.class);

        Root<Syllabus> syllabusRoot = criteriaQuery.from(Syllabus.class);
        List<Predicate> predList = new LinkedList<Predicate>();
        Predicate p = null;
        if(params.containsKey("schoolId") && params.get("schoolId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("schoolId"), params.get("schoolId"));
            predList.add(p);
        }

        if(params.containsKey("subjectId") && params.get("subjectId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("subjectId"), params.get("subjectId"));
            predList.add(p);
        }

        if(params.containsKey("standardId") && params.get("standardId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("standardId"), params.get("standardId"));
            predList.add(p);
        }

        if(params.containsKey("pId") && params.get("pId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("pId"), params.get("pId"));
            predList.add(p);
        }

        if(params.containsKey("studentId") && params.get("studentId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("studentId"), params.get("studentId"));
            predList.add(p);
        }

        if(params.containsKey("taskName") && params.get("taskName") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("taskName"), CommonTypes.valueOf(params.get("taskName")));
            predList.add(p);
        }

        if(params.containsKey("moduleId") && params.get("moduleId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("moduleId"), params.get("moduleId"));
            predList.add(p);
        }

        if(params.containsKey("syllabusDueDate") && params.get("syllabusDueDate") != null) {

//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            try {
                startDate = CalendarUtil.getSystemDateFormat().parse(params.get("syllabusDueDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            p = criteriaBuilder.greaterThanOrEqualTo(syllabusRoot.<Date>get("taskDueDate"), startDate);
            predList.add(p);
        }


        Predicate[] predArray = new Predicate[predList.size()];
        predList.toArray(predArray);

        criteriaQuery.where(predArray);

        CriteriaQuery<Syllabus> select = criteriaQuery.select(syllabusRoot);
        TypedQuery<Syllabus> typedQuery = em.createQuery(select);

        List<Syllabus> resultList = typedQuery.getResultList();
        if(resultList.size()>0)
            return resultList.get(0);
        else
            return null;
    }

    @Override
    public List<Syllabus> getSyllabusListBySyllabusFilters(Map<String, String> params){
        EntityManager em = DbCon.getEntityManager();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Syllabus> criteriaQuery = criteriaBuilder.createQuery(Syllabus.class);

        Root<Syllabus> syllabusRoot = criteriaQuery.from(Syllabus.class);
        List<Predicate> predList = new LinkedList<Predicate>();
        Predicate p = null;
        if(params.containsKey("schoolId") && params.get("schoolId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("schoolId"), params.get("schoolId"));
            predList.add(p);
        }

        if(params.containsKey("subjectId") && params.get("subjectId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("subjectId"), params.get("subjectId"));
            predList.add(p);
        }

        if(params.containsKey("standardId") && params.get("standardId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("standardId"), params.get("standardId"));
            predList.add(p);
        }

        if(params.containsKey("pId") && params.get("pId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("pId"), params.get("pId"));
            predList.add(p);
        }

        if(params.containsKey("studentId") && params.get("studentId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("studentId"), params.get("studentId"));
            predList.add(p);
        }

        if(params.containsKey("moduleId") && params.get("moduleId") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("moduleId"), params.get("moduleId"));
            predList.add(p);
        }

        if(params.containsKey("taskName") && params.get("taskName") != null) {
            p = criteriaBuilder.equal(syllabusRoot.get("taskName"), params.get("taskName"));
            predList.add(p);
        }

        if(params.containsKey("syllabusDueDate") && params.get("syllabusDueDate") != null) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            try {
                startDate = df.parse(params.get("syllabusDueDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            p = criteriaBuilder.greaterThanOrEqualTo(syllabusRoot.<Date>get("taskDueDate"), startDate);
            predList.add(p);
        }


        Predicate[] predArray = new Predicate[predList.size()];
        predList.toArray(predArray);

        criteriaQuery.where(predArray);

        CriteriaQuery<Syllabus> select = criteriaQuery.select(syllabusRoot);
        TypedQuery<Syllabus> typedQuery = em.createQuery(select);

        List<Syllabus> resultList = typedQuery.getResultList();
        if(resultList.size()>0)
            return resultList;
        else
            return null;
    }
    
    public List<RatingTask> getWorkFromSyllabus(long standardId, long subjectId, Date taskDate){
    	EntityManager em = DbCon.getEntityManager();
    	/*String query = "select s.student_id, s.f_name,s.m_name,s.l_name, r.id, r.rate, r.outof, r.rate_type, r.comment, sy.task_id from student s left join syllabus sy on s.student_id=sy.student_id left join rating r on r.entity=sy.task_id"+
    	" where s.standard_id=:standardId and sy.subject_Id=:subjectId";*/
    	String query = "select s.student_id, s.f_name,s.m_name,s.l_name, coalesce(r.id,0) rate_id, coalesce(r.rate,0) rate, coalesce(r.outof,0) rate_outof, "+
    			"coalesce(st.task_name,'') task_name, coalesce(r.comment,'') rate_comment, st.task_id "+
    			"from syllabus st inner join Student s on s.student_id=st.student_id left join rating  r on r.entity=st.task_id "+
    			"where st.pid in (select task_id from syllabus sy where sy.standard_id =:standardId and sy.subject_id=:subjectId and date(sy.created_date) = :taskDate and sy.pid =0)";
    	Query q= em.createNativeQuery(query);
    	q.setParameter("standardId", standardId);
    	q.setParameter("subjectId", subjectId);
    	q.setParameter("taskDate", taskDate);
    	List<Object[]> results = q.getResultList();
    	List<RatingTask> ratingTask = new ArrayList<RatingTask>();
    	for(Object[] oj : results){
    		RatingTask rt = new RatingTask();
    		rt.setStudenID((Integer)oj[0]);
    		rt.setStudentName((String)oj[1]+" "+(StringUtils.isEmpty((String)oj[2]) ? "" :" ")+(String)oj[3]);
    		rt.setRateId(oj[4] !=null ? ((BigInteger)oj[4]).longValue(): 0);
    		rt.setRate(((BigInteger)oj[5]).intValue());
    		rt.setOutOf(((BigInteger)oj[6]).intValue());
    		rt.setTaskType((String)oj[7]);
    		rt.setComment((String)oj[8]);
    		rt.setSyllabusId(oj[9] !=null ? ((BigInteger)oj[9]).longValue(): 0);
    		ratingTask.add(rt);
    	}
    	return ratingTask;
    }

	@Override
	public Syllabus getParentSyllabus(long id) {
		Syllabus syllabus = null;
		try{
			EntityManager em = DbCon.getEntityManager();
			Query q= em.createQuery("select syp from Syllabus syp, Syllabus syp1 where syp.taskId = syp1.pId and syp1.taskId=:task_id");
	    	q.setParameter("task_id", id);
	    	syllabus = (Syllabus) q.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return syllabus;
	}

	@Override
	public Map<String, Long> getParentStudentBySyllabus(long syllabusId) {
		Map<String, Long> parentStudentIds =  new HashMap<String, Long>();
		try{
			EntityManager em = DbCon.getEntityManager();
			Query q= em.createNativeQuery("select coalesce(s.parent,0),coalesce(s.user_id,0)  from syllabus sy inner join student s on s.student_id=sy.student_id where sy.task_id=:syllabusId");
	    	q.setParameter("syllabusId", syllabusId);
	    	Object[] result= (Object[]) q.getSingleResult();
	    	parentStudentIds.put("parent", ((BigInteger)result[0]).longValue());
	    	parentStudentIds.put("student", ((BigInteger)result[1]).longValue());
		}catch(Exception e){
			e.printStackTrace();
		}
    	return parentStudentIds;
	}
}


