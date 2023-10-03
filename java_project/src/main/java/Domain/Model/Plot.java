package Domain.Model;

public class Plot {

    public String designation;
    public int area;
    public String culture;

    public Plot(String designation) {
        this.designation = designation;
        this.area = 500; // default area
        this.culture = "apples"; // default culture
    }



    @Override
    public String toString() {
        return "Plot{" +
                "designation='" + designation + '\'' +
                '}';
    }
}