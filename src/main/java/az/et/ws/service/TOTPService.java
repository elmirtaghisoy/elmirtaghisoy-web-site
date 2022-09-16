package az.et.ws.service;

import az.et.ws.component.entity.AppUserEntity;
import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.request.QRLoginRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.repository.postgres.AppUserRepository;
import az.et.ws.security.JwtUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class TOTPService {

    private final GoogleAuthenticator googleAuthenticator;
    private final AppUserRepository appUserRepository;

    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @SneakyThrows
    public void generateQRAuthenticator(HttpServletResponse response) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("et-website", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    public AuthResponse loginWithQr(QRLoginRequest qrLoginRequest) {
        if (googleAuthenticator.authorizeUser(qrLoginRequest.getEmail(), qrLoginRequest.getGoogleCode())) {
            AppUser appUser = objectMapper.generateAppUser(appUserRepository.findByEmail(qrLoginRequest.getEmail()));
            return jwtUtil.createTokenAndSession(appUser);
        }
        return null;
    }
}
