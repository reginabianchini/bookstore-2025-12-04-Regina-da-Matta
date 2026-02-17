package csd214.bookstore.pojos;


import java.util.Objects;
import java.util.Scanner;

public abstract class MakeupProduct extends Product {

    private String shade;

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public MakeupProduct() {
    }

    public MakeupProduct(String shade, double price) {
    }


    @Override
    public void edit(Scanner input) {
        System.out.println("Edit shade [" + this.shade + "]: ");
        this.shade = getInput(input,this.shade);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MakeupProduct that = (MakeupProduct) o;
        return Objects.equals(shade, that.shade);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shade);
    }

    @Override
    public void initialize(Scanner input) {
        System.out.println("Select a shade");
        this.shade = getInput(input,"100");
    }
}