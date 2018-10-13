package edu.returnsvoid.main.options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class MandelBreakerCLIOptions {

    private static final String MIN_C_REAL = "mincr";
    private static final String MAX_C_REAL = "maxcr";
    private static final String MIN_C_IMG = "minci";
    private static final String MAX_C_IMG = "maxci";
    private static final String PART_WIDTH = "px";
    private static final String PART_HEIGHT = "py";
    private static final String WIDTH = "x";
    private static final String HEIGHT = "y";
    private static final String MAX_ITERATIONS = "i";
    private static final String SERVERS = "s";

    private MandelBreakerCLIOptions() {}

    public static Options initOptions() {
        Options result = new Options();

        result.addOption(Option.builder(MIN_C_REAL)
                .desc("Minimum real value of C across picture")
                .longOpt("mincreal")
                .numberOfArgs(1)
                .required(true)
//                .argName("minimumcreal")
                .build());

        result.addOption(Option.builder(MAX_C_REAL)
                .desc("Maximum real value of C across picture")
                .longOpt("maxcreal")
                .numberOfArgs(1)
                .required(true)
//                .argName("maximumcreal")
                .build());

        result.addOption(Option.builder(MIN_C_IMG)
                .desc("Minimum imaginary value of C across picture")
                .longOpt("mincimg")
                .numberOfArgs(1)
                .required(true)
//                .argName("minimumcimaginary")
                .build());

        result.addOption(Option.builder(MAX_C_IMG)
                .desc("Maximum imaginary value of C across picture")
                .longOpt("maxcimg")
                .numberOfArgs(1)
                .required(true)
//                .argName("minimumcimaginary")
                .build());

        result.addOption(Option.builder(PART_WIDTH)
                .desc("Width of each part of the image to send for calculation")
                .longOpt("part-width")
                .numberOfArgs(1)
                .required(true)
//                .argName("partwidth")
                .build());
        result.addOption(Option.builder(PART_HEIGHT)
                .desc("Height of each part of the image to send for calculation")
                .longOpt("part-height")
                .numberOfArgs(1)
                .required(true)
//                .argName("MinimumCReal")
                .build());

        result.addOption(Option.builder(WIDTH)
                .desc("Width of complete image")
                .longOpt("width")
                .numberOfArgs(1)
                .required(true)
//                .argName("MinimumCReal")
                .build());

        result.addOption(Option.builder(HEIGHT)
                .desc("Height of complete image")
                .longOpt("height")
                .numberOfArgs(1)
                .required(true)
//                .argName("MinimumCReal")
                .build());

        result.addOption(Option.builder(MAX_ITERATIONS)
                .desc("Maximum number of iterations to perform before considering value as bounded")
                .longOpt("max-iterations")
                .numberOfArgs(1)
                .required(true)
                .type(Integer.class)
//                .argName("MinimumCReal")
                .build());

        result.addOption(Option.builder(SERVERS)
                .desc("List of servers to send jobs to")
                .longOpt("servers")
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .required(true)
//                .argName("servers")
                .build());

        return result;
    }
}
