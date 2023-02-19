package codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    void damageCalculationsShouldReturnTen() {

        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(10);
        when(stats.getDamageModifier()).thenReturn(1.0F);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(10, damage.getAmount());
    }

    @Test
    void damageCalculationsShouldReturnZero() {

        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(0);
        when(stats.getDamageModifier()).thenReturn(1.4F);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(0, damage.getAmount());
    }

    @Test
    void damageCalculationsShouldNotReturnThirty() {

        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(20);
        when(stats.getDamageModifier()).thenReturn(1.0F);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertNotEquals(30, damage.getAmount());
    }


    @Test
    void damageCalculationsShouldReturnZeroWhenSoakExceedsTotalDamage() {

        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(10);
        when(stats.getDamageModifier()).thenReturn(1.0F);
        when(target.getSoak(10)).thenReturn(20);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(0, damage.getAmount());
    }

    @Test
    void damageCalculationsShouldReturnNonNegativeDamage() {
        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(10);
        when(stats.getDamageModifier()).thenReturn(0.5F);
        when(target.getSoak(5)).thenReturn(3);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(2, damage.getAmount());
    }

    @ParameterizedTest
    @ValueSource(floats = {0.0F, 1.0F})
    void damageCalculationsShouldApplyDamageModifierCorrectly(float modifier) {

        when(inventory.getEquipment()).thenReturn(equipment);
        when(inventory.getBaseDamage()).thenReturn(10);
        when(stats.getDamageModifier()).thenReturn(modifier);
        when(target.getSoak(10)).thenReturn(5);

        damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(Math.max(0, Math.round(10 * modifier - 5)), damage.getAmount());
    }
}
