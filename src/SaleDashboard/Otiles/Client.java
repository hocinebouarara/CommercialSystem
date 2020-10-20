package SaleDashboard.Otiles;

public class Client {
    int IDCL;
    String NOCL;
    String ADCL;
    String VICL;
    String TECL;
    String FACL;
    String NORECL;

    public Client(int IDCL, String NOCL, String ADCL, String VICL, String TECL, String FACL, String NORECL) {
        this.IDCL = IDCL;
        this.NOCL = NOCL;
        this.ADCL = ADCL;
        this.VICL = VICL;
        this.TECL = TECL;
        this.FACL = FACL;
        this.NORECL = NORECL;
    }

    public int getIDCL() {
        return IDCL;
    }

    public void setIDCL(int IDCL) {
        this.IDCL = IDCL;
    }

    public String getNOCL() {
        return NOCL;
    }

    public void setNOCL(String NOCL) {
        this.NOCL = NOCL;
    }

    public String getADCL() {
        return ADCL;
    }

    public void setADCL(String ADCL) {
        this.ADCL = ADCL;
    }

    public String getVICL() {
        return VICL;
    }

    public void setVICL(String VICL) {
        this.VICL = VICL;
    }

    public String getTECL() {
        return TECL;
    }

    public void setTECL(String TECL) {
        this.TECL = TECL;
    }

    public String getFACL() {
        return FACL;
    }

    public void setFACL(String FACL) {
        this.FACL = FACL;
    }

    public String getNORECL() {
        return NORECL;
    }

    public void setNORECL(String NORECL) {
        this.NORECL = NORECL;
    }
}
