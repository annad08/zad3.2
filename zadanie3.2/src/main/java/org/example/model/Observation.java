package org.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;

@XmlRootElement(name = "weather-observation", namespace = "http://example.org/weather-observation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Observation {

    public enum TemperatureUnit { Celsius, Fahrenheit, Kelvin }

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime timestamp;

    @XmlElement(required = true)
    private TemperatureUnit unit;

    @XmlElement(required = true)
    private double value;

    @XmlElement(required = false)
    private String comment;

    // Konstruktor bezargumentowy
    public Observation() {}

    // Konstruktor z argumentami
    public Observation(LocalDateTime timestamp, TemperatureUnit unit, double value, String comment) {
        this.timestamp = timestamp;
        this.unit = unit;
        this.value = value;
        this.comment = comment;
    }

    // Gettery i Settery
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public TemperatureUnit getUnit() { return unit; }
    public void setUnit(TemperatureUnit unit) { this.unit = unit; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    // Metoda toString
    @Override
    public String toString() {
        return "Observation{" +
                "timestamp=" + timestamp +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                ", comment='" + comment + '\'' +
                '}';
    }
}