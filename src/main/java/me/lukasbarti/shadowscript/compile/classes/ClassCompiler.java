package me.lukasbarti.shadowscript.compile.classes;

import me.lukasbarti.shadowscript.compile.BlockCompiler;
import me.lukasbarti.shadowscript.compile.CompileState;
import me.lukasbarti.shadowscript.scope.Block;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class ClassCompiler implements BlockCompiler {

    @Override
    public List<String> compile(Block block, CompileState compileState) {
        var lines = new LinkedList<String>();
        var classDeclaration = compileState.blocks.get(compileState.currentIndex).code();

        var classNamePattern = Pattern.compile("class\\s+(.+)");
        var classNameMatcher = classNamePattern.matcher(classDeclaration);

        if (!classNameMatcher.find())
            throw new RuntimeException("No valid class declaration could be found: " + classDeclaration);

        var className = classNameMatcher.group(1);

        lines.add("-- CLASS: " + className);
        return lines;
    }
}