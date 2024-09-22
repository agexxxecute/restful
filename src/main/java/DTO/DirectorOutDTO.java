package DTO;

public class DirectorOutDTO {
    private Integer id;
    private String firstName;
    private String lastName;

    public DirectorOutDTO() {
    }

    public DirectorOutDTO(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
