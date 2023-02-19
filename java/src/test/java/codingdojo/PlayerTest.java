package codingdojo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    @Test
    void damageCalculationsShouldReturnTen() {
        Inventory inventory = mock(Inventory.class);
        Stats stats = mock(Stats.class);
        SimpleEnemy target = mock(SimpleEnemy.class);
        Equipment equipment = mock(Equipment.class);

        when(inventory.getEquipment()).thenReturn(equipment);
        when(equipment.getBaseDamage()).thenReturn(10);
        when(equipment.getDamageModifier()).thenReturn(1.0F);

        Damage damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(10, damage.getAmount());
    }

    @Test
    void damageCalculationsShouldReturnThirty() {
        Inventory inventory = mock(Inventory.class);
        Stats stats = mock(Stats.class);
        SimpleEnemy target = mock(SimpleEnemy.class);
        Equipment equipment = mock(Equipment.class);

        when(inventory.getEquipment()).thenReturn(equipment);
        when(equipment.getBaseDamage()).thenReturn(20);
        when(equipment.getDamageModifier()).thenReturn(1.5F);

        Damage damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(30, damage.getAmount());
    }

    @Test
    void damageCalculationsShouldReturnZero() {
        Inventory inventory = mock(Inventory.class);
        Stats stats = mock(Stats.class);
        SimpleEnemy target = mock(SimpleEnemy.class);
        Equipment equipment = mock(Equipment.class);

        when(inventory.getEquipment()).thenReturn(equipment);
        when(equipment.getBaseDamage()).thenReturn(0);
        when(equipment.getDamageModifier()).thenReturn(1.4F);

        Damage damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(0, damage.getAmount());
    }

    @Test
    void damageCalculationsShouldReturnZeroWhenSoakExceedsTotalDamage() {
        Inventory inventory = mock(Inventory.class);
        Stats stats = mock(Stats.class);
        SimpleEnemy target = mock(SimpleEnemy.class);
        Equipment equipment = mock(Equipment.class);

        when(inventory.getEquipment()).thenReturn(equipment);
        when(equipment.getBaseDamage()).thenReturn(10);
        when(equipment.getDamageModifier()).thenReturn(1.0F);
        when(target.getSoak(10)).thenReturn(20);

        Damage damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(0, damage.getAmount());
    }

    @Test
    void damageCalculationsShouldReturnNonNegativeDamage() {
        Inventory inventory = mock(Inventory.class);
        Stats stats = mock(Stats.class);
        SimpleEnemy target = mock(SimpleEnemy.class);
        Equipment equipment = mock(Equipment.class);

        when(inventory.getEquipment()).thenReturn(equipment);
        when(equipment.getBaseDamage()).thenReturn(10);
        when(equipment.getDamageModifier()).thenReturn(0.5F);
        when(target.getSoak(5)).thenReturn(3);

        Damage damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(2, damage.getAmount());
    }

    @ParameterizedTest
    @ValueSource(floats = {0.0F, 0.5F, 1.0F})
    void damageCalculationsShouldRespectDamageModifier(float modifier) {
        Inventory inventory = mock(Inventory.class);
        Stats stats = mock(Stats.class);
        SimpleEnemy target = mock(SimpleEnemy.class);
        Equipment equipment = mock(Equipment.class);

        when(inventory.getEquipment()).thenReturn(equipment);
        when(equipment.getBaseDamage()).thenReturn(10);
        when(equipment.getDamageModifier()).thenReturn(modifier);
        when(target.getSoak(10)).thenReturn(5);

        Damage damage = new Player(inventory, stats).calculateDamage(target);
        assertEquals(Math.max(0, Math.round(10 * modifier - 5)), damage.getAmount());
    }
}
