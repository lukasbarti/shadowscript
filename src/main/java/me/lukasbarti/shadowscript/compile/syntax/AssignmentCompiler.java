package me.lukasbarti.shadowscript.compile.syntax;

import me.lukasbarti.shadowscript.compile.BlockCompiler;
import me.lukasbarti.shadowscript.compile.CompileState;
import me.lukasbarti.shadowscript.scope.Block;

import java.util.Collections;
import java.util.List;

public class AssignmentCompiler implements BlockCompiler {

    private final static String GLOBAL_KEYWORD = "global";

    @Override
    public List<String> compile(Block block, CompileState compileState) {
        var line = block.code();
        boolean isGlobal = line.startsWith(GLOBAL_KEYWORD + " ");

        if (isGlobal) {
            line = line.substring((GLOBAL_KEYWORD + " ").length());
        } else {
            line = "local " + line;
        }
        return Collections.singletonList(line);
    }
}
