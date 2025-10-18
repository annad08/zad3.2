package org.example;

import org.example.model.Observation;
import org.example.model.ObservationList;
import org.example.service.XmlFileReader;
import org.example.service.XmlValidator;
import java.util.List;
import java.util.Comparator;

public class App {

    final static String[] schemaFiles = {"schema1.xsd", "schema2.xsd"};
    final static String dataObservationFile = "observation.xml";
    final static String dataObservationsFile = "observations.xml";

    public static void main(String[] args) {

        System.out.println("--- Zadanie 3.2: Walidacja i odczyt lokalny ---");


        boolean reslut_1 = XmlValidator.validate(dataObservationFile, schemaFiles);
        boolean reslut_list = XmlValidator.validate(dataObservationsFile, schemaFiles);

        System.out.println("Validate: " + dataObservationFile + " -> " + reslut_1);
        System.out.println("Validate: " + dataObservationsFile + " -> " + reslut_list);


        System.out.println("\n--- Odczyt pojedynczego pomiaru (3.2) ---");

        
        Observation observation = XmlFileReader.readObservation(dataObservationFile, Observation.class);
        System.out.println(observation);


        System.out.println("\n--- Przetwarzanie listy pomiarów (3.2) ---");

        // 3. Przeczytanie pliku z listą (ze Slajdu 11)
        ObservationList observationList = XmlFileReader.readObservation(dataObservationsFile, ObservationList.class);

        // Sprawdzenie, czy plik został wczytany
        if (observationList != null) {

            // 4. Przetwarzanie strumieniowe (ze Slajdu 11)
            List<Observation> observationListFiltered = observationList.getObservations().stream()

                    // pomijając pomiary bez komentarza
                    .filter(o -> o.getComment() != null)

                    // konwersja wszystkich jednostek na Celsjusza
                    .map(o -> {
                        double valueInCelsius = switch (o.getUnit()) {
                            case Celsius -> o.getValue();
                            case Fahrenheit -> (o.getValue() - 32) * 5.0 / 9.0;
                            case Kelvin -> o.getValue() - 273.15;
                        };
                        return new Observation(o.getTimestamp(), Observation.TemperatureUnit.Celsius, valueInCelsius, o.getComment());
                    })

                    // filtrowanie: wartości między -1 a +1
                    .filter(o -> o.getValue() > -1 && o.getValue() < 1)


                    .sorted(Comparator.comparingDouble(Observation::getValue))


                    .toList();

            observationListFiltered.forEach(System.out::println);

        } else {
            System.out.println("BŁĄD: Nie udało się wczytać pliku " + dataObservationsFile);
        }
    }
}