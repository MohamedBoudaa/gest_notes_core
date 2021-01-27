package com.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.Note;
import com.dao.DaoException;
import com.dao.HibernateGenericDao;
import com.dao.NoteDao;

public class NoteDaoImpl extends HibernateGenericDao<Long, Note> implements NoteDao{

	public NoteDaoImpl() {
		super(Note.class);
	}
	
	public List<HashMap<String, Object>> getModulesNotesByEtudiant(Long isncPedago, Long idNiveau) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultat = null;
		Session s = null;
		Transaction tx = null;
		try {
			s = super.sf.getCurrentSession();
			tx = s.beginTransaction();
			String hql = null;
			Query query;
			hql = "select m.title AS titre ,m.semester as semester,m.coeff AS coeff,n.noteFinal AS noteFinale "
					+ "FROM Module m, Note n "
					+ "INNER JOIN InscriptionModule i ON (i.InscPedago.id =:idInscP) "
					+ "WHERE m.niveau.id =:idN AND n.idNote = i.note.id AND i.module.id=m.id Order by semester";
					
			query = s.createQuery(hql);
			query.setParameter("idInscP", isncPedago);
			query.setParameter("idN", idNiveau);
			
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			resultat = query.list();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new DaoException(e);

		} finally {

			if (s != null && s.isOpen()) {
				s.close();
			}

		}
		return resultat;
	}
	
}
