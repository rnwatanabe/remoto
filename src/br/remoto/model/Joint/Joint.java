package br.remoto.model.Joint;


import java.util.ArrayList;


import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.model.vo.JointVO;
import br.remoto.util.Signal;
import br.remoto.model.Joint.Ankle.AnkleSuperClass;
import br.remoto.model.Joint.Ankle.Models.AnkleIsometricModel;
import br.remoto.model.Joint.Ankle.Models.InvertedPendulumModel;
import br.remoto.model.Joint.Ankle.Models.PositionTaskModel;
import br.remoto.model.Musculotendon.MusculotendonSuperClass;
import br.remoto.model.Musculotendon.Muscle.MuscleSuperClass;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;

public class Joint
{
	
	private static final long serialVersionUID = 1L;
	
	protected AnkleIsometricModel ankleModel;
	protected InvertedPendulumModel ankleModel2;
	protected PositionTaskModel ankleModel3;
	
	Configuration conf;
	String JointModel;
	
	public Joint(Configuration conf){
		this.conf = conf;
		this.JointModel = conf.getCdJointModel();
		System.out.println("Creating Joint");
	}
	
	public void atualize(double t){
		if(JointModel.equals("isometric"))
			ankleModel.atualize(t);
		else if(JointModel.equals("pendulum"))
			ankleModel2.atualize(t);
		else if(JointModel.equals("position"))
			ankleModel3.atualize(t);
	}

	public AnkleIsometricModel getAnkleModel() {
		return ankleModel;
	}

	public void setAnkleModel(AnkleIsometricModel ankleModel) {
		this.ankleModel = ankleModel;
	}
	
	public InvertedPendulumModel getAnkleModel2() {
		return ankleModel2;
	}
	
	public void setAnkleModel2(InvertedPendulumModel ankleModel2) {
		this.ankleModel2 = ankleModel2;
	}
	
	public PositionTaskModel getAnkleModel3() {
		return ankleModel3;
	}
	
	public void setAnkleModel3(PositionTaskModel ankleModel3) {
		this.ankleModel3 = ankleModel3;
	}
	
	public ArrayList getJointAngleStore() {
		if(JointModel.equals("pendulum"))
			return ankleModel2.getJointAngleStore();
		else if(JointModel.equals("position"))
			return ankleModel3.getJointAngleStore();
		else
			return ankleModel.getJointAngleStore();
	}
	
	public ArrayList getJointVelocityStore() {
		if(JointModel.equals("position"))
			return ankleModel3.getJointVelocityStore();
		else
			return ankleModel2.getJointVelocityStore();
	}
	
	public ArrayList getJointCenterMassStore(){
		return ankleModel2.getJointCenterMassStore();
	}
	
	public ArrayList getJointCenterPressureStore(){
		return ankleModel2.getJointCenterPressureStore();
	}
	
	public ArrayList getJointTorqueStore(){
		if(JointModel.equals("pendulum"))
			return ankleModel2.getJointTorqueStore();
		else if(JointModel.equals("position"))
			return ankleModel3.getJointTorqueStore();
		else
			return ankleModel.getJointTorqueStore();
	}

	public ArrayList getJointMuscleTorqueStore(){
		if(JointModel.equals("position"))
			return ankleModel3.getJointMuscleTorqueStore();
		else
			return ankleModel2.getJointMuscleTorqueStore();
	}
	
	public ArrayList getJointGravTorqueStore(){
		if(JointModel.equals("position"))
			return ankleModel3.getJointGravTorqueStore();
		else
			return ankleModel2.getJointGravTorqueStore();
	}
	
	public ArrayList getJointPassiveTorqueStore(){
		if(JointModel.equals("position"))
			return ankleModel3.getJointPassiveTorqueStore();
		else
			return ankleModel2.getJointPassiveTorqueStore();
	}
	
	public ArrayList getJointDisturbanceStore(){
		if(JointModel.equals("position"))
			return ankleModel3.getJointDisturbanceStore();
		else
			return ankleModel2.getJointDisturbanceStore();
	}
	
}
