package codingdojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleEnemy extends Target {

    private Armor armor;
    private List<Buff> buffs;

    public SimpleEnemy(Armor armor, List<Buff> buffs) {
        this.armor = armor;
        this.buffs = buffs;
    }

    List<Buff> getBuffs() {
        return buffs;
    }

    Armor getArmor() {
        return this.armor;
    }

    int getSoak(int totalDamage) {
        Armor armor = getArmor();
        int armorSoak = Math.round(armor.getDamageSoak());
        float buffModifier = 1 + (float) getBuffs().stream().mapToDouble(Buff::soakModifier).sum();
        return Math.round(armorSoak * buffModifier);
    }
}
