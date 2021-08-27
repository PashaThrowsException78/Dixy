package com.tabakov;

import com.tabakov.division.DivisionManager;

public class Main {

    public static void main(String[] args) {
        DivisionManager manager = new DivisionManager();
        manager.addDivision("K1\\SK2\\SSK1");
        manager.addDivision("K1\\SK1\\SSK1");
        manager.addDivision("K1\\SK1\\SSK2");
        manager.addDivision("K2\\SK1\\SSK2");

        manager.sortDivisions();
        System.out.println(manager.getDivisions());
    }
}
