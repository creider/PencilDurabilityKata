package pillar.pencilDurability.objects;

import java.util.ArrayList;
import java.util.List;

public class Paper {

    private final StringBuilder stringBuilder = new StringBuilder();
    private final List<Integer> erasedIndices = new ArrayList<>();

    /**
     *
     * @param character Append an individual character to the 'paper'
     */

    void write(char character) {
        stringBuilder.append(character);
    }

    /**
     *
     * @param replacementString string to be added into the paper
     *     In order to edit, something must have been already deleted from the paper
     *     If there is a character collision between the replacing word and the replacement area
     *     the character '@' will be printed at the collision site. Otherwise, the word will replace white space
     *
     */
    void edit(String replacementString) {
        if (erasedIndices.size() != 0 ) {
            final int indiceToCheck = erasedIndices.remove(erasedIndices.size() - 1);
            //Getting the substring  length that needs to be replaced
            final String substringToBeReplaced = stringBuilder.substring(indiceToCheck, indiceToCheck + replacementString.length());
            final StringBuilder replacement =  new StringBuilder();
            for (int i = 0; i < replacementString.length(); i ++) {
                if (Character.isWhitespace(substringToBeReplaced.charAt(i))) {
                    replacement.append(replacementString.charAt(i));
                } else {
                    replacement.append("@");
                }
            }
            stringBuilder.replace(indiceToCheck, indiceToCheck + replacementString.length(), replacement.toString());
        } else {
            throw new RuntimeException("Can't edit paper that hasn't had anything deleted");
        }
    }

    /**
     *
     * @param original   The original string that you want to replace
     * @param replacement The string that is going to replace the original string in the paper
     *                    This string is already padded with the correct amount of whitespace
     */
    void replace(String original, String replacement) {
       final int lastOccurrence = stringBuilder.lastIndexOf(original);
        stringBuilder.replace(lastOccurrence, lastOccurrence + original.length(), replacement);
        erasedIndices.add(lastOccurrence);
    }

    /**
     *
     * @param s The string to be erased from the paper
     *          This erase method removes the last occurrence of the String (s) found
     */

    void erase(String erase) {
        final StringBuilder whiteSpace = new StringBuilder();
        for (int i = 0; i < erase.length(); i ++ ) {
            whiteSpace.append(" ");
        }
        final int lastOccurrence = stringBuilder.lastIndexOf(erase);
        stringBuilder.replace(lastOccurrence, lastOccurrence + erase.length(), whiteSpace.toString());
        erasedIndices.add(lastOccurrence);
    }

    /**
     *
     * @return The String representation of the paper
     */
    public String display() {
        return stringBuilder.toString();
    }




}
