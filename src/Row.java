public class Row
{
    NumberField[] numberFields;

    public Row(byte number1, byte number2, byte number3)
    {
        numberFields = new NumberField[]{new NumberField(number1), new NumberField(number2), new NumberField(number3)};
    }

    public NumberField[] getNumbers()
    {
        return numberFields;
    }
}
