package codingdojo;


class Player extends Target {
    private Inventory inventory;
    private Stats stats;

    Player(Inventory inventory, Stats stats) {
        this.inventory = inventory;
        this.stats = stats;
    }

    Damage calculateDamage(Target other) {
        int baseDamage = inventory.getBaseDamage();
        float damageModifier = getDamageModifier();
        int totalDamage = Math.round(baseDamage * damageModifier);
        int soak = other.getSoak(totalDamage);
        return new Damage(Math.max(0, totalDamage - soak));
    }


    private float getDamageModifier() {
        Equipment equipment = this.inventory.getEquipment();
        float strengthModifier = stats.getStrength() * 0.1f;
        return strengthModifier + equipment.getDamageModifier();
    }
}
