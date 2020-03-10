package pillar.pencilDurability.objects;

import org.junit.Assert;
import org.junit.Test;

public class PencilTest {

    private Pencil pencil = new Pencil(2, 200, 100);
    private Paper paper = new Paper();


    @Test
    public void writeSingleWordAllLowerCase () {
        pencil.write(paper, "llama");
        Assert.assertEquals(195, pencil.getPointDurability());
    }

    @Test
    public void writeSingleWordOneCapital () {
        pencil.write(paper, "Llama");
        Assert.assertEquals(194, pencil.getPointDurability());
    }

    @Test
    public void writeTwoWordsAllLowerCase() {
        pencil.write(paper, "llama alpaca");
        Assert.assertEquals(189, pencil.getPointDurability());
    }

    @Test
    public void writeTwoWordsAllUpperCaseWithWhiteSpace() {
        pencil.write(paper, "LLAMA \n  ALPACA");
        Assert.assertEquals(178, pencil.getPointDurability());
    }

    @Test
    public void pencilRunsOutOfDurabilityAllLowerCase() {
        Pencil lowPointDurabilityPencil = new Pencil(2, 5, 5);
        lowPointDurabilityPencil.write(paper, "alpaca");
        Assert.assertEquals(-1, lowPointDurabilityPencil.getPointDurability());
        Assert.assertEquals("alpac ", paper.display());
    }

    @Test
    public void pencilRunsOutOfDurabilityAllUpperCase() {
        Pencil lowPointDurabilityPencil = new Pencil(2, 5, 5);
        lowPointDurabilityPencil.write(paper, "ALPACA");
        Assert.assertEquals("AL    ", paper.display());
    }

    @Test
    public void sharpenPencil() {
        pencil.write(paper, "llama and alpacas");
        Assert.assertEquals(185, pencil.getPointDurability());
        pencil.sharpen();
        Assert.assertEquals(200, pencil.getPointDurability());
        Assert.assertEquals(1, pencil.getPencilDurability());
    }

    @Test
    public void sharpenPencilUntilDull () {
        Pencil lowPointDurabilityPencil = new Pencil(2, 5, 5);
        for (int i = 0; i <= 2; i ++) {
            lowPointDurabilityPencil.write(paper, "llama ");
            lowPointDurabilityPencil.sharpen();
        }
        lowPointDurabilityPencil.sharpen();
        Assert.assertEquals(0, lowPointDurabilityPencil.getPencilDurability());
        Assert.assertEquals(0, lowPointDurabilityPencil.getPointDurability());
        lowPointDurabilityPencil.write(paper, "alpaca");
        Assert.assertEquals("llama llama llama       ", paper.display());
    }

    @Test
    public void testEraser() {
        Pencil smallEraserPencil = new Pencil(1, 20, 3);
        smallEraserPencil.write(paper, "Buffalo Bill");
        Assert.assertEquals(7, smallEraserPencil.getPointDurability());
        smallEraserPencil.erase(paper, "Bill");
        Assert.assertEquals(0, smallEraserPencil.getEraserDurability());
        Assert.assertEquals("Buffalo B   ", paper.display());
        smallEraserPencil.erase(paper, "Buffalo");
        Assert.assertEquals("Buffalo B   ", paper.display());
    }

    @Test
    public void editWordLongerThanAvailableSpace() {
        pencil.write(paper, "An apple a day keeps the doctor away");
        Assert.assertEquals(170, pencil.getPointDurability());
        pencil.erase(paper, "apple");
        Assert.assertEquals(95, pencil.getEraserDurability());
        pencil.edit(paper, "artichoke");
        Assert.assertEquals(161, pencil.getPointDurability());
        Assert.assertEquals("An artich@k@ay keeps the doctor away", paper.display());
    }

    @Test
    public void nothingToEdit() {
        try {
            pencil.edit(paper, "word");
        } catch (RuntimeException e) {
            Assert.assertEquals("Can't edit paper that hasn't had anything deleted", e.getMessage());
        }
    }

    @Test
    public void editTwoDeletedWords() {
        pencil.write(paper, "An apple a day keeps the doctor away");
        pencil.erase(paper, "apple");
        pencil.edit(paper, "onion");
        Assert.assertEquals("An onion a day keeps the doctor away", paper.display());
        pencil.erase(paper, "doctor");
        pencil.edit(paper, "vampire");
        Assert.assertEquals("An onion a day keeps the vampireaway", paper.display());
    }
}
