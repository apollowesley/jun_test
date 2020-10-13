package boot.spring.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class ReadExcel {

    //判断指定的单元格是否是合并单元格
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    // 获取合并单元格的值
    public static CellRegion getMergedRegionValue(Sheet sheet, int row, int column) {
        CellRegion r = new CellRegion();
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    r.startrownum = firstRow;
                    r.endrownum = lastRow;
                    r.value = getCellValue(fCell);
                    return r;
                }
            }
        }

        return null;
    }


    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        return "";
    }

    //求单元格或者合并单元格的高度
    public static int getheight(Sheet sheet, int rownum, int colnum) {
        if (isMergedRegion(sheet, rownum, colnum)) {
            CellRegion r = getMergedRegionValue(sheet, rownum, colnum);
            return r.endrownum - r.startrownum + 1;
        } else
            return 1;
    }

    //获取合并或未合并的任意单元格
    public static String getvalue(Sheet sheet, int rownum, int colnum) {
        if (isMergedRegion(sheet, rownum, colnum)) {
            CellRegion c = getMergedRegionValue(sheet, rownum, colnum);
            return c.value;
        } else {
            Row row = sheet.getRow(rownum);
            Cell cell = row.getCell(colnum);
            return getCellValue(cell);
        }
    }
}
