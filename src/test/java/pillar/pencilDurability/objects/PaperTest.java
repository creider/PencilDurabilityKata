package pillar.pencilDurability.objects;

import org.junit.Assert;
import org.junit.Test;

public class PaperTest {

    private final Paper paper = new Paper();
    private final Pencil pencil = new Pencil(1, 40_000, 60);

    @Test
    public void writeSimpleOnPaper() {
        pencil.write(paper, "Hello Llama");
        Assert.assertEquals("Hello Llama", paper.display());
    }

    @Test
    public void writeComplexOnPaper() {
        pencil.write(paper, "She sells sea shells");
        Assert.assertEquals("She sells sea shells", paper.display());
        pencil.write(paper, " down by the sea shore");
        Assert.assertEquals("She sells sea shells down by the sea shore", paper.display());
    }

    @Test
    public void testErasingWithSmallEraser() {
        Pencil smallEraser = new Pencil(1, 20, 3);
        smallEraser.write(paper, "Buffalo Bill");
        Assert.assertEquals("Buffalo Bill", paper.display());
        smallEraser.erase(paper, "Bill");
        Assert.assertEquals("Buffalo B   ", paper.display());
        smallEraser.erase(paper, "Buffalo");
        Assert.assertEquals("Buffalo B   ", paper.display());
    }

    @Test
    public void eraseWordsMultipleTimes() {
        pencil.write(paper, "How much wood would a woodchuck chuck if a woodchuck could chuck wood?");
        pencil.erase(paper, "chuck");
        Assert.assertEquals("How much wood would a woodchuck chuck if a woodchuck could       wood?", paper.display());
        pencil.erase(paper, "chuck");
        Assert.assertEquals("How much wood would a woodchuck chuck if a wood      could       wood?", paper.display());
    }
}
