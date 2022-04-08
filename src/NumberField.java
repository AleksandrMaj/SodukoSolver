import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class NumberField
{
    public LinkedList<Byte> possibleNumbers;
    public byte correctNumber;
    public boolean numberUknown = true;

    public static byte[] numberList = {1,2,3,4,5,6,7,8,9};

    public NumberField(byte correctNumber)
    {
        if(correctNumber == 0)
        {
            possibleNumbers = new LinkedList<>();
            fillPossibleNumbersArray();
            return;
        }
        this.correctNumber = correctNumber;
        this.numberUknown = false;
    }

    public Byte[] getPossibleNumbers()
    {
        if(possibleNumbers == null) return new Byte[]{correctNumber};
        return possibleNumbers.toArray(new Byte[0]);
    }

    public byte getCorrectNumber()
    {
        return correctNumber;
    }

    public void setCorrectNumber(byte correctNumber)
    {
        this.correctNumber = correctNumber;
        this.numberUknown = false;
    }

    public void removeNumber(byte number)
    {
        possibleNumbers.remove((Object) number);
        if(possibleNumbers.size() == 1)
        {
            setCorrectNumber(possibleNumbers.getFirst());
        }
    }

    public void fillPossibleNumbersArray()
    {
        for(byte x : numberList)
        {
            possibleNumbers.add(x);
        }
    }
}
