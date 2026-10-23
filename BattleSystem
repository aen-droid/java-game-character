class BattleSystem {
    private final GameCharacter human;
    private final GameCharacter monster;
    private final GameCharacter firstActor;

    // Queue of who acts next (front = current actor, rotates after each action)
    private final Queue<GameCharacter> turnQueue = new LinkedList<GameCharacter>();
    private final Queue<BattleAction> actionQueue = new LinkedList<BattleAction>();
    private int roundCounter = 1;

    public BattleSystem(GameCharacter human, GameCharacter monster) {
        this.human = human;
        this.monster = monster;
        this.firstActor = human;
        turnQueue.add(human);
        turnQueue.add(monster);
    }

    public GameCharacter getCurrentActor() { return turnQueue.peek(); }
    public int getRound() { return roundCounter; }
    public int getPendingActions() { return actionQueue.size(); }

    private GameCharacter getOpponent(GameCharacter actor) {
        if (actor == human) {
            return monster;
        }
        return human;
    }

    // Adds the current actor's action to the action queue
    public boolean enqueueAction(boolean special) {
        GameCharacter actor = turnQueue.peek();
        if (actor == null || isOver()) return false;
        if (special && !actor.canUseSpecial()) return false;
        actionQueue.add(new BattleAction(actor, getOpponent(actor), special));
        return true;
    }

    // Dequeues and executes every queued action, then rotates the turn queue
    public List<String> processActions() {
        List<String> messages = new ArrayList<String>();
        while (!actionQueue.isEmpty()) {
            BattleAction action = actionQueue.poll();
            messages.add(action.execute());

            if (isOver()) {
                actionQueue.clear();
                break;
            }

            // Move the actor who just acted to the back of the turn queue
            GameCharacter finished = turnQueue.poll();
            turnQueue.add(finished);
            if (turnQueue.peek() == firstActor) {
                roundCounter++;
            }
        }
        return messages;
    }

    public boolean isOver() {
        return human.isDefeated() || monster.isDefeated();
    }

    public GameCharacter getWinner() {
        if (!isOver()) return null;
        if (human.isDefeated()) {
            return monster;
        }
        return human;
    }
}

// A single queued action (Basic or Special)
class BattleAction {
    private final GameCharacter actor;
    private final GameCharacter target;
    private final boolean special;

    public BattleAction(GameCharacter actor, GameCharacter target, boolean special) {
        this.actor = actor;
        this.target = target;
        this.special = special;
    }

    public String execute() {
        if (actor.isDefeated()) {
            return actor.getName() + " is unconscious and cannot act!";
        }
        if (target.isDefeated()) {
            return "";
        }
        if (special) {
            return actor.specialMove(target);
        }
        return actor.attack(target);
    }
}
