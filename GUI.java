class GUI extends JFrame {
    private GameCharacter player1;
    private GameCharacter player2;
    private BattleSystem battleSystem;
    private boolean gameActive = false;

    // Queue ADTs (backed by LinkedList)
    private Queue<GameCharacter> turnQueue = new LinkedList<>();   // who acts next
    private Queue<BattleSystem> actionQueue = new LinkedList<>();  // pending actions

    private JTextField p1NameField, p2NameField;
    private JComboBox<String> p1ClassBox, p2ClassBox;
    private JButton startButton;
    private JButton p1BasicBtn, p1SpecialBtn, p2BasicBtn, p2SpecialBtn;
    private JTextArea battleLog;
    private JLabel turnLabel;

    private JLabel p1StatusLabel, p2StatusLabel;
    private JProgressBar p1HealthBar, p2HealthBar;
    private JLabel p1StacksLabel, p2StacksLabel;

    public GUI() {
        setTitle("Human vs Monsters Arena");
        setSize(800, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Selection Top panel
        JPanel setupPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        setupPanel.setBorder(BorderFactory.createTitledBorder("Character Selection"));
        setupPanel.add(new JLabel("Human Name:"));
        p1NameField = new JTextField("Hero"); setupPanel.add(p1NameField);
        setupPanel.add(new JLabel("Monster Name:"));
        p2NameField = new JTextField("Beast"); setupPanel.add(p2NameField);
        setupPanel.add(new JLabel("Human Class:"));
        p1ClassBox = new JComboBox<>(new String[]{"Warrior", "Mage", "Archer"}); setupPanel.add(p1ClassBox);
        setupPanel.add(new JLabel("Monster Class:"));
        p2ClassBox = new JComboBox<>(new String[]{"Siren", "Werewolf", "Slime"}); setupPanel.add(p2ClassBox);
        startButton = new JButton("Start");
        setupPanel.add(new JLabel("")); setupPanel.add(startButton);
        add(setupPanel, BorderLayout.NORTH);

        // Main Arena Panel
        JPanel arenaPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        arenaPanel.setBorder(BorderFactory.createTitledBorder("Battlefield Dashboard"));

        // Human panel
        JPanel p1Panel = new JPanel(new BorderLayout(5, 5));
        p1StatusLabel = new JLabel("Human: Not Active", SwingConstants.CENTER);
        p1HealthBar = new JProgressBar(0, 100); p1HealthBar.setValue(100); p1HealthBar.setStringPainted(true); p1HealthBar.setForeground(Color.GREEN);
        p1StacksLabel = new JLabel("Special Charge: 0 / 3 Stacks", SwingConstants.CENTER);
        p1BasicBtn = new JButton("Basic Attack");
        p1SpecialBtn = new JButton("Special Attack");
        JPanel p1South = new JPanel(new GridLayout(3, 1, 5, 5));
        p1South.add(p1StacksLabel); p1South.add(p1BasicBtn); p1South.add(p1SpecialBtn);
        p1Panel.add(p1StatusLabel, BorderLayout.NORTH);
        p1Panel.add(p1HealthBar, BorderLayout.CENTER);
        p1Panel.add(p1South, BorderLayout.SOUTH);

        // Monster panel
        JPanel p2Panel = new JPanel(new BorderLayout(5, 5));
        p2StatusLabel = new JLabel("Monster: Not Active", SwingConstants.CENTER);
        p2HealthBar = new JProgressBar(0, 100); p2HealthBar.setValue(100); p2HealthBar.setStringPainted(true); p2HealthBar.setForeground(Color.RED);
        p2StacksLabel = new JLabel("Special Charge: 0 / 3 Stacks", SwingConstants.CENTER);
        p2BasicBtn = new JButton("Basic Attack");
        p2SpecialBtn = new JButton("Special Attack");
        JPanel p2South = new JPanel(new GridLayout(3, 1, 5, 5));
        p2South.add(p2StacksLabel); p2South.add(p2BasicBtn); p2South.add(p2SpecialBtn);
        p2Panel.add(p2StatusLabel, BorderLayout.NORTH);
        p2Panel.add(p2HealthBar, BorderLayout.CENTER);
        p2Panel.add(p2South, BorderLayout.SOUTH);

        arenaPanel.add(p1Panel);
        arenaPanel.add(p2Panel);
        add(arenaPanel, BorderLayout.CENTER);

        // Turn indicator + log
        JPanel bottomContainer = new JPanel(new BorderLayout(5, 5));
        turnLabel = new JLabel("Press Start to begin", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 14));
        battleLog = new JTextArea(8, 50); battleLog.setEditable(false); battleLog.setLineWrap(true);
        bottomContainer.add(turnLabel, BorderLayout.NORTH);
        bottomContainer.add(new JScrollPane(battleLog), BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);

        // Handlers
        startButton.addActionListener(e -> initGame());
        p1BasicBtn.addActionListener(e -> performAction(true, false));
        p1SpecialBtn.addActionListener(e -> performAction(true, true));
        p2BasicBtn.addActionListener(e -> performAction(false, false));
        p2SpecialBtn.addActionListener(e -> performAction(false, true));

        updateUIState();
    }

    private void initGame() {
        String p1Name = p1NameField.getText().trim();
        String p2Name = p2NameField.getText().trim();
        if (p1Name.isEmpty() || p2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter names for both characters!");
            return;
        }
        switch ((String) p1ClassBox.getSelectedItem()) {
            case "Warrior": player1 = new Warrior(p1Name); break;
            case "Mage": player1 = new Mage(p1Name); break;
            case "Archer": player1 = new Archer(p1Name); break;
        }
        switch ((String) p2ClassBox.getSelectedItem()) {
            case "Siren": player2 = new Siren(p2Name); break;
            case "Werewolf": player2 = new Werewolf(p2Name); break;
            case "Slime": player2 = new Slime(p2Name); break;
        }
        battleSystem = new BattleSystem(player1, player2);
        gameActive = true;
        battleLog.setText("=== Match Formed! ===\nHuman goes first. Choose Basic or Special Attack.\n");
        battleLog.append("\n--- ROUND " + battleSystem.getRound() + " ---\n");
        updateUIState();
    }

    // Enqueue the chosen action, then let the BattleSystem process the queue
    private void performAction(boolean isHuman, boolean special) {
        if (!gameActive) return;

        GameCharacter requester;
        if (isHuman) {
            requester = player1;
        } else {
            requester = player2;
        }
        if (battleSystem.getCurrentActor() != requester) return;

        int roundBefore = battleSystem.getRound();
        if (!battleSystem.enqueueAction(special)) {
            battleLog.append(requester.getName() + " needs 3 stacks to use the Special Attack!\n");
            return;
        }

        List<String> results = battleSystem.processActions();
        for (String line : results) {
            if (!line.isEmpty()) {
                battleLog.append(line + "\n");
            }
        }

        if (battleSystem.isOver()) {
            gameActive = false;
            updateUIState();
            GameCharacter winner = battleSystem.getWinner();
            battleLog.append("\n*** BATTLE OVER! " + winner.getName().toUpperCase() + " WINS THE MATCH! ***\n");
            JOptionPane.showMessageDialog(this, winner.getName() + " has defeated their opponent!");
            return;
        }

        if (battleSystem.getRound() > roundBefore) {
            battleLog.append("\n--- ROUND " + battleSystem.getRound() + " ---\n");
        }
        updateUIState();
    }

    private void updateUIState() {
        if (player1 != null && player2 != null) {
            p1HealthBar.setValue(player1.getHealth());
            p1HealthBar.setString(player1.getHealth() + " / 100 HP");
            p2HealthBar.setValue(player2.getHealth());
            p2HealthBar.setString(player2.getHealth() + " / 100 HP");

            p1StatusLabel.setText(player1.getName() + " (" + player1.getClass().getSimpleName() + ")");
            p2StatusLabel.setText(player2.getName() + " (" + player2.getClass().getSimpleName() + ")");

            p1StacksLabel.setText("Special Charge: " + player1.getBasicAttackCount() + " / 3 Stacks");
            p2StacksLabel.setText("Special Charge: " + player2.getBasicAttackCount() + " / 3 Stacks");
        }

        boolean p1Active = false;
        boolean p2Active = false;
        if (gameActive) {
            p1Active = battleSystem.getCurrentActor() == player1;
            p2Active = battleSystem.getCurrentActor() == player2;
        }

        p1BasicBtn.setEnabled(p1Active);
        p1SpecialBtn.setEnabled(p1Active && player1.canUseSpecial());
        p2BasicBtn.setEnabled(p2Active);
        p2SpecialBtn.setEnabled(p2Active && player2.canUseSpecial());

        if (!gameActive) {
            if (player1 == null) {
                turnLabel.setText("Press Start to begin");
            } else {
                turnLabel.setText("Match finished - press Start for a new match");
            }
        } else if (p1Active) {
            turnLabel.setText("Turn: " + player1.getName() + " (Human)");
        } else {
            turnLabel.setText("Turn: " + player2.getName() + " (Monster)");
        }
    }
}
