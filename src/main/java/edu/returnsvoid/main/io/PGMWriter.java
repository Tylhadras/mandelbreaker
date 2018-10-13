package edu.returnsvoid.main.io;

import edu.returnsvoid.main.delegator.SubImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PGMWriter {

    private static final Logger LOG = LoggerFactory.getLogger(PGMWriter.class);

    private final Map<Integer, SubImage> subImageMap;
    private final int width;
    private final int height;
    private final int subImageWidth;
    private final int subImageHeight;

    public PGMWriter(int width, int height, int subImageWidth, int subImageHeight, Map<Integer, SubImage> subImageMap) {
        this.width = width;
        this.height = height;
        this.subImageMap = subImageMap;
        this.subImageWidth = subImageWidth;
        this.subImageHeight = subImageHeight;
    }

    public void write() {

        List<Integer> printOrderedList = new ArrayList<>();

        int subImagesPerRow = width / subImageWidth;
        int subImagesPerColumn = height / subImageHeight;
        int numberOfSubImages = subImagesPerRow * subImagesPerColumn;

        List<List<SubImage>> rows = new ArrayList<>();

        for (int z = 0; z < numberOfSubImages; z += subImagesPerRow) {
            int maxIndex = z + subImagesPerRow;
            int minIndex = z;
            rows.add(subImageMap.entrySet().stream().filter(entry -> entry.getKey() < maxIndex && entry.getKey() >= minIndex)
                    .map(Map.Entry::getValue).collect(Collectors.toList()));
        }

        for (List<SubImage> subImageRow : rows) {
            for (int x = 0; x < subImageWidth; x++) {
                for (SubImage image : subImageRow) {
                    for (int y = 0; y < subImageHeight; y++) {
                        printOrderedList.add(image.getData()[x][y]);
                    }
                }
            }
        }

        writeToFile(printOrderedList);
    }

    private void writeToFile(List<Integer> output) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./a.pgm"))) {
            bw.write("P2\n" + width + " " + height + "\n" + "10\n");
            for (Integer integer : output) {
                bw.write(integer + " ");
            }
        } catch (IOException e) {
            LOG.error("Cannot write to output file", e);
            throw new IllegalStateException(e);
        }
    }
}
