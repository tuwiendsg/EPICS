package at.ac.tuwien.dsg.jbpmengine.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


public class JBPMApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public JBPMApplication() {
		singletons.add(new JBPMRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
