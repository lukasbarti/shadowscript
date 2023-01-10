package me.lukasbarti.shadowscript.compile;

import me.lukasbarti.shadowscript.scope.Block;

import java.util.LinkedList;
import java.util.List;

public class DefaultCompiler implements BlockCompiler {

    public static final int SPACES_PER_SCOPE = 2;

    @Override
    public List<String> compile(Block block, CompileState compileState) {
        var lines = new LinkedList<String>();

        if (block.block() != null) {
            var newState = CompileState.withBlocks(block.block(), compileState.compilerInstance);

            while (newState.currentIndex < newState.blocks.size()) {
                var childBlock = newState.blocks.get(newState.currentIndex);
                var blockOutput = compileState.compilerInstance.compileBlock(childBlock, newState).stream().map(line -> " ".repeat(DefaultCompiler.SPACES_PER_SCOPE) + line).toList();
                lines.addAll(blockOutput);
            }

        } else {
            lines.add(block.code());
        }

        return lines;
    }
}
