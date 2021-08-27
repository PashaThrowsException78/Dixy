package com.tabakov.division;

import com.tabakov.exception.InvalidDivisionCodeException;
import com.tabakov.util.DivisionValidator;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DivisionManager {

    private ArrayList<String> divisions;
    private boolean isSorted;

    public DivisionManager() {
        divisions = new ArrayList<>();
        isSorted = false;
    }

    public ArrayList<String> getDivisions() {
        return divisions;
    }

    public void setDivisions(ArrayList<String> divisions) {
        for(String division : divisions) {
            addDivision(division);
        }
        isSorted = false;
        this.divisions = divisions;
    }

    public void addDivision(String division) {
        if (!DivisionValidator.isValidDivision(division)) {
            try {
                throw new InvalidDivisionCodeException("Impossible to insert division into handler: invalid division code");
            } catch (InvalidDivisionCodeException e) {
                e.printStackTrace();
            }
        } else if (isAlreadyExists(division)) {
            return;
        } else {
            isSorted = false;
            divisions.add(division);
        }
    }

    public void addDivisions(List<String> divisions) {
        for (String division : divisions) {
            addDivision(division);
        }
    }

    public boolean isSorted() {
        return isSorted;
    }

    public void sortDivisions() {
        extractAllDivisionCases();
        // Now division list includes all divisions cases without repeats, so we can sort them

    }

    private boolean isAlreadyExists(String division) {
        for(String d : divisions) {
            if(d.equals(division)) {
                return true;
            }
        }
        return false;
    }

    private void extractAllDivisionCases() {
        ArrayList<String> extraDivisionCases = new ArrayList<>();
        for(String division : divisions) {
            extraDivisionCases.addAll(extractAllCasesFromDivision(division));
        }
        addDivisions(extraDivisionCases);
    }

    private ArrayList<String> extractAllCasesFromDivision(String division) {
        ArrayList<String> extractedDivisions = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(division, "\\");
        while(tokenizer.hasMoreTokens()) {
            extractedDivisions.add(tokenizer.nextToken());
        }

        ArrayList<String> subDivisions = new ArrayList<>();

        if(extractedDivisions.size() > 1) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(extractedDivisions.get(0));

            subDivisions.add(stringBuilder.toString());
            for (int i = 1; i < extractedDivisions.size(); i++) {
                stringBuilder.append("\\");
                stringBuilder.append(extractedDivisions.get(i));
                subDivisions.add(stringBuilder.toString());
            }

            subDivisions.add(stringBuilder.toString());
        }
        return subDivisions;
    }
}
