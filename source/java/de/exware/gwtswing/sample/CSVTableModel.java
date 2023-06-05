package de.exware.gwtswing.sample;

import de.exware.gwtswing.swing.table.GAbstractTableModel;

public class CSVTableModel extends GAbstractTableModel
{
    private String[] columnNames; 
    private Object[][] data; 
    
    public CSVTableModel(String csvdata)
    {
        String[] lines = csvdata.split("\n");
        for(int i=0;i<lines.length;i++)
        {
            String[] colums = lines[i].trim().split(";");
            if(i==0)
            {
                data = new Object[lines.length-1][colums.length];
                columnNames = colums;
            }
            else
            {
                for(int c=0;c<colums.length;c++)
                {
                    data[i-1][c] = colums[c].trim();
                }
            }
        }
    }
    
    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }
    
    @Override
    public int getColumnCount()
    {
        return data[0].length;
    }

    @Override
    public int getRowCount()
    {
        return data.length;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        return data[row][column];
    }

}
