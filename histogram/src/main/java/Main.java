import java.io.IOException;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Main {
    public static void main(String[] args) {
        Histogram egg = new Histogram();
        try {
            long start = System.currentTimeMillis();
            egg.loadImage("src/main/java/example.jpg");
            egg.setBrightness(50);
            egg.saveImage("example1.jpg");
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Histogram egg1 = new Histogram();
        try {
            long start = System.currentTimeMillis();
            egg1.loadImage("src/main/java/example.jpg");
            try {
                egg1.setBrightnessWithThreads(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            egg1.saveImage("example2.jpg");
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Histogram egg2= new Histogram();
        try {
            long start = System.currentTimeMillis();
            egg2.loadImage("src/main/java/example.jpg");
            try {
                egg2.setBrightnessWithThreadPool(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            egg2.saveImage("example3.jpg");
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Histogram egg3 = new Histogram();
        try {
            egg3.loadImage("src/main/java/example.jpg");
            AtomicIntegerArray histogram = egg.calculateHistogramWithThreadPool();

            // wyswioetlanmie histogramu
            for (int i = 0; i < histogram.length(); i++) {
                System.out.println("Jasność " + i + ": " + histogram.get(i) + " pikseli");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        Histogram blackAndWhite = new Histogram();
        try {
            blackAndWhite.loadImage("src/main/java/Unequalized_Hawkes_Bay_NZ.jpg");
            blackAndWhite.equalizeHistogram();
            blackAndWhite.saveImage("src/main/java/Unequalized_Hawkes_Bay_NZ1.jpg");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        Histogram convert = new Histogram();
        try {
            convert.loadImage("src/main/java/Unequalized_Hawkes_Bay_NZ.jpg");
            convert.convertToCIEXYZ();
            convert.equalizeHistogram();
            convert.convertToRGB();
            convert.saveImage("src/main/java/Unequalized_Hawkes_Bay_NZ2.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}












