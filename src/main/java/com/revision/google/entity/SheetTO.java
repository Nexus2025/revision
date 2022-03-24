package com.revision.google.entity;

public class SheetTO {
    private final String id;
    private final String name;
    private final Extension extension;

    public SheetTO(String id, String name, Extension exception) {
        this.id = id;
        this.name = name;
        this.extension = exception;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Extension getExtension() {
        return extension;
    }

    public enum Extension {
        XLSX, SPREADSHEET
    }

    @Override
    public String toString() {
        return "SheetTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", extension=" + extension +
                '}';
    }
}