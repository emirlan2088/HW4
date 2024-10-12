import java.util.Random;
public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 240, 200}; // Добавляем здоровье Медика
    public static int[] heroesDamage = {20, 15, 10, 0}; // У Медика нет урона
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic"};
    public static int roundNumber;
    public static int medicHealAmount = 30; // Сколько лечит Медик
    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }
    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        medicHeal();
        printStatistics();
    }
    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }
    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length - 1); // 0,1,2 (без Медика)
        bossDefence = heroesAttackType[randomInd];
    }
    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length - 1; i++) { // Не атакует Медик
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage *= coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth = Math.max(bossHealth - damage, 0);
            }
        }
    }
    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] = Math.max(heroesHealth[i] - bossDamage, 0);
            }
        }
    }
    public static void medicHeal() {
        for (int i = 0; i < heroesHealth.length - 1; i++) { // Не лечит себя
            if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                heroesHealth[i] = Math.min(heroesHealth[i] + medicHealAmount, 100);
                System.out.println("Medic heals " + heroesAttackType[i] + " for " + medicHealAmount + " health.");
                break;
            }
        }
    }
    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}