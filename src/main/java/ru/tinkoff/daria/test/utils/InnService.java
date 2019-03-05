package ru.tinkoff.daria.test.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InnService {
    private static List<Integer> iCoeffList1 = new ArrayList<>(Arrays.asList(
            7, 2, 4, 10, 3, 5, 9, 4, 6, 8
    ));
    private static List<Integer> iCoeffList2 = new ArrayList<>(Arrays.asList(
            3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8
    ));

    public static String getIndividualInn() {
        int checkSum1 = 0;
        int checkSum2 = 0;
        StringBuilder builder = new StringBuilder();
        Random generator = new Random();
        List<Integer> innDig = new ArrayList<>(Arrays.asList(7, 7));

        for (int i = 0; i < 8; i++) {
            innDig.add(generator.nextInt(10)); //Заполняем цифрами первый 10 для инн в нужном порядке
        }

        for (int i = 0; i < 10; i++) {
            checkSum1 += innDig.get(i) * iCoeffList1.get(i);
            checkSum2 += innDig.get(i) * iCoeffList2.get(i);
            builder.append(innDig.get(i));
        }

        checkSum1 = (checkSum1 % 11) % 10;
        checkSum2 = ((checkSum2 + checkSum1 * iCoeffList2.get(10)) % 11) % 10;

        return builder.append(checkSum1).append(checkSum2).toString();
    }
}

