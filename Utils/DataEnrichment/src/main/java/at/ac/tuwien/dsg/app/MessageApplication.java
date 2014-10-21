package at.ac.tuwien.dsg.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import at.ac.tuwien.dsg.rest.SensorRestService;

public class MessageApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public MessageApplication() {
		singletons.add(new SensorRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
