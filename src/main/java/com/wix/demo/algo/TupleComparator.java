package com.wix.demo.algo;

import com.sun.tools.javac.util.Pair;
import com.wix.demo.model.Tuple;

import java.util.ArrayList;
import java.util.List;

public final class TupleComparator {

    public static Result compareTuples(List<Tuple> tuples1, List<Tuple> tuples2) {
        int index1 = 0;
        int index2 = 0;

        List<Tuple> absentIn1 = new ArrayList<>();
        List<Tuple> absentIn2 = new ArrayList<>();
        List<Pair<Tuple, Tuple>> different = new ArrayList<>();

        while (index1 < tuples1.size() && index2 < tuples2.size()) {
            Tuple tuple1 = tuples1.get(index1);
            Tuple tuple2 = tuples2.get(index2);
            int tupleCompare = tuple1.compareTo(tuple2);

            if (tupleCompare < 0) {
                absentIn2.add(tuple1);
                index1++;

            } else if (tupleCompare > 0) {
                absentIn1.add(tuple2);
                index2++;

            } else {
                if (!tuple1.equals(tuple2)) {
                    different.add(new Pair<>(tuple1, tuple2));
                }
                index1++;
                index2++;
            }
        }

        while (index1 < tuples1.size()) {
            absentIn2.add(tuples1.get(index1++));
        }

        while (index2 < tuples2.size()) {
            absentIn1.add(tuples2.get(index2++));
        }

        return new Result(absentIn1, absentIn2, different);
    }

    /**
     * Result of tuple comparison is:
     * <ul>
     *     <li>list of tuples that are absent in file1 for one UUID</li>
     *     <li>list of tuples that are absent in file2 for the same UUID</li>
     *     <li>list of tuple pairs that are different for the same UUID and {@code createdDate}</li>
     * </ul>
     */
    public static class Result {
        final List<Tuple> absentIn1;
        final List<Tuple> absentIn2;
        final List<Pair<Tuple, Tuple>> different;

        Result(List<Tuple> absentIn1, List<Tuple> absentIn2, List<Pair<Tuple, Tuple>> different) {
            this.absentIn1 = absentIn1;
            this.absentIn2 = absentIn2;
            this.different = different;
        }

        /**
         * @return true only if there is any difference between tuples
         */
        public boolean hasDifferences() {
            return !absentIn1.isEmpty() || !absentIn2.isEmpty() || !different.isEmpty();
        }

        @Override
        public String toString() {
            if (!hasDifferences()) {
                return "List of tuples are equal";
            }

            StringBuilder sb = new StringBuilder();
            if (!absentIn1.isEmpty()) {
                sb.append("Absent in first file: ")
                        .append(absentIn1)
                        .append("\n");
            }
            if (!absentIn2.isEmpty()) {
                sb.append("Absent in second file: ")
                        .append(absentIn2)
                        .append("\n");
            }
            if (!different.isEmpty()) {
                sb.append("Different tuples: ")
                        .append(different)
                        .append("\n");
            }
            return sb.toString();
        }
    }
}
