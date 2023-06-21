package com.example.buensabor.Services.PdfService;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class PdfBillConfig {
    public static final int MIN_CELL_HEIGHT = 20;
    public static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, new BaseColor(23, 54, 93));
    public static final Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, new BaseColor(90, 90, 90));
    public static final Font TABLE_HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    public static final Font TABLE_CONTENT_FONT_1 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
    public static final Font TABLE_CONTENT_FONT_2 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    public static final String CURRENCY_SYMBOL = "$";
}
