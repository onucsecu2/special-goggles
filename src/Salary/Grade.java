package Salary;

/**
 * Employee salary Grades/Ranks.
 * There are 6 grades/ranks. Grade/Rank ONE is the highest grade/rank and grade/rank SIX is the lowest rank/grade.
 */
public enum Grade {
    ONE(6),
    TWO(5),
    THREE(4),
    FOUR(3),
    FIVE(2),
    SIX(1);
    private final int gradeValue;

    /**
     * @param i     It is an int denoting the grade weight.
     */
    Grade(int i) {
        this.gradeValue = i;
    }

    /**
     * @return      return corresponding int of grade weight.
     */
    public int getGradeValue(){
        return this.gradeValue;
    }
}
