package br.com.wk.donors.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("rg")
    private String identityCard;

    @JsonProperty("data_nasc")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @JsonProperty("sexo")
    private String gender;

    @JsonProperty("mae")
    private String mother;

    @JsonProperty("pai")
    private String father;

    @JsonProperty("email")
    private String email;

    @JsonProperty("cep")
    private String postalCode;

    @JsonProperty("endereco")
    private String address;

    @JsonProperty("numero")
    private int number;

    @JsonProperty("bairro")
    private String neighborhood;

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("estado")
    private String state;

    @JsonProperty("sigla")
    private String abbreviation;

    @JsonProperty("telefone_fixo")
    private String landline;

    @JsonProperty("celular")
    private String mobile;

    @JsonProperty("altura")
    private double height;

    @JsonProperty("peso")
    private double weight;

    @JsonProperty("tipo_sanguineo")
    private String bloodType;

    public Candidate() {
    }

    public Candidate(LocalDate birthDate, double weight) {
        this.birthDate = birthDate;
        this.weight = weight;
    }

    public Candidate(Long id, String name, String cpf, String identityCard, LocalDate birthDate, String gender, String mother, String father, String email, String postalCode, String address, int number, String neighborhood, String city, String state, String abbreviation, String landline, String mobile, double height, double weight, String bloodType) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.identityCard = identityCard;
        this.birthDate = birthDate;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.email = email;
        this.postalCode = postalCode;
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.abbreviation = abbreviation;
        this.landline = landline;
        this.mobile = mobile;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", mother='" + mother + '\'' +
                ", father='" + father + '\'' +
                ", email='" + email + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", address='" + address + '\'' +
                ", number=" + number +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", landline='" + landline + '\'' +
                ", mobile='" + mobile + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", bloodType='" + bloodType + '\'' +
                '}';
    }
}
