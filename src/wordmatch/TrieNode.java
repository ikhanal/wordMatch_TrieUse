package wordmatch;

/**
 *
 * @author Ishwor
 */
import java.util.*;

public class TrieNode {
    // declare the variables

    private boolean isFinalChar;
    private char charValue;
    private int depth;
    private Map<Character, TrieNode> childrenMap;

    // create empty root trie node
    public TrieNode() {
    }
    // to be called by addChild only

    private TrieNode(char charValue) {
        this.charValue = charValue;
        childrenMap = null;
    }
    // adds child to the existing Trie node, if there is no child with given character value present already

    public boolean addChild(char charValue) {
        if (childrenMap == null) {
            childrenMap = new TreeMap<Character, TrieNode>();
        }
        Character charValueObject = Character.valueOf(charValue);
        if (childrenMap.containsKey(charValueObject)) {
            return false;
        }
        childrenMap.put(charValueObject, new TrieNode(charValue).setDepth(this.depth + 1));
        return true;
    }
    // returns the child TrieNode if it exists ( true ) otherwise Null

    public TrieNode getChild(char charValue) {
        if (childrenMap == null) {
            return null;
        }
        return childrenMap.get(Character.valueOf(charValue));
    }
    // return Set of all the children char values of the current Trie NOde

    public Set<Character> getChildrenValues() {
        if (childrenMap == null) {
            return null;
        }
        return childrenMap.keySet();
    }
// return Set of all the children nodes of current TrieNode

    public Set<TrieNode> getChildrenNodes() {
        if (childrenMap == null) {
            return null;
        }
        Set<TrieNode> trieNodes = new HashSet<TrieNode>();
        for (Character childChar : childrenMap.keySet()) {
            trieNodes.add(getChild(childChar));
        }
        return trieNodes;
    }

    public char getCharValue() {
        return charValue;
    }

    public boolean isFinalChar() {
        return isFinalChar;
    }

    public void setFinalChar(boolean isFinalChar) {
        this.isFinalChar = isFinalChar;
    }

    public int getDepth() {
        return depth;
    }

    public TrieNode setDepth(int depth) {
        this.depth = depth;
        return this;
    }
    // to override

    public int hashCode() {
        return charValue + (childrenMap != null ? childrenMap.hashCode() : 0);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Node Value :" + charValue + "; isFinalChar :" + isFinalChar + "; depth :" + depth + "children:");
        if (childrenMap != null) {
            return stringBuilder.append(childrenMap.keySet().toString()).toString();
        }
        return stringBuilder.append(" No Childrens").toString();
    }
}
