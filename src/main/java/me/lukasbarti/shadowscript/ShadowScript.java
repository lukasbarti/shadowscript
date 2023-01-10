package me.lukasbarti.shadowscript;

import me.lukasbarti.shadowscript.compile.ShadowCompiler;
import me.lukasbarti.shadowscript.scope.Block;
import me.lukasbarti.shadowscript.scope.WhiteSpaceBlockParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.file.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class ShadowScript {
    public static void main(String[] args) {
        var parser = new DefaultParser();
        var options = ShadowScriptUsage.generateUsageOptions();
        try {
            var line = parser.parse(options, args);

            var inputPath = Path.of(line.getOptionValue("input"));
            var outputPath = Path.of(line.getOptionValue("output"));

            if (PathUtils.isDirectory(inputPath) != PathUtils.isDirectory(outputPath)) {
                System.err.println("Both input and output must either be a file or a directory");
                return;
            }

            var inputLines = Files.readAllLines(inputPath);
            var scopeProvider = new WhiteSpaceBlockParser(6);

            var compiler = new ShadowCompiler(scopeProvider);
            var result = compiler.compile(inputLines);


            Files.writeString(outputPath, result);
        } catch (ParseException e) {
            var helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("shadowscript", options);
            System.err.println("Could not parse command line inputs. Reason: " + e.getMessage() + ".");
        } catch (InvalidPathException e) {
            System.err.println("Could not parse either input or output path.");
        } catch (IOException e) {
            System.err.println("Could not access the filesystem.");
            e.printStackTrace();
        }
    }

    private static void printScope(Block block, int tabs) {
        block.block().forEach(childBlock -> {
            if (childBlock.block() == null)
                System.out.println("\t".repeat(tabs) + childBlock.code());
            else
                ShadowScript.printScope(childBlock, tabs + 1);
        });
    }
}