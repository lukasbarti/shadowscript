package me.lukasbarti.shadowscript.compile;

import me.lukasbarti.shadowscript.scope.Block;

import java.util.List;

public interface BlockCompiler {

    List<String> compile(Block block, CompileState compileState);

}
