package com.dao.impl;

import com.bo.Note;
import com.dao.HibernateGenericDao;
import com.dao.NoteDao;

public class NoteDaoImpl extends HibernateGenericDao<Long, Note> implements NoteDao{

	public NoteDaoImpl() {
		super(Note.class);
	}

}
