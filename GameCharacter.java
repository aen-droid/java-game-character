abstract class GameCharacter {
    protected String name;
    protected int health;
    protected final int maxHealth = 100;
    protected int basicAttackCount = 0;

    public GameCharacter(String name) {
        this.name = name;
        this.health = maxHealth;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getBasicAttackCount() { return basicAttackCount; }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health < 0) this.health = 0;
    }

    public boolean isDefeated() { return this.health <= 0; }
    public boolean canUseSpecial() { return basicAttackCount >= 3; }

    public abstract String attack(GameCharacter enemy);
    public abstract String specialMove(GameCharacter enemy);
}
