package DTO;

public class DirectorInDTO {
    private String firstName;
    private String lastName;

    public DirectorInDTO() {
    }

    public DirectorInDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
