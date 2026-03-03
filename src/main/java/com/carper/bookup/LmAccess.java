package com.carper.bookup;

import com.carper.bookup.base.LibraryManager;

public class LmAccess {

    private static final LibraryManager manager = new LibraryManager();

    static {
        try {
            manager.loadFromFile("library.txt");
            System.out.println("Library loaded from file.");
        } catch (Exception e) {
            System.out.println("No saved library found. Starting empty.");
        }
    }

    public static LibraryManager getLibraryManager() {
        return manager;
    }
}
