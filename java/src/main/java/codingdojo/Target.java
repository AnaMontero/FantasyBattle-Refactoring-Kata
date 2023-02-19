package codingdojo;

abstract class Target {
    int getSoak(int totalDamage) {
        if (this instanceof SimpleEnemy) {
            SimpleEnemy simpleEnemy = (SimpleEnemy) this;
            return simpleEnemy.getSoak(totalDamage);
        } else {
            // TODO: Not implemented yet
            //  Add friendly fire
            return totalDamage;
        }
    }
}
