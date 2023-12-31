import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HangLib {
    private String secretWord;
    private char[] guessedWord;
    private int attemptsLeft;
    private Set<Character> guessedLetters;
    private List<String> wordsList;

    public void initializeGameFromWordsFile(String filename) {
        Set<String> wordsSet = readWordsFromFile(filename);
        wordsList = new ArrayList<>(wordsSet);
        initializeGame(getRandomWord(), 6);
    }

    public void makeGuess(char guess) {
        boolean correctGuess = false;
        guessedLetters.add(guess);

        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guess) {
                guessedWord[i] = guess;
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            attemptsLeft--;
        }
    }

    public boolean isGameOver() {
        return attemptsLeft <= 0 || isWordGuessed();
    }

    public boolean isWordGuessed() {
        return secretWord.equals(String.valueOf(guessedWord));
    }

    public String getCurrentProgress() {
        return String.valueOf(guessedWord);
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public String getFormattedWordWithBlanks() {
        StringBuilder formattedWord = new StringBuilder();
        for (char letter : secretWord.toCharArray()) {
            if (guessedLetters.contains(letter)) {
                formattedWord.append(letter).append(" ");
            } else {
                formattedWord.append("_ ");
            }
        }
        return formattedWord.toString();
    }

    public String getRandomWord() {
        if (wordsList.isEmpty()) {
            throw new RuntimeException("No words available.");
        }

        int randomIndex = (int) (Math.random() * wordsList.size());
        return wordsList.remove(randomIndex);
    }

    private void initializeGame(String word, int maxAttempts) {
        setSecretWord(word);
        guessedWord = new char[secretWord.length()];
        guessedLetters = new HashSet<>();
        attemptsLeft = maxAttempts;

        for (int i = 0; i < secretWord.length(); i++) {
            guessedWord[i] = '_';
        }
    }

    private Set<String> readWordsFromFile(String filename) {
        Set<String> wordsSet = new HashSet<>();
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNext()) {
                wordsSet.add(fileScanner.next().toLowerCase());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + filename);
        }
        return wordsSet;
    }

    private void setSecretWord(String secretWord) {
        this.secretWord = secretWord.toLowerCase();
    }
}
