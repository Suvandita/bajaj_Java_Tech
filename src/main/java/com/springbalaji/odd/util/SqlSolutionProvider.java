package com.springbalaji.odd.util;

public class SqlSolutionProvider {

    public static String getFinalSql(String regNo) {

        int lastTwoDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));

        if (lastTwoDigits % 2 != 0) {

            return "SELECT "
                    + "d.DEPARTMENT_NAME, "
                    + "t.SALARY, "
                    + "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS EMPLOYEE_NAME, "
                    + "FLOOR(DATEDIFF(CURDATE(), e.DOB) / 365) AS AGE "
                    + "FROM ( "
                    + "    SELECT e.DEPARTMENT AS DEP_ID, e.EMP_ID, SUM(p.AMOUNT) AS SALARY "
                    + "    FROM EMPLOYEE e "
                    + "    JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID "
                    + "    WHERE DAY(p.PAYMENT_TIME) != 1 "
                    + "    GROUP BY e.DEPARTMENT, e.EMP_ID "
                    + ") t "
                    + "JOIN ( "
                    + "    SELECT DEP_ID, MAX(SALARY) AS MAX_SALARY "
                    + "    FROM ( "
                    + "        SELECT e.DEPARTMENT AS DEP_ID, e.EMP_ID, SUM(p.AMOUNT) AS SALARY "
                    + "        FROM EMPLOYEE e "
                    + "        JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID "
                    + "        WHERE DAY(p.PAYMENT_TIME) != 1 "
                    + "        GROUP BY e.DEPARTMENT, e.EMP_ID "
                    + "    ) x "
                    + "    GROUP BY DEP_ID "
                    + ") mx ON t.DEP_ID = mx.DEP_ID AND t.SALARY = mx.MAX_SALARY "
                    + "JOIN EMPLOYEE e ON e.EMP_ID = t.EMP_ID "
                    + "JOIN DEPARTMENT d ON d.DEPARTMENT_ID = e.DEPARTMENT "
                    + "ORDER BY d.DEPARTMENT_NAME;";
        }

        else {
            return "NOT_REQUIRED_FOR_YOU";
        }
    }
}
