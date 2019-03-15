package com.wix.demo.algo;

import com.wix.demo.model.ParsedLine;
import org.junit.Test;

import static org.junit.Assert.*;


public class LineParserTest {

    @Test
    public void parseLine3Tuples() {
        // given
        String source = "00005b06-522d-4026-bc5e-92b756d392a8\t(201 1487565865676   app81.test.com)   ( 202 1487565865686 app81.aus.wixpress.com)(2 1487565991000 null)";

        // when
        ParsedLine parsedLine = LineParser.parseLine(source);

        // then
        assertNotNull(parsedLine);
        assertEquals("00005b06-522d-4026-bc5e-92b756d392a8", parsedLine.uuid);
        assertEquals(3, parsedLine.tuples.size());

        assertEquals("201", parsedLine.tuples.get(0).eventId);
        assertEquals("1487565865676", parsedLine.tuples.get(0).dateCreated);
        assertEquals("app81.test.com", parsedLine.tuples.get(0).host);

        assertEquals("202", parsedLine.tuples.get(1).eventId);
        assertEquals("1487565865686", parsedLine.tuples.get(1).dateCreated);
        assertEquals("app81.aus.wixpress.com", parsedLine.tuples.get(1).host);

        assertEquals("2", parsedLine.tuples.get(2).eventId);
        assertEquals("1487565991000", parsedLine.tuples.get(2).dateCreated);
        assertEquals("null", parsedLine.tuples.get(2).host);
    }

    @Test
    public void parseLineIncorrectTuple() {
        // given
        String source = "00005b06-522d-4026-bc5e-92b756d392a8\t(201-1487565865676 app81.test.com)(202 1487565865686 app81.aus.wixpress.com)(2 1487565991000 null)";

        // when
        ParsedLine parsedLine = LineParser.parseLine(source);

        // then
        assertNotNull(parsedLine);
        assertEquals("00005b06-522d-4026-bc5e-92b756d392a8", parsedLine.uuid);
        assertEquals(2, parsedLine.tuples.size());
    }

    @Test
    public void parseLineUUIDOnly() {
        // given
        String source = "  00005b06-522d-4026-bc5e-92b756d392a8 ";

        // when
        ParsedLine parsedLine = LineParser.parseLine(source);

        // then
        assertNotNull(parsedLine);
        assertEquals("00005b06-522d-4026-bc5e-92b756d392a8", parsedLine.uuid);
        assertTrue(parsedLine.tuples.isEmpty());
    }
}