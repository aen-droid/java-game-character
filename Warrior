class Warrior extends GameCharacter {
    public Warrior(String name) { super(name); }
    @Override
    public String attack(GameCharacter enemy) {
        basicAttackCount++;
        enemy.takeDamage(10);
        return name + " swings a mighty sword! Dealt 10 damage to " + enemy.getName() + ".";
    }
    @Override
    public String specialMove(GameCharacter enemy) {
        basicAttackCount -= 3;
        enemy.takeDamage(25);
        return name + " triggers Shield Bash! Dealt 25 heavy damage to " + enemy.getName() + ".";
    }
}
