package com.example.buensabor.Services.PdfService.utils;

import com.example.buensabor.Services.PdfService.PdfBillConfig;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

public class FooterHelper extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document)
    {
        Rectangle rect = writer.getBoxSize("billing_doc");
        Phrase footer_phrase = new Phrase("FES (c), 2023", PdfBillConfig.SUBTITLE_FONT);

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER,
                footer_phrase,
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 15, 0);

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER,
                new Phrase(String.format("Page %d", writer.getPageNumber()), PdfBillConfig.SUBTITLE_FONT),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 28, 0);

        super.onEndPage(writer, document);
    }
}
