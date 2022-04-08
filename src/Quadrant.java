public class Quadrant
{
    public Row[] rows = new Row[3];
    public Column[] columns = new Column[3];

    public Quadrant()
    {
    }

    public void fillQuadrant(byte[] numbers)
    {
        rows[0] = new Row(numbers[0],numbers[1],numbers[2]);
        rows[1] = new Row(numbers[3],numbers[4],numbers[5]);
        rows[2] = new Row(numbers[6],numbers[7],numbers[8]);

        columns[0] = new Column(numbers[0],numbers[3],numbers[6]);
        columns[1] = new Column(numbers[1],numbers[4],numbers[7]);
        columns[2] = new Column(numbers[2],numbers[5],numbers[8]);
    }

    public byte[] getNumbers()
    {
        byte[] allNumbers = new byte[9];
        byte counter = 0;

        for (Row row : rows)
        {
            for (NumberField number : row.getNumbers())
            {
                allNumbers[counter] = number.getCorrectNumber();
                counter++;
            }
        }

        return allNumbers;
    }
}
