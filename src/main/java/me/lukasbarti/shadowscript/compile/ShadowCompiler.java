package me.lukasbarti.shadowscript.compile;

import me.lukasbarti.shadowscript.scope.Block;
import me.lukasbarti.shadowscript.scope.BlockParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public record ShadowCompiler(BlockParser blockParser) {

    public String compileFile(File file) throws IOException {
        return this.compile(Files.readAllLines(file.toPath()));
    }

    public String compile(List<String> input) {
        var rootBlock = blockParser.parseBlocks(input);
        var rootState = CompileState.withBlocks(rootBlock.block(), this);
        rootState.outputLines.addAll(this.compileBlock(rootBlock, rootState));

        rootState.outputLines = rootState.outputLines.stream().map(line -> {
            if(line.length() >= 2)
                return line.substring(2);
            return line;
        }).collect(Collectors.toList());

        return String.join(System.lineSeparator(), rootState.outputLines);
    }

    public List<String> compileBlock(Block block, CompileState state) {
        var output = BlockCompilers.getCompilerForBlock(block).compile(block, state);
        state.currentIndex++;
        return output;
    }

}
