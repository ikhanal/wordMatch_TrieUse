package wordmatch;

/**
 *
 * @author Ishwor
 */
import java.io.*;
import java.util.*;

public class WordMatch implements Trie {

// declare variables;   
    private String fileName;
    private TrieNode trieRoot;
    private Set<String> totalLexicons = new HashSet<String>();

    public WordMatch() {
        trieRoot = new TrieNode();
    }
    // display main menu

    public void mainMenu() throws IOException {
        System.out.println("Enter the character 'r', 's', 'w' or 'q'");
        System.out.println("----------------------------------------------------------------");
        System.out.println("r) Read in a text file and add the words in it to the lexicon");
        System.out.println("s) Search for a word");
        System.out.println("w) Write the lexican to a new file");
        System.out.println("q) Quit");
        System.out.println("----------------------------------------------------------------");

        Scanner enter = new Scanner(System.in);
        // to check the user's
        String input = enter.nextLine();
        if (input.length() > 1) {
            System.out.println("Please enter one  character");
            mainMenu();

        }
        char c = input.charAt(0);
        switch (c) {
            case 'r':
                System.out.println("Enter file name");
                enter = new Scanner(System.in);
                fileName = enter.nextLine();
                readData(fileName);
                mainMenu();
                break;
            case 's':
                System.out.println("Enter the searching pattern e.g. ma? or ?o?");
                enter = new Scanner(System.in);
                String key = enter.nextLine().toLowerCase();
                searchLexicon(key);
                mainMenu();
                break;
            case 'w':
                System.out.println("Enter the file name ");
                enter = new Scanner(System.in);
                String outputFile = enter.nextLine();
                writeLexicon(outputFile);
                mainMenu();
                break;
            case 'q':
                System.out.println("Are you sure to exit the program ??(yes/no)");
                Scanner keyboard = new Scanner(System.in);
                String check = keyboard.nextLine();
                if (check.equalsIgnoreCase("no")) {
                    mainMenu();
                } else if (check.equalsIgnoreCase("yes")) {
                    System.out.println("Thank you for using this program !");
                    System.exit(0);
                } else {
                    System.out.println("Something went wrong");
                    mainMenu();
                }

            default:
                if (c == 'R' || c == 'S' || c == 'W' || c == 'Q') {
                    System.out.println("Please enter only small lettter");
                    mainMenu();
                } else {
                    System.out.println("Something went wrong!!!");
                    mainMenu();
                }

        }
    }
// to read the text file utill goes end of file 
// the file should be text file and consisitng lines

