package de.appdynamics.ace.test.iot.rmi;

import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.List;

public class CLIOptions {
    public static List<Option> generateBasicOptions() {
        List<Option> options = new ArrayList<>();


        Option o;
        options.add(o = new Option ("port",true,"portnumber"));
        o.setRequired(true);

        return options;
    }
}
