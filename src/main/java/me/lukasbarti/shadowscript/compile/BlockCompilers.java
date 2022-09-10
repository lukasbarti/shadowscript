package me.lukasbarti.shadowscript.compile;

import me.lukasbarti.shadowscript.compile.classes.ClassCompiler;
import me.lukasbarti.shadowscript.compile.structures.IfCompiler;
import me.lukasbarti.shadowscript.compile.syntax.AssignmentCompiler;
import me.lukasbarti.shadowscript.scope.Block;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class BlockCompilers {

    private final static Map<Predicate<Block>, BlockCompiler> BLOCK_COMPILERS = new LinkedHashMap<>();

    static {
        BLOCK_COMPILERS.put(block -> block.code() != null && block.code().startsWith("class "), new ClassCompiler());
        BLOCK_COMPILERS.put(block -> block.code() != null && (block.code().startsWith("if ") || block.code().startsWith("else ") || block.code().startsWith("elseif")), new IfCompiler());
        BLOCK_COMPILERS.put(block -> {
            if(block.code() == null)
                return false;

            var pattern = Pattern.compile("[^'\".]+=");
            var matcher = pattern.matcher(block.code());

            return matcher.find() && matcher.start() == 0;
        }, new AssignmentCompiler());
    }

    public static BlockCompiler getCompilerForBlock(Block block) {
        return BLOCK_COMPILERS.entrySet()
                .stream()
                .filter((entry) -> entry.getKey().test(block))
                .map(Map.Entry::getValue)
                .findFirst().orElse(new DefaultCompiler());
    }

}
