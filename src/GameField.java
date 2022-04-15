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

        gamefield.solve();

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
        for(int quadrantID = 0;quadrantID<9;quadrantID++)
        {
            Quadrant currentQuadrant = quadrants[quadrantID];

            //Die Nullen entfernen, damit das Programm schneller ist
            Vector<Byte> mesh = new Vector<>();
            mesh.add((byte) 0);
            LinkedList<Byte> quadrantNumbers = currentQuadrant.getNumbers();
            quadrantNumbers.removeAll(mesh);

            System.out.println(quadrantNumbers);
            //Wir gehen die 3 Reihen durch
            for(int rowID = 0; rowID < 3; rowID++)
            {
                System.out.println(currentQuadrant.rows[rowID]);
                //Wir gehen die 3 Felder einer Reiher durch
                for(int numberFieldID = 0; numberFieldID < 3; numberFieldID++)
                {
                    NumberField currentNumberfield = currentQuadrant.rows[rowID].numberFields[numberFieldID];
                    //Zahlen, die bereits im Quadranten sind, aus der Anzahl der möglichen Zahlen entfernen
                    currentNumberfield.getPossibleNumbers().removeAll(quadrantNumbers);
                    //System.out.println(currentQuadrant.rows[rowID].numberFields[numberFieldID].getPossibleNumbers().toString());

                    //TODO Die Zahlen, die ich in der Reihe ermittelt habe, kann ich sofort auf die anderen numberFields der Reihe anwenden --> Geht schneller (Nur vielleicht umsetzen)
                    //TODO Code verkleinern, indem ich den in mehrere Methoden aufsplitte hier unten
                    switch(quadrantID){
                        case 0:
                            LinkedList<Byte> rowNumbers = new LinkedList<>();

                            //Die beiden Quadranten rechts ansprechen und Werte holen
                            for(int offset = 1;offset<3;offset++)
                            {
                                //Die Numberfields der Reihen durchgehen und Werte sammeln
                                for(NumberField num : quadrants[quadrantID+offset].rows[rowID].getNumbers())
                                {
                                    byte number = num.getCorrectNumber();
                                    if(number != 0) rowNumbers.add(number);
                                }
                            }
                            currentNumberfield.getPossibleNumbers().removeAll(rowNumbers);

                            LinkedList<Byte> columnNumbers = new LinkedList<>();

                            //Die beiden Quadranten unten ansprechen und Werte holen
                            for(int offset = 3;offset<7;offset += 3)
                            {
                                //Die Numberfields der Spalten durchgehen und Werte sammeln
                                for(NumberField num : quadrants[quadrantID+offset].columns[numberFieldID].getNumbers())
                                {
                                    byte number = num.getCorrectNumber();
                                    if(number != 0) columnNumbers.add(number);
                                }
                            }
                            currentNumberfield.getPossibleNumbers().removeAll(columnNumbers);

                            //Zahlen aus der Reihe holen und entfernen --> Reihe ermittelt durch die rowID (Erledigt)
                            //Zahlen aus der Spalte holen und entfernen --> Spalte ermitteln anhand der numberFieldID (Erledigt)
                        case 1:
                        case 2:
                        case 3: //Gleich
                        case 4:
                        case 5:
                        case 6: //Gleich
                        case 7:
                        case 8:
                    }
                    currentNumberfield.checkForCorrectNumber();
                    System.out.println(currentQuadrant.rows[rowID].numberFields[numberFieldID].getPossibleNumbers().toString());
                }
            }
            System.out.println();
        }
    }
}
