package org.mastermind;

public class RoundData {
    private Integer wellPlaced;
    private Integer misplaced;

    RoundData() {
        wellPlaced = 0;
        misplaced = 0;
    }

    RoundData(Integer wellPlaced, Integer misplaced) {
        this.wellPlaced = wellPlaced;
        this.misplaced = misplaced;
    }

    public void setWellPlaced(Integer wellPlaced) {
        this.wellPlaced = wellPlaced;
    }

    public void setMisplaced(Integer misplaced) {
        this.misplaced = misplaced;
    }

    public Integer getWellPlaced() {
        return wellPlaced;
    }

    public Integer getMisplaced() {
        return misplaced;
    }
}
