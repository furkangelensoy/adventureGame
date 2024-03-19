public class Inventory {
    private Weapon weapon;
    private Armor armor;
    private String[] awards;

    public Inventory() {
        this.weapon = new Weapon(-1, "Punch", 0, 0);
        this.armor = new Armor(-1, "Scrap", 0, 0);
        this.awards = new String[]{null, null, null};
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void addAwards(int number, String award) {
        this.awards[number] = award;
    }

    public boolean checkAwards() {
        for (String award : this.awards) {
            if (award == null) {
                return false;
            }
        }

        return true;
    }

    public int numberOfAwards() {
        int count = 0;
        for (String award : this.awards) {
            if (award == null) {
                return count;
            }
            count++;
        }
        return count;
    }

    public void printAwards() {
        System.out.print("You collected these awards: ");
        for (String award : this.awards) {
            if (award != null) {
                System.out.print(award + " ");
            }
        }
        System.out.println();
    }
}
