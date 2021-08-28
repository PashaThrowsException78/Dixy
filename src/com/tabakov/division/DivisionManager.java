package com.tabakov.division;

import com.tabakov.exception.InvalidDivisionCodeException;
import com.tabakov.util.DivisionValidator;

import java.util.*;


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

    public void addDivision(String division) {
        if (!DivisionValidator.isValidDivision(division)) {
            try {
                throw new InvalidDivisionCodeException("Impossible to insert division into handler: invalid division code");
            } catch (InvalidDivisionCodeException e) {
                e.printStackTrace();
            }
        } else if (isDivisionAlreadyExists(division)) {
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

    // ToDo
    public void sortDivisions() {
        extractAllDivisionCases();
        // Now divisionList includes all divisions cases without repeats, so we can start sorting

        for(int i = 0; i < divisions.size() - 1; i++) {
            for(int j = 0; j < divisions.size() - i - 1; j++) {
                if (requiredToBeRight(divisions.get(j), divisions.get(j+1)).equals(divisions.get(j))) {
                    String t = divisions.get(j);
                    divisions.set(j, divisions.get(j+1));
                    divisions.set(j+1, t);
                }
            }
        }

        isSorted = true;
    }

    public void clear() {
        isSorted = false;
        this.divisions.clear();
    }

    private boolean isDivisionAlreadyExists(String division) {
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

    // This is exotic realization of "max()" method: we have to compare 2
    // string with our own rule, like it's numbers
    // Returns string, that sortDivisions() must move right, to complete algorithm
    private String requiredToBeRight(String div1, String div2) {
        int num1 = Integer.parseInt(div1.replaceAll("\\D+",""));
        int num2 = Integer.parseInt(div2.replaceAll("\\D+",""));

        List<Integer> nums1 = new ArrayList<>();
        List<Integer> nums2 = new ArrayList<>();

        while((num1 > 0) && (num2 > 0)) {
            nums1.add(num1%10);
            nums2.add(num2%10);
            num1/=10;
            num2/=10;
        }

        // Now the order of the numbers in ArrayLists is reversed, we have to fix this

        Collections.reverse(nums1);
        Collections.reverse(nums2);

        if(nums1.size() >= nums2.size()) {
            for(int i = 0; i < nums2.size(); i++) {
                if(nums1.get(i) > nums2.get(i)) {
                    return div2;
                } else if(nums2.get(i) > nums1.get(i)) {
                    return div1;
                }
            }
            return div1;
        } else {
            for(int i = 0; i < nums1.size(); i++) {
                if(nums1.get(i) > nums2.get(i)) {
                    return div2;
                } else if(nums2.get(i) > nums1.get(i)) {
                    return div1;
                }
            }
            return div2;
        }

    }
}
