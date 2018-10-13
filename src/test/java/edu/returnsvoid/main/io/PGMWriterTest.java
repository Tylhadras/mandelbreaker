package edu.returnsvoid.main.io;


import edu.returnsvoid.main.delegator.SubImage;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PGMWriterTest {

    //Used for manual testing.
    @Test
    public void testWrite() {

        int[][] testData = new int[2][1];
        int[][] testData2 = new int[2][1];
        testData[0][0] = 255;
        testData[1][0] = 0;
        testData2[0][0] = 255;
        testData2[1][0] = 0;

        SubImage subImage1 = new SubImage(1, 1, 1, 1);
        subImage1.setData(testData);

        SubImage subImage2 = new SubImage(1, 1, 1, 1);
        subImage2.setData(testData2);

        Map<Integer, SubImage> subImageMap = new HashMap<>();
        subImageMap.put(0, subImage1);
        subImageMap.put(1, subImage2);

        new PGMWriter(2, 2, 2, 1, subImageMap).write();
    }
}