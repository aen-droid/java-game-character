class Siren extends GameCharacter {
    public Siren(String name) { super(name); }
    @Override
    public String attack(GameCharacter enemy) {
        basicAttackCount++;
        enemy.takeDamage(8);
        return name + " scratches viciously! Dealt 8 damage to " + enemy.getName() + ".";
    }
    @Override
    public String specialMove(GameCharacter enemy) {
        basicAttackCount -= 3;
        enemy.takeDamage(26);
        return name + " shrieks a haunting Siren Song! Dealt 26 damage to " + enemy.getName() + ".";
    }
}
