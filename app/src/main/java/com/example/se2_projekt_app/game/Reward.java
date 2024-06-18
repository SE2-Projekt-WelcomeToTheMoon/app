package com.example.se2_projekt_app.game;

import com.example.se2_projekt_app.enums.RewardCategory;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class Reward {
    private final RewardCategory category;
    private int numberRockets;
    private boolean systemErrorClaimed = false;

    public Reward(RewardCategory category) {
        if (category == null) throw new IllegalArgumentException("Reward Category may not be null");
        this.category = category;

    }

    public Reward(RewardCategory category, int numberRockets) {
        if (category == null) throw new IllegalArgumentException("Reward Category may not be null");
        this.category = category;
        this.numberRockets = numberRockets;
    }




    public void claimSystemError() {
        this.systemErrorClaimed = true;
    }

    /**
     * Top Floor 1 Chamber 3 Rockets
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getFirstFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,3));
        list.add(firstChamber);
        return list;
    }

    /**
     * Second Floor 1 Chamber 4 Rockets 1 SysError
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getSecondFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,4));
        firstChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        list.add(firstChamber);
        return list;
    }

    /**
     * Third Floor 2 Chambers first 2 Rocket 1 SysError second 6 Rockets
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getThirdFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,2));
        firstChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        list.add(firstChamber);
        ArrayList<Reward> secondChamber=new ArrayList<>();
        secondChamber.add(new Reward(RewardCategory.ROCKET,6));
        list.add(secondChamber);
        return list;
    }

    /**
     * Fourth Floor 2 Chamber first 2 Rockets second 3 Rockets
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getFourthFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,2));
        list.add(firstChamber);
        ArrayList<Reward> secondChamber=new ArrayList<>();
        secondChamber.add(new Reward(RewardCategory.ROCKET,3));
        list.add(secondChamber);
        return list;
    }

    /**
     * Fifth floor 1 chamber 3 Rockets 2 SysError
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getFifthFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,3));
        firstChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        firstChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        list.add(firstChamber);
        return list;
    }

    /**
     * Sixth Floor 1 Chamber 8 Rockets 1 SysError
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getSixthFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,8));
        firstChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        list.add(firstChamber);
        return list;
    }

    /**
     * Seventh Floor 3 Chamber first 4 Rockets 2 SysErrors second 3 Rockets third 2 Rockets
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getSeventhFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,4));
        firstChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        firstChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        list.add(firstChamber);
        ArrayList<Reward> secondChamber=new ArrayList<>();
        secondChamber.add(new Reward(RewardCategory.ROCKET,3));
        list.add(secondChamber);
        ArrayList<Reward> thirdChamber=new ArrayList<>();
        thirdChamber.add(new Reward(RewardCategory.ROCKET,2));
        list.add(thirdChamber);
        return list;
    }

    /**
     * Eighth Floor 4 chambers first and third 4 rockets second 2 rockets fourth 1 rocket 1 SysError
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getEighthFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,4));
        list.add(firstChamber);
        ArrayList<Reward> secondChamber=new ArrayList<>();
        secondChamber.add(new Reward(RewardCategory.ROCKET,2));
        list.add(secondChamber);
        ArrayList<Reward> thirdChamber=new ArrayList<>();
        thirdChamber.add(new Reward(RewardCategory.ROCKET,4));
        list.add(thirdChamber);
        ArrayList<Reward> fourthChamber=new ArrayList<>();
        fourthChamber.add(new Reward(RewardCategory.ROCKET,1));
        fourthChamber.add(new Reward(RewardCategory.SYSTEMERROR));
        list.add(fourthChamber);
        return list;
    }

    /**
     * Ninth floor 4 chambers all 2 Rockets
     * @return the created List
     */
    public static ArrayList<ArrayList<Reward>> getNinthFloorRewards() {
        ArrayList<ArrayList<Reward>> list=new ArrayList<>();
        ArrayList<Reward> firstChamber=new ArrayList<>();
        firstChamber.add(new Reward(RewardCategory.ROCKET,2));
        list.add(firstChamber);
        ArrayList<Reward> secondChamber=new ArrayList<>();
        secondChamber.add(new Reward(RewardCategory.ROCKET,2));
        list.add(secondChamber);
        ArrayList<Reward> thirdChamber=new ArrayList<>();
        thirdChamber.add(new Reward(RewardCategory.ROCKET,2));
        list.add(thirdChamber);
        ArrayList<Reward> fourthChamber=new ArrayList<>();
        fourthChamber.add(new Reward(RewardCategory.ROCKET,2));
        list.add(fourthChamber);
        return list;
    }

}