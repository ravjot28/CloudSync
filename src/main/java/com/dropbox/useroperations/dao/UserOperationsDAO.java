package com.dropbox.useroperations.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.dropbox.dao.DatabaseConfig;
import com.dropbox.useroperations.bean.DropBoxUserBean;
import com.dropbox.useroperations.model.DropBoxRegistrationRequest;

public class UserOperationsDAO {

	public void registerUser(DropBoxRegistrationRequest request)
			throws Exception {

		DropBoxUserBean bean = new DropBoxUserBean();
		bean.setPassword(request.getPassword());
		bean.setUserName(request.getUserName());
		bean.setEmailAddress(request.getEmailAddress());
		bean.setActive("Y");
		Session session = null;

		try {
			session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			session.save(bean);

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public DropBoxUserBean searchUser(String userName) throws Exception {
		DropBoxUserBean user = null;
		try {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			Criteria crit = session.createCriteria(DropBoxUserBean.class);
			Criterion userNameRestriction = Restrictions.eq("userName",
					userName);

			Criterion activeRestriction = Restrictions.eq("active", "Y");

			LogicalExpression andExp = Restrictions.and(userNameRestriction,
					activeRestriction);
			crit.add(andExp);

			List<DropBoxUserBean> userList = ((List<DropBoxUserBean>) crit
					.list());

			if (userList != null && userList.size() > 0) {
				user = userList.get(0);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return user;
	}
	
	
	@SuppressWarnings("unchecked")
	public DropBoxUserBean searchUser(int userId) throws Exception {
		DropBoxUserBean user = null;
		try {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			Criteria crit = session.createCriteria(DropBoxUserBean.class);
			Criterion userNameRestriction = Restrictions.eq("userId",
					userId);

			Criterion activeRestriction = Restrictions.eq("active", "Y");

			LogicalExpression andExp = Restrictions.and(userNameRestriction,
					activeRestriction);
			crit.add(andExp);

			List<DropBoxUserBean> userList = ((List<DropBoxUserBean>) crit
					.list());

			if (userList != null && userList.size() > 0) {
				user = userList.get(0);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public DropBoxUserBean validateUserPasssword(String userName,
			String password) throws Exception {
		DropBoxUserBean user = null;
		try {
			Session session = DatabaseConfig.getSessionFactory().openSession();

			session.beginTransaction();

			Criteria crit = session.createCriteria(DropBoxUserBean.class);
			Criterion userNameRestriction = Restrictions.eq("userName",
					userName);

			Criterion activeRestriction = Restrictions.eq("active", "Y");

			Criterion passwordRestriction = Restrictions.eq("password",
					password);

			LogicalExpression andExp = Restrictions.and(userNameRestriction,
					activeRestriction);

			LogicalExpression andExp1 = Restrictions.and(passwordRestriction,
					andExp);
			crit.add(andExp1);

			List<DropBoxUserBean> userList = ((List<DropBoxUserBean>) crit
					.list());

			if (userList != null && userList.size() > 0) {
				user = userList.get(0);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return user;

	}

}
