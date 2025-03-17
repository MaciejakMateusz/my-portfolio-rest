package pl.maciejak.my_portfolio_rest.util;

import pl.maciejak.my_portfolio_rest.dto.MeasurementAnalysis;
import pl.maciejak.my_portfolio_rest.dto.MeasurementsDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestsDataRepository {

    protected static final String EXPECTED_REPORT = """
            DANE PRODUKTU
            Długość produktu: 60 mm
            Tolerancja dodatnia: 0.5 mm
            Tolerancja ujemna: -0.5 mm

            DANE POMIAROWE
            Wszystkie pomiary (mm): [60.1, 60.5, 60.4, 59.4, 59.5, 59.9, 60, 60.9]

            Ilość pomiarów: 7sztuk(i)
            Średnia wszystkich pomiarów: 60,10 mm

            Poza tolerancją: 2sztuk(i)
            W granicach tolerancji: 5sztuk(i)
            Większe niż 60.500 mm: 1sztuk(i)
            Mniejsze niż 59.500 mm: 1sztuk(i)

            Największy pomiar: 60.900 mm
            Najmniejszy pomiar: 59.400 mm
            Różnica między najmniejszym a największym pomiarem: 1.500 mm

            Pomiary posortowane rosnąco: [59.4, 59.5, 59.9, 60, 60.1, 60.4, 60.5, 60.9]""";

    protected static MeasurementsDTO getMeasurementsDTO() {
        return new MeasurementsDTO(BigDecimal.valueOf(60),
                BigDecimal.valueOf(-0.5),
                BigDecimal.valueOf(0.5),
                List.of(BigDecimal.valueOf(60.1),
                        BigDecimal.valueOf(60.5),
                        BigDecimal.valueOf(60.4),
                        BigDecimal.valueOf(59.4),
                        BigDecimal.valueOf(59.5),
                        BigDecimal.valueOf(59.9),
                        BigDecimal.valueOf(60),
                        BigDecimal.valueOf(60.9)));
    }

    protected static MeasurementsDTO getInvalidMeasurementsDTO() {
        return new MeasurementsDTO(null,
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(-0.5),
                List.of(BigDecimal.valueOf(60.1)));
    }

    protected static MeasurementAnalysis getMeasurementAnalysis() {
        MeasurementsDTO measurementsDTO = getMeasurementsDTO();
        List<BigDecimal> sortedMeasurements = new ArrayList<>(measurementsDTO.measurements());
        Collections.sort(sortedMeasurements);
        return new MeasurementAnalysis(
                getFormattedDate(),
                BigDecimal.valueOf(0.5),
                BigDecimal.valueOf(-0.5),
                measurementsDTO.measurements(),
                7,
                round(60.1),
                round(59.5),
                round(60.5),
                2,
                5,
                BigDecimal.valueOf(60),
                1,
                1,
                round(60.9),
                round(59.4),
                round(1.5),
                sortedMeasurements
        );
    }

    private static String getFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private static BigDecimal round(Double value) {
        return BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP);
    }
}
