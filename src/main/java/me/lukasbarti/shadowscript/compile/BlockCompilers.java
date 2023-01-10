package me.lukasbarti.shadowscript.compile;

import me.lukasbarti.shadowscript.compile.classes.ClassCompiler;
import me.lukasbarti.shadowscript.compile.structures.ConditionalCompiler;
import me.lukasbarti.shadowscript.compile.syntax.AssignmentCompiler;
import me.lukasbarti.shadowscript.scope.Block;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class BlockCompilers {

    private static final Map<Predicate<Block>, BlockCompiler> BLOCK_COMPILERS = new LinkedHashMap<>();

    static {
        BlockCompilers.BLOCK_COMPILERS.put(block -> block.code() != null && block.code().startsWith("class "), new ClassCompiler());
        BlockCompilers.BLOCK_COMPILERS.put(block -> block.code() != null && ConditionalCompiler.CONDITIONAL_KEYWORDS.stream().anyMatch(keyword -> block.code().startsWith(keyword + " ")), new ConditionalCompiler());
        BlockCompilers.BLOCK_COMPILERS.put(block -> {
            if(block.code() == null)
                return false;

            var pattern = Pattern.compile("^((?!['\".=]).)+=((?!=).)+$");
            var matcher = pattern.matcher(block.code());

            return matcher.find() && matcher.start() == 0;
        }, new AssignmentCompiler());
    }

    public static BlockCompiler getCompilerForBlock(Block block) {
        return BlockCompilers.BLOCK_COMPILERS.entrySet()
                .stream()
                .filter((entry) -> entry.getKey().test(block))
                .map(Map.Entry::getValue)
                .findFirst().orElse(new DefaultCompiler());
    }

}
