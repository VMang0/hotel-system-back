package com.example.hotel.configuratoins;

import com.example.hotel.models.Reservation;
import com.example.hotel.repositories.ReservationRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class Doc {

    BaseFont bf;
    Font f_title;
    Font f_text;
    Font f_cost;

    public ResponseEntity<?> CreatePDF(Reservation reservation) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        setFont();
        Header(document, reservation);
        document.close();
        String folderPath = "D:/6_sem/kursach_prog/договоры/";
        String index = String.valueOf(reservation.getId());
        String fileName = "contract" + index + ".pdf";
        Path path = Paths.get(folderPath + fileName);
        Files.write(path, baos.toByteArray());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void Header(Document document, Reservation reservation) throws DocumentException {
        LocalDate date = LocalDate.now();
        String datenow = String.valueOf(date);
        String header = "ДОГОВОР БРОНИРОВАНИЯ" + "\n\n" +
                datenow + "                                                                                               "
                +"г.Минск ул.Белецкого-50" + "\n\n" +
                "Индивидуальный предприниматель Королькова Валерия Серргеевна, действующий на "+
                "основании ОГРНИП 311169009400102 далее «ГОСТИНИЦА», и \n" +
                "ФИО гостя: " + reservation.getLastname() + " " + reservation.getName() + " " + reservation.getPatronymic() + "\n" +
                "Паспорт гостя: серия ___________________ №__________________ Выдан ____________\n" +
                "____________________________________________________________________________\n" +
                "Адрес постоянного места жительства гостя:_______________________________________\n" +
                "____________________________________________________________________________\n" +
                "Номер телефона гостя: " + reservation.getPhoneNumber() + "\n" +
                "именуемая(ый) в дальнейшем «ГОСТЬ», с другой стороны, заключили настоящий договор\n" +
                "о нижеследующем: \n\n\n" +
                "1. ПРЕДМЕТ ДОГОВОРА.\n" +
                "1.1. Бронирование - услуга, предварительное резервирование номера (номеров) в Гостинице на\n" +
                "определенный период проживания. Бронирование может осуществляться на различных условиях, в\n" +
                "зависимости от действующего тарифа на выбранный номер и на запрашиваемый период проживания, при условии наличия номерного фонда.\n" +
                "1.2. Гарантией бронирования номера является оплата общей стоимости проживания. В случае непоступления предоплаты бронирование не может быть осуществлено.\n" +
                "2. ПРАВА И ОБЯЗАННОСТИ ГОСТЯ.\n" +
                "2.1. Гость обязуется производить оплату в течении 24 часов с момента согласования заявки. " +
                "Основанием для внесения оплаты за услугу бронирования является заявка, оформленная через сайт.\n" +
                "2.2. Гость имеет право отменить услугу бронирования, сообщив об этом в Гостиницу.\n" +
                "2.3. В случае изменения цен, оплаченные Гостем путевки переоценке, не подлежат.\n" +
                "2.4. Внесением оплаты Гость дает свое согласие с условиями договора бронирования.\n" +
                "3. ПРАВА И ОБЯЗАННОСТИ ГОСТИНИЦЫ.\n" +
                "3.1. В случае невнесения оплаты за бронирование по истечению 24 часов с момента " +
                "согласования заявки, Гостиница вправе заявку аннулировать, и сдать в аренду номерной фонд по" +
                "своему усмотрению.\n" +
                "3.2. При условии своевременного внесения оплаты, Гостиница обязана предоставить выбранный" +
                "Гостем номер на запрашиваемый период проживания.\n";
        document.add(new Paragraph(header, f_text));
    }

    public void setFont(){
        try {
            bf = BaseFont.createFont("./src/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            f_title = new Font(bf, 14);
            f_text = new Font(bf);
            f_cost = new Font(bf,16, Font.BOLD, BaseColor.BLACK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
