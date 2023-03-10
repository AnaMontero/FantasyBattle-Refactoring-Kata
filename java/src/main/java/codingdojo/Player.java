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
        float equipmentDamageModifier = inventory.getEquipment().getDamageModifier();
        float statsDamageModifier = stats.getDamageModifier();
        int totalDamage = Math.round(baseDamage * (equipmentDamageModifier + statsDamageModifier));
        int soak = other.getSoak(totalDamage);
        return new Damage(Math.max(0, totalDamage - soak));
    }
}
