package com.wix.demo.algo;

import com.wix.demo.model.Tuple;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;


public class TupleComparatorTest {

    @Test
    public void compareTuples() {
        // given
        List<Tuple> tuples1 = tuples(1, 2, -4, 7);
        List<Tuple> tuples2 = tuples(2, 4, 6, 8);

        // when
        TupleComparator.Result result = TupleComparator.compareTuples(tuples1, tuples2);

        // then
        assertTrue(result.hasDifferences());
        assertEquals(tuples(6, 8), result.absentIn1);
        assertEquals(tuples(1, 7), result.absentIn2);
        assertEquals(1, result.different.size());
        assertEquals(tuple(-4), result.different.get(0).fst);
        assertEquals(tuple(4), result.different.get(0).snd);
    }

    @Test
    public void compareTuplesEmptySecond() {
        // given
        List<Tuple> tuples1 = tuples(1, 2, -4, 7);
        List<Tuple> tuples2 = tuples();

        // when
        TupleComparator.Result result = TupleComparator.compareTuples(tuples1, tuples2);

        // then
        assertTrue(result.hasDifferences());
        assertTrue(result.absentIn1.isEmpty());
        assertEquals(tuples(1, 2, -4, 7), result.absentIn2);
        assertTrue(result.different.isEmpty());
    }

    @Test
    public void compareTuplesEqual() {
        // given
        List<Tuple> tuples1 = tuples(1, 2, -4, 7);
        List<Tuple> tuples2 = tuples(1, 2, -4, 7);

        // when
        TupleComparator.Result result = TupleComparator.compareTuples(tuples1, tuples2);

        // then
        assertFalse(result.hasDifferences());
    }

    private List<Tuple> tuples(int... ids) {
        return Arrays.stream(ids)
                .mapToObj(this::tuple)
                .collect(toList());
    }

    private Tuple tuple(int id) {
        return new Tuple(String.valueOf(id), String.valueOf(Math.abs(id)), "host" + id);
    }
}