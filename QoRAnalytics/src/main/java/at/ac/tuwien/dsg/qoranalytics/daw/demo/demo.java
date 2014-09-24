package at.ac.tuwien.dsg.qoranalytics.daw.demo;

import java.util.HashMap;
import java.util.Map;

import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.api.io.ResourceType;
import org.kie.internal.logger.KnowledgeRuntimeLogger;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class demo {
	
	public static final void main(String[] args) {
		try {
			// load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "test", 1000);
			// start a new process instance
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("count", 5);
                        params.put("ac.at.tuwien.dsg.daw1_P_1", 9.8);
			ksession.startProcess("ac.at.tuwien.dsg.daw1", params);
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("daw/daw1.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}
