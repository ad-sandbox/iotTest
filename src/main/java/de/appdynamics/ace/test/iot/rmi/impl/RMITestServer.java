package de.appdynamics.ace.test.iot.rmi.impl;

import com.appdynamics.apm.appagent.api.AgentDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Random;

public class RMITestServer implements IRMITestServer{


    private final Random _rnd;
    Logger logger = LoggerFactory.getLogger(RMITestServer.class);

    private String _prefix;

    public RMITestServer(String prefix) {
        _prefix = prefix;
        _rnd = new Random();

    }

    @Override
    public RMIMessageObject sayHello(String s) throws RemoteException{
        AgentDelegate.getEndUserMonitoringDelegate().filterStart();
        RMIMessageObject result = sayHelloImpl(s);
        AgentDelegate.getEndUserMonitoringDelegate().filterEnd();

        return result;
    }

    private RMIMessageObject sayHelloImpl(String s) {

        logger.debug("Message Received "+s);
        // Add some random Delay
        try {
            Thread.sleep(200+_rnd.nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RMIMessageObject erg = new RMIMessageObject(_prefix + s);
        String f = AgentDelegate.getEndUserMonitoringDelegate().getFooter();
        HashMap<String, String> footer = unwrapAdrum(f);


        logger.debug("Footer:"+footer);
        erg.setHeaders(footer);
        return erg;
    }

    private static HashMap<String, String> unwrapAdrum(String footer) {
       /* need to remove the html coding from
           //<![CDATA[
           if (window.ADRUM) { ADRUM.footerMetadataChunks = ["g%3A4dc431f0-c0c3-4b04-a120-3c17a85f2698","n%3Acustomer1_7ed95886-faaa-4cc2-83cb-10212a6c852d","i%3A150","e%3A1010","d%3A1005"]; }
           //]]>
       */
//        System.out.println("footer:"+footer);
        if (footer == null || footer.length()<=0)
        {   return new HashMap<String,String>();
        }
        else
        {   footer = footer.replaceAll("\n","")
                .replaceAll("^[^\\\"]+(.*)\\];.*","$1")
                .replaceAll("\"","");
            String[] parts = footer.split(",");
            int x = 0;
            HashMap<String, String> result = new HashMap<String, String>();
            for (String l:parts) {
                result.put("ADRUM_"+(x++),l.replaceAll("%3A",":"));
            }
            return result;
        }
    }
}
