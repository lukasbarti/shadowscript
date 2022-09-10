package me.lukasbarti.shadowscript.scope;

import java.util.List;

public record Block(List<Block> block, String code) {


}
