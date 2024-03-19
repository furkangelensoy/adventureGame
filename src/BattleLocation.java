import java.util.Random;

public abstract class BattleLocation extends Location {
    private Obstacle obstacle;
    private String award;
    private int maxObstacle;
    private boolean isComplete;
    private Random r = new Random();

    public BattleLocation(Player player, String name, Obstacle obstacle, String award, int maxObstacle) {
        super(player, name);
        this.obstacle = obstacle;
        this.award = award;
        this.maxObstacle = maxObstacle;
    }

    @Override
    boolean onLocation() {
        if (this.isComplete()) {
            System.out.println("=====================================================");
            System.out.println("You have already cleared this region. There is no enemy here!");
            System.out.println("=====================================================");
            return true;
        }
        int obsNumber = this.randomObstacleNumber();
        System.out.println("Your location: " + this.getName());
        System.out.println("Be careful! " + obsNumber + " unit " + this.getObstacle().getName() + " live here!");
        System.out.println("----------------------------------------");
        System.out.print("<W>ar or <R>un: ");
        String selectCase = input.next();
        System.out.println("----------------------------------------");
        selectCase = selectCase.toUpperCase();
        if (selectCase.equals("W") && combat(obsNumber)) {
            System.out.println(this.getName() + ", you have successfully cleared the area!");
            System.out.println("----------------------------------------");
            this.setComplete(true);
            if (this.getName().equals("Mine")) {
                return true;
            }
            int number = this.getPlayer().getInventory().numberOfAwards();
            this.getPlayer().getInventory().addAwards(number, this.getAward());
            System.out.println("Congratulations, you won a " + this.getAward() + " !");
            System.out.println("----------------------------------------");
            if (this.getPlayer().getInventory().checkAwards()) {
                System.out.println("===============================================");
                System.out.println("Congratulations, you have cleared all regions and you got all awards!");
                System.out.println("Now, you must go to the safe house for win the game!");
                System.out.println("===============================================");
            }
            return true;
        }
        if (this.getPlayer().getHealth() <= 0) {
            System.out.println("You died!");
            return false;
        }
        return true;
    }

    public boolean combat(int obstacleNumber) {
        for (int i = 1; i <= obstacleNumber; i++) {
            if (this.getObstacle().getId() == 4) {
                drop();
                this.getObstacle().setDamage(r.nextInt(2, 6) + 1);
            }
            System.out.println("=====================================================");
            this.getObstacle().setHealth(this.getObstacle().getDefaultHealth());
            playerStats();
            obstacleStat(i);
            while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                System.out.print("<A>ttack or <R>un: ");
                String selectCombat = input.next();
                if (selectCombat.equalsIgnoreCase("A")) {
                    if (whoIsFirst() == 0) {
                        playerAttack();
                        obstacleAttack();
                    } else {
                        obstacleAttack();
                        playerAttack();
                    }
                } else {
                    return false;
                }
            }

            if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                System.out.println("You have killed enemy!");
                System.out.println("You got " + this.getObstacle().getAward() + " money.");
                this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getAward());
                System.out.println("Your current money: " + this.getPlayer().getMoney());
            } else {
                return false;
            }
        }
        System.out.println("=====================================================");
        return true;
    }

    public void afterHit() {
        System.out.println("Your health: " + this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getName() + "'s health: " + this.getObstacle().getHealth());
        System.out.println("--------------");
    }

    public void playerStats() {
        System.out.println("Player Stats");
        System.out.println("----------------------------");
        System.out.println("Health: " + this.getPlayer().getHealth());
        System.out.println("Weapon: " + this.getPlayer().getInventory().getWeapon().getName());
        System.out.println("Amor:" + this.getPlayer().getInventory().getArmor().getName());
        System.out.println("Damage: " + this.getPlayer().getTotalDamage());
        System.out.println("Block: " + this.getPlayer().getInventory().getArmor().getBlock());
        System.out.println("Money: " + this.getPlayer().getMoney());
        System.out.println("----------------------------");
    }

    public void obstacleStat(int i) {
        System.out.println(i + ". " + this.getObstacle().getName() + " Stats");
        System.out.println("-----------------------");
        System.out.println("Health: " + this.getObstacle().getHealth());
        System.out.println("Damage: " + this.getObstacle().getDamage());
        System.out.println("Award: " + this.getObstacle().getAward());
        System.out.println("-----------------------");
    }

    public int randomObstacleNumber() {
        return r.nextInt(this.getMaxObstacle()) + 1;
    }

    public void playerAttack() {
        System.out.println("You attacked! ");
        this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
        afterHit();
    }

    public void obstacleAttack() {
        if (this.getObstacle().getHealth() > 0) {
            System.out.println();
            System.out.println(this.getObstacle().getName() + " attacked you!");
            int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
            if (obstacleDamage < 0) {
                obstacleDamage = 0;
            }
            this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
            afterHit();
        }
    }

    public int whoIsFirst() {
        return r.nextInt(2);
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getMaxObstacle() {
        return maxObstacle;
    }

    public void setMaxObstacle(int maxObstacle) {
        this.maxObstacle = maxObstacle;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void drop() {
        int drop = chances();
        if (drop <= 15) {
            dropWeapon();
        } else if (drop > 15 && drop <= 30) {
            dropArmor();
        } else if (drop > 30 && drop <= 45) {
            dropMoney();
        } else {
            System.out.println("You could not drop any items!");
        }
    }

    public int chances() {
        int chance = r.nextInt(100) + 1;
        return chance;
    }

    public void dropWeapon() {
        int drop = chances();
        if (drop <= 20) {
            System.out.println("You got drop: " + Weapon.getWeaponObjByID(3).getName());
        } else if (drop > 20 && drop <= 50) {
            System.out.println("You got drop: " + Weapon.getWeaponObjByID(2).getName());
        } else {
            System.out.println("You got drop: " + Weapon.getWeaponObjByID(1).getName());
        }
    }

    public void dropArmor() {
        int drop = chances();
        if (drop <= 20) {
            System.out.println("You got drop: " + Armor.getArmorObjByID(3).getName());
        } else if (drop > 20 && drop <= 50) {
            System.out.println("You got drop: " + Armor.getArmorObjByID(2).getName());
        } else {
            System.out.println("You got drop: " + Armor.getArmorObjByID(1).getName());
        }
    }

    public void dropMoney() {
        int drop = chances();
        if (drop <= 20) {
            System.out.println("You got 10 money!");
            this.getPlayer().setMoney(this.getPlayer().getMoney() + 10);
        } else if (drop > 20 && drop <= 50) {
            System.out.println("You got 5 money!");
            this.getPlayer().setMoney(this.getPlayer().getMoney() + 5);
        } else {
            System.out.println("You got 1 money!");
            this.getPlayer().setMoney(this.getPlayer().getMoney() + 1);
        }
    }
}
