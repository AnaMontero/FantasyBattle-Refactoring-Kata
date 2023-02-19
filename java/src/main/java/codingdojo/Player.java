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
        float damageModifier = inventory.getEquipment().getDamageModifier() +
                stats.getDamageModifier();
        int totalDamage = Math.round(baseDamage * damageModifier);
        int soak = other.getSoak(totalDamage);
        return new Damage(Math.max(0, totalDamage - soak));
    }
}
