
package br.remoto.control.action;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.NeuronForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.Electrotonic;
import br.remoto.model.vo.NeuronVO;
import br.remoto.util.ElectroCalculation;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ElectrotonicAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		NeuronForm neuronForm = (NeuronForm) form;
		Configuration conf = getConfiguration(req);
		
		ElectroCalculation calc = new ElectroCalculation(); 
		DecimalFormat df = new DecimalFormat("0.00");
		
		ArrayList electrotonics = new ArrayList();
		
		String task = (String)req.getParameter("task");
		
		if( task == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		// For MN's properties
		if( "MN".equals( task ) )
		{
			ArrayList motoneurons = (ArrayList)neuronForm.getMotoneurons();

			NeuronVO reference;
			NeuronVO referenceS = null;
			NeuronVO referenceFR = null;
			NeuronVO referenceFF = null;
			
			for(int i = 0; i < motoneurons.size(); i++)
			{
				reference = (NeuronVO)motoneurons.get(i);
				if( reference.getType().equals( ReMoto.S ) )
					referenceS = reference;
				else if( reference.getType().equals( ReMoto.FR ) )
					referenceFR = reference;
				else if( reference.getType().equals( ReMoto.FF ) )
					referenceFF = reference;
					
			}

			for(int i = 0; i < motoneurons.size(); i++)
			{
				reference = (NeuronVO)motoneurons.get(i);

				// Get pattern for a MN in the middle of the pool
				NeuronVO pattern = calc.getPatternMerged(conf, reference, referenceS, referenceFR, referenceFF, 0.5);
				
				Electrotonic electrotonic = new Electrotonic();
				
				//double rheobase = pattern.getRheobase1();
				double dSoma = pattern.getDsoma1(); 
				double dDend = pattern.getDdend1();
				double lSoma = pattern.getLsoma1();
				double lDend = pattern.getLdend1();
				double rmSoma = pattern.getRmSoma1();
				double rmDend = pattern.getRmDend1();
				double ri = pattern.getRi();
				double cm = pattern.getCm();
				
				double areaDend = Math.PI * dDend * lDend;
				double areaSoma = Math.PI * dSoma * lSoma;
	
				double gCoupling = calc.calcGCoupling(ri, lDend, dDend, lSoma, dSoma);
				double gLeakSoma = calc.calcGLeak(areaSoma, rmSoma);
				double gLeakDend = calc.calcGLeak(areaDend, rmDend);
				double rns = calc.calcRn(gCoupling, gLeakSoma, gLeakDend);
				double rnd = calc.calcRn(gCoupling, gLeakDend, gLeakSoma);
				double cs = calc.calcCapacitance(cm, areaSoma);
				double cd = calc.calcCapacitance(cm, areaDend);
				double tauMemb = calc.calcTauMemb(cm, rmSoma, rmDend, areaSoma, areaDend);
	
				//double threshold = rns * rheobase;
				double threshold = pattern.getThreshold1();
				
				electrotonic.setType( reference.getType() );
				electrotonic.setRnd( df.format( rnd ) );
				electrotonic.setRns( df.format( rns ) );
				electrotonic.setCd( df.format( cd ) );
				electrotonic.setCs( df.format( cs ) );
				electrotonic.setGcoupling( df.format( gCoupling ) );
				electrotonic.setGleakDend( df.format( gLeakDend ) );
				electrotonic.setGleakSoma( df.format( gLeakSoma ) );
				electrotonic.setTauMemb( df.format( tauMemb ) );
				//electrotonic.setRheobase( df.format( rheobase ) );
				electrotonic.setThreshold( df.format( threshold ) );
				
				//Testing
				electrotonic.setThresholdCa( df.format( pattern.getThresholdCa1() ) );
				
				electrotonics.add( electrotonic );
			}
			
			conf.setListNeuronTypes( neuronForm.getMotoneurons() );
		}
		// For IN's properties
		else if( "IN".equals( task ) )
		{
			ArrayList interneurons = (ArrayList)neuronForm.getInterneurons();

			// For IN's properties
			for(int i = 0; i < interneurons.size(); i++)
			{
				NeuronVO in = (NeuronVO)interneurons.get(i);
				
				/*
				double dSoma = in.getDsoma() / 1e4; 
				double lSoma = in.getLsoma() / 1e4;

				double area = Math.PI * dSoma * lSoma;
				*/
				
				double area = in.getTotalArea() / 1e8;
				
				double gLeak = calc.calcGLeak(area, in.getRmSoma1());
				double rn = 1 / gLeak;

				double cs = calc.calcCapacitance(in.getCm(), area);
				double tauMemb = cs * rn;
				
				//double threshold = rn * (in.getRheobase1() + in.getRheobase2())/2;
				
				// OBSERVAR SE ESTÁ CORRETO
				
				double threshold = (in.getThreshold1() + in.getThreshold2())/2;
				
				Electrotonic electrotonic = new Electrotonic();
				
				electrotonic.setType( in.getType() );
				electrotonic.setGleakSoma( df.format( gLeak ) );
				electrotonic.setRns( df.format( rn ) );
				electrotonic.setCs( df.format( cs ) );
				electrotonic.setTauMemb( df.format( tauMemb ) );
				electrotonic.setThreshold( df.format( threshold ) );
				
				electrotonics.add( electrotonic );
			}
			
			conf.setListNeuronTypes( neuronForm.getInterneurons() );
		}
		
		neuronForm.setElectrotonics( electrotonics );
		
		af = map.findForward( "configuration" );
		  
		return af;
	}

}
