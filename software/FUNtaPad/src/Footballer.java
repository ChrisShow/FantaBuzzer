
public class Footballer {

    private double id, actualValue, initialValue, valueDiff, actualValueMantra, initialValueMantra, valueDiffMantra, FVM, FVMMantra;
    private String role, mantraRole, surname, team;

    public Footballer(double id, String role, String mantraRole, String surname,
                      String team, double actualValue, double initialValue, double valueDiff,
                      double actualValueMantra, double initialValueMantra,
                      double valueDiffMantra, double FVM, double FVMMantra){

        this.id = id;
        this.role = role;
        this.mantraRole = mantraRole;
        this.surname = surname;
        this.team = team;
        this.actualValue = actualValue;
        this.initialValue = initialValue;
        this.valueDiff = valueDiff;
        this.actualValueMantra = actualValueMantra;
        this.initialValueMantra = initialValueMantra;
        this.valueDiffMantra = valueDiffMantra;
        this.FVM = FVM;
        this.FVMMantra = FVMMantra;

    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    public double getValueDiff() {
        return valueDiff;
    }

    public void setValueDiff(double valueDiff) {
        this.valueDiff = valueDiff;
    }

    public double getActualValueMantra() {
        return actualValueMantra;
    }

    public void setActualValueMantra(double actualValueMantra) {
        this.actualValueMantra = actualValueMantra;
    }

    public double getInitialValueMantra() {
        return initialValueMantra;
    }

    public void setInitialValueMantra(double initialValueMantra) {
        this.initialValueMantra = initialValueMantra;
    }

    public double getValueDiffMantra() {
        return valueDiffMantra;
    }

    public void setValueDiffMantra(double valueDiffMantra) {
        this.valueDiffMantra = valueDiffMantra;
    }

    public double getFVM() {
        return FVM;
    }

    public void setFVM(double FVM) {
        this.FVM = FVM;
    }

    public double getFVMMantra() {
        return FVMMantra;
    }

    public void setFVMMantra(double FVMMantra) {
        this.FVMMantra = FVMMantra;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMantraRole() {
        return mantraRole;
    }

    public void setMantraRole(String mantraRole) {
        this.mantraRole = mantraRole;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString(){
        return "" + this.id + "---" + this.role + "---" + this.mantraRole + "---" + this.surname + "---" +
                this.team + "---" + this.actualValue + "---" + this.initialValue + "---" +
                this.valueDiff + "---" + this.actualValueMantra + "---" + this.initialValueMantra + "---"
                + this.valueDiffMantra + "---" + this.FVM + "---" + this.FVMMantra + "\n";
    }
}
