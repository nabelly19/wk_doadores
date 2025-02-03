package br.com.wk.donors.services;

import br.com.wk.donors.entities.Candidate;
import br.com.wk.donors.repositories.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository repository;

    @InjectMocks
    private CandidateService service;

    private List<Candidate> mockCandidates;

    @BeforeEach
    void setUp() {
        mockCandidates = List.of(
            new Candidate(1L, "Milena Analu Pires", "775.256.099-50", "44.084.541-5",
                    LocalDate.of(1964, 5, 23), "Feminino", "Isadora Marli", "Noah Severino César Pires",
                    "mmilenaanalupires@keffin.com.br", "39801-678", "Rua Kurt W. Rothe", 675, "Castro Pires",
                    "Teófilo Otoni", "MG", "MG", "(33) 3611-4613", "(33) 98481-0191",
                    1.53, 80, "O-"),

            new Candidate(2L, "Marcos Vinicius Kevin Samuel Santos", "024.934.035-68", "10.701.456-7",
                    LocalDate.of(1992, 9, 7), "Masculino", "Isabella Andrea", "Lorenzo Marcos André Santos",
                    "marcosviniciuskevinsamuelsantos_@dhl.com", "49091-043", "Rua Manoel de Oliveira França", 634, "Jardim Centenário",
                    "Aracaju", "SE", "SE", "(79) 2686-2642", "(79) 99666-0063",
                    1.92, 95, "O-"),

            new Candidate(3L, "Noah Severino Freitas", "745.405.478-10", "13.501.191-7",
                    LocalDate.of(1991, 3, 27), "Masculino", "Silvana Bárbara", "Tiago Manoel Kaique Freitas",
                    "nnoahseverinofreitas@tjrj.jus.br", "38411-338", "Rua José Cunha Chaves", 880, "Jardim Colina",
                    "Uberlândia", "MG", "MG", "(34) 2903-7300", "(34) 98476-5223",
                    1.93, 84, "A+"),

            new Candidate(4L, "Nair Brenda Ayla Cardoso", "673.439.652-55", "38.583.121-3",
                    LocalDate.of(1994, 12, 20), "Feminino", "Esther Stefany Tereza", "Alexandre Arthur Igor Cardoso",
                    "nnairbrendaaylacardoso@andrade.com", "50110-640", "Rua Luiz Teixeira Gonzaga", 101, "Santo Amaro",
                    "Recife", "PE", "PE", "(81) 2697-4896", "(81) 99928-1146",
                    1.66, 72, "A+"),

            new Candidate(5L, "Vinicius Leandro Danilo Assunção", "464.646.882-43", "47.866.582-9",
                    LocalDate.of(1951, 10, 5), "Masculino", "Flávia Maya", "Elias Raimundo Assunção",
                    "viniciusleandrodaniloassuncao..viniciusleandrodaniloassuncao@lbrazil.com.br", "65081-432", "Rua São Félix", 361, "Vila Embratel",
                    "São Luís", "MA", "MA", "(98) 2836-3051", "(98) 98594-3430",
                    1.88, 100, "AB+"),

            new Candidate(6L, "Oliver Rafael Marcos Vinicius Fogaça", "759.602.133-62", "13.168.953-8",
                    LocalDate.of(1975, 1, 3), "Masculino", "Isabelly Andreia Alessandra", "Sebastião Augusto Renato Fogaça",
                    "oliverrafaelmarcosviniciusfogaca..oliverrafaelmarcosviniciusfogaca@salvagninigroup.com", "59073-205", "Rua Zeca da Silva", 672, "Planalto",
                    "Natal", "RN", "RN", "(84) 2899-9078", "(84) 99908-3858",
                    1.80, 87, "AB-"),

            new Candidate(7L, "Renan Matheus Murilo Peixoto", "350.189.672-77", "47.168.600-1",
                    LocalDate.of(1965, 9, 18), "Masculino", "Cristiane Melissa", "Tomás Gabriel Peixoto",
                    "renanmatheusmurilopeixoto-70@tce.sp.gov.br", "76873-648", "Rua Tomás Antônio Gonzaga", 702, "Setor 06",
                    "Ariquemes", "RO", "RO", "(69) 2950-9595", "(69) 98888-6053",
                    1.87, 62, "B-"),

            new Candidate(8L, "Oliver Thomas Vieira", "779.665.729-35", "42.073.802-2",
                    LocalDate.of(1945, 4, 8), "Masculino", "Sophie Luiza", "Cauã Carlos Eduardo Hugo Vieira",
                    "oliverthomasvieira_@macroengenharia.com", "77425-270", "Rua Messias da Silveira", 200, "Alto da Boa Vista",
                    "Gurupi", "TO", "TO", "(63) 3581-4474", "(63) 99251-0835",
                    1.80, 61, "A-"),

            new Candidate(9L, "Iago César Calebe da Paz", "686.885.244-40", "26.822.357-9",
                    LocalDate.of(1985, 9, 3), "Masculino", "Helena Rebeca Luna", "Manoel Henrique da Paz",
                    "iagocesarcalebedapaz-73@inepar.com.br", "45078-080", "Rua Doze", 867, "Zabelê",
                    "Vitória da Conquista", "BA", "BA", "(77) 3812-9441", "(77) 99231-0087",
                    1.90, 92, "B+"),

            new Candidate(10L, "Giovana Vera da Mata", "983.193.965-49", "33.218.457-2",
                    LocalDate.of(1958, 7, 12), "Feminino", "Juliana Isabelle", "Manoel Lucca da Mata",
                    "giovanaveradamata-97@pp33.com.br", "12072-370", "Rua Elias João Andraus Neto", 713, "UNA",
                    "Taubaté", "SP", "SP", "(12) 3867-8390", "(12) 99996-2778",
                    1.54, 82, "B-")
        );
    }


    @Test
    void countByState_ShouldReturnCorrectCounts() {
        when(repository.findAll()).thenReturn(mockCandidates);

        Map<String, Long> result = service.countByState();

        assertEquals(1, result.get("BA"));
        assertEquals(2, result.get("MG"));
        assertEquals(1, result.get("SP"));
    }

    @Test
    void averageBmiByAgeGroup_ShouldReturnCorrectBmi() {
        when(repository.findAll()).thenReturn(mockCandidates);

        Map<String, Double> result = service.averageBmiByAgeGroup();

        assertNotNull(result);
        assertTrue(result.containsKey("De 60 para 69 anos"));
    }

    @Test
    void calculateObesityPercentage_ShouldReturnCorrectValues() {
        when(repository.findAll()).thenReturn(mockCandidates);

        Map<String, Double> result = service.calculateObesityPercentage();

        assertNotNull(result);
        assertTrue(result.containsKey("Masculino"));
        assertEquals(66, result.get("Feminino").intValue(),0.1);
    }

    @Test
    void averageAgeByBloodType_ShouldReturnCorrectAverages() {
        when(repository.findAll()).thenReturn(mockCandidates);

        Map<String, Double> result = service.averageAgeByBloodType();

        assertEquals(31, result.get("A+").intValue(), 0.1);
        assertEquals(46, result.get("O-").intValue(), 0.1);

    }

    @Test
    void eligibleDonors_ShouldReturnCorrectCounts() {
        when(repository.findAll()).thenReturn(mockCandidates);

        Map<String, Integer> result = service.donorsPerRecipient();

        assertNotNull(result);
        assertTrue(result.containsKey("A+"));
        assertTrue(result.containsKey("A-"));

    }

    @Test
    void donorsPerRecipient_ShouldReturnCorrectCounts() {
        when(repository.findAll()).thenReturn(mockCandidates);

        Map<String, Integer> result = service.donorsPerRecipient();

        assertNotNull(result);
        assertTrue(result.containsKey("A+"));
        assertTrue(result.containsKey("O+"));
    }

    @Test
    void saveAll_ShouldSaveCandidatesWithCorrectState() {
        List<Candidate> candidates = new ArrayList<>(mockCandidates);

        service.saveAll(candidates);

        verify(repository, times(1)).deleteAll();
        verify(repository, times(1)).saveAll(candidates);
    }

    @Test
    void deleteAll_ShouldCallRepositoryDeleteAll() {
        service.deleteAll();

        verify(repository, times(1)).deleteAll();
    }

    @Test
    void getAge_ShouldReturnCorrectAge() {
        LocalDate birthDate = LocalDate.of(1990, 5, 20);
        int age = service.getAge(birthDate);

        assertEquals(34, age);
    }

    @Test
    void isEligibleToDonate_ShouldReturnCorrectEligibility() {

        Candidate eligibleCandidate = new Candidate(LocalDate.of(1964, 5, 23),80);
        Candidate ineligibleCandidate = new Candidate(LocalDate.of(1992, 9, 7), 35);

        assertTrue(service.isEligibleToDonate(eligibleCandidate));
        assertFalse(service.isEligibleToDonate(ineligibleCandidate));
    }
}
