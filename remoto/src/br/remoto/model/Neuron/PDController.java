package br.remoto.model.Neuron;

import br.remoto.model.Ankle;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.vo.NeuronVO;
import br.remoto.util.Distribution;
import br.remoto.util.Signal;


public class PDController extends Neuron
{

	@Override
	public void atualize(double t) {
		// TODO Auto-generated method stub
		
	}
	/*
	private static final long serialVersionUID = 1L;

	public Ankle ankle;
	
	public double kd;
	public double kp;

	public double step = 0.05;
	public double angleSetPoint = 4;
	
	protected double tTerminalSpike;
	
	
	
	public PDController()
	{
	}
	
	
	public PDController(NeuronVO neu, int index, Miscellaneous misc, ModulatingSignal signal)
	{
		super(neu, index, misc);
		
		reset();
	}
	
	
	public void reset()
	{
		super.reset();
		
		tTerminalSpike = 0;
		addSpike( 0 );
	}
	

	public void atualize(double t) 
	{
		try
		{
			// Propagate spike to all post-synaptic neurons
			if( t > tTerminalSpike )
			{
				propagateSpike(t);
	
				addSpike(t);
			}
		}
		catch (Exception e) 
		{
			System.out.println( "Error while atualizing TR: " + cd );
		}
	}


	protected void addSpike(double t)
	{
		if( ankle.getDelayList().size() < 1 )
			return;
		
		Signal feedbackSignal0 = (Signal)ankle.getDelayList().get(0);
		Signal feedbackSignal1 = (Signal)ankle.getDelayList().get(1);
		
		if( feedbackSignal1.getTime() < t )
			return;
		
		double input0 = angleSetPoint - feedbackSignal0.getValue();
		double input1 = angleSetPoint - feedbackSignal1.getValue();
		
		double freq = kd * (input1 - input0)/step + kp * (input1 + input0)/2.0;

		double mean = 1.0 / freq;
		
		ankle.getDelayList().remove(1);
		ankle.getDelayList().remove(0);
		
		tTerminalSpike += Distribution.poissonPoint( mean );
		
		terminalSpikeTrain.add( new Double(tTerminalSpike) );
	}


	public double getAngleSetPoint() {
		return angleSetPoint;
	}


	public void setAngleSetPoint(double angleSetPoint) {
		this.angleSetPoint = angleSetPoint;
	}


	public double getKd() {
		return kd;
	}


	public void setKd(double kd) {
		this.kd = kd;
	}


	public double getKp() {
		return kp;
	}


	public void setKp(double kp) {
		this.kp = kp;
	}


	public double getStep() {
		return step;
	}


	public void setStep(double step) {
		this.step = step;
	}
	
	*/
}
