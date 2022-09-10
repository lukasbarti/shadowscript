package me.lukasbarti.shadowscript.scope;

import java.util.List;

public interface BlockParser {

    Block parseBlocks(List<String> lines);

}
