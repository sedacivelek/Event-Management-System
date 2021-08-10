package yte.intern.application.usecases.manageevents.qrcodegen;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.intern.application.usecases.manageevents.entity.Participant;

import java.nio.file.FileSystems;
import java.nio.file.Path;
@NoArgsConstructor
@Getter
public class QrCodeGenerator {
    private static String qrcodePath = "/home/sedacivelek/Desktop/app/src/main/resources/";

    public String writeQRCode(Participant participant,String eventId) throws Exception {
        String qrcode = qrcodePath + participant.getParticipantName() + eventId + "-QRCODE.png";
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(
                participant.getEmail() + "\n" + participant.getParticipantName() + "\n"
                        + participant.getNameAndSurname() + "\n" + participant.getTckn()+ "\n" + eventId,
                BarcodeFormat.QR_CODE, 350, 350);
        Path path = FileSystems.getDefault().getPath(qrcode);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return qrcode;
    }

}
