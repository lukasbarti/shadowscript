package me.lukasbarti.shadowscript.compile;

import me.lukasbarti.shadowscript.scope.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompileState {

    public List<Block> blocks;
    public int currentIndex;
    public List<String> outputLines;
    public final ShadowCompiler compilerInstance;
    private final Map<String, Object> customProperties;

    private CompileState(List<Block> blocks, int currentIndex, List<String> outputLines, ShadowCompiler compilerInstance, Map<String, Object> customProperties) {
        this.blocks = blocks;
        this.currentIndex = currentIndex;
        this.outputLines = outputLines;
        this.compilerInstance = compilerInstance;
        this.customProperties = customProperties;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String name) {
        return (T) this.customProperties.get(name);
    }

    public <T> void setProperty(String name, T value) {
        this.customProperties.put(name, value);
    }

    public static CompileState withBlocks(List<Block> blocks, ShadowCompiler compilerInstance) {
        return new CompileState(blocks, 0, new ArrayList<>(), compilerInstance, new HashMap<>());
    }

}
