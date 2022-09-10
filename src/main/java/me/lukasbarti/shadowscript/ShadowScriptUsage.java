package me.lukasbarti.shadowscript;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class ShadowScriptUsage {

    public static Options generateUsageOptions() {
        var options = new Options();

        var helpOption = Option.builder("h")
                .longOpt("help")
                .desc("Print this usage guide")
                .build();


        var inputOption = Option.builder("i")
                .argName("file/dir")
                .longOpt("input")
                .desc("The input file/directory to compile")
                .hasArg(true)
                .required()
                .build();

        var outputOption = Option.builder("o")
                .argName("file/dir")
                .longOpt("output")
                .desc("The output file/directory to store the compiled script(s) in")
                .hasArg(true)
                .required()
                .build();


        options.addOption(helpOption);
        options.addOption(inputOption);
        options.addOption(outputOption);
        return options;
    }

}
