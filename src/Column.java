public class Column
{
    NumberField[] columns;

    public Column(byte number1, byte number2, byte number3)
    {
        columns = new NumberField[]{new NumberField(number1), new NumberField(number2), new NumberField(number3)};
    }

    public NumberField[] getNumbers()
    {
        return columns;
    }
}
