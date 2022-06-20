package by.it.academy.user_service.services.api;

public interface IRequestService {

    /**
     * Sending post request to the mail-service for email confirmation
     * @param email username like email
     * @param name user's name
     * @param code verify code. It is used uuid
     */
    void sendVerifyCode(String email, String name, String code);

}
