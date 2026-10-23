class Mage extends GameCharacter {
    public Mage(String name) { super(name); }
    @Override
    public String attack(GameCharacter enemy) {
        basicAttackCount++;
        enemy.takeDamage(12);
        return name + " casts a Fireball! Dealt 12 damage to " + enemy.getName() + ".";
    }
    @Override
    public String specialMove(GameCharacter enemy) {
        basicAttackCount -= 3;
        enemy.takeDamage(30);
        return name + " channels Lightning Storm! Dealt 30 elemental damage to " + enemy.getName() + ".";
    }
}
