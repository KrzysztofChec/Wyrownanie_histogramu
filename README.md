## Histogram

### Opis
Klasa `Histogram` zawiera metody do manipulacji obrazami, generowania histogramów i wykonywania operacji na pikselach.

## Efekt przed: 

![image](https://github.com/KrzysztofChec/Wyrownanie_histogramu/assets/126595487/1474a5b7-83ee-4cc3-bb17-8190c43c3a6c)


## Efekt po:

![image](https://github.com/KrzysztofChec/Wyrownanie_histogramu/assets/126595487/17df97cf-57b5-4b29-93f4-df75772d333b)
![image](https://github.com/KrzysztofChec/Wyrownanie_histogramu/assets/126595487/42f55e23-94bb-4753-bac3-0c306b760a26)


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
