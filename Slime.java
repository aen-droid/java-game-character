class Slime extends GameCharacter {
    public Slime(String name) { super(name); }
    @Override
    public String attack(GameCharacter enemy) {
        basicAttackCount++;
        enemy.takeDamage(7);
        return name + " bounces forward to slam! Dealt 7 damage to " + enemy.getName() + ".";
    }
    @Override
    public String specialMove(GameCharacter enemy) {
        basicAttackCount -= 3;
        enemy.takeDamage(24);
        return name + " spits Acidic Sludge! Dealt 24 corrosion damage to " + enemy.getName() + ".";
    }
}
