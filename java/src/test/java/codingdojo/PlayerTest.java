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

    @BeforeEach
    public void setUp() {
        inventory = mock(Inventory.class);
        stats  = mock(Stats.class);
        target  = mock(SimpleEnemy.class);
        equipment = mock(Equipment.class);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 0.6, 0.4, 0, 10"
    })
    void damageCalculationsShouldBeCorrectlyCalculated(int baseDamage, float equipmentDamageModifier, float statsDamageModifier, int soak, int expectedDamage) {

        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(baseDamage);
        when(equipment.getDamageModifier()).thenReturn(equipmentDamageModifier);
        when(stats.getDamageModifier()).thenReturn(statsDamageModifier);
        when(target.getSoak(baseDamage)).thenReturn(soak);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(expectedDamage, damage.getAmount());
    }
}