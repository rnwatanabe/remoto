package br.remoto.model.Musculotendon.Tendon.Innerveted.Lumped;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.model.Musculotendon.Tendon.TendonSuperClass;
import br.remoto.model.Musculotendon.Tendon.Innerveted.InnervatedTendonSuperClass;
import br.remoto.util.Signal;


public class LumpedSuperClass extends InnervatedTendonSuperClass
{
	public LumpedSuperClass (Configuration conf){
		super(conf);
	}
}

