package com.example.progettoisw.utils.properties;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @param <T> Support Only String and Integer
 */
public class StringListProperty<T> implements Property<String>, List<T> {

    private final Class<T> genericClass;
    private final StringProperty stringProperty;
    private final ObservableList<T> values = FXCollections.observableArrayList();

    public StringListProperty(Class<T> genericClass, Object obj, String name) {
        this.genericClass = genericClass;

        stringProperty = new SimpleStringProperty(obj, name);
        stringProperty.bind(Bindings.createStringBinding(() -> {
            StringBuilder sb = new StringBuilder();
            for (T v : values) {
                sb.append(v);

                if (!v.toString().equals("") && values.indexOf(v) != values.size() - 1)
                    sb.append(",");
            }

            return String.format("[%1$s]", sb);
        }, values));
    }

    public StringProperty stringProperty() {
        return stringProperty;
    }

    public void set(String s) {
        if (!(s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']'))
            throw new RuntimeException("String does not reflect an array. Must be [v1,v2,v3,...] ");

        s = s.substring(1, s.length() - 1);

        values.clear();
        for (String value : s.split(",")) {
            if (genericClass == Integer.class)
                values.add((T) value.strip());
            else if (genericClass == String.class)
                values.add((T) value.strip());
            else
                throw new RuntimeException("Unsupported generics");
        }
    }

    @Override
    public void bind(ObservableValue<? extends String> observableValue) {
        stringProperty.bind(observableValue);
    }

    @Override
    public void unbind() {
        stringProperty.unbind();
    }

    @Override
    public boolean isBound() {
        return stringProperty.isBound();
    }

    @Override
    public void bindBidirectional(Property<String> property) {
        stringProperty.bindBidirectional(property);
    }

    @Override
    public void unbindBidirectional(Property<String> property) {
        stringProperty.unbindBidirectional(property);
    }

    @Override
    public Object getBean() {
        return stringProperty.getBean();
    }

    @Override
    public String getName() {
        return stringProperty.getName();
    }

    @Override
    public void addListener(ChangeListener<? super String> changeListener) {
        stringProperty.addListener(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super String> changeListener) {
        stringProperty.removeListener(changeListener);
    }

    @Override
    public String getValue() {
        return stringProperty.getValue();
    }

    @Override
    public void setValue(String s) {
        stringProperty.setValue(s);
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        stringProperty.addListener(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        stringProperty.removeListener(invalidationListener);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return values.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return values.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return values.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return values.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return values.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        return values.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return values.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return values.retainAll(c);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public T get(int index) {
        return values.get(index);
    }

    @Override
    public T set(int index, T element) {
        return values.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        values.add(index, element);
    }

    @Override
    public T remove(int index) {
        return values.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return values.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return values.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return values.listIterator(index);
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return values.subList(fromIndex, toIndex);
    }
}
