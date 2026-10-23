class Archer extends GameCharacter {
    public Archer(String name) { super(name); }
    @Override
    public String attack(GameCharacter enemy) {
        basicAttackCount++;
        enemy.takeDamage(9);
        return name + " fires a Quick Shot! Dealt 9 damage to " + enemy.getName() + ".";
    }
    @Override
    public String specialMove(GameCharacter enemy) {
        basicAttackCount -= 3;
        enemy.takeDamage(28);
        return name + " releases Piercing Arrow! Dealt 28 damage to " + enemy.getName() + ".";
    }
}
