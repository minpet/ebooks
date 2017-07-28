package com.minpet.test;

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
		
		ModelNode address = new ModelNode();
		address.add("subsystem","naming");
		address.add("binding","java:global/ebooks/bookstore");
		
		String dmrStr="{"+
						"\"binding-type\" => \"simple\" "+
						", \"type\" => \"java.net.URL\" "+
						", \"value\" => \""+store.toExternalForm()+"\""+
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
