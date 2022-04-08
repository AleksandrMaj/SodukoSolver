import java.util.Arrays;

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
            byte[] temp = quadrants[q].getNumbers();

            for (int i = 0; i < 9; i++)
            {
                soduko[rowPos][fieldPos] = temp[i];
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
}
