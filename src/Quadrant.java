import java.util.LinkedList;

public class Quadrant
{
    public Row[] rows = new Row[3];
    public Column[] columns = new Column[3];

    public void fillQuadrant(byte[] numbers)
    {
        NumberField[][] temp = {
                {new NumberField(numbers[0]),new NumberField(numbers[1]),new NumberField(numbers[2])},
                {new NumberField(numbers[3]),new NumberField(numbers[4]),new NumberField(numbers[5])},
                {new NumberField(numbers[6]),new NumberField(numbers[7]),new NumberField(numbers[8])}
        };

        rows[0] = new Row(temp[0][0],temp[0][1],temp[0][2]);
        rows[1] = new Row(temp[1][0],temp[1][1],temp[1][2]);
        rows[2] = new Row(temp[2][0],temp[2][1],temp[2][2]);

        columns[0] = new Column(temp[0][0],temp[1][0],temp[2][0]);
        columns[1] = new Column(temp[0][1],temp[1][1],temp[2][1]);
        columns[2] = new Column(temp[0][2],temp[1][2],temp[2][2]);
    }

    public LinkedList<Byte> getNumbers()
    {
        LinkedList<Byte> allNumbers = new LinkedList<>();

        for (Row row : rows)
        {
            for (NumberField number : row.getNumbers())
            {
                allNumbers.add(number.getCorrectNumber());
            }
        }

        return allNumbers;
    }
}
