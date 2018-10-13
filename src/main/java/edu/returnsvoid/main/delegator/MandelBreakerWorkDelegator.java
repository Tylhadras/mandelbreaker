package edu.returnsvoid.main.delegator;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.returnsvoid.main.io.PGMWriter;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MandelBreakerWorkDelegator {

    private static final Logger LOG = LoggerFactory.getLogger(MandelBreakerWorkDelegator.class);

    private final double minCReal;
    private final double maxCReal;
    private final double minCImg;
    private final double maxCImg;
    private final int width;
    private final int height;
    private final int subImageWidth;
    private final int subImageHeight;
    private final int maxIterations;
    private final String[] servers;

    public MandelBreakerWorkDelegator(CommandLine commandLine) {
        minCReal = Double.valueOf(commandLine.getOptionValue("mincr"));
        maxCReal = Double.valueOf(commandLine.getOptionValue("maxcr"));
        minCImg = Double.valueOf(commandLine.getOptionValue("mincimg"));
        maxCImg = Double.valueOf(commandLine.getOptionValue("maxcimg"));
        width = Integer.valueOf(commandLine.getOptionValue("x"));
        height = Integer.valueOf(commandLine.getOptionValue("y"));
        subImageWidth = Integer.valueOf(commandLine.getOptionValue("px"));
        subImageHeight = Integer.valueOf(commandLine.getOptionValue("py"));
        maxIterations = Integer.valueOf(commandLine.getOptionValue("i"));
        servers = commandLine.getOptionValues("s");
    }

    public void execute() {
        printArguments();
        int serverCount = servers.length;

        if (width % subImageWidth != 0 || height % subImageHeight != 0) {
            LOG.error("Subimage width/height not evenly divisible over whole picture");
            System.exit(2);
        }

        Map<Integer, SubImage> subImageMap = createSubImageMap();

        subImageMap.entrySet().parallelStream().forEach(subImageEntry -> {
            executeQuery(servers[subImageEntry.getKey() % serverCount], subImageEntry);
        });

        new PGMWriter(width, height, subImageWidth, subImageHeight, subImageMap).write();
    }

    private void executeQuery(String server, Map.Entry<Integer, SubImage> subImageEntry) {
        SubImage subImage = subImageEntry.getValue();
        try {
            HttpResponse<JsonNode> response = Unirest.get("http://" + server + "/mandelbrot/{min_c_re}/{min_c_im}/{max_c_re}/{max_c_im}/{width}/{height}/{inf_n}")
                    .routeParam("min_c_re", String.valueOf(subImage.getSubImageMinCReal()))
                    .routeParam("min_c_im", String.valueOf(subImage.getSubImageMinCImg()))
                    .routeParam("max_c_re", String.valueOf(subImage.getSubImageMaxCReal()))
                    .routeParam("max_c_im", String.valueOf(subImage.getSubImageMaxCImg()))
                    .routeParam("width", String.valueOf(subImageWidth))
                    .routeParam("height", String.valueOf(subImageHeight))
                    .routeParam("inf_n", String.valueOf(maxIterations))
                    .asJson();

            if (response.getStatus() == 200) {
                subImage.setData(new Gson().fromJson(response.getBody().toString(), int[][].class));
            } else {
                LOG.error("Unable to complete request number {}", subImageEntry.getKey());
                System.exit(3);
            }
        } catch (UnirestException e) {
            LOG.error("Unable to complete request number {}", subImageEntry.getKey(), e);
            System.exit(3);
        }
    }

    private Map<Integer, SubImage> createSubImageMap() {
        double stepCountReal = (maxCReal - minCReal) / width;
        double stepCountImg = (maxCImg - minCImg) / height;

        Map<Integer, SubImage> subImageMap = new HashMap<>();
        int index = 0;
        for (int y = 0; y < height; y += subImageHeight) {
            for (int x = 0; x < width; x += subImageWidth) {
                double subImageMinCReal = minCReal + (x * stepCountReal);
                double subImageMaxCReal = subImageMinCReal + (subImageWidth * stepCountReal);
                double subImageMinCImg = minCImg + (y * stepCountImg);
                double subImageMaxCImg = subImageMinCImg + (subImageHeight * stepCountImg);
                subImageMap.put(index, new SubImage(subImageMinCReal, subImageMaxCReal, subImageMinCImg, subImageMaxCImg));
                index++;
            }
        }

        return subImageMap;
    }

    private void printArguments() {
        LOG.info("Min C Real: {}", minCReal);
        LOG.info("Max C Real: {}", maxCReal);
        LOG.info("Min C Img: {}", minCImg);
        LOG.info("Max C Img: {}", maxCImg);
        LOG.info("Width: {}", width);
        LOG.info("Height: {}", height);
        LOG.info("Sub Image Width: {}", subImageWidth);
        LOG.info("sub Image Height: {}", subImageHeight);
        LOG.info("Max iterations: {}", maxIterations);
        LOG.info("Servers:");
        for (String server : servers) {
            LOG.info("  {}", server);
        }
    }
}
