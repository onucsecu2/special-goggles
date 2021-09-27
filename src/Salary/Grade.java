package Salary;

/**
 * Employee salary Grades/Ranks.
 * There are 6 grades/ranks. Grade/Rank ONE is the highest grade/rank and grade/rank SIX is the lowest rank/grade.
 */
public enum Grade {
    ONE(5),
    TWO(4),
    THREE(3),
    FOUR(3),
    FIVE(1),
    SIX(0);
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
