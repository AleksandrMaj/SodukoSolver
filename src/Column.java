public class Column
{
    NumberField[] numberFields;

    public Column(NumberField number1, NumberField number2, NumberField number3)
    {
        numberFields = new NumberField[]{number1, number2, number3};
    }

    public NumberField[] getNumbers()
    {
        return numberFields;
    }
}
