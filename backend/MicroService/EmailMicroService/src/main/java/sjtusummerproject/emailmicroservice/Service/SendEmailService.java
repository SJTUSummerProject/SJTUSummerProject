package sjtusummerproject.emailmicroservice.Service;

public interface SendEmailService {
    public boolean sendMail(String to, String code);
}
