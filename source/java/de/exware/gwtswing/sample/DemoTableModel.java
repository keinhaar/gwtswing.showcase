package de.exware.gwtswing.sample;

import de.exware.gwtswing.swing.table.GAbstractTableModel;

public class DemoTableModel extends GAbstractTableModel
{
    private Object[][] data = 
    {
        {"Mr.", "Max", "Master", 44}
        , {"Mr.", "Max", "Miller", 32}
        , {"Mr.", "Will", "Johnson", 55}
//        , {"Mr.", "Carl", "Meyer", 24}
//        , {"Mr.", "Joe", "Meyer", 26}
//        , {"Mrs.", "Claire", "Smith", 26}
//        , {"Mrs.", "Joanne", "Carlson", 44}
//        , {"Mrs.", "Elaine", "Summers", 58}
//        , {"Mrs.", "Henrietta", "Borgers", 20}
    };
    
    public DemoTableModel()
    {
    }
    
    @Override
    public int getColumnCount()
    {
        return 4;
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
