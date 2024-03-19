public class SafeHouse extends NormalLocation {
    public SafeHouse(Player player) {
        super(player, "Safe House");
    }

    @Override
    boolean onLocation() {
        if (!getPlayer().getInventory().checkAwards()) {
            System.out.println("#######################################################");
            System.out.println("You are in safe house!");
            this.getPlayer().setHealth(this.getPlayer().getDefaultHealth());
            System.out.println("Your health is renewed!");
            System.out.println("#######################################################");
            return true;
        }
        return true;
    }
}
