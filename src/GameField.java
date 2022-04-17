import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class GameField
{
    public Quadrant[] quadrants = new Quadrant[9];

    public static void main(String[] args)
    {
        byte[][] sodukoData = {
                {3, 9, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 7, 0, 0, 4, 8, 0, 0},
                {0, 0, 4, 0, 5, 0, 1, 9, 6},
                {6, 7, 2, 1, 0, 0, 0, 8, 4},
                {0, 3, 1, 9, 4, 0, 0, 0, 0},
                {0, 4, 0, 7, 0, 0, 6, 1, 0},
                {9, 2, 0, 4, 0, 3, 5, 6, 0},
                {0, 0, 3, 5, 0, 1, 4, 0, 9},
                {0, 0, 0, 0, 6, 9, 0, 0, 0}
        };
        GameField gamefield = new GameField(sodukoData);

        for(int i = 0;i<10;i++){

            gamefield.solve();
        }

        gamefield.showSoduko();
    }

    public GameField(byte[][] sodukoData)
    {
        for (int i = 0; i < 9; i++)
        {
            quadrants[i] = new Quadrant();
        }
        fillQuadrants(generateOrderedQuadrantData(sodukoData));
    }

    public byte[][] generateOrderedQuadrantData(byte[][] sodukoData)
    {
        byte[][] quadrantData = new byte[9][9];
        byte rowStartPos = 0;
        byte fieldStartPos = 0;
        byte counter = 0;

        //Durch alle Quadranten gehen
        for (int reps = 0; reps < 9; reps++)
        {
            //Durch einen Quadranten gehen
            for (byte dataRow = rowStartPos; dataRow < rowStartPos + 3; dataRow++)
            {
                //Durch alle 3 Reihen gehen
                for (byte rowField = fieldStartPos; rowField < fieldStartPos + 3; rowField++)
                {
                    quadrantData[reps][counter] = sodukoData[dataRow][rowField];
                    counter++;
                }
            }
            //Vorbereitung auf den nächsten Quadranten
            fieldStartPos += 3;
            counter = 0;

            //Ab in die nächste Quadrantenreihe
            if (fieldStartPos == 9)
            {
                fieldStartPos = 0;
                rowStartPos += 3;
            }
        }
        return quadrantData;
    }

    public void fillQuadrants(byte[][] quadrantData)
    {
        for (byte i = 0; i < 9; i++)
        {
            quadrants[i].fillQuadrant(quadrantData[i]);
        }
    }

    public void showSoduko()
    {
        byte[][] soduko = new byte[9][9];
        byte rowPos = 0;
        byte fieldPos = 0;
        byte fieldPosOffset = 0;
        byte rowPosOffset = 0;

        for (int q = 0; q < 9; q++)
        {
            LinkedList<Byte> temp = quadrants[q].getNumbers();

            for (int i = 0; i < 9; i++)
            {
                soduko[rowPos][fieldPos] = temp.get(i);
                fieldPos++;

                if (fieldPos % 3 == 0)
                {
                    rowPos++;
                    fieldPos = fieldPosOffset;
                }
            }
            fieldPosOffset += 3;
            fieldPos = fieldPosOffset;
            rowPos = rowPosOffset;

            if (fieldPos == 9)
            {
                rowPosOffset += 3;
                rowPos = rowPosOffset;
                fieldPosOffset = 0;
                fieldPos = fieldPosOffset;
            }
        }

        for (byte[] arr : soduko)
        {
            System.out.println(Arrays.toString(arr));
        }
    }

    public void solve()
    {
        //Jeden einzelnen Quadranten durchgehen
        for (byte quadrantID = 0; quadrantID < 9; quadrantID++)
        {
            Quadrant currentQuadrant = quadrants[quadrantID];

            //Die Nullen entfernen, damit das Programm schneller ist
            Vector<Byte> mesh = new Vector<>();
            mesh.add((byte) 0);
            LinkedList<Byte> quadrantNumbers = currentQuadrant.getNumbers();
            quadrantNumbers.removeAll(mesh);

            System.out.println(quadrantNumbers);
            //Wir gehen die 3 Reihen durch
            for (byte rowID = 0; rowID < 3; rowID++)
            {
                System.out.println(currentQuadrant.rows[rowID]);
                //Wir gehen die 3 Felder einer Reiher durch
                for (byte numberFieldID = 0; numberFieldID < 3; numberFieldID++)
                {
                    NumberField currentNumberfield = currentQuadrant.rows[rowID].numberFields[numberFieldID];
                    //Zahlen, die bereits im Quadranten sind, aus der Anzahl der möglichen Zahlen entfernen
                    currentNumberfield.getPossibleNumbers().removeAll(quadrantNumbers);

                    LinkedList<Byte> deletingNumbers = new LinkedList<>();

                    //Rows
                    // 0, 3, 6
                    if(quadrantID % 3 == 0){
                        deletingNumbers = getRowNumbers((byte) (quadrantID+1), rowID, deletingNumbers);
                        deletingNumbers = getRowNumbers((byte) (quadrantID+2), rowID, deletingNumbers);
                    }
                    //1, 4, 7
                    else if ((quadrantID-1) % 3 == 0){
                        deletingNumbers = getRowNumbers((byte) (quadrantID-1), rowID, deletingNumbers);
                        deletingNumbers = getRowNumbers((byte) (quadrantID+1), rowID, deletingNumbers);
                    }
                    //2, 5, 8
                    else {
                        deletingNumbers = getRowNumbers((byte) (quadrantID-1), rowID, deletingNumbers);
                        deletingNumbers = getRowNumbers((byte) (quadrantID-2), rowID, deletingNumbers);
                    }

                    //Columns
                    // 0, 1, 2
                    if(quadrantID < 3){
                        deletingNumbers = getColumnNumbers((byte) (quadrantID+3), numberFieldID, deletingNumbers);
                        deletingNumbers = getColumnNumbers((byte) (quadrantID+6), numberFieldID, deletingNumbers);
                    }
                    //3, 4, 5
                    else if (quadrantID < 6){
                        deletingNumbers = getColumnNumbers((byte) (quadrantID-3), numberFieldID, deletingNumbers);
                        deletingNumbers = getColumnNumbers((byte) (quadrantID+3), numberFieldID, deletingNumbers);
                    }
                    //6, 7, 8
                    else {
                        deletingNumbers = getColumnNumbers((byte) (quadrantID-6), numberFieldID, deletingNumbers);
                        deletingNumbers = getColumnNumbers((byte) (quadrantID-3), numberFieldID, deletingNumbers);
                    }

                    currentNumberfield.getPossibleNumbers().removeAll(deletingNumbers);
                    currentNumberfield.checkForCorrectNumber();
                    System.out.println(currentQuadrant.rows[rowID].numberFields[numberFieldID].getPossibleNumbers().toString());
                }
            }
            System.out.println();
        }
    }

    public LinkedList<Byte> getRowNumbers(byte quadrantID, byte rowID, LinkedList<Byte> list)
    {
        for (NumberField num : quadrants[quadrantID].rows[rowID].getNumbers())
        {
            byte number = num.getCorrectNumber();
            if (number != 0 && !list.contains(number)) list.add(number);
        }
        return list;
    }

    public LinkedList<Byte> getColumnNumbers(byte quadrantID, byte numberFieldID, LinkedList<Byte> list)
    {
        for (NumberField num : quadrants[quadrantID].columns[numberFieldID].getNumbers())
        {
            byte number = num.getCorrectNumber();
            if (number != 0 && !list.contains(number)) list.add(number);
        }
        return list;
    }
}
