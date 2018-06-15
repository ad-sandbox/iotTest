package de.appdynamics.ace.test.iot.rmi;

import com.appdynamics.ace.util.cli.api.api.AbstractCommand;
import com.appdynamics.ace.util.cli.api.api.Command;
import com.appdynamics.ace.util.cli.api.api.CommandException;
import com.appdynamics.ace.util.cli.api.api.OptionWrapper;
import com.appdynamics.iot.AgentConfiguration;
import com.appdynamics.iot.DeviceInfo;
import com.appdynamics.iot.Instrumentation;
import com.appdynamics.iot.VersionInfo;
import com.appdynamics.iot.events.CustomEvent;
import com.appdynamics.iot.events.NetworkRequestEvent;
import de.appdynamics.ace.test.iot.rmi.impl.IRMITestServer;
import de.appdynamics.ace.test.iot.rmi.impl.RMIMessageObject;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import static de.appdynamics.ace.test.iot.rmi.CLIOptions.ARG_PORT;

public class ClientCmd extends AbstractCommand {

    Logger logger = LoggerFactory.getLogger(ClientCmd.class);

    @Override
    protected List<Option> getCLIOptionsImpl() {
        return CLIOptions.generateBasicOptions();
    }

    @Override
    protected int executeImpl(OptionWrapper options) throws CommandException {

        // App Key: AD-AAB-AAJ-ZVY
        // Collector URL: https://iot-col.eum-appdynamics.com

        AgentConfiguration.Builder agentConfigBuilder = AgentConfiguration.builder();
        AgentConfiguration agentConfig = agentConfigBuilder
                .withAppKey("AD-AAB-AAJ-ZVY")
                .withCollectorUrl("https://iot-col.eum-appdynamics.com")
                .build();


        DeviceInfo.Builder deviceInfoBuilder = DeviceInfo.builder("POS-MASTER", UUID.randomUUID().toString());
        DeviceInfo deviceInfo = deviceInfoBuilder.withDeviceName("POS").build();


        VersionInfo.Builder versionInfoBuilder = VersionInfo.builder();
        VersionInfo versionInfo = versionInfoBuilder
                .withFirmwareVersion("2.3.4")
                .withHardwareVersion("1.6.7")
                .withOsVersion("8.9.9")
                .withSoftwareVersion("3.1.1").build();

        Instrumentation.start(agentConfig, deviceInfo, versionInfo);

        try {
            Registry reg = LocateRegistry.getRegistry("localhost", Integer.parseInt(options.getOptionValue(ARG_PORT)));
            IRMITestServer stub = (IRMITestServer) reg.lookup("TST");
            for (;;) {
                CustomEvent event = CustomEvent.builder("TestEvent1", "MyTester")
                        .withTimestamp(System.currentTimeMillis())
                        .withDuration(345)
                        .build();

                Instrumentation.addEvent(event);

                NetworkRequestEvent.Builder builder = NetworkRequestEvent.builder(new URL("http://plain.test.url.de"))
                        .withTimestamp(System.currentTimeMillis());

                String msg = new Date().toString();
                logger.debug("   Send Data:"+msg);
                long c = System.currentTimeMillis();

                RMIMessageObject result = stub.sayHello(msg);

                logger.debug("   Received:"+result.getMsg());
                logger.debug("   Headers:"+result.getHeaders());

                long d = System.currentTimeMillis()-c;
                logger.debug("+"+d+" ms");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Instrumentation.addEvent(builder.withStatusCode(200)
                        .withDuration(d)
                        .withServerResponseHeaders(convertHeaders(result.getHeaders())).build());

                // Force all to be send
                Instrumentation.sendAllEvents();
            }
        } catch (RemoteException e) {
            logger.error("Couldn Find Registy at localhost:"+options.getOptionValue(ARG_PORT),e);
        } catch (NotBoundException e) {
            logger.error("Error locating Stubb",e);
        } catch (MalformedURLException e) {
            logger.error("Error creating URL",e);       }
        return 0;
    }

    private Map<String, List<String>> convertHeaders(Map<String, String> headers) {
        Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<String,String> e : headers.entrySet()) {

            List<String> l = new ArrayList<>();
            l.add(e.getValue());
            result.put(e.getKey(),l);
        }
        return result;
    }

    @Override
    public String getName() {
        return "Client";
    }

    @Override
    public String getDescription() {
        return "Start Client PRocess";
    }
}
