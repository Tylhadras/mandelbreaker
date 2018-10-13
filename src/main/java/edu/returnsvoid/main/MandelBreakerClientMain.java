package edu.returnsvoid.main;

import edu.returnsvoid.main.delegator.MandelBreakerWorkDelegator;
import edu.returnsvoid.main.options.MandelBreakerCLIOptions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MandelBreakerClientMain {

    private static final Logger LOG = LoggerFactory.getLogger(MandelBreakerClientMain.class);
    private static final Options OPTIONS = MandelBreakerCLIOptions.initOptions();

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(OPTIONS, args);
            new MandelBreakerWorkDelegator(commandLine).execute();
        } catch (ParseException e) {
            LOG.error(e.getMessage());
            new HelpFormatter().printHelp("-mincr <double> -maxcr <double> -mincim <double> -maxcim <double> -px <int> -py <int> -x <int> -y <int> -i <int> -s <string...>", OPTIONS);
            System.exit(1);
        }
    }
}
