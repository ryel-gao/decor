package com.bluemobi.decor.utils.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by reny on 2015/8/28.
 */
public class ExcelUtil {

    public static String getFileName(MultipartFile file) {
        return (String) file.getOriginalFilename();
    }

    /**
     * 读取excel数据
     *
     * @param sheetIndex    sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine      去除最后读取的行
     * @param file
     */
    public List<Map<String, Object>> readExcelToObj(File file, int sheetIndex, int startReadLine, int tailLine) {

        //excle的所有值
        List<Map<String, Object>> excleMaps = new ArrayList<Map<String, Object>>();

        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(file);

            Sheet sheet = wb.getSheetAt(sheetIndex);
            Row row = null;

            for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    int index = 0;
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (Cell c : row) {
                        c.setCellType(Cell.CELL_TYPE_STRING);

                        if (i != startReadLine) {
                            if (excleMaps == null) {
                                return null;
                            }
                            Map<String, Object> keyMaps = excleMaps.get(0);
                            if (keyMaps.size() - 1 < index) {
                                return null;
                            }
                            map.put(keyMaps.get(index + "").toString(), c.getRichStringCellValue().toString());
                        } else {
                            map.put(index + "", c.getRichStringCellValue().toString());
                        }
                        index++;
                    }
                    excleMaps.add(map);
                    index = 0;
                } else {
                    return null;
                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excleMaps;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell) {

        if (cell == null) return "";

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

            return cell.getStringCellValue();

        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }

}
