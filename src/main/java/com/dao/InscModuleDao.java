package com.dao;

import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Module;

public interface InscModuleDao  extends IDao<Long, InscriptionModule>{

	public boolean exists(InscriptionPedagogique inscPdg,Module m);

}
