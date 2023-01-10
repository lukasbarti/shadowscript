package me.lukasbarti.shadowscript.compile.syntax;

import me.lukasbarti.shadowscript.compile.BlockCompiler;
import me.lukasbarti.shadowscript.compile.CompileState;
import me.lukasbarti.shadowscript.scope.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class AssignmentCompiler implements BlockCompiler {

    private static final String GLOBAL_KEYWORD = "global";
    private static final Pattern LAMBDA_PATTERN = Pattern.compile("[(](.*)+[)]:");

    @Override
    public List<String> compile(Block block, CompileState compileState) {
        var lines = new ArrayList<String>();
        var declaration = block.code();
        boolean isGlobal = declaration.startsWith(AssignmentCompiler.GLOBAL_KEYWORD + " ");
        boolean isClassMember = declaration.startsWith("@");

        var parameterMatcher = AssignmentCompiler.LAMBDA_PATTERN.matcher(declaration);

        var isLambda = parameterMatcher.find();

        if (isLambda) {
            declaration = declaration.replace(parameterMatcher.group(), "function(" + parameterMatcher.group(1) + ")");
        }

        if (isGlobal) {
            declaration = declaration.substring((AssignmentCompiler.GLOBAL_KEYWORD + " ").length());
        } else if (isClassMember) {
            declaration = "self." + declaration.substring(1);
        } else {
            declaration = "local " + declaration;
        }

        lines.add(declaration);
        if(isLambda) {
            var nextBlock = compileState.blocks.get(compileState.currentIndex + 1);
            lines.addAll(compileState.compilerInstance.compileBlock(nextBlock, compileState).stream().map(line -> "  " + line).toList());
            lines.add("end");
        }

        return lines;
    }
}
