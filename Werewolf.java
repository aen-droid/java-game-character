class Werewolf extends GameCharacter {
    public Werewolf(String name) { super(name); }
    @Override
    public String attack(GameCharacter enemy) {
        basicAttackCount++;
        enemy.takeDamage(11);
        return name + " bites aggressively! Dealt 11 damage to " + enemy.getName() + ".";
    }
    @Override
    public String specialMove(GameCharacter enemy) {
        basicAttackCount -= 3;
        enemy.takeDamage(27);
        return name + " performs a Full Moon Rage! Dealt 27 shredding damage to " + enemy.getName() + ".";
    }
}
