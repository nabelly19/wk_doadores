-- QUERIES USADAS PARA VALIDAR O LAMBDA NO JAVA

-- Selecionar todos os candidatos
SELECT * FROM wk_db.candidate;

-- Contar o número de candidatos por estado
SELECT state, COUNT(*) AS total FROM wk_db.candidate GROUP BY state ORDER BY state;
SELECT COUNT(*) AS total FROM wk_db.candidate c WHERE c.state LIKE 'Acre';
SELECT COUNT(*) AS total FROM wk_db.candidate c WHERE c.state LIKE 'Bahia';
SELECT COUNT(*) AS total FROM wk_db.candidate c WHERE c.state LIKE 'Paraná';

-- Calcular o IMC médio por faixa etária
SELECT 
    CASE 
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 0 AND 10 THEN '0-10'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 11 AND 20 THEN '11-20'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 21 AND 30 THEN '21-30'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 31 AND 40 THEN '31-40'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 41 AND 50 THEN '41-50'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 51 AND 60 THEN '51-60'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 61 AND 70 THEN '61-70'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 71 AND 80 THEN '71-80'
        WHEN TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) > 80 THEN '80+'
    END AS age_group,
    AVG(weight / (height * height)) AS avg_bmi
FROM wk_db.candidate
GROUP BY age_group
ORDER BY age_group;

-- 1985 a 1994
SELECT birth_date FROM  wk_db.candidate WHERE TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 31 AND 40;

WITH candidates AS (
   SELECT * FROM  wk_db.candidate WHERE TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 31 AND 40
)
SELECT AVG(weight / (height * height)) AS avg_bmi FROM candidates;


-- Percentual de obesidade por gênero (IMC > 30)
SELECT 
    gender, 
    (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM wk_db.candidate WHERE gender = c.gender)) AS obesity_percentage
FROM wk_db.candidate c
WHERE (weight / (height * height)) > 30
GROUP BY gender;

WITH candidates AS (
     SELECT weight, height, (SELECT COUNT(*) FROM wk_db.candidate c WHERE c.gender LIKE 'Masculino')  AS total 
     FROM wk_db.candidate c WHERE (weight / (height * height)) > 30 AND c.gender LIKE 'Masculino' ORDER BY name
)
SELECT count(*) AS obesities, total, COUNT(*) * 100.0 / total AS PERCENTAGE FROM candidates;

-- Média de idade por tipo sanguíneo
WITH dates AS (
SELECT c.blood_type, EXTRACT(YEAR FROM c.birth_date) AS ano, TIMESTAMPDIFF(YEAR, c.birth_date, CURDATE()) AS dif FROM wk_db.candidate c WHERE c.blood_type LIKE 'A-'
)
SELECT AVG(dif) AS avg_blood FROM dates;

SELECT blood_type, AVG(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) AS avg_age
FROM wk_db.candidate
GROUP BY blood_type
ORDER BY blood_type;

-- Quantidade de doadores por receptor considerando compatibilidade sanguínea
SELECT * FROM wk_db.candidate;
SELECT 'A+' as blood_type, COUNT(*) FROM wk_db.candidate c WHERE c.blood_type IN ('A+', 'A-', 'O+', 'O-') 
      AND TIMESTAMPDIFF(YEAR, C.birth_date, CURDATE()) BETWEEN 16 AND 69
      AND c.weight > 50;


WITH compatibility AS (
    SELECT 'A+' AS recipient, 'A+' AS donor UNION ALL
    SELECT 'A+', 'O+' UNION ALL
    SELECT 'A+', 'A-' UNION ALL
    SELECT 'A+', 'O-' UNION ALL
    SELECT 'A-', 'A-' UNION ALL
    SELECT 'A-', 'O-' UNION ALL
    SELECT 'B+', 'B+' UNION ALL
    SELECT 'B+', 'O+' UNION ALL
    SELECT 'B+', 'B-' UNION ALL
    SELECT 'B+', 'O-' UNION ALL
    SELECT 'B-', 'B-' UNION ALL
    SELECT 'B-', 'O-' UNION ALL
    SELECT 'AB+', 'A+' UNION ALL
    SELECT 'AB+', 'B+' UNION ALL
    SELECT 'AB+', 'O+' UNION ALL
    SELECT 'AB+', 'AB+' UNION ALL
    SELECT 'AB+', 'A-' UNION ALL
    SELECT 'AB+', 'B-' UNION ALL
    SELECT 'AB+', 'O-' UNION ALL
    SELECT 'AB+', 'AB-' UNION ALL
    SELECT 'AB-', 'A-' UNION ALL
    SELECT 'AB-', 'B-' UNION ALL
    SELECT 'AB-', 'O-' UNION ALL
    SELECT 'AB-', 'AB-' UNION ALL
    SELECT 'O+', 'O+' UNION ALL
    SELECT 'O+', 'O-' UNION ALL
    SELECT 'O-', 'O-'
)
SELECT 
    recipient,
    COUNT(candidate.id) AS donor_count
FROM compatibility
LEFT JOIN wk_db.candidate AS candidate
ON compatibility.donor = candidate.blood_type
WHERE TIMESTAMPDIFF(YEAR, candidate.birth_date, CURDATE()) BETWEEN 16 AND 69 -- 🔹 Calcula a idade corretamente
  AND candidate.weight > 50
GROUP BY recipient;

