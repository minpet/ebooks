package com.minpet.test;

import java.io.IOException;
import java.net.URL;

import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

import static org.junit.Assert.assertNotNull;

public class CreateJndiResource implements ServerSetupTask{

	private static final Logger LOGGER = Logger.getLogger(CreateJndiResource.class);

	@Override
	public void setup(ManagementClient mClient, String arg1) throws Exception {
		/*
		  /subsybsystem=naming/binding=java\:global\/videos\/store:add(binding=simple, type=java.net.URL, value=plpl)
		 */
		URL store = CreateJndiResource.class.getResource("/store");
		
		LOGGER.warn("store is "+store);
		
		assertNotNull(store);
				
		addJndi("java.net.URL","java:global/ebooks/bookstore", store.toExternalForm(),mClient);
		addJndi("java.lang.String","java:global/elasticsearch/host","localhost",mClient);
		addJndi("java.lang.Integer","java:global/elasticsearch/port","12200",mClient);
		
	}

	private void addJndi(String type, String name, String value, ManagementClient mClient) throws IOException {
		ModelNode address = new ModelNode();
		address.add("subsystem","naming");
		address.add("binding",name);
		
		String dmrStr="{"+
						"\"binding-type\" => \"simple\" "+
						", \"type\" => \""+type+"\" "+
						", \"value\" => \""+value+"\""+
						"}";
		ModelNode dmr = ModelNode.fromString(dmrStr);
		dmr.get("operation").set("add");
		dmr.get("address").set(address);
		
		mClient.getControllerClient().execute(dmr);
	}
	
	@Override
	public void tearDown(ManagementClient arg0, String arg1) throws Exception {
		
	}

}
