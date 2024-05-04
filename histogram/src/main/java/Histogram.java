import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Histogram {

    BufferedImage image;
    int height, width;

    public void loadImage(String path) throws IOException {
        File file = new File(path);
        image = ImageIO.read(file);
        height = image.getHeight();
        width = image.getWidth();
    }

    public void saveImage(String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
    }

    private int setBrightnessForPixel(int color, int factor) {
        Color colorFromPixel = new Color(color);
        int r = colorFromPixel.getRed() + factor;
        int g = colorFromPixel.getGreen() + factor;
        int b = colorFromPixel.getBlue() + factor;
        return new Color(clump(r), clump(g), clump(b)).getRGB();
    }

    private int clump(int color){
        if(color>255){
            return 255;
        }else if(color < 0){
            return 0;
        }
        return color;
    }

    public void setBrightness(int factor) {
        for(int y = 0;y<height;y++){
            for(int x = 0;x<width;x++){
                image.setRGB(x, y,setBrightnessForPixel(image.getRGB(x, y), factor));
            }
        }
    }

    public void setBrightnessWithThreads(int factor) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        int threadWorkSpace = height/cores;
        ArrayList<Thread>threads = new ArrayList<>();
        for(int i = 0;i<cores;i++) {
            int threadIndex = i;
            threads.add(new Thread(() -> {
                int start = threadIndex*threadWorkSpace;
                int end;
                if(threadIndex == cores-1){
                    end = height;
                }else{
                    end = start+threadWorkSpace;
                }
                for(int y = start;y<end;y++){
                    for(int x = 0;x<width;x++){
                        image.setRGB(x, y,setBrightnessForPixel(image.getRGB(x, y), factor));
                    }
                }
            }));
        }
        for (Thread x: threads) {
            x.start();
        }
        for (Thread x: threads) {
            x.join();
        }
    }
    public void setBrightnessWithThreadPool(int factor) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        for(int y = 0;y<height;y++){
            final int row = y;
            executor.submit(() -> {
                for(int x = 0;x<width;x++){
                    image.setRGB(x, row, setBrightnessForPixel(image.getRGB(x, row), factor));
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public AtomicIntegerArray calculateHistogramWithThreadPool() throws InterruptedException { //liczymy histogram skumulowany potrzeby do wyrownywania uzywamy atomicow bo sa bezpieczniejszer
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        AtomicIntegerArray histogram = new AtomicIntegerArray(256); // dla 256 poziomów intensywności

        for(int y = 0;y<height;y++){
            final int row = y;
            executor.submit(() -> {
                for(int x = 0;x<width;x++){
                    int red = new Color(image.getRGB(x, row)).getRed(); // pobierz wartość czerwonego kanału
                    histogram.incrementAndGet(red); // zwiększ licznik dla danej wartości
                }
            });
        }

        executor.shutdown();
        try {

            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {

                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return histogram;
    }
    public AtomicIntegerArray calculateCumulativeHistogram() throws InterruptedException {
        AtomicIntegerArray histogram = calculateHistogramWithThreadPool();

        int sum = 0;
        for (int i = 0; i < histogram.length(); i++) {
            sum += histogram.get(i);
            histogram.set(i, sum);
        }

        return histogram;
    }

    public void equalizeHistogram() throws InterruptedException { //wyrownujemy histogmra
        AtomicIntegerArray histogram = calculateCumulativeHistogram();

        float scaleFactor = 255f / (width * height);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int y = 0; y < height; y++) {
            final int row = y;
            executor.submit(() -> {
                for (int x = 0; x < width; x++) {
                    Color oldColor = new Color(image.getRGB(x, row));
                    int red = Math.round(histogram.get(oldColor.getRed()) * scaleFactor);
                    int green = Math.round(histogram.get(oldColor.getGreen()) * scaleFactor);
                    int blue = Math.round(histogram.get(oldColor.getBlue()) * scaleFactor);
                    Color newColor = new Color(clump(red), clump(green), clump(blue));
                    image.setRGB(x, row, newColor.getRGB());
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void convertToCIEXYZ() {
        ColorSpace ciexyz = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);
        ColorConvertOp op = new ColorConvertOp(ciexyz, null);
        image = op.filter(image, null);
    }

    public void convertToRGB() {
        ColorSpace rgb = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorConvertOp op = new ColorConvertOp(rgb, null);
        image = op.filter(image, null);
    }




}


