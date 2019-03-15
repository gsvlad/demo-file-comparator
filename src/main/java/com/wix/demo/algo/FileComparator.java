package com.wix.demo.algo;

import com.wix.demo.model.ParsedLine;

import java.io.*;

import static com.wix.demo.algo.LineParser.parseLine;
import static com.wix.demo.algo.TupleComparator.compareTuples;


public class FileComparator {

    /**
     * Compares content of file1 and file2.
     * Result would be written to 3 files:
     *
     * <ul>
     *     <li>1.out - absent lines (by uuid) in file1 + malformed lines in file1</li>
     *     <li>2.out - absent lines (by uuid) in file2 + malformed lines in file2</li>
     *     <li>diff.out - differences in tuples for uuids that both file1 and file2 have</li>
     * </ul>
     */
    public void startComparing(String file1, String file2) {
        System.out.println(">>> Started");
        long startedTime = System.currentTimeMillis();

        String file1Out = "1.out";
        String file2Out = "2.out";
        String fileDiffOut = "diff.out";

        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2));
             BufferedWriter writer1 = new BufferedWriter(new FileWriter(file1Out));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter(file2Out));
             BufferedWriter diffWriter = new BufferedWriter(new FileWriter(fileDiffOut))) {

            compareWithReaders(reader1, reader2, writer1, writer2, diffWriter);
        } catch (IOException e) {
            System.out.println("Finishing with error");
            e.printStackTrace();
        }

        deleteIfEmpty(file1Out);
        deleteIfEmpty(file2Out);
        deleteIfEmpty(fileDiffOut);

        System.out.println(">>> Finished in " + (System.currentTimeMillis() - startedTime) + " millis");
    }

    private void compareWithReaders(BufferedReader reader1, BufferedReader reader2, BufferedWriter writer1,
                                    BufferedWriter writer2, BufferedWriter diffWriter) throws IOException {

        String nextLine1 = reader1.readLine();
        String nextLine2 = reader2.readLine();

        while (nextLine1 != null && nextLine2 != null) {
            ParsedLine parsedLine1 = parseLine(nextLine1);
            ParsedLine parsedLine2 = parseLine(nextLine2);

            if (parsedLine1 == null) {
                logMalformedLine(writer1, nextLine1);
                nextLine1 = reader1.readLine();
            }
            if (parsedLine2 == null) {
                logMalformedLine(writer2, nextLine2);
                nextLine2 = reader2.readLine();
            }

            if (parsedLine1 == null || parsedLine2 == null) {
                continue;
            }

            int comparedLines = parsedLine1.compareTo(parsedLine2);
            if (comparedLines < 0) {
                logAbsentLine(writer2, nextLine1);
                nextLine1 = reader1.readLine();
                continue;
            }
            if (comparedLines > 0) {
                logAbsentLine(writer1, nextLine2);
                nextLine2 = reader2.readLine();
                continue;
            }

            // UUIDs are same. Let's check tuples
            TupleComparator.Result result = compareTuples(parsedLine1.tuples, parsedLine2.tuples);

            if (result.hasDifferences()) {
                diffWriter.write(parsedLine1.uuid + ": \n");
                diffWriter.write(result.toString() + "\n");
            }

            nextLine1 = reader1.readLine();
            nextLine2 = reader2.readLine();
        }

        while (nextLine1 != null) {
            logAbsentLine(writer2, nextLine1);
            nextLine1 = reader1.readLine();
        }

        while (nextLine2 != null) {
            logAbsentLine(writer1, nextLine2);
            nextLine2 = reader2.readLine();
        }
    }

    private void logAbsentLine(BufferedWriter writer, String line) throws IOException {
        writer.write("Absent line: " + line + "\n");
    }

    private void logMalformedLine(BufferedWriter writer, String line) throws IOException {
        writer.write("Malformed line: " + line + "\n");
    }

    private static void deleteIfEmpty(String fileName) {
        File file = new File(fileName);
        if (file.length() == 0) {
            System.out.println("Created file '" + fileName + "' is empty and will be removed");
            file.deleteOnExit();
        }
    }
}
