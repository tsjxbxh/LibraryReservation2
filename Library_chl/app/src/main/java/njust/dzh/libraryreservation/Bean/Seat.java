package njust.dzh.libraryreservation.Bean;

public class Seat {
    private int id;
    private String account;

    public Seat(int id) {
        this.id = id;
    }

    public Seat(int id, String account) {
        this.id = id;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


}
