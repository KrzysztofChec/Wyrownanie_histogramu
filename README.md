## Histogram

### Opis
Klasa `Histogram` zawiera metody do manipulacji obrazami, generowania histogramów i wykonywania operacji na pikselach.

### Metody

- `loadImage(String path) throws IOException`: Wczytuje obraz z podanej ścieżki.
- `saveImage(String path) throws IOException`: Zapisuje obraz na podanej ścieżce.
- `setBrightness(int factor)`: Zmienia jasność obrazu o określony współczynnik.
- `setBrightnessWithThreads(int factor) throws InterruptedException`: Zmienia jasność obrazu z wykorzystaniem wielu wątków.
- `setBrightnessWithThreadPool(int factor) throws InterruptedException`: Zmienia jasność obrazu z wykorzystaniem puli wątków.
- `calculateHistogramWithThreadPool() throws InterruptedException`: Oblicza histogram obrazu z wykorzystaniem puli wątków.
- `calculateCumulativeHistogram() throws InterruptedException`: Oblicza histogram skumulowany obrazu.
- `equalizeHistogram() throws InterruptedException`: Wykonuje operację wyrównania histogramu.
- `convertToCIEXYZ()`: Konwertuje obraz do przestrzeni kolorów CIEXYZ.
- `convertToRGB()`: Konwertuje obraz z powrotem do przestrzeni kolorów RGB.

### Klasa `Main`
Klasa `Main` zawiera przykłady użycia metod klasy `Histogram` oraz operacje na obrazach.

- Metoda `main(String[] args)`: Uruchamia przykłady operacji na obrazach, w tym zmianę jasności, obliczanie histogramu, wyrównywanie histogramu i konwersję przestrzeni kolorów.
