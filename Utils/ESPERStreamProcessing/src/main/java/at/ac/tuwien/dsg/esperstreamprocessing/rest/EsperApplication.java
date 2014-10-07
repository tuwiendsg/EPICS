package at.ac.tuwien.dsg.esperstreamprocessing.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;



public class EsperApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public EsperApplication() {
		singletons.add(new EsperRESTWS());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
