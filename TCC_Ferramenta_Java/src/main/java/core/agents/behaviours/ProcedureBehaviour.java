package core.agents.behaviours;

import java.util.ArrayList;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public interface ProcedureBehaviour {
	
	public void start();
	public void start(ACLMessage msg);
	public void start(Object object);
	
	public Behaviour getBehaviour();
	
	

}
