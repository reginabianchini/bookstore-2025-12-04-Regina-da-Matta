package csd214.bookstore.pojos;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Scanner;

public class Foundation extends MakeupProduct{
    private String coverage;

    public Foundation(String superFoundation, double v) {
        super();
    }

    public Foundation() {

    }

    @Override
    public void initialize(Scanner input) {
        super.initialize(input);
        System.out.println("Select coverage");
        this.coverage = getInput(input,"Full coverage");
    }
    @Override
    public void edit(Scanner input) {
        super.edit(input);
        System.out.print("Edit coverage [" +  this.coverage + "]:");
        this.coverage = getInput(input,this.coverage);
    }
    @Override
    public void sellItem() {
        System.out.println("Selling Foundation: " + getShade() + " with " + coverage + " for " + getPrice());
    }

    @Override
    public double getPrice() {
        return 40;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Foundation that = (Foundation) o;
        return Objects.equals(coverage, that.coverage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coverage);
    }

    public PreparedStatement prepareStatement(String sql) {
        return null;
    }
}
