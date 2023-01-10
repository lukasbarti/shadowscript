package me.lukasbarti.shadowscript.compile.structures;

import me.lukasbarti.shadowscript.compile.BlockCompiler;
import me.lukasbarti.shadowscript.compile.CompileState;
import me.lukasbarti.shadowscript.scope.Block;

import java.util.LinkedList;
import java.util.List;

public class ConditionalCompiler implements BlockCompiler {

    public static final List<String> CONDITIONAL_KEYWORDS = List.of("if", "elseif", "else", "while");

    @Override
    public List<String> compile(Block block, CompileState compileState) {
        var lines = new LinkedList<String>();
        var operation = block.code().split(" ")[0];
        var nextBlock = compileState.blocks.get(compileState.currentIndex + 1);

        switch (operation.toLowerCase()) {
            case "if":
            case "elseif":
                lines.add(block.code() + " then");
                lines.addAll(compileState.compilerInstance.compileBlock(nextBlock, compileState).stream().map(line -> "  " + line).toList());
                lines.add("end");
                break;
            case "else":
                lines.add(block.code());
                lines.addAll(compileState.compilerInstance.compileBlock(nextBlock, compileState).stream().map(line -> "  " + line).toList());
                lines.add("end");
            case "while":
                lines.add(block.code() + " do");
                lines.addAll(compileState.compilerInstance.compileBlock(nextBlock, compileState).stream().map(line -> "  " + line).toList());
                lines.add("end");
                break;
            default:
                break;
        }

        return lines;
    }
}
