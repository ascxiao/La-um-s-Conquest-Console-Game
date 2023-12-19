public interface BattleDialogue {
    void encounter();
    void gotHit(boolean hit);
    void enemyDefeat();
    void death();
}
