package pillar.pencilDurability.objects;

public class Pencil {

    private static final int LOWER_CASE_DEGREDATION_FACTOR = 1;
    private static final int UPPER_CASE_DEGREDATION_FACTOR = 2;

    private final int maxPointDurability;
    private int pointDurability;
    private int eraserDurability;
    private int pencilDurability;

    public Pencil(int pencilDurability, int pointDurability, int eraserDurability) {
        this.pencilDurability = pencilDurability;
        this.maxPointDurability = pointDurability;
        this.pointDurability = pointDurability;
        this.eraserDurability = eraserDurability;
    }

    /**
     * @return  The pencil point durability as an int
     */
    int getPointDurability() {
        return pointDurability;
    }

    /**
     *
     * @return Pencil point Durability
     *          -1 indicates pencil can't write
     */
    int getPencilDurability() {
        return pencilDurability;
    }

    int getEraserDurability() {
        return eraserDurability;
    }

    /**
     *  Sharpens the pencil back to its initial max durability. As the pencil is sharpened,
     *  the pencil durability decreases until it reaches 0
     *  //TODO Upon sharpening, should the pencil max durability degrade by a certain %?
     */
    void sharpen() {
        if (pencilDurability > 0) {
            this.pencilDurability--;
            this.pointDurability = maxPointDurability;
        } else  {
            pointDurability = 0;
        }
    }

    /**
     *
     * @param paper The paper that is going to be changed
     * @param replacement The string that is going to replace the last deletions whitespace
     */
    void edit(Paper paper, String replacement) {
        if (pointDurability >= replacement.length()) {
            pointDurability = pointDurability - replacement.length();
            paper.edit(replacement);
        } else {
            throw new RuntimeException("Not enough durability to write");
        }
    }

    /**
     *
     * @param paper The paper that is going to have content erased
     * @param erase  The string to be erased from the paper (This will erase the last occurrence of the string)
     */

    void erase(Paper paper, String erase) {
        if (eraserDurability > 0) {
            if (erase.length() >= eraserDurability) {
                final String replacementString = String.format("%1$-" + erase.length() + "s", erase.substring(0, erase.length() - eraserDurability));
                paper.replace(erase, replacementString);
                eraserDurability = 0;
            } else {
                paper.erase(erase);
            }
            int numberToErase = 0;
            for(char c : erase.toCharArray()) {
                if (!Character.isWhitespace(c)) {
                    numberToErase++;
                }
            }
            eraserDurability = Math.max(0, eraserDurability - numberToErase);
        }
    }

    /**
     *
     * @param paper The paper to be written to
     * @param s The string to be written to the paper
     */
    void write(Paper paper, String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                if (Character.isUpperCase(c)) {
                    pointDurability = pointDurability - UPPER_CASE_DEGREDATION_FACTOR;
                } else {
                    pointDurability = pointDurability - LOWER_CASE_DEGREDATION_FACTOR;
                }
            }
            if (pointDurability >= 0) {
                paper.write(c);
            } else {
                paper.write(' ');
            }
        }
    }
}
