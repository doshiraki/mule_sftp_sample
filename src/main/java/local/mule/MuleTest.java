package local.mule;

import org.mule.DefaultMuleMessage;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.config.ConfigurationBuilder;
import org.mule.api.config.MuleConfiguration;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.context.MuleContextBuilder;
import org.mule.api.context.MuleContextFactory;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.registry.MuleRegistry;
import org.mule.session.DefaultMuleSession;
import org.mule.DefaultMuleEvent;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.MessageExchangePattern;

import org.mule.api.construct.FlowConstruct;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transport.Connector;
import org.mule.config.DefaultMuleConfiguration;
import org.mule.config.builders.DefaultsConfigurationBuilder;
import org.mule.config.builders.SimpleConfigurationBuilder;
import org.mule.context.DefaultMuleContextBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.context.notification.MuleContextNotification;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.construct.Flow;
import java.io.File;
import java.util.logging.Logger;
import java.util.Iterator;
import org.mule.util.concurrent.Latch;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.context.notification.MuleContextNotification;
import java.util.concurrent.atomic.AtomicReference;
import com.fasterxml.jackson.databind.JsonNode;

public class MuleTest {
  static Logger logger = Logger.getLogger(MuleTest.class.getName());
  Properties properties;
  String flowName;

  public void setStartUpProperties(JsonNode json) {
    properties = new Properties();
    Iterator<String> fieldNames = json.fieldNames();
    while (fieldNames.hasNext()) {
      String key = fieldNames.next();
      properties.setProperty(key, json.get(key).asText());
    }
  }

  public void setFlowName(String flowName) {
    this.flowName = flowName;
  }

  public void exec(String files[]) {
    MuleContext muleContext;

    // Make MuleContextFactory
    MuleContextFactory muleContextFactory = new DefaultMuleContextFactory();

    List<ConfigurationBuilder> builders = new ArrayList<ConfigurationBuilder>();
    builders.add(new SimpleConfigurationBuilder(properties));
    for (int i = 0; i < files.length; i++) {
      try {
        builders.add(new SpringXmlConfigurationBuilder(files[i]));
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
    }

    MuleContextBuilder contextBuilder = new DefaultMuleContextBuilder();
    DefaultMuleConfiguration muleConfiguration = new DefaultMuleConfiguration();
    muleConfiguration.setWorkingDirectory(System.getProperty("user.dir") + "/work");
    logger.info("Using working directory for test: " + muleConfiguration.getWorkingDirectory());
    contextBuilder.setMuleConfiguration(muleConfiguration);
    try {
      muleContext = muleContextFactory.createMuleContext(builders, contextBuilder);
      MuleRegistry muleRegistry = muleContext.getRegistry();
      Flow f = (Flow) muleRegistry.lookupFlowConstruct(flowName);
      muleContext.start();
      f.start();
      Thread.sleep(2000);
      f.stop();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } finally {
      System.out.println("ok");
    }
    muleContext.dispose();
  }
}
