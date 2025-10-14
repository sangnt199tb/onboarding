package com.example.onboarding.presentation.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import org.apache.commons.lang3.StringUtils;

public class PDFUtils {
    public static PdfPCell createCell(int row, int col){
        PdfPCell cell = new PdfPCell();
        cell.setRowspan(row);
        cell.setColspan(col);
        return cell;
    }

    public static Paragraph createParagraph(String text, Font font, int align){
        Paragraph paragraph = new Paragraph(StringUtils.isBlank(text) ? "" : text, font);
        paragraph.setAlignment(align);
        return paragraph;
    }

    public static Image createImg(String url, float with) throws Exception{
        Image image = Image.getInstance(url);
        image.scaleAbsolute(with, with);
        return image;
    }


    public static PdfPCell createCellText(int row, int col, String text, Font font, int[] disableBorder){
        PdfPCell cell = createCell(row, col);
        cell.addElement(createParagraph(text, font, Element.ALIGN_LEFT));
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(14f);
        for (int i : disableBorder) {
            if(Rectangle.NO_BORDER == i){
                cell.setBorder(i);
                break;
            }
            if(Rectangle.BOX != i){
                cell.disableBorderSide(i);
            }
        }
        return cell;
    }

    public static PdfPCell createCellRight(int row, int col, String text, Font font, int[] disableBorder){
        PdfPCell cell = createCell(row, col);
        cell.addElement(createParagraph(text, font, Element.ALIGN_RIGHT));
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(14f);
        for (int i : disableBorder) {
            if(Rectangle.NO_BORDER == i){
                cell.setBorder(i);
                break;
            }
            if(Rectangle.BOX != i){
                cell.disableBorderSide(i);
            }
        }
        return cell;
    }


    public static PdfPCell createCellCenter(int row, int col, String text, Font font, int[] disableBorder){
        PdfPCell cell = createCell(row, col);
        cell.addElement(createParagraph(text, font, Element.ALIGN_CENTER));
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(14f);
        for (int i : disableBorder) {
            if(Rectangle.NO_BORDER == i){
                cell.setBorder(i);
                break;
            }
            if(Rectangle.BOX != i){
                cell.disableBorderSide(i);
            }
        }
        return cell;
    }


}
