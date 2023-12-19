public interface WeaponActions {
    int weaponAtk1(Inventory i, WeaponList weapon, Laum laum, Gods god, Operations op);
    int weaponAtk2(Inventory i, WeaponList weapon, Laum laum, Operations op, StatusEffects e, StatListOperation stat, Gods god, Gods tempGod);
    void shield(StatListOperation stat, StatusEffects e, Laum laum, Laum tempLaum, Operations op);
}
