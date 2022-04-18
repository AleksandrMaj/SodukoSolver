import java.util.LinkedList;

public class NumberField
{
    public LinkedList<Byte> possibleNumbers;
    public byte correctNumber;
    public boolean numberUknown = true;

    public NumberField(byte correctNumber)
    {
        if(correctNumber == 0)
        {
            GameField.fieldsToSolve++;
            possibleNumbers = new LinkedList<>();
            fillPossibleNumbersArray();
            return;
        }
        this.correctNumber = correctNumber;
        this.numberUknown = false;
    }

    public LinkedList<Byte> getPossibleNumbers()
    {
        if(possibleNumbers == null) {
            LinkedList<Byte> temp = new LinkedList<>();
            temp.add(correctNumber);
            return temp;
        }
        return possibleNumbers;
    }

    public byte getCorrectNumber()
    {
        return correctNumber;
    }

    public void setCorrectNumber(byte correctNumber)
    {
        GameField.fieldsToSolve--;
        this.correctNumber = correctNumber;
        this.numberUknown = false;
    }

    public void fillPossibleNumbersArray()
    {
        possibleNumbers = new LinkedList<>();
        for(int i = 1;i<10;i++)
        {
            possibleNumbers.add((byte) i);
        }
    }

    public void checkForCorrectNumber()
    {
        if(possibleNumbers != null && possibleNumbers.size() == 1)
            setCorrectNumber(possibleNumbers.getFirst());
    }
}
