package codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    Inventory inventory;
    Stats stats;
    SimpleEnemy target;
    Equipment equipment;
    Damage damage;
    SimpleEnemy simpleEnemy;

    @BeforeEach
    public void setUp() {
        inventory = mock(Inventory.class);
        stats  = mock(Stats.class);
        target  = mock(SimpleEnemy.class);
        equipment = mock(Equipment.class);
        simpleEnemy = mock(SimpleEnemy.class);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 0.6, 0.4, 0, 10",
            "8, 1.0, 1.0, 5, 11",
            "17, 0.4, 1.9, 20, 19",
            "0, 0.6, 0.4, 1, 0",
            "15, 1.0, 0.0, 20, 0",
    })
    void damageCalculationsShouldBeCorrectlyCalculated(int baseDamage, float equipmentDamageModifier, float statsDamageModifier, int soak, int expectedDamage) {

        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(baseDamage);
        when(equipment.getDamageModifier()).thenReturn(equipmentDamageModifier);
        when(stats.getDamageModifier()).thenReturn(statsDamageModifier);
        int totalDamage = Math.round(baseDamage * (equipmentDamageModifier + statsDamageModifier));
        when(target.getSoak(totalDamage)).thenReturn(soak);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(expectedDamage, damage.getAmount());
    }
}