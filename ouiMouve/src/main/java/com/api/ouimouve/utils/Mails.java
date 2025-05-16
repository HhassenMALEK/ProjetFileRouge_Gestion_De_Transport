package com.api.ouimouve.utils;

import lombok.Data;

/**
 * Class for gestion fo mails
 */
@Data
public class Mails {

    private String user;
    private String mail;
    private String message;

    /**
     * Method to send a mail when there a re a reparation to a vehicle who's reserved but there are a reparation on it
     * @param user user
     * @param body body of the message
     * @return
     */
    public static  String sendMAilCarpoolingIsCanceledForReparation(String user,  String body){

        return ("Reservation Cancelled, because this vehicule is entrance in repair");
    }
}
