package csd214.bookstore.pojos;

import java.util.Objects;
import java.util.Scanner;

public class Lipstick extends MakeupProduct {
    private String finish;

    public Lipstick(String pilot, String color, double v) {
        super();
    }

    public Lipstick() {

    }

    public Lipstick(String superLipstick, double v) {
        super();
    }

    public Lipstick(String s) {
        super();
    }

    @Override
    public void sellItem() {
        System.out.println("Selling Lipstick: " + getShade() + "with a " + finish + " finish for " + getPrice());
    }

    @Override
    public double getPrice() {
        return 20;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lipstick lipstick = (Lipstick) o;
        return Objects.equals(finish, lipstick.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), finish);
    }

    @Override
    public void initialize(Scanner input) {
        super.initialize(input);
        System.out.println(" Select the finish for your Lipstick");
        this.finish = getInput(input,"Gloss");
    }
    

    @Override
    public void edit(Scanner input) {
        super.edit(input);
        System.out.println("Edit your finish Lipstick [" + this.finish + "]:");
        this.finish = getInput(input,this.finish);

    }

    public MakeupProduct prepareStatement(String sql) {
    return new Lipstick("");
    }
}
