package wordmatch;

/**
 *
 * @author Ishwor
 */
import java.util.*;

public interface Trie {
// declare the methods in interface to implement later
    public boolean addWords(String wordString);

    public boolean hasString(String wordString);

    public char[] getNextCharacters(String prefixString);
}
