package me.lukasbarti.shadowscript.scope;

import java.util.ArrayList;
import java.util.List;

public record WhiteSpaceBlockParser(int spacesPerTab) implements BlockParser {

    @Override
    public Block parseBlocks(List<String> code) {
        return this.parseBlocks(code, 0);
    }

    private Block parseBlocks(List<String> code, int previousWhiteSpaces) {
        var blocks = new ArrayList<Block>();
        for (int i = 0; i < code.size(); i++) {
            var line = code.get(i);
            int whiteSpaces = this.getNumberOfWhiteSpaces(line);

            if (whiteSpaces > previousWhiteSpaces) {
                int lastScopeLine = this.findScopeEnd(code, i);

                blocks.add(this.parseBlocks(code.subList(i, lastScopeLine), whiteSpaces));
                i = lastScopeLine - 1;
            } else {
                blocks.add(new Block(null, line.trim()));
            }
        }
        return new Block(blocks, null);
    }

    private int findScopeEnd(List<String> lines, int startIndex) {
        var startWhiteSpaces = this.getNumberOfWhiteSpaces(lines.get(startIndex));

        while (true) {
            startIndex++;

            if (startIndex >= lines.size())
                return lines.size();

            if (this.getNumberOfWhiteSpaces(lines.get(startIndex)) < startWhiteSpaces) {
                return startIndex;
            }
        }
    }

    private int getNumberOfWhiteSpaces(String line) {
        int spaces = 0;
        for (char c : line.toCharArray()) {
            switch (c) {
                case ' ':
                    spaces++;
                    break;
                case '\t':
                    spaces += this.spacesPerTab;
                    break;
                default:
                    return spaces;
            }
        }
        return spaces;
    }
}
