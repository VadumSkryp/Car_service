package org.example.car_service.model;

public class MechanicSkill {
    private int mechanicId;
    private int skillId;

    public MechanicSkill() {
    }

    public MechanicSkill(int mechanicId, int skillId) {
        this.mechanicId = mechanicId;
        this.skillId = skillId;
    }

    public int getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(int mechanicId) {
        this.mechanicId = mechanicId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    @Override
    public String toString() {
        return "MechanicSkill{" +
                "mechanicId=" + mechanicId +
                ", skillId=" + skillId +
                '}';
    }
}
