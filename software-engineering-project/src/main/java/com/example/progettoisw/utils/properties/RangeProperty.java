package com.example.progettoisw.utils.properties;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RangeProperty {
    private double start, end;
    private DoubleProperty rangeProperty;

    public RangeProperty(double start, double end) {
        this.start = start;
        this.end = end;
        this.rangeProperty = new SimpleDoubleProperty(start);
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public DoubleProperty rangeProperty() {
        return rangeProperty;
    }

    public void bind(DoubleProperty source) {
        this.rangeProperty.bind(source.multiply(end - start).add(start));
    }
}
