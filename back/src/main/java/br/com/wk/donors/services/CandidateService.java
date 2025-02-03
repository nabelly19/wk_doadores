package br.com.wk.donors.services;

import br.com.wk.donors.entities.Candidate;
import br.com.wk.donors.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private static final Map<String, String> STATE_ABBREVIATIONS;

    static {
        STATE_ABBREVIATIONS = new HashMap<>();
        STATE_ABBREVIATIONS.put("AC", "Acre");
        STATE_ABBREVIATIONS.put("AL", "Alagoas");
        STATE_ABBREVIATIONS.put("AP", "Amapá");
        STATE_ABBREVIATIONS.put("AM", "Amazonas");
        STATE_ABBREVIATIONS.put("BA", "Bahia");
        STATE_ABBREVIATIONS.put("CE", "Ceará");
        STATE_ABBREVIATIONS.put("DF", "Distrito Federal");
        STATE_ABBREVIATIONS.put("ES", "Espírito Santo");
        STATE_ABBREVIATIONS.put("GO", "Goiás");
        STATE_ABBREVIATIONS.put("MA", "Maranhão");
        STATE_ABBREVIATIONS.put("MT", "Mato Grosso");
        STATE_ABBREVIATIONS.put("MS", "Mato Grosso do Sul");
        STATE_ABBREVIATIONS.put("MG", "Minas Gerais");
        STATE_ABBREVIATIONS.put("PA", "Pará");
        STATE_ABBREVIATIONS.put("PB", "Paraíba");
        STATE_ABBREVIATIONS.put("PR", "Paraná");
        STATE_ABBREVIATIONS.put("PE", "Pernambuco");
        STATE_ABBREVIATIONS.put("PI", "Piauí");
        STATE_ABBREVIATIONS.put("RJ", "Rio de Janeiro");
        STATE_ABBREVIATIONS.put("RN", "Rio Grande do Norte");
        STATE_ABBREVIATIONS.put("RS", "Rio Grande do Sul");
        STATE_ABBREVIATIONS.put("RO", "Rondônia");
        STATE_ABBREVIATIONS.put("RR", "Roraima");
        STATE_ABBREVIATIONS.put("SC", "Santa Catarina");
        STATE_ABBREVIATIONS.put("SP", "São Paulo");
        STATE_ABBREVIATIONS.put("SE", "Sergipe");
        STATE_ABBREVIATIONS.put("TO", "Tocantins");
    }

    @Autowired
    private CandidateRepository repository;

    public Map<String, Long> countByState() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Candidate::getState,
                        TreeMap::new,
                        Collectors.counting()));
    }

    public Map<String, Double> averageBmiByAgeGroup() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        c -> getAgeGroup(getAge(c.getBirthDate())),
                        TreeMap::new,
                        Collectors.averagingDouble(c -> c.getWeight() / (c.getHeight() * c.getHeight()))
                ));
    }

    private String getAgeGroup(int age) {
        return "De " + (age / 10) * 10 + " para " + ((age / 10) * 10 + 9) + " anos";
    }

    public Map<String, Double> calculateObesityPercentage() {
        Map<String, List<Candidate>> groupedByGender = repository.findAll().stream()
                .collect(Collectors.groupingBy(Candidate::getGender));

        Map<String, Double> obesityPercentage = new HashMap<>();
        groupedByGender.forEach((gender, candidates) -> {
            long obeseCount = candidates.stream()
                    .filter(c -> (c.getWeight() / (c.getHeight() * c.getHeight())) > 30)
                    .count();
            obesityPercentage.put(gender, (obeseCount * 100.0) / candidates.size());
        });
        return obesityPercentage;
    }

    public Map<String, Double> averageAgeByBloodType() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Candidate::getBloodType,
                        TreeMap::new,
                        Collectors.averagingInt(candidate -> getAge(candidate.getBirthDate()))
                ));
    }

    public Map<String, Integer> eligibleDonors() {
        return repository.findAll().stream()
                .filter(this::isEligibleToDonate)
                .collect(Collectors.groupingBy(
                        Candidate::getBloodType,
                        TreeMap::new,
                        Collectors.summingInt(c -> 1)
                ));
    }


    final Map<String, Set<String>> bloodCompatibility = Map.of(
            "A+", Set.of("A+", "A-", "O+", "O-"),
            "A-", Set.of("A-", "O-"),
            "AB+", Set.of("A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"),
            "AB-", Set.of("A-", "B-", "O-", "AB-"),
            "B+", Set.of("B+", "B-", "O+", "O-"),
            "B-", Set.of("B-", "O-"),
            "O+", Set.of("O+", "O-"),
            "O-", Set.of("O-")
    );
//    public Map<String, Integer> donorsPerRecipient() {
//
//        List<Candidate> candidates = repository.findAll();
//
//        return bloodCompatibility.keySet().stream()
//                .collect(Collectors.toMap(
//                        recipient -> recipient,
//                        recipient -> (int) candidates.stream()
//                                .filter(
//                                        candidate -> bloodCompatibility.get(recipient)
//                                                .contains(candidate.getBloodType()))
//                                .count(),
//                        (e1, e2) -> e1,
//                        TreeMap::new
//                ));
//
//    }

    public Map<String, Integer> donorsPerRecipient() {
        List<Candidate> candidates = repository.findAll();

        return bloodCompatibility.keySet().stream()
                .collect(Collectors.toMap(
                        recipient -> recipient,
                        recipient -> (int) candidates.stream()
                                .filter(candidate ->
                                        bloodCompatibility.get(recipient).contains(candidate.getBloodType()) &&
                                                isEligibleToRecipient(candidate)
                                )
                                .count(),
                        (e1, e2) -> e1,
                        TreeMap::new
                ));
    }

    private boolean isEligibleToRecipient(Candidate candidate) {
        int age = Period.between(candidate.getBirthDate(), LocalDate.now()).getYears();
        return age >= 16 && age <= 69 && candidate.getWeight() > 50;
    }

    public void saveAll(List<Candidate> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            throw new IllegalArgumentException("The candidate list cannot be null or empty.");
        }

        for (Candidate candidate : candidates) {
            String abbreviation = candidate.getState();
            candidate.setAbbreviation(abbreviation);
            candidate.setState(STATE_ABBREVIATIONS.get(abbreviation));
        }

        repository.deleteAll();
        repository.saveAll(candidates);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    int getAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    boolean isEligibleToDonate(Candidate candidate) {
        int age = getAge(candidate.getBirthDate());
        return age >= 16 && age <= 69 && candidate.getWeight() > 50;
    }
}
