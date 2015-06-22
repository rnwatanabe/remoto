package br.remoto.model.Musculotendon.Tendon.Innerveted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.model.Musculotendon.Tendon.TendonSuperClass;
import br.remoto.util.Signal;


public class InnervatedTendonSuperClass extends TendonSuperClass
{
	
	public InnervatedTendonSuperClass(Configuration conf){
		super(conf);
		//slackLengthNorm = 0.268 / 3;
		//elasticCoeficientNorm = 37.5 / slackLengthNorm;
	}
	
}

