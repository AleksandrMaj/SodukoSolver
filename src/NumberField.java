public class NumberField
{
    byte[] possibleNumbers;
    byte correctNumber;
    boolean numberUknown = true;

    public NumberField(byte[] possibleNumbers)
    {
        if(possibleNumbers.length == 1) correctNumber = possibleNumbers[0];
        this.possibleNumbers = possibleNumbers;
    }

    public NumberField(byte correctNumber)
    {
        this.correctNumber = correctNumber;
        this.numberUknown = false;
    }

    public byte[] getPossibleNumbers()
    {
        return possibleNumbers;
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
}