    public void readData(String fileName) {
        try {

            boolean fileFlag = checkFileType(fileName);
            if (fileFlag) {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                String line = "";
                while ((line = br.readLine()) != null) {

                    StringTokenizer token = new StringTokenizer(line);
                    while (token.hasMoreElements()) {
                        String word = token.nextToken().toLowerCase();
                        String lexicon = removeNonLettersChars(word);
                        boolean validLexi = isValidLexicon(lexicon);
                        //add only valid lexicons
                        if (validLexi) {

                            addWords(lexicon);
                            if (addWords(lexicon)) {
                                HashSet<String> wordSet = new HashSet<String>(Arrays.asList(lexicon));
                                totalLexicons.addAll(wordSet);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went  wrong to read the text file");
        }
    }
    // to write the words to output file

    public void writeLexicon(String fileName) {

        Set<String> allWords = totalLexicons;
        List<String> wordList = new ArrayList<String>(allWords);
        MergeSort.mergeSort(wordList);
        int count = 0;
        try {
            PrintWriter outFile = new PrintWriter(new File(fileName));
            for (String word : wordList) {
                outFile.println(++count + ".  " + word);
            }

            outFile.close();
        } catch (Exception e) {
            System.out.println("Some thing went wrong writing the words to output file");
        }
    }

    // to add the words if not existed in the triereturn true  otehrwise fasle    
    public boolean addWords(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        char[] wordCharArr = word.toCharArray();
        TrieNode tempRoot = trieRoot;
        for (char charValue : wordCharArr) {
            tempRoot.addChild(charValue);
            tempRoot = tempRoot.getChild(charValue);
        }
        tempRoot.setFinalChar(true);
        return true;
    }

    public char[] getNextCharacters(String prefixString) {
        throw new UnsupportedOperationException("remove string not supporteed trie implementation");

    }
    // check weteher Trie has more string retrun true if yes otherwise false

    public boolean hasString(String wordString) {
        if (wordString.isEmpty()) {
            return false;
        }
        wordString = wordString.toLowerCase();
        TrieNode node = trieRoot;
        char[] wordCharArr = wordString.toCharArray();
        for (char charValue : wordCharArr) {
            node = node.getChild(charValue);
            if (node == null) {
                return false;
            }
        }
        if (node.isFinalChar()) {
            return true;
        }
        return false;
    }
    // to check valid string

    protected boolean isValidString(String wordString) {
        if (wordString == null || wordString.isEmpty()) {
            return false;
        }
        String newWordString = wordString.replaceAll("[^*?0-9A-Za-z]", "");
        return wordString.trim().length() == newWordString.trim().length() ? true : false;
    }

    protected boolean checkWhiteSpace(String wordString) {
        for (int i = 0; i < wordString.length(); i++) {
            if (Character.isWhitespace(wordString.charAt(i))) {
                System.out.println("Word contains a whitespace in '" + wordString + "'");
                return false;
            }
        }
        return true;
    }

    // search words for the input match calling recursively 
    public Set<String> searchWords(String matchingString) {
        if (!isValidString(matchingString)) {
            return null;

        }
        matchingString = matchingString.toLowerCase();
        // recursive calling 
        Set<String> finalSet = searchWords(trieRoot, matchingString.toCharArray(), 0, null);
        return finalSet;
    }
    //recursive function 

    protected Set<String> searchWords(TrieNode curNode, char[] wordArray, int curIndex, StringBuffer wordFormed) {
        if (curNode == null || wordArray == null || curIndex < 0) {
            return null;
        }

        if (curNode.isFinalChar() && curIndex >= wordArray.length && wordFormed != null && wordFormed.length() != 0) {
            return Collections.singleton(wordFormed.toString());
        }
        if (curIndex >= wordArray.length) {
            return null;
        }
        if (wordFormed == null) {
            wordFormed = new StringBuffer();
        }
        Set<String> wordSet = new HashSet<String>();
        char curChar = wordArray[curIndex];
        if (curChar == '?') {
            Set<TrieNode> childrenNodes = curNode.getChildrenNodes();
            if (childrenNodes != null) {
                for (TrieNode node : childrenNodes) {
                    StringBuffer wordFormedClone = new StringBuffer(wordFormed);
                    wordSet = addToSetIfNotNull(wordSet,
                            searchWords(node, wordArray, curIndex + 1, wordFormedClone.append(node.getCharValue())));

                }
                return wordSet;
            }
        } else {
            assert Character.isLetter(curChar);
            TrieNode node = curNode.getChild(curChar);
            if (node != null) {
                wordSet = addToSetIfNotNull(wordSet,
                        searchWords(node, wordArray, curIndex + 1, wordFormed.append(node.getCharValue())));
                return wordSet;
            }
        }
        return null;
    }
    // add the words in set if not null 

    private Set<String> addToSetIfNotNull(Set<String> wordSet, Set<String> setToAdd) {
        assert wordSet != null;
        if (setToAdd != null && !setToAdd.isEmpty()) {
            wordSet.addAll(setToAdd);
        }
        return wordSet;
    }
    // check filte type if true return otherwise display message to the screen

    public boolean checkFileType(String fileName) throws IOException {
        File f = new File(fileName);
        boolean isFile = f.isFile();
        if (!isFile) {
            System.out.println("This file does not exist");
            mainMenu();
            return false;
        } else {
            return true;
        }
    }
    // to remove nonLettersChars for example don't=dont

    public String removeNonLettersChars(String word) {
        String tempWord = "";
        for (int i = 0; i < word.length(); i++) {
            if (Character.isLetter(word.charAt(i))) {
                tempWord = tempWord + word.charAt(i);
            }
        }
        return tempWord;
    }
    // to ignore numeric word for example 1

    public boolean isValidLexicon(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (Character.isDigit(word.charAt(i))) {
                System.out.println("Word contains a number" + word);
                return false;
            }
        }
        return true;
    }

    public boolean checkMenuOptionOrder() {
        if (totalLexicons.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    //display searched words based on input key

    public void searchLexicon(String key) {
        try {
            if (checkMenuOptionOrder()) {
                if (checkWhiteSpace(key)) {

                
                    if ((searchWords(key).size() <= 0)) {
                        System.out.println("No words in the lexicon matched in your input");
                    } else {
                        Set<String> validWords = searchWords(key);
                        System.out.println("The list of words :" + validWords.size() + " that match the pattern for '" + key + "'");
                        System.out.println();
                        int count = 0;
                        // convertin set to list
                        List<String> list = new ArrayList<String>(validWords);
                        MergeSort.mergeSort(list);
                        for (String setWord : list) {
                            System.out.println("" + ++count + "." + setWord);
                        }
                        System.out.println();
                    }

                } else {
                    mainMenu();
                }
            } else {
                System.out.println("Searching can not complete without reading text file");
                mainMenu();
            }

        } catch (Exception e) {
            System.out.println("No words in the lexicon matched in your input");

        }
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        WordMatch wordMatch = new WordMatch();
        wordMatch.mainMenu();
    }
}
