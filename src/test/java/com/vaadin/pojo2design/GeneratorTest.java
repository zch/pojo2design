package com.vaadin.pojo2design;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.File;

/**
 * Created by jonatan on 15/09/15.
 */
public class GeneratorTest {

    @Test
    public void testGenerateSimpleDesign() throws Exception {
        Generator gen = new Generator("public class Foo { private String str; public String getStr() { return str; } }");
        assertEquals("<v-text-field caption=\"Str\"></v-text-field>", gen.generate());
    }

    @Test
    public void testForMyPojo() throws Exception {
        Generator gen = new Generator(getSourceFile("/MyPojo.java"));
        String expected = "<v-text-field caption=\"Chubbly Wubbly\"></v-text-field>\n"
                + "<v-text-field caption=\"Numbery Wumbery\"></v-text-field>\n"
                + "<v-text-field caption=\"Inty Pinty\"></v-text-field>\n"
                + "<v-text-field caption=\"Inty Pinty 2\"></v-text-field>\n"
                + "<v-text-field caption=\"Floaty Pouty\"></v-text-field>\n"
                + "<v-text-field caption=\"Floaty Pouty 2\"></v-text-field>\n"
                + "<v-text-field caption=\"Double Wouble\"></v-text-field>\n"
                + "<v-text-field caption=\"Double Wouble 2\"></v-text-field>\n"
                + "<v-check-box caption=\"Booly Wooly\"></v-check-box>\n"
                + "<v-check-box caption=\"Booly Wooly 2\"></v-check-box>\n"
                + "<v-date-field caption=\"Date\"></v-date-field>"
                ;


        assertEquals(expected, gen.generate());
    }

    private File getSourceFile(String name) {
        return new File(getClass().getResource(name).getFile());
    }
}
