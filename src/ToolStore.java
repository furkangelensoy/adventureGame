public class ToolStore extends NormalLocation {
    public ToolStore(Player player) {
        super(player, "Store");
    }

    @Override
    boolean onLocation() {
        System.out.println("############# Welcome to Store! #############");
        boolean showMenu = true;
        while (showMenu) {
            System.out.println("1- Weapons\n" + "2- Armors\n" + "3- Exit");
            System.out.print("Your choose: ");
            int select = input.nextInt();
            while (select < 1 || select > 3) {
                System.out.println("You have entered an invalid value. Try again!");
                select = input.nextInt();
            }
            switch (select) {
                case 1:
                    printWeapon();
                    buyWeapon();
                    break;
                case 2:
                    printArmor();
                    buyArmor();
                    break;
                case 3:
                    System.out.println("Have a nice day, see you later!");
                    showMenu = false;
                    break;
            }
        }

        return true;
    }

    public void printWeapon() {
        System.out.println("############# Weapons #############");
        for (Weapon w : Weapon.weapons()) {
            System.out.println(w.getId() + "- " + w.getName() + " <Price: " + w.getPrice() +
                    ", Damage: " + w.getDamage() + " >");
        }

        System.out.println("0- Exit");


    }

    public void buyWeapon() {
        System.out.print("Select a weapon: ");
        int selectWeaponID = input.nextInt();
        while (selectWeaponID < 0 || selectWeaponID > Weapon.weapons().length) {
            System.out.println("You have entered an invalid value! Try again.");
            selectWeaponID = input.nextInt();
        }
        if (selectWeaponID != 0) {
            Weapon selectedWeapon = Weapon.getWeaponObjByID(selectWeaponID);
            if (selectedWeapon != null) {
                if (selectedWeapon.getPrice() > this.getPlayer().getMoney()) {
                    System.out.println("Not enough money!");
                } else {
                    System.out.println("You bought a " + selectedWeapon.getName() + " !");
                    int balance = this.getPlayer().getMoney() - selectedWeapon.getPrice();
                    this.getPlayer().setMoney(balance);
                    this.getPlayer().getInventory().setWeapon(selectedWeapon);
                    System.out.println("Available balance: " + this.getPlayer().getMoney());
                }
            }
        }

    }

    public void printArmor() {
        System.out.println("############# Armors #############");
        for (Armor a : Armor.armors()) {
            System.out.println(a.getId() + "- " + a.getName() + "\t < price : " + a.getPrice()
                    + ",\tblock: " + a.getBlock() + " >");
        }
        System.out.println("0- Exit");
    }

    public void buyArmor() {
        System.out.print("Select an armor: ");
        int selectArmorID = input.nextInt();
        while (selectArmorID < 0 || selectArmorID > Armor.armors().length) {
            System.out.println("You have entered an invalid value! Try again.");
            selectArmorID = input.nextInt();
        }
        if (selectArmorID != 0) {
            Armor selectedArmor = Armor.getArmorObjByID(selectArmorID);
            if (selectedArmor != null) {
                if (selectedArmor.getPrice() > this.getPlayer().getMoney()) {
                    System.out.println("Not enough money!");
                } else {
                    System.out.println("You bought a " + selectedArmor.getName() + " !");
                    int balance = this.getPlayer().getMoney() - selectedArmor.getPrice();
                    this.getPlayer().setMoney(balance);
                    this.getPlayer().getInventory().setArmor(selectedArmor);
                    System.out.println("Available balance: " + this.getPlayer().getMoney());
                }
            }
        }

    }
}
