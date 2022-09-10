package me.lukasbarti.shadowscript.compile;

import me.lukasbarti.shadowscript.scope.Block;

import java.util.ArrayList;
import java.util.List;

public class CompileState {

    public List<Block> blocks;
    public int currentIndex;
    public List<String> outputLines;
    public final ShadowCompiler compilerInstance;

    private CompileState(List<Block> blocks, int currentIndex, List<String> outputLines, ShadowCompiler compilerInstance) {
        this.blocks = blocks;
        this.currentIndex = currentIndex;
        this.outputLines = outputLines;
        this.compilerInstance = compilerInstance;
    }

    public static CompileState withBlocks(List<Block> blocks, ShadowCompiler compilerInstance) {
        return new CompileState(blocks, 0, new ArrayList<>(), compilerInstance);
    }

}
